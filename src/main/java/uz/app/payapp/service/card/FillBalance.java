package uz.app.payapp.service.card;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.db.Database;
import uz.app.payapp.entity.Card;
import uz.app.payapp.entity.History;

import java.io.IOException;

@WebServlet("/fill-balance")
public class FillBalance extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = Database.getInstance().getEntityManager();
        String number = req.getParameter("number");
        Card card;
        try {
            em.getTransaction().begin();
            card = em.createNamedQuery("byCardNumber",Card.class)
                    .setParameter("number", number).getSingleResult();
        }finally {
                    em.getTransaction().commit();
        }
        if(card != null){
            if(card.getType().equals("HUMO") || card.getType().equals("UZCARD")){
                req.setAttribute("number", number);
                req.getRequestDispatcher("/view/card/fill-balance.jsp").forward(req, resp);
            }else {
                req.setAttribute("message", "Bu kartada amaliyot mavjud emas");
                req.getRequestDispatcher("/view/error/error.jsp").forward(req, resp);
            }
        }
        resp.sendRedirect("/welcome");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter("number");
        Double fill = Double.parseDouble(req.getParameter("fill"));
        if(fill <= 0){
            resp.sendRedirect("/profile");
            return;
        }
        EntityManager manager = Database.getInstance().getEntityManager();
        try {
            manager.getTransaction().begin();
            Query query = manager.createNamedQuery("byCardNumber").setParameter("number", number);
            Card card = (Card) query.getSingleResult();
            if(!card.getIsActive()){
                req.setAttribute("message","Karta bloklangan");
                req.getRequestDispatcher("/view/error/error.jsp").forward(req, resp);
                return;
            }
            Double balance = card.getBalance() + fill;
            manager.createNamedQuery("updateBalance")
                    .setParameter("balance", balance)
                    .setParameter("number", number).executeUpdate();
            History history = History
                    .builder()
                    .setFrom("ATM")
                    .setTo(card.getNumber())
                    .setType("Fill balance")
                    .amount(fill)
                    .user(card.getUser())
                    .build();
            manager.persist(history);
        } finally {
            manager.getTransaction().commit();
        }
        resp.sendRedirect("/profile");
    }
}
