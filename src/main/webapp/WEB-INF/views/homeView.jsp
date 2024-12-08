<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
  <jsp:include page="_meta.jsp"/>
  <title>Trang chủ</title>
</head>

<body>
<jsp:include page="_header.jsp"/>
<%--Thương hiệu--%>
<!--   Thương hiệu -->
<section class="section-content  bg-light">
  <div class="container ">
    <header class="section-heading py-4 d-flex justify-content-center align-items-center">
      <h3 class="section-title fw-bold">Thương hiệu</h3>
    </header> <!-- section-heading.// -->
    <div class="row item-grid pb-3 ">
      <div class="col-auto d-flex flex-row ">
        <%--        apple--%>
        <div class="card  d-flex flex-row align-items-center rounded-3 border border-1 me-2 " style="width: 250px; height: 75px;">
          <div style="width: 125px; height: 50px; overflow: hidden;">
            <img src="images/brand/apple.png" alt="Sample Image" style="width: 100%; height: 100%; object-fit: contain;">
          </div>
          <div class="ms-0">
            <h5 class="card-title mb-0 fw-bold">Apple</h5>
          </div>
        </div>
        <%--  asus      --%>
        <div class="card p-3 d-flex flex-row align-items-center rounded-3 border border-1 me-2" style="width: 250px; height: 75px;">
          <div class="" style="width: 125px; height: 50px; overflow: hidden;">
            <img src="images/brand/asus.png" alt="Sample Image" style="width: 90%; height: 90%; object-fit: contain;">
          </div>
          <div class="ms-3">
            <h5 class="card-title mb-0 fw-bold">ASUS</h5>
          </div>
        </div>
        <%--        lenovo--%>
        <div class="card p-3 d-flex flex-row align-items-center rounded-3 border border-1 me-2" style="width: 250px; height: 75px;">
          <div class="" style="width: 125px; height: 50px; overflow: hidden;">
            <img src="images/brand/Lenovo1.png" alt="Sample Image" style="width: 90%; height: 90%; object-fit: contain;">
          </div>
          <div class="ms-3">
            <h5 class="card-title mb-0 fw-bold">Lenovo</h5>
          </div>
        </div>
        <%--dell--%>
        <div class="card p-3 d-flex flex-row align-items-center rounded-3 border border-1 me-2" style="width: 250px; height: 75px;">
          <div class="pt-2" style="width: 125px; height: 50px; overflow: hidden;">
            <img src="images/brand/Dell2.png" alt="Sample Image" style="width: 70%; height: 70%; object-fit: contain;">
          </div>
          <div class="ms-0">
            <h5 class="card-title mb-0 fw-bold">Dell</h5>
          </div>
        </div>
        <%--        acer--%>
        <div class="card p-3 d-flex flex-row align-items-center rounded-3 border border-1 me-2" style="width: 250px; height: 75px;">
          <div class="pt-2" style="width: 125px; height: 50px; overflow: hidden;">
            <img src="images/brand/acer.png" alt="Sample Image" style="width: 70%; height: 70%; object-fit: contain;">
          </div>
          <div class="ms-0">
            <h5 class="card-title mb-0 fw-bold">Acer</h5>
          </div>
        </div>
        <%--hp--%>
        <div class="card  d-flex flex-row align-items-center rounded-3 border border-1 me-2" style="width: 250px; height: 75px;">
          <div style="width: 125px; height: 50px; overflow: hidden;">
            <img src="images/brand/hp.png" alt="Sample Image" style="width: 100%; height: 100%; object-fit: contain;">
          </div>
          <div class="ms-1">
            <h5 class="card-title mb-0 fw-bold">HP</h5>
          </div>
        </div>

      </div>
    </div>
  </div>
</section>
<%--danh mục--%>
<section class="section-content  bg-light">
  <div class="container">
    <header class="section-heading py-4 d-flex justify-content-between ">
      <h3 class="section-title section-title fw-bold">Danh mục sản phẩm</h3>
      <a class="btn btn-secondary" href="#" role="button" style="height: fit-content;">Xem tất cả</a>
    </header> <!-- section-heading.// -->
    <div class="row item-grid">
      <c:forEach var="category" items="${requestScope.categories}">
        <div class="col-lg-3 col-md-6 d-flex justify-content-center align-items-center my-3 ">
          <div class="custom-card" style="width: 300px; height: 100px; border-radius: 10px; background-color: #f8f9fa; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); position: relative; padding: 10px; overflow: visible;">
            <a href="${pageContext.request.contextPath}/category?id=${category.id}" class="stretched-link">
              <!-- Text -->
              <h6 class="text-dark fw-bold pt-2" style="margin: 0; font-size: 16px; line-height: 1.5;">${category.name}</h6>
              <!-- Image -->
              <c:choose>
                <c:when test="${empty category.imageName}">
                  <img src="${pageContext.request.contextPath}/img/50px.png" alt="No Image"
                       style="width: auto; height: 150px; object-fit: cover; position: absolute; top: -30px; right: 10px;">
                </c:when>
                <c:otherwise>
                  <img src="${pageContext.request.contextPath}/image/${category.imageName}" alt="${category.imageName}"
                       style="width: auto; height: 150px; object-fit: cover; position: absolute; top: -30px; right: 10px;">
                </c:otherwise>
              </c:choose>
            </a>
          </div>
        </div>
      </c:forEach>
    </div>
  </div> <!-- container.// -->
</section> <!-- section-content.// -->

<%--Sản phẩm mới nhất--%>
<section class="section-content mb-5 bg-light">
  <div class="container">
    <header class="section-heading py-4 d-flex justify-content-between">
      <h3 class="section-title">Sản phẩm mới nhất</h3>
    </header> <!-- section-heading.// -->
    <div class="row item-grid">
      <c:forEach var="product" items="${requestScope.products}">
        <div class="col-xl-3 col-lg-4 col-md-6 text-center">
          <div class="card p-3 shadow-sm rounded-4 h-100 border-0 text-center ">
            <!-- Image -->
            <a href="${pageContext.request.contextPath}/product?id=${product.id}" class="img-wrap text-center ">
              <div style="width: 150px; height: 150px; margin: 0 auto; overflow: hidden; border-radius: 10px; background-color: #f8f9fa;">
                <c:choose>
                  <c:when test="${empty product.imageName}">
                    <img class="img-fluid "
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
            <!-- Product Info -->
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
                      <fmt:formatNumber
                              pattern="#,##0"
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
    </div> <!-- row.// -->
  </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="_footer.jsp"/>
</body>

</html>
