<%--
  Created by IntelliJ IDEA.
  User: hieun
  Date: 12/2/2024
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <jsp:include page="_meta.jsp"/>
    <title>Thông tin Chương trình #${requestScope.promotion.id}</title>
</head>

<body>
<jsp:include page="_headerAdmin.jsp"/>

<section class="section-content">
    <div class="container">
        <header class="section-heading py-4">
            <h3 class="section-title">Thông tin chương trình</h3>
        </header> <!-- section-heading.// -->

        <div class="card mb-5">
            <div class="card-body">
                <dl class="row">
                    <dt class="col-md-3">ID</dt>
                    <dd class="col-md-9">${requestScope.promotion.id}</dd>

                    <dt class="col-md-3">Tên Chương trình</dt>
                    <dd class="col-md-9">
                        <a href="${pageContext.request.contextPath}/promotion?id=${requestScope.promotion.id}" target="_blank">
                            ${requestScope.promotion.name}
                        </a>
                    </dd>

                    <dt class="col-md-3">Mô tả chương trình</dt>
                    <dd class="col-md-9">${requestScope.promotion.description}</dd>

                    <dt class="col-md-3">Hình chương trình</dt>
                    <dd class="col-md-9">
                        <c:choose>
                            <c:when test="${empty requestScope.promotion.imageName}">
                                <img width="50" src="${pageContext.request.contextPath}/img/50px.png"
                                     alt="50px.png">
                            </c:when>
                            <c:otherwise>
                                <img width="50" src="${pageContext.request.contextPath}/image/${requestScope.promotion.imageName}"
                                     alt="${requestScope.promotion.imageName}">
                            </c:otherwise>
                        </c:choose>
                    </dd>
                    <dt class="col-md-3">Ngày bắt đầu khuyến mãi</dt>
                    <dd class="col-md-9">${requestScope.promotion.startsAt.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"))}</dd>

                    <dt class="col-md-3">Ngày kết thúc khuyến mãi</dt>
                    <dd class="col-md-9">${requestScope.promotion.endsAt.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"))}</dd>
                </dl>
            </div>
        </div> <!-- card.// -->
    </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="_footerAdmin.jsp"/>
</body>

</html>
