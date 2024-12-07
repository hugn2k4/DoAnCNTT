package com.laptop.servlet.admin.promotion;

import com.laptop.models.Category;
import com.laptop.models.Promotion;
import com.laptop.service.CategoryService;
import com.laptop.service.PromotionService;
import com.laptop.utils.Protector;
import com.laptop.utils.TextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "PromotionDetailServlet", value = "/admin/promotionManager/detail")

public class PromotionDetailServlet extends HttpServlet {
    private final PromotionService promotionService = new PromotionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Protector.of(() -> Long.parseLong(request.getParameter("id"))).get(0L);
        Optional<Promotion> promotionFromServer = Protector.of(() -> promotionService.getById(id)).get(Optional::empty);

        if (promotionFromServer.isPresent()) {
            Promotion promotion = promotionFromServer.get();
            promotion.setDescription(TextUtils.toParagraph(Optional.ofNullable(promotion.getDescription()).orElse("")));
            request.setAttribute("promotion", promotion);
            request.getRequestDispatcher("/WEB-INF/views/promotionDetailView.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/promotionManager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

}
