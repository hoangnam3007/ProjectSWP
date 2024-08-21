<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="./gui/header.jsp"></jsp:include>

    <!-- Include DataTables CSS and JS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <!-- Include Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        .chart-container {
            height: 300px;
            margin-bottom: 20px;
        }
    </style>

    <main class="ttr-wrapper">
        <div class="container-fluid">
            <div class="db-breadcrumb">
                <h4 class="breadcrumb-title">Dashboard</h4>
                <ul class="db-breadcrumb-list">
                    <li><a href="dashboard"><i class="fa fa-home"></i>Home</a></li>
                    <li>Dashboard</li>
                </ul>
            </div>
            <form action="dashboard" method="get">
                <div class="row">
                    <div class="col-md-3">
                        <label for="startDate">Start Date:</label>
                        <input type="date" id="startDate" class="form-control-sm" name="startDate" value="${selectedStartDate}">
                </div>
                <div class="col-md-3">
                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" class="form-control-sm" name="endDate" value="${selectedEndDate}">
                </div>
                <div class="col-md-4"  >
                    <button class="btn btn-sm" onclick="updateDateRange()">Show</button>
                    <p id="error-message" style="color: red; display: none;">Please select both start and end dates.</p>
                </div>
            </div>
        </form>
        <form action="dashboard" method="get" class="row">
            <div class="col-md-6" style="margin-bottom: 10px; margin-top: 10px">
                <label for="dateRange">Date Range:</label>
                <select id="dateRange" name="dateRange" onchange="updateDateRangeFields()">
                    <option value="custom" ${param.dateRange eq 'custom' ? 'selected' : ''}>Custom</option>
                    <option value="1week" ${param.dateRange eq '1week' ? 'selected' : ''}>1 Week Ago to Now</option>
                    <option value="1month" ${param.dateRange eq '1month' ? 'selected' : ''}>1 Month</option>
                    <option value="6months" ${param.dateRange eq '6months' ? 'selected' : ''}>6 Months</option>
                    <option value="1year" ${param.dateRange eq '1year' ? 'selected' : ''}>1 Year</option>
                    <option value="all" ${param.dateRange eq 'all' ? 'selected' : ''}>All</option>
                </select>
            </div>
            <div class="col-md-12" style="margin-bottom: 10px; margin-top: 10px">
                <button class="btn btn-sm" onclick="updateDateRange()">Show</button>
                <p id="error-message" style="color: red; display: none;">Please select both start and end dates.</p>
            </div>
        </form>
    </div>

    <div class="row">
        <div class="col-md-6 col-lg-4 col-xl-4 col-sm-6 col-12">
            <div class="widget-card widget-bg1">
                <div class="wc-item">
                    <h4 class="wc-title">New Subjects</h4>
                    <span class="wc-des">New subjects ${rangeDescription}</span>
                    <span class="wc-stats counter">${newSubjects}</span>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-4 col-xl-4 col-sm-6 col-12">
            <div class="widget-card widget-bg2">
                <div class="wc-item">
                    <h4 class="wc-title">All Subjects</h4>
                    <span class="wc-des">Total subjects</span>
                    <span class="wc-stats counter">${allSubjects}</span>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-4 col-xl-4 col-sm-6 col-12">
            <div class="widget-card widget-bg3">
                <div class="wc-item">
                    <h4 class="wc-title">New Registrations</h4>
                    <span class="wc-des">Registrations ${rangeDescription}</span>
                    <span class="wc-stats counter">${newRegistrations}</span>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-4 col-xl-4 col-sm-6 col-12">
            <div class="widget-card widget-bg5">
                <div class="wc-item">
                    <h4 class="wc-title">Newly Registered Customers</h4>
                    <span class="wc-des">Customers registered ${rangeDescription}</span>
                    <span class="wc-stats counter">${newlyRegisteredCustomers}</span>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-4 col-xl-4 col-sm-6 col-12">
            <div class="widget-card widget-bg5">
                <div class="wc-item">
                    <h4 class="wc-title">All Customers</h4>
                    <span class="wc-des">Total Customers</span>
                    <span class="wc-stats counter">${allCustomers}</span>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-4 col-xl-4 col-sm-6 col-12">
            <div class="widget-card widget-bg5">
                <div class="wc-item">
                    <h4 class="wc-title">All Quizzes</h4>
                    <span class="wc-des">Total Quizzes</span>
                    <span class="wc-stats counter">${allQuizzes}</span>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <h4>Subject Registration Distribution</h4>
            <div style="height: 400px; width: 100%;">
                <canvas id="subjectPieChart"></canvas>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <h4 class="m-b-20">Subject Registration by Category</h4>
            <div class="row" id="charts-container">
                <!-- Charts will be inserted here dynamically -->
            </div>
        </div>
    </div>
</main>

