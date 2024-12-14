package uz.app.payapp.service.card;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import uz.app.payapp.db.Database;
import uz.app.payapp.entity.Card;
import uz.app.payapp.entity.History;
import uz.app.payapp.entity.User;
import uz.app.payapp.util.CardType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = Database.getInstance().getEntityManager();
        User user = (User) req.getSession().getAttribute("user");
        List<Card> cards = new ArrayList<>();
        try {
            em.getTransaction().begin();
            cards = em.createNamedQuery("getAllCards", Card.class)
                    .setParameter("user_id", user.getId()).getResultList();
        }finally {
            em.getTransaction().commit();
        }
        req.setAttribute("cards", cards);
        req.setAttribute("from", req.getParameter("number"));
        req.getRequestDispatcher("/view/card/transfer.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String from = req.getParameter("from");
        String to = req.getParameter("to");

        if (from == null || to == null || from.equals(to)) {
            resp.sendRedirect("/welcome");
            return;
        }
        Double amount = Double.valueOf(req.getParameter("amount"));

        EntityManager manager = Database.getInstance().getEntityManager();
        try {
            manager.getTransaction().begin();

            Card fromCard = (Card) manager.createNamedQuery("byCardNumber")
                    .setParameter("number", from)
                    .getSingleResult();

            Card toCard = (Card) manager.createNamedQuery("byCardNumber")
                    .setParameter("number", to)
                    .getSingleResult();

            if (!TransferServlet.checkTypes(fromCard, toCard)) {
                req.setAttribute("message","Bu kartaga pul o'tkazish mumkin emas");
                req.getRequestDispatcher("/view/error/error.jsp").forward(req, resp);
                return;
            }
            if(amount > fromCard.getBalance()){
                req.setAttribute("message","Mablag' yetarli emas");
                req.getRequestDispatcher("/view/error/error.jsp").forward(req, resp);
                return;
            }

            manager.createNamedQuery("updateBalance")
                    .setParameter("number", from)
                    .setParameter("balance", fromCard.getBalance() - amount)
                    .executeUpdate();

            manager.createNamedQuery("updateBalance")
                    .setParameter("number", to)
                    .setParameter("balance", toCard.getBalance() + amount)
                    .executeUpdate();

            History transfer = History.builder()
                    .setType("Transfer")
                    .setFrom(from)
                    .setTo(to)
                    .amount(amount)
                    .user(user)
                    .build();
            manager.persist(transfer);

        } finally {
            manager.getTransaction().commit();
            resp.sendRedirect("/welcome");
        }
    }

    public static boolean checkTypes(Card from, Card to) {
        if (from.getType().equals(CardType.HUMO.name()) || from.getType().equals(CardType.UZCARD.name())) {
            return to.getType().equals(CardType.UZCARD.name()) || to.getType().equals(CardType.HUMO.name());
        }else {
            return to.getType().equals(CardType.VISA.name())|| to.getType().equals(CardType.MASTERCARD.name());
        }
    }

}

