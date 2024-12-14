package uz.app.payapp.service.authorization.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.db.ConfirmationCodeDAO;
import uz.app.payapp.db.Database;
import uz.app.payapp.entity.User;

import java.io.IOException;

@WebServlet("/check-confirmation-code")
public class CheckConfirmationCode extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("email", req.getSession().getAttribute("email"));
        req.getRequestDispatcher("/view/authorization/confirmation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("userSignUp");

        String email = req.getParameter("email");
        String code = req.getParameter("code");

        String confirmationCode = ConfirmationCodeDAO.getConfirmationCode(email);

        if (code.equals(confirmationCode)) {
            EntityManager em = Database.getInstance().getEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(user);
            }finally {
                em.getTransaction().commit();
            }
            req.getRequestDispatcher("/view/authorization/signIn.jsp").forward(req, resp);
        }else{
            req.setAttribute("errorMessage", "Xato kod kiritildi");
            req.getRequestDispatcher("/view/authorization/confirmation.jsp").forward(req, resp);
        }
    }
}
