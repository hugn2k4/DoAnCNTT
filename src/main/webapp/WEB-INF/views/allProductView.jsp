<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
    <jsp:include page="_meta.jsp"/>
    <title>Tất cả sản phẩm</title>
</head>

<body>
<jsp:include page="_header.jsp"/>

<section class="section-pagetop bg-light">
    <div class="container">
        <h2 class="title-page">Tất cả sản phẩm</h2>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item" aria-current="page">
                    <a href="${pageContext.request.contextPath}/">Trang chủ</a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">Tất cả sản phẩm</li>
            </ol>
        </nav>
    </div>
</section>

<section class="section-content padding-y">
    <div class="container">
        <div class="row">
            <!-- Sidebar bộ lọc -->
            <aside class="col-md-4 col-lg-3 mb-md-0 mb-3">
                <div class="card">
                    <form action="${pageContext.request.contextPath}/all-products" method="get">

                        <article class="filter-group">
                            <header class="card-header my-1">
                                <a data-bs-toggle="collapse" href="#collapse_4" aria-expanded="true" aria-controls="collapse_4">
                                    <i class="float-end bi bi-chevron-down"></i>
                                    <h6 class="title fw-bold">Ram</h6>
                                </a>
                            </header>
                            <div class="filter-content collapse show" id="collapse_4">
                                <div class="card-body pt-0">
                                    <c:forEach var="ram" items="${rams}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="${ram}" id="checkbox_ram_${ram}" name="checkedRams"
                                                ${fn:contains(checkedRams, ram) ? 'checked' : ''}>
                                            <label class="form-check-label" for="checkbox_ram_${ram}">
                                                Ram ${ram}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div> <!-- card-body.// -->
                            </div>
                        </article>

                        <article class="filter-group">
                            <header class="card-header my-1">
                                <a data-bs-toggle="collapse" href="#collapse_5" aria-expanded="true" aria-controls="collapse_5">
                                    <i class="float-end bi bi-chevron-down"></i>
                                    <h6 class="title fw-bold">CPU</h6>
                                </a>
                            </header>
                            <div class="filter-content collapse show" id="collapse_5">
                                <div class="card-body pt-0">
                                    <c:forEach var="cpu" items="${cpus}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="${cpu}" id="checkbox_cpu_${cpu}" name="checkedCpus"
                                                ${fn:contains(checkedCpus, cpu) ? 'checked' : ''}>
                                            <label class="form-check-label" for="checkbox_cpu_${cpu}">
                                                    ${cpu}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div> <!-- card-body.// -->
                            </div>
                        </article>

                        <article class="filter-group">
                            <header class="card-header my-1">
                                <a data-bs-toggle="collapse" href="#collapse_6" aria-expanded="true" aria-controls="collapse_6">
                                    <i class="float-end bi bi-chevron-down"></i>
                                    <h6 class="title fw-bold">VGA</h6>
                                </a>
                            </header>
                            <div class="filter-content collapse show" id="collapse_6">
                                <div class="card-body pt-0">
                                    <c:forEach var="vga" items="${vgas}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="${vga}" id="checkbox_vga_${vga}" name="checkedVgas"
                                                ${fn:contains(checkedVgas, vga) ? 'checked' : ''}>
                                            <label class="form-check-label" for="checkbox_vga_${vga}">
                                                    ${vga}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div> <!-- card-body.// -->
                            </div>
                        </article>

                        <article class="filter-group">
                            <header class="card-header my-1">
                                <a data-bs-toggle="collapse" href="#collapse_2" aria-expanded="true"
                                   aria-controls="collapse_2">
                                    <i class="float-end bi bi-chevron-down"></i>
                                    <h6 class="title fw-bold">Giá bán</h6>
                                </a>
                            </header>
                            <div class="filter-content collapse show" id="collapse_2">
                                <div class="card-body pt-0">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" value="0-5000000"
                                               id="checkbox_price_1" name="priceRanges"
                                        ${requestScope.priceRanges.contains('0-50000') ? 'checked' : ''}>
                                        <label class="form-check-label" for="checkbox_price_1">
                                            Dưới 5.000.000₫
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" value="5000000-20000000"
                                               id="checkbox_price_2" name="priceRanges"
                                        ${requestScope.priceRanges.contains('50000-20000000') ? 'checked' : ''}>
                                        <label class="form-check-label" for="checkbox_price_2">
                                            Từ 5.000.000₫ đến 20.0000.000₫
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" value="20000000-infinity"
                                               id="checkbox_price_3" name="priceRanges"
                                        ${requestScope.priceRanges.contains('20000000-infinity') ? 'checked' : ''}>
                                        <label class="form-check-label" for="checkbox_price_3">
                                            Trên 20.000.000₫
                                        </label>
                                    </div>
                                </div> <!-- card-body.// -->
                            </div>
                        </article>

                        <article class="filter-group">
                            <header class="card-header my-1">
                                <a data-bs-toggle="collapse" href="#collapse_3" aria-expanded="true"
                                   aria-controls="collapse_3">
                                    <i class="float-end bi bi-chevron-down"></i>
                                    <h6 class="title fw-bold">Sắp xếp theo</h6>
                                </a>
                            </header>
                            <div class="filter-content collapse show" id="collapse_3">
                                <div class="card-body pt-0">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="totalBuy-DESC" name="order"
                                               id="radio_order_1" ${requestScope.order == 'totalBuy-DESC' ? 'checked' : ''}>
                                        <label class="form-check-label" for="radio_order_1">
                                            Bán chạy nhất
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="createdAt-DESC" name="order"
                                               id="radio_order_2" ${requestScope.order == 'createdAt-DESC' ? 'checked' : ''}>
                                        <label class="form-check-label" for="radio_order_2">
                                            Mới nhất
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="price-ASC" name="order"
                                               id="radio_order_3" ${requestScope.order == 'price-ASC' ? 'checked' : ''}>
                                        <label class="form-check-label" for="radio_order_3">
                                            Giá thấp nhất
                                        </label>
                                    </div>
                                </div> <!-- card-body.// -->
                            </div>
                        </article>

                        <article class="card-body">
                            <button type="submit" class="btn btn-primary w-100">Lọc</button>
                        </article>
                    </form>
                </div>
            </aside>

            <!-- Danh sách sản phẩm -->
            <main class="col-md-8 col-lg-9">
                <header class="border-bottom mb-4 pb-3">
                    <div class="form-inline d-flex justify-content-between align-items-center">
                        <span>${requestScope.totalProducts} sản phẩm</span>
                    </div>
                </header>

                <div class="row item-grid">
                    <c:forEach var="product" items="${products}">
                        <div class="col-xl-4 col-lg-6 text-center">
                            <div class="card p-3 shadow-sm rounded-4 h-100 border-0 text-center">
                                <a href="${pageContext.request.contextPath}/product?id=${product.id}" class="img-wrap">
                                    <img class="img-fluid" src="${pageContext.request.contextPath}/image/${product.imageName}" alt="${product.name}">
                                </a>
                                <figcaption class="info-wrap mt-3">
                                    <a href="${pageContext.request.contextPath}/product?id=${product.id}" class="title fw-bold">${product.name}</a>
                                    <div class="mt-2">
                                        <fmt:formatNumber value="${product.price}" type="currency"/>₫
                                    </div>
                                </figcaption>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </main>
        </div>
    </div>
</section>

<jsp:include page="_footer.jsp"/>
</body>

</html>
