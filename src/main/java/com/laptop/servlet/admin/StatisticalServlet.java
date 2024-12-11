package com.laptop.servlet.admin;

import com.laptop.service.StatisticsService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StatisticalServlet", value = "/admin/statisticalManage")
public class StatisticalServlet extends HttpServlet {

    private final StatisticsService statisticsService;

    // Constructor để khởi tạo Service
    public StatisticalServlet() {
        this.statisticsService = new StatisticsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thống kê từ service
        List<Object[]> dailyStats = statisticsService.getDailyStatistics();
        List<Object[]> monthlyStatsCurrent = statisticsService.getMonthlyStatisticsCurrentYear();
        List<Object[]> monthlyStatsLastYear = statisticsService.getMonthlyStatisticsLastYear();

        // Gửi dữ liệu sang JSP
        request.setAttribute("dailyStats", dailyStats);
        request.setAttribute("monthlyStatsCurrent", monthlyStatsCurrent);
        request.setAttribute("monthlyStatsLastYear", monthlyStatsLastYear);

        // Chuyển tiếp tới JSP
        request.getRequestDispatcher("/WEB-INF/views/statisticalView.jsp").forward(request, response);
    }
}
