<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Products Catalog — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <div class="row mb-4">
        <div class="col-12">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Products</li>
                </ol>
            </nav>
            <div class="d-flex flex-wrap justify-content-between align-items-center gap-3">
                <h3 class="fw-bold m-0">
                    <c:choose>
                        <c:when test="${not empty searchQuery}">
                            Search Results for: <span class="text-primary">"${searchQuery}"</span>
                        </c:when>
                        <c:otherwise>
                            Our Catalog
                        </c:otherwise>
                    </c:choose>
                </h3>
                <span class="text-muted small">${products.size()} products available</span>
            </div>
        </div>
    </div>

    <!-- Category Filters -->
    <div class="d-flex flex-wrap gap-2 mb-4">
        <a href="${pageContext.request.contextPath}/products"
           class="btn btn-sm ${empty selectedCategory ? 'btn-primary' : 'btn-outline-secondary'}">
            All Categories
        </a>
        <c:forEach var="cat" items="${categories}">
            <a href="${pageContext.request.contextPath}/products?category=${cat.categoryId}"
               class="btn btn-sm ${selectedCategory == cat.categoryId ? 'btn-primary' : 'btn-outline-secondary'}">
                ${cat.name}
            </a>
        </c:forEach>
    </div>

    <!-- Product Grid -->
    <c:choose>
        <c:when test="${not empty products}">
            <div class="row">
                <c:forEach var="p" items="${products}">
                    <tags:productCard product="${p}" />
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center py-5 bg-white rounded-3 shadow-sm">
                <i class="bi bi-search text-muted" style="font-size: 3rem;"></i>
                <h5 class="fw-bold mt-3">No products match your criteria</h5>
                <p class="text-muted">Try clearing filters or searching for different keywords.</p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary px-4 mt-2">View All Products</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
