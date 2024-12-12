package com.laptop.servlet.client;

import com.laptop.models.Category;
import com.laptop.models.Promotion;
import com.laptop.service.CategoryService;
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

@WebServlet(name = "PromotionServlet", value = "/promotions")
public class PromotionServlet extends HttpServlet {

    private final PromotionService promotionService = new PromotionService();

    private static final int CATEGORIES_PER_PAGE = 6;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tổng số quảng cáo từ PromotionService
        int totalPromotions = promotionService.countPromotions();
        int totalPages = totalPromotions / CATEGORIES_PER_PAGE + (totalPromotions % CATEGORIES_PER_PAGE != 0 ? 1 : 0);

        String pageParam = Optional.ofNullable(request.getParameter("page")).orElse("1");
        int page = Protector.of(() -> Integer.parseInt(pageParam)).get(1);
        if (page < 1 || page > totalPages) {
            page = 1;
        }

        int offset = (page - 1) * CATEGORIES_PER_PAGE;

        // Lấy danh sách quảng cáo theo phân trang
        List<Promotion> promotions = Protector.of(() -> promotionService.getOrderedPart(
                CATEGORIES_PER_PAGE, offset, "id", "DESC"
        )).get(ArrayList::new);
        List<Category> categories = Protector.of(() -> new CategoryService().getPart(12, 0))
                .get(ArrayList::new);

        // Gửi dữ liệu tới JSP
        request.setAttribute("categories", categories);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", page);
        request.setAttribute("promotions", promotions);
        request.setAttribute("totalPromotions", totalPromotions); // Đặt tổng số quảng cáo vào request

        // Forward tới trang JSP hiển thị quảng cáo
        request.getRequestDispatcher("/WEB-INF/views/promotionView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}