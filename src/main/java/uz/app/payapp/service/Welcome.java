package uz.app.payapp.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.app.payapp.entity.Card;
import uz.app.payapp.entity.User;
import uz.app.payapp.service.card.CardService;

import java.io.IOException;
import java.util.List;

@WebServlet("/welcome")
public class Welcome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<Card> cards = new CardService().getCard(user.getId());
        Double balanceS = 0.0;
        Double balanceD = 0.0;
        for (Card card : cards) {
            if(card.getType().equals("HUMO") || card.getType().equals("UZCARD")) {
                balanceS += card.getBalance();
            }else {
                balanceD += card.getBalance();
            }
        }
        req.setAttribute("cards", cards);
        req.setAttribute("balanceS", balanceS);
        req.setAttribute("balanceD", balanceD);
        req.getRequestDispatcher("/view/welcome.jsp").forward(req, resp);
    }
}
