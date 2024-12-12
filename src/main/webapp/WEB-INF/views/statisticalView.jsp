<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<html>
<head>
    <jsp:include page="_meta.jsp"/>
    <title>Thống kê</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .container {
            width: 80%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .buttons {
            display: flex;
            justify-content: space-evenly;
            margin-bottom: 30px;
        }
        .button {
            padding: 12px 25px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 6px;
            background-color: #4caf50;
            color: white;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }
        .button:hover {
            background-color: #45a049;
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
        }
        .chart-container {
            display: none;
            width: 100%;
            max-width: 900px;
            height: 400px;
            margin: 0 auto;
            opacity: 0;
            transition: opacity 1s ease-in-out;
        }
        .chart-container.active {
            display: block;
            opacity: 1;
        }

        .total-registrations {
            text-align: center;
            margin-bottom: 30px;
        }
        .total-registrations h3 {
            font-size: 24px;
            color: #333;
            font-weight: bold;
        }
        .total-registrations span {
            color: #4caf50;
            font-size: 28px;
        }
    </style>
</head>
<body>
<jsp:include page="_headerAdmin.jsp"/>

<section class="section-content">
    <div class="container">
        <h3 class="section-title py-4">Thống kê chi tiết</h3>

        <!-- Total Stats Display -->
        <div class="total-registrations">
            <h3 id="totalStatsTitle">Tổng số khách hàng đăng ký: <span id="totalStats">${requestScope.totalUsers}</span></h3>
        </div>

        <!-- Control Buttons -->
        <div class="buttons">
            <button class="button" id="dailyBtn">Số lượng đăng ký trong ngày</button>
            <button class="button" id="monthlyBtn">Số lượng đăng ký trong tháng</button>
            <button class="button" id="productBtn">Thống kê sản phẩm</button>
            <button class="button" id="dailyRevenueBtn">Doanh thu hàng ngày</button>
            <button class="button" id="monthlyRevenueBtn">Doanh thu hàng tháng</button>
        </div>

        <!-- Chart Containers -->
        <div class="chart-container" id="dailyChartContainer">
            <canvas id="dailyChart"></canvas>
        </div>
        <div class="chart-container" id="monthlyChartContainer">
            <canvas id="monthlyChart"></canvas>
        </div>
        <div class="chart-container" id="productChartContainer">
            <canvas id="productChart"></canvas>
        </div>
        <div class="chart-container" id="dailyRevenueChartContainer">
            <canvas id="dailyRevenueChart"></canvas>
        </div>
        <div class="chart-container" id="monthlyRevenueChartContainer">
            <canvas id="monthlyRevenueChart"></canvas>
        </div>
    </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="_footerAdmin.jsp"/>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Data for Daily Registration Chart
        const dailyLabels = [];
        const dailyData = [];
        <c:forEach var="stat" items="${dailyStats}">
        dailyLabels.push("${stat[0]}");
        dailyData.push(${stat[1]});
        </c:forEach>

        const allDays = Array.from({ length: 31 }, (_, i) => i + 1);
        const completeDailyData = allDays.map(day => {
            const index = dailyLabels.indexOf(String(day));
            return index === -1 ? 0 : dailyData[index];
        });

        // Data for Monthly Registration Chart
        const monthlyLabels = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        const monthlyData = [
            <c:forEach var="stat" items="${monthlyStatsCurrent}">
            ${stat[1]},
            </c:forEach>
        ];

        // Data for Product Sales Pie Chart
        const productLabels = [];
        const productData = [];
        <c:forEach var="stat" items="${productStats}">
        productLabels.push("${stat.name}");
        productData.push(${stat.totalBuy});
        </c:forEach>

        const totalProductQuantity = productData.reduce((sum, value) => sum + value, 0);
        const productPercentages = productData.map(quantity => ((quantity / totalProductQuantity) * 100).toFixed(2));

        // Data for Daily Revenue Chart
        const dailyRevenueLabels = [];
        const dailyRevenueData = [];
        <c:forEach var="stat" items="${dailyRevenueStats}">
        dailyRevenueLabels.push("${stat[0]}");
        dailyRevenueData.push(${stat[1]});
        </c:forEach>

        const completeDailyRevenueData = allDays.map(day => {
            const index = dailyRevenueLabels.indexOf(String(day));
            return index === -1 ? 0 : dailyRevenueData[index];
        });

        // Data for Monthly Revenue Chart
        const monthlyRevenueData = [
            <c:forEach var="stat" items="${monthlyRevenueStatsCurrent}">
            ${stat[1]},
            </c:forEach>
        ];

        // Chart Configurations
        function createChart(ctx, type, labels, data, colors) {
            return new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        data: data,
                        backgroundColor: colors,
                        borderColor: colors.map(color => color.replace('0.5', '1')),
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        legend: { display: false },
                        tooltip: { enabled: true }
                    },
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });
        }

        const dailyCtx = document.getElementById('dailyChart').getContext('2d');
        const dailyChart = createChart(dailyCtx, 'bar', allDays, completeDailyData, ['rgba(75, 192, 192, 0.5)']);

        const monthlyCtx = document.getElementById('monthlyChart').getContext('2d');
        const monthlyChart = createChart(monthlyCtx, 'bar', monthlyLabels, monthlyData, ['rgba(153, 102, 255, 0.5)']);

        const productCtx = document.getElementById('productChart').getContext('2d');
        const productChart = createChart(productCtx, 'pie', productLabels, productPercentages, [
            'rgba(255, 99, 132, 0.5)', 'rgba(54, 162, 235, 0.5)', 'rgba(255, 206, 86, 0.5)',
            'rgba(75, 192, 192, 0.5)', 'rgba(153, 102, 255, 0.5)', 'rgba(255, 159, 64, 0.5)'
        ]);

        const dailyRevenueCtx = document.getElementById('dailyRevenueChart').getContext('2d');
        const dailyRevenueChart = createChart(dailyRevenueCtx, 'bar', allDays, completeDailyRevenueData, ['rgba(255, 159, 64, 0.5)']);

        const monthlyRevenueCtx = document.getElementById('monthlyRevenueChart').getContext('2d');
        const monthlyRevenueChart = createChart(monthlyRevenueCtx, 'bar', monthlyLabels, monthlyRevenueData, ['rgba(54, 162, 235, 0.5)']);

        // Functions to show different charts and update totals
        function toggleChartVisibility(chartId) {
            const allCharts = ['dailyChartContainer', 'monthlyChartContainer', 'productChartContainer', 'dailyRevenueChartContainer', 'monthlyRevenueChartContainer'];
            allCharts.forEach(chart => {
                document.getElementById(chart).classList.remove('active');
            });
            document.getElementById(chartId).classList.add('active');
        }

        function updateTotalStats(title, value) {
            document.getElementById('totalStatsTitle').textContent = title;
            document.getElementById('totalStats').textContent = value;
        }

        // Event Listeners for Buttons
        document.getElementById('dailyBtn').addEventListener('click', function() {
            toggleChartVisibility('dailyChartContainer');
            updateTotalStats('Tổng khách hàng đã đăng kí (hằng ngày): ', "${totalUsers}");
        });

        document.getElementById('monthlyBtn').addEventListener('click', function() {
            toggleChartVisibility('monthlyChartContainer');
            updateTotalStats('Tổng khách hàng đã đăng kí (hằng tháng): ', "${totalUsers}");
        });

        document.getElementById('productBtn').addEventListener('click', function() {
            toggleChartVisibility('productChartContainer');
            updateTotalStats('Tổng sản phẩm đã được bán: ', "${totalProductSoldCurrentYear}");
        });

        document.getElementById('dailyRevenueBtn').addEventListener('click', function() {
            toggleChartVisibility('dailyRevenueChartContainer');
            updateTotalStats('Tổng doanh thu (hằng ngày): ', "${totalRevenueCurrentMonth}");
        });

        document.getElementById('monthlyRevenueBtn').addEventListener('click', function() {
            toggleChartVisibility('monthlyRevenueChartContainer');
            updateTotalStats('Tổng doanh thu (hằng tháng): ', "${totalRevenueCurrentMonth}");
        });

    });
</script>

</body>
</html>
