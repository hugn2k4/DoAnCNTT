<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
  <jsp:include page="_meta.jsp"/>
  <title>Sản phẩm mới</title>
</head>

<body>
<jsp:include page="_header.jsp"/>

<section class="section-pagetop bg-light">
  <div class="container">
    <h2 class="title-page">Sản phẩm mới</h2>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="${pageContext.request.contextPath}/">Trang chủ</a>
        </li>
        <li class="breadcrumb-item active">Sản phẩm mới</li>
      </ol>
    </nav>
  </div> <!-- container.// -->
</section> <!-- section-pagetop.// -->

<section class="section-content padding-y">
  <div class="container">
    <header class="border-bottom mb-4 pb-3">
      <div class="form-inline d-flex justify-content-between align-items-center">
        <span>${requestScope.totalNewProducts} sản phẩm mới</span>
      </div>
    </header>

    <div class="row item-grid">
      <c:forEach var="product" items="${requestScope.newProducts}">
        <div class="col-xl-4 col-lg-6 text-center">
          <div class="card p-3 shadow-sm rounded-4 h-100 border-0 text-center">
            <!-- Image -->
            <a href="${pageContext.request.contextPath}/product?id=${product.id}" class="img-wrap text-center">
              <div style="width: 150px; height: 150px; margin: 0 auto; overflow: hidden; border-radius: 10px; background-color: #f8f9fa;">
                <c:choose>
                  <c:when test="${empty product.imageName}">
                    <img class="img-fluid"
                         src="${pageContext.request.contextPath}/img/280px.png"
                         alt="280px.png"
                         style="width: 100%; height: 100%; object-fit: contain;">
                  </c:when>
                  <c:otherwise>
                    <img class="img-fluid bg-white"
                         src="${pageContext.request.contextPath}/image/${product.imageName}"
                         alt="${product.imageName}"
                         style="width: 100%; height: 100%; object-fit: contain;">
                  </c:otherwise>
                </c:choose>
              </div>
            </a>
            <figcaption class="info-wrap mt-3">
              <!-- Product Name -->
              <a href="${pageContext.request.contextPath}/product?id=${product.id}"
                 class="title text-decoration-none text-dark fw-bold d-block">${product.name}</a>
              <!-- Product Price -->
              <div class="mt-2">
                <c:choose>
                  <c:when test="${product.discount == 0}">
                    <span class="price text-success fw-bold">
                      <fmt:formatNumber pattern="#,##0" value="${product.price}"/>₫
                    </span>
                  </c:when>
                  <c:otherwise>
                    <span class="price text-success fw-bold">
                      <fmt:formatNumber pattern="#,##0"
                                        value="${product.price * (100 - product.discount) / 100}"/>₫
                    </span>
                    <span class="ms-2 text-muted text-decoration-line-through">
                      <fmt:formatNumber pattern="#,##0" value="${product.price}"/>₫
                    </span>
                    <span class="ms-2 badge bg-info">
                      -<fmt:formatNumber pattern="#,##0" value="${product.discount}"/>%
                    </span>
                  </c:otherwise>
                </c:choose>
              </div>
            </figcaption>
          </div>
        </div> <!-- col.// -->
      </c:forEach>
    </div> <!-- row end.// -->

    <c:if test="${requestScope.totalPages > 1}">
      <nav class="mt-4">
        <ul class="pagination">
          <li class="page-item ${requestScope.page == 1 ? 'disabled' : ''}">
            <a class="page-link"
               href="${pageContext.request.contextPath}/new-products?page=${requestScope.page - 1}">
              Trang trước
            </a>
          </li>

          <c:forEach begin="1" end="${requestScope.totalPages}" var="i">
            <c:choose>
              <c:when test="${requestScope.page == i}">
                <li class="page-item active">
                  <a class="page-link">${i}</a>
                </li>
              </c:when>
              <c:otherwise>
                <li class="page-item">
                  <a class="page-link"
                     href="${pageContext.request.contextPath}/new-products?page=${i}">
                      ${i}
                  </a>
                </li>
              </c:otherwise>
            </c:choose>
          </c:forEach>

          <li class="page-item ${requestScope.page == requestScope.totalPages ? 'disabled' : ''}">
            <a class="page-link"
               href="${pageContext.request.contextPath}/new-products?page=${requestScope.page + 1}">
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
