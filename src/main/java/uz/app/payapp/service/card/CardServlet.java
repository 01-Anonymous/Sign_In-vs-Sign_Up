package uz.app.payapp.service.card;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.entity.User;
import uz.app.payapp.util.CardType;

import java.io.IOException;

@WebServlet("/create-card")
public class CardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/sign-in");
            return;
        }
        req.setAttribute("user", user);
        req.setAttribute("types", CardType.values());
        req.getRequestDispatcher("/view/card/card.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String type = req.getParameter("type");
        String password = req.getParameter("password");


        if(user == null){
            resp.sendRedirect("/sign-in");
            return;
        }
        new CardService().addCard(user,password,type);
        resp.sendRedirect("/profile");
    }
}
