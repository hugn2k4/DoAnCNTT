<%--
  Created by IntelliJ IDEA.
  User: hieun
  Date: 12/2/2024
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

<head>
    <jsp:include page="_meta.jsp"/>
    <title>Sửa Chương Trình</title>
</head>

<body>
<jsp:include page="_headerAdmin.jsp"/>

<section class="section-content">
    <div class="container">
        <header class="section-heading py-4">
            <h3 class="section-title">Sửa Chương Trình</h3>
        </header> <!-- section-heading.// -->

        <main class="row mb-5">
            <form class="col-lg-6" method="POST" action="${pageContext.request.contextPath}/admin/promtionManager/update"
                  enctype="multipart/form-data">
                <c:if test="${not empty requestScope.successMessage}">
                    <div class="alert alert-success mb-3" role="alert">
                            ${requestScope.successMessage}
                    </div>
                </c:if>
                <c:if test="${not empty requestScope.errorMessage}">
                    <div class="alert alert-danger mb-3" role="alert">
                            ${requestScope.errorMessage}
                    </div>
                </c:if>
                <div class="mb-3">
                    <label for="promotion-name" class="form-label">Tên chương trình <span class="text-danger">*</span></label>
                    <input type="text"
                           class="form-control ${not empty requestScope.violations.nameViolations
                   ? 'is-invalid' : (not empty requestScope.promotion.name ? 'is-valid' : '')}"
                           id="promotion-name"
                           name="name"
                           value="${requestScope.promotion.name}"
                           required>
                    <c:if test="${not empty requestScope.violations.nameViolations}">
                        <div class="invalid-feedback">
                            <ul class="list-unstyled">
                                <c:forEach var="violation" items="${requestScope.violations.nameViolations}">
                                    <li>${violation}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="promotion-description" class="form-label">Mô tả chương trình</label>
                    <textarea class="form-control ${not empty requestScope.violations.descriptionViolations
                      ? 'is-invalid' : (not empty requestScope.promotion.description ? 'is-valid' : '')}"
                              id="promotion-description"
                              rows="5"
                              name="description">${requestScope.category.description}</textarea>
                    <c:if test="${not empty requestScope.violations.descriptionViolations}">
                        <div class="invalid-feedback">
                            <ul class="list-unstyled">
                                <c:forEach var="violation" items="${requestScope.violations.descriptionViolations}">
                                    <li>${violation}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="promotion-imageName" class="form-label d-block">Hình chương trình</label>

                    <c:choose>
                        <c:when test="${not empty requestScope.promotion.imageName}">
                            <img width="200"
                                 class="img-thumbnail mb-3"
                                 src="${pageContext.request.contextPath}/image/${requestScope.promotion.imageName}"
                                 alt="${requestScope.promotion.imageName}"
                                 title="${requestScope.promotion.imageName}">
                            <div class="mb-3 form-check">
                                <input class="form-check-input"
                                       type="checkbox"
                                       value="deleteImage"
                                       id="delete-image"
                                       name="deleteImage" ${not empty requestScope.deleteImage ? 'checked' : ''}>
                                <label class="form-check-label" for="delete-image">Xóa hình này?</label>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="fst-italic mb-3">Không có hình</div>
                        </c:otherwise>
                    </c:choose>
                    <input type="file"
                           class="form-control"
                           id="promotion-imageName"
                           name="image"
                           accept="image/*">
                </div>
                    <div class="mb-3">
                        <label for="promtion-startsAt" class="form-label">Ngày bắt đầu khuyến mãi</label>
                        <input type="datetime-local"
                               class="form-control ${not empty requestScope.violations.startsAtViolations
                               ? 'is-invalid' : (not empty requestScope.promtion.startsAt ? 'is-valid' : '')}"
                               id="promtion-startsAt"
                               name="startsAt"
                               value="${requestScope.promotion.startsAt}">
                        <c:if test="${not empty requestScope.violations.startsAtViolations}">
                            <div class="invalid-feedback">
                                <ul class="list-unstyled">
                                    <c:forEach var="violation" items="${requestScope.violations.startsAtViolations}">
                                        <li>${violation}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="promtion-endsAt" class="form-label">Ngày kết thúc khuyến mãi</label>
                        <input type="datetime-local"
                               class="form-control ${not empty requestScope.violations.endsAtViolations
                               ? 'is-invalid' : (not empty requestScope.promotin.endsAt ? 'is-valid' : '')}"
                               id="promtion-endsAt"
                               name="endsAt"
                               value="${requestScope.promotion.endsAt}">
                        <c:if test="${not empty requestScope.violations.endsAtViolations}">
                            <div class="invalid-feedback">
                                <ul class="list-unstyled">
                                    <c:forEach var="violation" items="${requestScope.violations.endsAtViolations}">
                                        <li>${violation}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                    <input type="hidden" name="id" value="${requestScope.promotion.id}">
                    <input type="hidden" name="imageName" value="${requestScope.promotion.imageName}">
                    <button type="submit" class="btn btn-primary me-2 mb-3">
                        Sửa
                    </button>
                    <button type="reset"
                            class="btn btn-warning me-2 mb-3"
                            onclick="return confirm('Bạn có muốn để giá trị mặc định?')">
                        Mặc định
                    </button>
                    <a class="btn btn-danger mb-3"
                       href="${pageContext.request.contextPath}/admin/promotionManager"
                       role="button"
                       onclick="return confirm('Bạn có muốn hủy?')">
                        Hủy
                    </a>
                </div>


            </form>
        </main>
    </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="_footerAdmin.jsp"/>
</body>

</html>