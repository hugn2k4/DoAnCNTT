package com.laptop.servlet.client;

import com.laptop.models.Category;
import com.laptop.models.User;
import com.laptop.service.CartService;
import com.laptop.service.CategoryService;
import com.laptop.utils.Protector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {

    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user != null) {
            int countCartItemQuantityByUserId = cartService.countCartItemQuantityByUserId(user.getId());
            request.setAttribute("countCartItemQuantity", countCartItemQuantityByUserId);

            int countOrderByUserId = cartService.countOrderByUserId(user.getId());
            request.setAttribute("countOrder", countOrderByUserId);

            int countOrderDeliverByUserId = cartService.countOrderDeliverByUserId(user.getId());
            request.setAttribute("countOrderDeliver", countOrderDeliverByUserId);

            int countOrderReceivedByUserId = cartService.countOrderReceivedByUserId(user.getId());
            request.setAttribute("countOrderReceived", countOrderReceivedByUserId);

            List<Category> categories = Protector.of(() -> new CategoryService().getPart(12, 0))
                    .get(ArrayList::new);
            request.setAttribute("categories", categories);
        }

        request.getRequestDispatcher("/WEB-INF/views/userView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}
