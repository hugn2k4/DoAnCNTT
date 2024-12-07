<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<footer class="section-footer">
  <section class="footer-top py-5 bg-light">
    <div class="container">
      <div class="row">
        <aside class="col-sm-6 col-lg-3">
          <h6 class="pb-2">Giới thiệu</h6>
          <ul class="list-unstyled">
            <li><a href="#">Về Shop</a></li>
            <li><a href="#">Tuyển dụng</a></li>
            <li><a href="#">Chính sách thanh toán</a></li>
            <li><a href="#">Chính sách bảo mật</a></li>
            <li><a href="#">Giải quyết khiếu nại</a></li>
            <li><a href="#">Hợp tác</a></li>
          </ul>
        </aside>
        <aside class="col-sm-6 col-lg-3">
          <h6 class="pb-2">Hỗ trợ khách hàng</h6>
          <ul class="list-unstyled">
            <li>Hotline: 076-939-4157</li>
            <li><a href="#">Câu hỏi thường gặp</a></li>
            <li><a href="#">Hướng dẫn đặt hàng</a></li>
            <li><a href="#">Phương thức vận chuyển</a></li>
            <li><a href="#">Chính sách đổi trả</a></li>
          </ul>
        </aside>
        <aside class="col-lg-5">
          <h6 class="pb-2">Đăng ký nhận tin</h6>
          <form action="${pageContext.request.contextPath}/news" method = "post" >
            <div class="input-group w-100">
              <input
                type="text"
                class="form-control"
                ${not empty requestScope.violations.emailViolations
                ? 'is-invalid' : (not empty requestScope.values.email ? 'is-valid' : '')}
                placeholder="Email của bạn ..."
                name ="email"
                value="${requestScope.values.email}"
              />
              <c:if test="${not empty requestScope.violations.emailViolations}">
                <div class="invalid-feedback">
                  <ul class="list-unstyled">
                    <c:forEach var="violation" items="${requestScope.violations.emailViolations}">
                      <li>${violation}</li>
                    </c:forEach>
                  </ul>
                </div>
              </c:if>
              <c:if test="${not empty requestScope.success}">
                <div class="alert alert-success">${requestScope.success}</div>
              </c:if>
              <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">${requestScope.error}</div>
              </c:if>
              <button class="btn btn-primary" type="submit" >Đăng ký</button>
            </div>
          </form>
        </aside>
      </div>
      <!-- row.// -->
    </div>
    <!-- container.// -->
  </section>
  <!-- footer-top.// -->

  <section class="footer-bottom text-center bg-light border-top py-3">
    <div class="container-fluid">© Đồ án web 2024 - HCMUTE</div>
  </section>
</footer>
<!-- section-footer.// -->
