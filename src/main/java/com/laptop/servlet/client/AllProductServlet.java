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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "AllProductServlet", value = "/all-products")
public class AllProductServlet extends HttpServlet {
    private final ProductService productService = new ProductService();
    private static final int PRODUCTS_PER_PAGE = 100;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tiêu chí lọc: Khoảng giá, RAM, CPU, VGA
        List<String> priceRanges = Optional.ofNullable(request.getParameterValues("priceRanges"))
                .map(Arrays::asList).orElse(new ArrayList<>());
        List<String> checkedRams = Optional.ofNullable(request.getParameterValues("checkedRams"))
                .map(Arrays::asList).orElse(new ArrayList<>());
        List<String> checkedCpus = Optional.ofNullable(request.getParameterValues("checkedCpus"))
                .map(Arrays::asList).orElse(new ArrayList<>());
        List<String> checkedVgas = Optional.ofNullable(request.getParameterValues("checkedVgas"))
                .map(Arrays::asList).orElse(new ArrayList<>());

        // Sắp xếp
        String orderParam = Optional.ofNullable(request.getParameter("order")).orElse("totalBuy-DESC");
        String orderBy = productService.getFirst(orderParam);
        String orderDir = productService.getLast(orderParam);

        // Tạo chuỗi truy vấn lọc
        List<String> filters = new ArrayList<>();
        if (!priceRanges.isEmpty()) filters.add(productService.filterByPriceRanges(priceRanges));
        if (!checkedRams.isEmpty()) filters.add(productService.filterByRams(checkedRams));
        if (!checkedCpus.isEmpty()) filters.add(productService.filterByCpus(checkedCpus));
        if (!checkedVgas.isEmpty()) filters.add(productService.filterByVgas(checkedVgas));
        String filtersQuery = productService.createFiltersQuery(filters);

        // Tổng số sản phẩm phù hợp
        int totalProducts = filters.isEmpty()
                ? productService.count()
                : Protector.of(() -> productService.countByFilters(filtersQuery)).get(0);

        // Tính toán phân trang
        int totalPages = (int) Math.ceil((double) totalProducts / PRODUCTS_PER_PAGE);
        int currentPage = Optional.ofNullable(request.getParameter("page"))
                .map(Integer::parseInt).orElse(1);
        currentPage = Math.max(1, Math.min(currentPage, totalPages)); // Giới hạn trang trong khoảng hợp lệ
        int offset = (currentPage - 1) * PRODUCTS_PER_PAGE;

        // Lấy danh sách sản phẩm
        List<Product> products = filters.isEmpty()
                ? productService.getOrderedPart(PRODUCTS_PER_PAGE, offset, orderBy, orderDir)
                : productService.getOrderedPartByFilters(PRODUCTS_PER_PAGE, offset, orderBy, orderDir, filtersQuery);

        // Lấy danh sách thông số kỹ thuật (RAM, CPU, VGA)
        List<String> rams = productService.getRamsByCategoryId(0); // Không cần category ID
        List<String> cpus = productService.getCpusByCategoryId(0);
        List<String> vgas = productService.getVgasByCategoryId(0);
        List<Category> categories = Protector.of(() -> new CategoryService().getPart(12, 0))
                .get(ArrayList::new);

        // Gửi dữ liệu tới JSP
        request.setAttribute("categories", categories);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("products", products);
        request.setAttribute("priceRanges", priceRanges);
        request.setAttribute("checkedRams", checkedRams);
        request.setAttribute("checkedCpus", checkedCpus);
        request.setAttribute("checkedVgas", checkedVgas);
        request.setAttribute("rams", rams);
        request.setAttribute("cpus", cpus);
        request.setAttribute("vgas", vgas);
        request.setAttribute("order", orderParam);
        request.setAttribute("filterQueryString", request.getQueryString());

        request.getRequestDispatcher("/WEB-INF/views/allProductView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/all-products");
    }
}
