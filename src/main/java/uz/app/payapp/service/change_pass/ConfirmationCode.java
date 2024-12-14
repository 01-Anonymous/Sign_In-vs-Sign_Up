package uz.app.payapp.service.change_pass;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.db.ConfirmationCodeDAO;
import uz.app.payapp.db.UserDAO;
import uz.app.payapp.service.authorization.EmailService;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebServlet("/confirmation-code")
public class ConfirmationCode extends HttpServlet {
    private final ScheduledExecutorService schedule = Executors
            .newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        System.out.println("=========================================================" + email);
        if (! UserDAO.checkUserByEmail(email)) {
            req.setAttribute("message","Email topilmadi");
            req.getRequestDispatcher("/view/error/authorizationError.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("email", email);

        String code = EmailService.getConfirmationCode(email);

        schedule.schedule(() -> ConfirmationCodeDAO.deleteConfirmationCode(email, code), 1, TimeUnit.MINUTES);
        req.getRequestDispatcher("/view/change_pass/confirmation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        req.setAttribute("email", email);
        String confirmationCode = ConfirmationCodeDAO.getConfirmationCode(email);
        if (code.equals(confirmationCode)) {
            req.getRequestDispatcher("/view/change_pass/reset-pass.jsp").forward(req, resp);
        }else {
            req.setAttribute("errorMessage","Xato kod kiritildi");
            req.getRequestDispatcher("/view/change_pass/confirmation.jsp").forward(req, resp);
        }

    }

    @Override
    public void destroy() {
        schedule.shutdownNow();
        super.destroy();
    }
}
