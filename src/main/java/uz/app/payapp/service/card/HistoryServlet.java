package uz.app.payapp.service.card;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.db.Database;
import uz.app.payapp.entity.History;
import uz.app.payapp.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = Database.getInstance().getEntityManager();
        User user = (User) req.getSession().getAttribute("user");
        List<History> history = new ArrayList<>();
        try {
            em.getTransaction().begin();
            history = em.createNamedQuery("getByUser",History.class)
                    .setParameter("id",user.getId())
                    .getResultList();
            req.setAttribute("history", history);
        }finally {
            em.getTransaction().commit();
        }
        req.getRequestDispatcher("/view/card/show-history.jsp").forward(req, resp);
    }
}
