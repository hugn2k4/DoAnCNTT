package com.laptop.servlet.admin.promotion;

import com.laptop.models.Category;
import com.laptop.models.Promotion;
import com.laptop.service.CategoryService;
import com.laptop.service.PromotionService;
import com.laptop.utils.ImageUtils;
import com.laptop.utils.Protector;
import com.laptop.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CreatePromotionServlet", value = "/admin/promotionManager/create")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, // 5 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class CreatePromotionServlet extends HttpServlet {
    private final PromotionService promotionService = new PromotionService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/createPromotionView.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Promotion promotion = new Promotion();
        promotion.setName(request.getParameter("name"));
        promotion.setDescription(request.getParameter("description").trim().isEmpty()
                ? null : request.getParameter("description"));

        promotion.setStartsAt(request.getParameter("startsAt").trim().isEmpty()
                ? null : LocalDateTime.parse(request.getParameter("startsAt")));
        promotion.setEndsAt(request.getParameter("endsAt").trim().isEmpty()
                ? null : LocalDateTime.parse(request.getParameter("endsAt")));

        Map<String, List<String>> violations = new HashMap<>();
        violations.put("nameViolations", Validator.of(promotion.getName())
                .isNotNullAndEmpty()
                .isNotBlankAtBothEnds()
                .isAtMostOfLength(100)
                .toList());
        violations.put("descriptionViolations", Validator.of(promotion.getDescription())
                .isAtMostOfLength(350)
                .toList());

        int sumOfViolations = violations.values().stream().mapToInt(List::size).sum();
        String successMessage = "Thêm thành công!";
        String errorMessage = "Thêm thất bại!";

        if (sumOfViolations == 0) {
            ImageUtils.upload(request).ifPresent(promotion::setImageName);
            Protector.of(() -> promotionService.insert(promotion))
                    .done(r -> request.setAttribute("successMessage", successMessage))
                    .fail(e -> {
                        request.setAttribute("promotion", promotion);
                        request.setAttribute("errorMessage", errorMessage);
                    });
        } else {
            request.setAttribute("promotion", promotion);
            request.setAttribute("violations", violations);
        }

        request.getRequestDispatcher("/WEB-INF/views/createPromotionView.jsp").forward(request, response);
    }
}
