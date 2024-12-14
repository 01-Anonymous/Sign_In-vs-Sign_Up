package uz.app.payapp.service.change_pass;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.db.UserDAO;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/change-pass")
public class ChangePass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("email", req.getParameter("email"));
        req.getRequestDispatcher("/view/change_pass/send-code.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("newPassword");
        boolean validEmail = isValidEmail(password);
        if (validEmail) {
            UserDAO.resetPass(email, password);
            req.getRequestDispatcher("/view/authorization/signIn.jsp").forward(req, resp);
        }else {
            req.setAttribute("email", email);
            req.setAttribute("errorMessage", "Uzunligi 8 dan oshsin kamida bitta son bo'lsin");
            req.getRequestDispatcher("/view/change_pass/reset-pass.jsp").forward(req, resp);
        }
    }

    private boolean isValidEmail(String code) {
        String regex = "^(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }
}