<script>
    function updateDateRange() {
        var year = document.getElementById('year').value;
        var week = document.getElementById('week').value;
        var errorMessage = document.getElementById('error-message');

        if (!year || !week) {
            errorMessage.style.display = 'block';
        } else {
            errorMessage.style.display = 'none';
            var url = 'dashboard?year=' + year + '&week=' + week;
            window.location.href = url;
        }
    }

    function updateWeekOptions() {
        var year = document.getElementById('year').value;
        window.location.href = 'dashboard?year=' + year;
    }

    // Định nghĩa bảng màu mới
    const colorPalette = [
        'rgba(255, 99, 132, 0.8)', // Hồng đậm
        'rgba(54, 162, 235, 0.8)', // Xanh dương đậm
        'rgba(255, 206, 86, 0.8)', // Vàng đậm
        'rgba(75, 192, 192, 0.8)', // Xanh lá cây đậm
        'rgba(153, 102, 255, 0.8)', // Tím đậm
        'rgba(255, 159, 64, 0.8)', // Cam đậm
        'rgba(199, 199, 199, 0.8)', // Xám
        'rgba(83, 102, 255, 0.8)', // Xanh dương nhạt
        'rgba(255, 99, 255, 0.8)', // Hồng nhạt
        'rgba(159, 159, 64, 0.8)', // Vàng nâu
        'rgba(0, 128, 128, 0.8)', // Xanh lục
        'rgba(128, 0, 128, 0.8)', // Tím đậm
    ];

    // Hàm để lấy màu từ bảng màu
    function getColor(index) {
        return colorPalette[index % colorPalette.length];
    }

    // Lấy dữ liệu từ server-side và chuyển đổi thành mảng JavaScript
    var subjectNames = JSON.parse('<c:out value="${subjectNamesJson}" escapeXml="false" />');
    var subjectCounts = JSON.parse('<c:out value="${subjectCountsJson}" escapeXml="false" />');

    // Tạo biểu đồ tròn
    var ctx = document.getElementById('subjectPieChart').getContext('2d');
    var subjectPieChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: subjectNames,
            datasets: [{
                    data: subjectCounts,
                    backgroundColor: subjectNames.map((_, index) => getColor(index)),
                    borderColor: 'rgba(255, 255, 255, 1)',
                    borderWidth: 2
                }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'right',
                    labels: {
                        font: {
                            size: 14
                        },
                        color: 'black'
                    }
                },
                tooltip: {
                    bodyFont: {
                        size: 14
                    },
                    titleFont: {
                        size: 16
                    }
                }
            }
        }
    });

    var categorySubjectRegistrations = JSON.parse('<c:out value="${categorySubjectRegistrationsJson}" escapeXml="false" />');

    // Tạo các biểu đồ theo từng chuyên ngành
    var chartsContainer = document.getElementById('charts-container');
    for (var category in categorySubjectRegistrations) {
        var chartContainer = document.createElement('div');
        chartContainer.classList.add('col-md-4');
        chartContainer.innerHTML = '<div class="chart-container"><h5>' + category + '</h5><canvas id="chart-' + category + '"></canvas></div>';
        chartsContainer.appendChild(chartContainer);

        var ctx = document.getElementById('chart-' + category).getContext('2d');
        var data = categorySubjectRegistrations[category];
        var labels = Object.keys(data);
        var counts = Object.values(data);

        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                        data: counts,
                        backgroundColor: labels.map((_, index) => getColor(index)),
                        borderColor: 'rgba(255, 255, 255, 1)',
                        borderWidth: 2
                    }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'right',
                        labels: {
                            font: {
                                size: 12
                            },
                            color: 'black'
                        }
                    },
                    tooltip: {
                        bodyFont: {
                            size: 12
                        },
                        titleFont: {
                            size: 14
                        }
                    }
                }
            }
        });
    }

    function updateDateRangeFields() {
        const dateRange = document.getElementById('dateRange').value;
        const startDateField = document.getElementById('startDate');
        const endDateField = document.getElementById('endDate');
        const today = new Date();
        let startDate, endDate;

        switch (dateRange) {
            case '1week':
                startDate = new Date();
                startDate.setDate(today.getDate() - 7);
                endDate = today;
                break;
            case '1month':
                startDate = new Date();
                startDate.setMonth(today.getMonth() - 1);
                endDate = today;
                break;
            case '6months':
                startDate = new Date();
                startDate.setMonth(today.getMonth() - 6);
                endDate = today;
                break;
            case '1year':
                startDate = new Date();
                startDate.setFullYear(today.getFullYear() - 1);
                endDate = today;
                break;
            case 'all':
                startDate = new Date('2000-01-01');
                endDate = new Date('2050-01-01');
                break;
            default:
                startDate = null;
                endDate = null;
        }

        if (startDate && endDate) {
            startDateField.value = startDate.toISOString().split('T')[0];
            endDateField.value = endDate.toISOString().split('T')[0];
        }
    }
</script>

<jsp:include page="./gui/footer.jsp"></jsp:include>