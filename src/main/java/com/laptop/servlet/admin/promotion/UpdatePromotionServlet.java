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
import java.util.Optional;

@WebServlet(name = "UpdatePromotionServlet", value = "/admin/promotionManager/update")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, // 5 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class UpdatePromotionServlet extends HttpServlet {
    private final PromotionService promotionService = new PromotionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Protector.of(() -> Long.parseLong(request.getParameter("id"))).get(0L);
        Optional<Promotion> promotionFromServer = Protector.of(() -> promotionService.getById(id)).get(Optional::empty);

        if (promotionFromServer.isPresent()) {
            request.setAttribute("promotion", promotionFromServer.get());
            request.getRequestDispatcher("/WEB-INF/views/updatePromotionView.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/promotionManager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Promotion promotion = new Promotion();
        promotion.setId(Protector.of(() -> Long.parseLong(request.getParameter("id"))).get(0L));
        promotion.setName(request.getParameter("name"));
        promotion.setDescription(request.getParameter("description").trim().isEmpty()
                ? null : request.getParameter("description"));
        promotion.setImageName(request.getParameter("imageName").trim().isEmpty()
                ? null : request.getParameter("imageName"));
        promotion.setStartsAt(request.getParameter("startsAt").trim().isEmpty()
                ? null : LocalDateTime.parse(request.getParameter("startsAt")));
        promotion.setEndsAt(request.getParameter("endsAt").trim().isEmpty()
                ? null : LocalDateTime.parse(request.getParameter("endsAt")));

        String deleteImage = request.getParameter("deleteImage");

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
        String successMessage = "Sửa thành công!";
        String errorMessage = "Sửa thất bại!";

        if (sumOfViolations == 0) {
            if (promotion.getImageName() != null) {
                String currentImageName = promotion.getImageName();
                if (deleteImage != null) {
                    ImageUtils.delete(currentImageName);
                    promotion.setImageName(null);
                }
                ImageUtils.upload(request).ifPresent(imageName -> {
                    ImageUtils.delete(currentImageName);
                    promotion.setImageName(imageName);
                });
            } else {
                ImageUtils.upload(request).ifPresent(promotion::setImageName);
            }
            Protector.of(() -> promotionService.update(promotion))
                    .done(r -> {
                        request.setAttribute("promotion", promotion);
                        request.setAttribute("successMessage", successMessage);
                    })
                    .fail(e -> {
                        request.setAttribute("promotion", promotion);
                        request.setAttribute("errorMessage", errorMessage);
                    });
        } else {
            request.setAttribute("promotion", promotion);
            request.setAttribute("violations", violations);
            request.setAttribute("deleteImage", deleteImage);
        }

        request.getRequestDispatcher("/WEB-INF/views/updatePromotionView.jsp").forward(request, response);
    }
}
