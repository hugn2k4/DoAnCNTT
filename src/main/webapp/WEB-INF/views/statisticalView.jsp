<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Statistics View</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f7f6;
        }
        h2 {
            text-align: center;
            margin-top: 20px;
            color: #333;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .buttons {
            text-align: center;
            margin-bottom: 20px;
        }
        .button {
            padding: 10px 20px;
            margin: 0 10px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            background-color: #4caf50;
            color: white;
            transition: background-color 0.3s ease;
        }
        .button:hover {
            background-color: #45a049;
        }
        .chart-container {
            display: none;
            width: 100%;
            height: 400px;
            opacity: 0;
            transition: opacity 1s ease-in-out;  /* Thêm hiệu ứng mờ dần */
        }
        .chart-container.active {
            display: block;
            opacity: 1; /* Đảm bảo rằng nó sẽ xuất hiện */
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Statistics View</h2>

    <!-- Các nút điều khiển -->
    <div class="buttons">
        <button class="button" id="dailyBtn">Statistics by Day</button>
        <button class="button" id="monthlyBtn">Statistics by Month</button>
    </div>

    <!-- Biểu đồ số người đăng ký theo ngày -->
    <div class="chart-container" id="dailyChartContainer">
        <canvas id="dailyChart"></canvas>
    </div>

    <!-- Biểu đồ số người đăng ký theo tháng -->
    <div class="chart-container" id="monthlyChartContainer">
        <canvas id="monthlyChart"></canvas>
    </div>
</div>

<script>
    // Dữ liệu cho biểu đồ ngày
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

    // Dữ liệu cho biểu đồ tháng
    const monthlyLabels = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    const monthlyData = [
        <c:forEach var="stat" items="${monthlyStatsCurrent}">
        ${stat[1]},
        </c:forEach>
    ];

    // Vẽ biểu đồ số người đăng ký theo ngày
    const dailyCtx = document.getElementById('dailyChart').getContext('2d');
    const dailyChart = new Chart(dailyCtx, {
        type: 'bar',
        data: {
            labels: allDays,
            datasets: [{
                label: 'Registrations (Daily)',
                data: completeDailyData,
                backgroundColor: 'rgba(75, 192, 192, 0.5)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            plugins: {
                legend: { display: false },
                tooltip: { enabled: true },
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Vẽ biểu đồ số người đăng ký theo tháng
    const monthlyCtx = document.getElementById('monthlyChart').getContext('2d');
    const monthlyChart = new Chart(monthlyCtx, {
        type: 'bar',
        data: {
            labels: monthlyLabels,
            datasets: [{
                label: 'Registrations (Monthly)',
                data: monthlyData,
                backgroundColor: 'rgba(153, 102, 255, 0.5)',
                borderColor: 'rgba(153, 102, 255, 1)',
                borderWidth: 1
            }]
        },
        options: {
            plugins: {
                legend: { display: false },
                tooltip: { enabled: true },
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Hàm hiển thị biểu đồ theo ngày
    function showDailyChart() {
        document.getElementById('monthlyChartContainer').classList.remove('active');
        document.getElementById('dailyChartContainer').classList.add('active');
    }

    // Hàm hiển thị biểu đồ theo tháng
    function showMonthlyChart() {
        document.getElementById('dailyChartContainer').classList.remove('active');
        document.getElementById('monthlyChartContainer').classList.add('active');
    }

    // Sự kiện cho các nút
    document.getElementById('dailyBtn').addEventListener('click', function() {
        showDailyChart();
    });

    document.getElementById('monthlyBtn').addEventListener('click', function() {
        showMonthlyChart();
    });

    // Mặc định hiển thị biểu đồ theo ngày
    showDailyChart();
</script>

</body>
</html>
