package com.laptop.servlet.admin.promotion;

import com.laptop.models.Category;
import com.laptop.models.ProductReview;
import com.laptop.models.Promotion;
import com.laptop.service.ProductService;
import com.laptop.service.PromotionService;
import com.laptop.utils.Protector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "PromotionManagerServlet", value = "/admin/promotionManager")
public class PromotionManagerServlet extends HttpServlet {
    private final PromotionService promotionService = new PromotionService();

    private static final int CATEGORIES_PER_PAGE = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int totalPromotions = Protector.of(promotionService::count).get(0);
        int totalPages = totalPromotions / CATEGORIES_PER_PAGE + (totalPromotions % CATEGORIES_PER_PAGE != 0 ? 1 : 0);

        String pageParam = Optional.ofNullable(request.getParameter("page")).orElse("1");
        int page = Protector.of(() -> Integer.parseInt(pageParam)).get(1);
        if (page < 1 || page > totalPages) {
            page = 1;
        }

        int offset = (page - 1) * CATEGORIES_PER_PAGE;

        List<Promotion> promotions = Protector.of(() -> promotionService.getOrderedPart(
                CATEGORIES_PER_PAGE, offset, "id", "DESC"
        )).get(ArrayList::new);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", page);
        request.setAttribute("promotions", promotions);
        request.getRequestDispatcher("/WEB-INF/views/promotionManagerView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}
