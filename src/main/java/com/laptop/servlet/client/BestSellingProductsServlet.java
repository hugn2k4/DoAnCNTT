package com.laptop.servlet.client;

import com.laptop.models.Category;
import com.laptop.models.Product;
import com.laptop.service.CategoryService;
import com.laptop.service.ProductService;
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

@WebServlet(name = "BestSellingProductsServlet", value = "/best-selling")
public class BestSellingProductsServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    private static final int PRODUCTS_PER_PAGE = 3; // Số sản phẩm trên mỗi trang

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách 3 sản phẩm bán chạy
        List<Product> bestSellingProducts = Optional.ofNullable(
                productService.getBestSellingProducts(3, 0) // Lấy 3 sản phẩm đầu tiên
        ).orElse(new ArrayList<>());

        // Lấy danh sách các danh mục (optionally)
        List<Category> categories = Protector.of(() -> new CategoryService().getPart(12, 0))
                .get(ArrayList::new);

        // Gửi dữ liệu tới JSP
        request.setAttribute("categories", categories);

        request.setAttribute("bestSellingProducts", bestSellingProducts);

        // Forward tới trang JSP hiển thị sản phẩm bán chạy
        request.getRequestDispatcher("/WEB-INF/views/bestSellingProductsView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
