<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="My Wishlist — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <h3 class="fw-bold mb-4"><i class="bi bi-heart-fill text-danger me-2"></i>My Wishlist</h3>

    <c:choose>
        <c:when test="${not empty wishlistItems}">
            <div class="row">
                <c:forEach var="item" items="${wishlistItems}">
                    <tags:productCard product="${item.product}" />
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center py-5 bg-white rounded-4 shadow-sm">
                <i class="bi bi-heart text-muted" style="font-size: 4rem;"></i>
                <h4 class="fw-bold mt-3">Your wishlist is empty</h4>
                <p class="text-muted">Explore our catalog and click the heart icon on products you love.</p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary px-4 mt-2">Discover Products</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
