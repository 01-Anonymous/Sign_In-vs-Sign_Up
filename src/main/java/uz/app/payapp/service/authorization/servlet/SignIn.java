package uz.app.payapp.service.authorization.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.db.UserDAO;
import uz.app.payapp.entity.User;

import java.io.IOException;

import java.util.List;

@WebServlet("/sign-in")
public class SignIn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/authorization/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = UserDAO.getUsers();
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect("/welcome");
                return;
            }
        }
        req.setAttribute("message","Email yoki kod xato kiritildi");
        req.getRequestDispatcher("/view/error/authorizationError.jsp").forward(req, resp);
    }
}
