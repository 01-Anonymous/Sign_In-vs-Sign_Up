package uz.app.payapp.service.card;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.db.Database;
import uz.app.payapp.entity.Card;
import uz.app.payapp.entity.History;
import uz.app.payapp.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/buy-dollar")
public class BuyDollar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = Database.getInstance().getEntityManager();
        User user = (User) req.getSession().getAttribute("user");
        List<Card> cards = new ArrayList<>();
        String amountParam = req.getParameter("amount");

        try {
            em.getTransaction().begin();
            cards = em.createNamedQuery("getAllCards", Card.class)
                    .setParameter("user_id", user.getId()).getResultList();
        }finally {
            em.getTransaction().commit();
        }
        req.setAttribute("cards", cards);
        req.setAttribute("from", req.getParameter("number"));

        double dollar = 0.0;
        double amount = 0.0;
        if (amountParam != null && !amountParam.trim().isEmpty()) {
            amount = Double.parseDouble(amountParam);
            dollar = amount / 12766.06;
        } else {
            req.setAttribute("message", "12766.06 so'm -> 1 $");
            req.getRequestDispatcher("/view/card/buy-dollar.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("message","%,.2f so'm -> %,.2f $".formatted(amount,dollar));
        req.getRequestDispatcher("/view/card/buy-dollar.jsp").forward(req, resp);
    }

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
                    .setType("Transfer $")
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
}
