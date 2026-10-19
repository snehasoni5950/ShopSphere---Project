<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="ShopSphere — RTU Advanced Java E-Commerce Store" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> ${param.success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Hero Banner -->
    <div class="hero-banner p-4 p-md-5 mb-5 shadow">
        <div class="row align-items-center">
            <div class="col-lg-8">
                <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3">
                    <i class="bi bi-award-fill me-1"></i> RTU Java Enterprise Capstone
                </span>
                <h1 class="display-5 fw-bold mb-3">Welcome to ShopSphere</h1>
                <p class="lead mb-4 opacity-90">
                    A comprehensive N-tier e-commerce enterprise application built with Jakarta Servlet 6.1, JSP, JSTL,
                    JDBC, MySQL, Java Swing, RMI, and java.net Sockets.
                </p>
                <div class="d-flex flex-wrap gap-3">
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-light btn-lg px-4 fw-semibold text-primary">
                        <i class="bi bi-cart3 me-1"></i> Browse Catalog
                    </a>
                    <a href="${pageContext.request.contextPath}/demo/jsp-elements.jsp" class="btn btn-outline-light btn-lg px-4">
                        <i class="bi bi-code-slash me-1"></i> Explore RTU Demos
                    </a>
                </div>
            </div>
            <div class="col-lg-4 d-none d-lg-block text-center">
                <i class="bi bi-layers-half text-white-50" style="font-size: 10rem;"></i>
            </div>
        </div>
    </div>

    <!-- Category Pills -->
    <div class="mb-5">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="fw-bold m-0"><i class="bi bi-grid-fill text-primary me-2"></i>Shop by Category</h4>
            <a href="${pageContext.request.contextPath}/products" class="text-primary text-decoration-none fw-semibold small">View All &rarr;</a>
        </div>
        <div class="d-flex flex-wrap gap-2">
            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-dark category-pill px-4 py-2">All Products</a>
            <c:forEach var="cat" items="${categories}">
                <a href="${pageContext.request.contextPath}/products?category=${cat.categoryId}" class="btn btn-outline-secondary category-pill px-4 py-2">
                    ${cat.name}
                </a>
            </c:forEach>
        </div>
    </div>

    <!-- Featured Products Grid -->
    <div class="mb-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h4 class="fw-bold m-0"><i class="bi bi-stars text-warning me-2"></i>Featured Deals</h4>
                <p class="text-muted small m-0">Handpicked items with exclusive discounts</p>
            </div>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary btn-sm px-3">Explore All</a>
        </div>

        <div class="row">
            <c:forEach var="p" items="${products}">
                <tags:productCard product="${p}" />
            </c:forEach>
        </div>
    </div>

    <!-- Syllabus Architecture Callout Banner -->
    <div class="card border-0 bg-light shadow-sm p-4 rounded-4">
        <div class="row align-items-center g-4">
            <div class="col-md-8">
                <h5 class="fw-bold mb-2"><i class="bi bi-mortarboard text-primary me-2"></i>RTU Advanced Java Syllabus Coverage</h5>
                <p class="text-muted small mb-0">
                    This project includes dedicated executable modules for Swing Desktop Admin, java.net Socket/ServerSocket inventory server,
                    Java RMI Registry & Remote Services, JNDI lookups, URLConnection content handlers, and JSTL XML/SQL.
                </p>
            </div>
            <div class="col-md-4 text-md-end">
                <a href="${pageContext.request.contextPath}/demo/servlet-lifecycle" class="btn btn-primary px-4">
                    Inspect Architecture &rarr;
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
