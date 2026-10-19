<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<footer class="bg-dark text-white pt-5 pb-4 mt-5 border-top border-secondary">
    <div class="container">
        <div class="row g-4">
            <div class="col-12 col-md-4">
                <h5 class="fw-bold mb-3 d-flex align-items-center gap-2">
                    <span class="bg-primary text-white px-2 py-1 rounded-2 fs-6"><i class="bi bi-bag-heart-fill"></i></span>
                    ShopSphere
                </h5>
                <p class="text-secondary small">
                    An enterprise full-stack Java e-commerce system built for the Rajasthan Technical University (RTU) Advanced Java syllabus.
                </p>
                <div class="d-flex gap-2">
                    <span class="badge bg-secondary">Java 17</span>
                    <span class="badge bg-secondary">Servlet 6.1</span>
                    <span class="badge bg-secondary">Tomcat 11</span>
                    <span class="badge bg-secondary">MySQL 8.0</span>
                    <span class="badge bg-secondary">Swing</span>
                    <span class="badge bg-secondary">RMI</span>
                </div>
            </div>

            <div class="col-6 col-md-2">
                <h6 class="text-uppercase fw-bold text-light mb-3">Quick Links</h6>
                <ul class="list-unstyled small">
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/" class="text-secondary text-decoration-none">Home</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/products" class="text-secondary text-decoration-none">All Products</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/cart" class="text-secondary text-decoration-none">My Cart</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/orders" class="text-secondary text-decoration-none">Order Tracking</a></li>
                </ul>
            </div>

            <div class="col-6 col-md-3">
                <h6 class="text-uppercase fw-bold text-light mb-3">RTU Syllabus Demos</h6>
                <ul class="list-unstyled small">
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/demo/jsp-elements.jsp" class="text-secondary text-decoration-none">JSP Traditional Syntax</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/demo/jstl-xml.jsp" class="text-secondary text-decoration-none">JSTL XML Feed Parser</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/demo/jstl-sql.jsp" class="text-secondary text-decoration-none">JSTL SQL Integration</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/demo/servlet-lifecycle" class="text-secondary text-decoration-none">Servlet Lifecycle & Config</a></li>
                    <li class="mb-2"><a href="${pageContext.request.contextPath}/demo/i18n-demo.jsp" class="text-secondary text-decoration-none">Internationalization</a></li>
                </ul>
            </div>

            <div class="col-12 col-md-3">
                <h6 class="text-uppercase fw-bold text-light mb-3">System Metrics</h6>
                <p class="text-secondary small mb-1">
                    <i class="bi bi-hdd-network me-1"></i> Web Container: Apache Tomcat 11
                </p>
                <p class="text-secondary small mb-1">
                    <i class="bi bi-people me-1"></i> Active Web Sessions: 
                    <span class="badge bg-primary">${applicationScope.activeUserCount}</span>
                </p>
                <p class="text-secondary small mb-3">
                    <i class="bi bi-database me-1"></i> Persistence: MySQL via JDBC DAO
                </p>
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline-warning btn-sm w-100">
                    <i class="bi bi-shield-lock me-1"></i> Admin Portal
                </a>
            </div>
        </div>

        <hr class="border-secondary my-4">

        <div class="d-flex flex-wrap justify-content-between align-items-center small text-secondary">
            <span>&copy; 2026 ShopSphere Project. All rights reserved.</span>
            <span>Rajasthan Technical University (RTU) — Final Practical Examination</span>
        </div>
    </div>
</footer>

<!-- Bootstrap 5 Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
