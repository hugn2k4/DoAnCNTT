package com.laptop.servlet.admin;

import com.laptop.models.Product;
import com.laptop.service.StatisticsService;
import com.laptop.service.UserService;
import com.laptop.utils.Protector;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StatisticalServlet", value = "/admin/statisticalManage")
public class StatisticalServlet extends HttpServlet {

    private final StatisticsService statisticsService;
    private final UserService userService = new UserService();

    // Constructor to initialize the service
    public StatisticalServlet() {
        this.statisticsService = new StatisticsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve statistics from the service
        List<Object[]> dailyStats = statisticsService.getDailyStatistics();
        List<Object[]> monthlyStatsCurrent = statisticsService.getMonthlyStatisticsCurrentYear();
        List<Object[]> monthlyStatsLastYear = statisticsService.getMonthlyStatisticsLastYear();
        List<Product> productStats = statisticsService.getTotalBuyStatistics();
        List<Object[]> dailyRevenueStats = statisticsService.getDailyRevenueStatistics();  // New
        List<Object[]> monthlyRevenueStatsCurrent = statisticsService.getMonthlyRevenueStatisticsCurrentYear(); // New
        List<Object[]> monthlyRevenueStatsLastYear = statisticsService.getMonthlyRevenueStatisticsLastYear();  // New
        List<Product> topSellingProducts = statisticsService.getTopSellingProducts();  // New

        // Get totals
        int totalUsers = statisticsService.getTotalUsers();  // New: Get total users
        double totalRevenueCurrentMonth = statisticsService.getTotalRevenueCurrentMonth(); // New: Get total revenue for the current month
        double totalRevenueLastYear = statisticsService.getTotalRevenueLastYear();  // New: Get total revenue for last year
        int totalProductSoldCurrentYear = statisticsService.getTotalProductSoldCurrentYear();  // New: Get total products sold this year

        // Set the statistics as request attributes
        request.setAttribute("dailyStats", dailyStats);
        request.setAttribute("monthlyStatsCurrent", monthlyStatsCurrent);
        request.setAttribute("monthlyStatsLastYear", monthlyStatsLastYear);
        request.setAttribute("productStats", productStats);
        request.setAttribute("dailyRevenueStats", dailyRevenueStats);  // New
        request.setAttribute("monthlyRevenueStatsCurrent", monthlyRevenueStatsCurrent); // New
        request.setAttribute("monthlyRevenueStatsLastYear", monthlyRevenueStatsLastYear);  // New
        request.setAttribute("topSellingProducts", topSellingProducts);  // New

        // Set total values as request attributes
        request.setAttribute("totalUsers", totalUsers);  // New
        request.setAttribute("totalRevenueCurrentMonth", totalRevenueCurrentMonth);  // New
        request.setAttribute("totalRevenueLastYear", totalRevenueLastYear);  // New
        request.setAttribute("totalProductSoldCurrentYear", totalProductSoldCurrentYear);  // New

        // Forward the request to the JSP page for rendering
        request.getRequestDispatcher("/WEB-INF/views/statisticalView.jsp").forward(request, response);
    }
}
