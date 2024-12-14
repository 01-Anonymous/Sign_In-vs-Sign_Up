package uz.app.payapp.service.authorization.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.db.ConfirmationCodeDAO;

import uz.app.payapp.db.UserDAO;
import uz.app.payapp.entity.User;
import uz.app.payapp.service.authorization.EmailService;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebServlet("/sign-up")
public class SingUp extends HttpServlet {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/authorization/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        if (UserDAO.checkUserByEmail(email)) {
            req.setAttribute("message", "Email tizimda mavjud");
            req.getRequestDispatcher("/view/error/authorizationError.jsp").forward(req, resp);
            return;
        }
        if (UserDAO.checkUserByPhone(phone)) {
            req.setAttribute("message", "Telefon raqam tizimda mavjud");
            req.getRequestDispatcher("/view/error/authorizationError.jsp").forward(req, resp);
            return;
        }
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phone(phone)
                .role("USER")
                .build();

        req.getSession().setAttribute("userSignUp", user);

        String code = EmailService.getConfirmationCode(email);

        scheduler.schedule(() -> ConfirmationCodeDAO.deleteConfirmationCode(email, code), 1, TimeUnit.MINUTES);

        req.getSession().setAttribute("email", email);
        resp.sendRedirect("/check-confirmation-code");
    }

    @Override
    public void destroy() {
        scheduler.shutdownNow();
        super.destroy();
    }
}










