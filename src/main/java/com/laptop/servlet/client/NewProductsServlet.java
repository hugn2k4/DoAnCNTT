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

@WebServlet(name = "NewProductsServlet", value = "/new-products")
public class NewProductsServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    private static final int PRODUCTS_PER_PAGE = 6; // Số sản phẩm trên mỗi trang

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tính tổng số sản phẩm mới
        int totalNewProducts = Optional.ofNullable(productService.countProductsMax1Page()).orElse(0);

        // Tính tổng số trang
        int totalPages = totalNewProducts / PRODUCTS_PER_PAGE;
        if (totalNewProducts % PRODUCTS_PER_PAGE != 0) {
            totalPages++;
        }

        // Lấy trang hiện tại
        String pageParam = Optional.ofNullable(request.getParameter("page")).orElse("1");
        int page = Integer.parseInt(pageParam);
        if (page < 1 || page > totalPages) {
            page = 1;
        }

        // Tính mốc truy vấn (offset)
        int offset = (page - 1) * PRODUCTS_PER_PAGE;

        // Lấy danh sách sản phẩm mới theo trang
        List<Product> newProducts = Optional.ofNullable(
                productService.getNewProducts(PRODUCTS_PER_PAGE, offset)
        ).orElse(new ArrayList<>());
        List<Category> categories = Protector.of(() -> new CategoryService().getPart(12, 0))
                .get(ArrayList::new);

        // Gửi dữ liệu tới JSP
        request.setAttribute("categories", categories);
        request.setAttribute("totalNewProducts", totalNewProducts);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", page);
        request.setAttribute("newProducts", newProducts);

        // Forward tới trang JSP hiển thị sản phẩm mới
        request.getRequestDispatcher("/WEB-INF/views/newProductsView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
