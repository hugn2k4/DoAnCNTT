package com.laptop.servlet.general;

import com.laptop.models.Category;
import com.laptop.models.Product;
import com.laptop.models.User;
import com.laptop.service.CategoryService;
import com.laptop.service.ProductService;
import com.laptop.service.UserService;
import com.laptop.utils.MailUtils;
import com.laptop.utils.Protector;
import com.laptop.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "RecieveNewsServlet" , value = "/news")

public class ReceiveNewsServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();
    private final ProductService productService = new ProductService();
    private final String subject = "CẢM ƠN QUÝ KHÁCH ĐÃ THAM GIA ĐĂNG KY NHẬN TIN";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> values = new HashMap<>();

        values.put("email", request.getParameter("email"));

        Map<String, List<String>> violations = new HashMap<>();

        violations.put("emailViolations", Validator.of(values.get("email"))
                .isNotNullAndEmpty() // Không được null hoặc rỗng
                .isNotBlankAtBothEnds() // Không chứa khoảng trắng ở hai đầu
                .hasPattern("^[^@]+@[^@]+\\.[^@]+$", "email") // Đúng định dạng email
                .toList());

        System.out.println(values.get("email"));
        if(violations.get("emailViolations").isEmpty()) {
            // Xây dựng nội dung email
            String emailContent = MailUtils.buildDetailEmail(values.get("email"));
            System.out.println();
            try {
                // Gửi email
                MailUtils.sendMail(values.get("email"),subject, emailContent);
                request.setAttribute("success", "Email đã được gửi thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Đã xảy ra lỗi khi gửi email.");
            }

        } else {
            // Xử lý khi có lỗi
            request.setAttribute("values", values);
            request.setAttribute("violations", violations);
        }

        doGet(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = Protector.of(() -> categoryService.getPart(12, 0))
                .get(ArrayList::new);
        List<Product> products = Protector.of(() -> productService.getOrderedPart(12, 0, "createdAt", "DESC"))
                .get(ArrayList::new);
        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/views/homeView.jsp").forward(request, response);
    }
}
