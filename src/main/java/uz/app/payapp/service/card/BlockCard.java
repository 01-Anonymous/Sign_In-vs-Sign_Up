package uz.app.payapp.service.card;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/block")
public class BlockCard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        boolean status = Boolean.parseBoolean(req.getParameter("status"));
        String method = req.getParameter("method");

        CardService cardService = new CardService();
        if (method.equals("block") && status) {
            resp.sendRedirect("/profile");
            cardService.blockUnblock(id,false);
        } else if (method.equals("unblock") && !status) {
            cardService.blockUnblock(id,true);
            resp.sendRedirect("/profile");
        }else if (method.equals("block") && !status) {
            req.setAttribute("message", "Karta avval bloklangan");
            req.getRequestDispatcher("/view/error/error.jsp").forward(req, resp);
        }else if (method.equals("unblock") && status) {
            req.setAttribute("message", "Karta bloklanmagan");
            req.getRequestDispatcher("/view/error/error.jsp").forward(req, resp);
        }

    }
}
