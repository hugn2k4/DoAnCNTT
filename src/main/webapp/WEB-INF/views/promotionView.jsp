<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
    <jsp:include page="_meta.jsp"/>
    <title>Quảng Cáo</title>
</head>

<body>
<jsp:include page="_header.jsp"/>

<section class="section-pagetop bg-light">
    <div class="container">
        <h2 class="title-page">Chương trình khuyến mãi</h2>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/">Trang chủ</a>
                </li>
                <li class="breadcrumb-item active">Chương trình khuyến mãi</li>
            </ol>
        </nav>
    </div> <!-- container.// -->
</section> <!-- section-pagetop.// -->

<section class="section-content padding-y">
    <div class="container">
        <header class="border-bottom mb-4 pb-3">
            <div class="form-inline d-flex justify-content-between align-items-center">
                <span>${requestScope.totalPromotions} quảng cáo hiện tại</span> <!-- Tổng số quảng cáo -->
            </div>
        </header>

        <div class="row item-grid" style="margin-top: 20px; margin-bottom: 20px;">
            <c:forEach var="promotion" items="${requestScope.promotions}">
                <div class="col-xl-4 col-lg-6 mb-4">
                    <div class="card p-3 shadow-sm rounded-4 h-100 border-0">
                        <!-- Image -->
                        <a href="${pageContext.request.contextPath}/promotion?id=${promotion.id}" class="img-wrap text-center">
                            <div class="img-container" style="width: 100%; height: 200px; margin: 0 auto; overflow: hidden; border-radius: 10px; background-color: #f8f9fa;">
                                <c:choose>
                                    <c:when test="${empty promotion.imageName}">
                                        <img class="img-fluid"
                                             src="${pageContext.request.contextPath}/img/280px.png"
                                             alt="Default Advertisement"
                                             style="width: 100%; height: 100%; object-fit: cover;">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="img-fluid"
                                             src="${pageContext.request.contextPath}/image/${promotion.imageName}"
                                             alt="${promotion.name}"
                                             style="width: 100%; height: 100%; object-fit: cover;">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </a>

                        <!-- Card Content -->
                        <div class="card-body d-flex flex-column justify-content-between">
                            <!-- Promotion Name -->
                            <a href="${pageContext.request.contextPath}/promotion?id=${promotion.id}"
                               class="title text-decoration-none text-dark fw-bold d-block mb-2">${promotion.name}</a>

                            <!-- Promotion Description -->
                            <p class="text-muted mb-3">${promotion.description}</p>

                            <!-- Promotion Start and End Dates -->
                            <div class="text-muted">
                                <span><strong>Bắt đầu:</strong> ${promotion.startsAt}</span>
                                <br/>
                                <span><strong>Kết thúc:</strong> ${promotion.endsAt}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div> <!-- row end.// -->

        <c:if test="${requestScope.totalPages > 1}">
            <nav class="mt-4">
                <ul class="pagination">
                    <li class="page-item ${requestScope.page == 1 ? 'disabled' : ''}" style="margin-right: 5px;">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/promotions?page=${requestScope.page - 1}">
                            Trang trước
                        </a>
                    </li>

                    <c:forEach begin="1" end="${requestScope.totalPages}" var="i">
                        <c:choose>
                            <c:when test="${requestScope.page == i}">
                                <li class="page-item active" style="margin-right: 5px;">
                                    <a class="page-link">${i}</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item" style="margin-right: 5px;">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/promotions?page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <li class="page-item ${requestScope.page == requestScope.totalPages ? 'disabled' : ''}" style="margin-right: 5px;">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/promotions?page=${requestScope.page + 1}">
                            Trang sau
                        </a>
                    </li>
                </ul>
            </nav>
        </c:if>

    </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="_footer.jsp"/>
</body>

</html>
