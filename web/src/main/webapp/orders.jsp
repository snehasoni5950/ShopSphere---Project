<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="My Orders — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <h3 class="fw-bold mb-4"><i class="bi bi-box-seam-fill text-primary me-2"></i>My Orders History</h3>

    <c:choose>
        <c:when test="${not empty orders}">
            <div class="d-flex flex-column gap-3">
                <c:forEach var="o" items="${orders}">
                    <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                        <div class="card-header bg-light d-flex flex-wrap justify-content-between align-items-center py-3 px-4">
                            <div class="d-flex flex-wrap gap-4 align-items-center">
                                <div>
                                    <span class="text-muted small d-block">Order Placed</span>
                                    <span class="fw-bold small">${o.createdAt}</span>
                                </div>
                                <div>
                                    <span class="text-muted small d-block">Total Amount</span>
                                    <span class="fw-bold text-primary small"><app:currency value="${o.totalAmount}" /></span>
                                </div>
                                <div>
                                    <span class="text-muted small d-block">Ship To</span>
                                    <span class="fw-bold small">${sessionScope.user.name}</span>
                                </div>
                            </div>
                            <div class="mt-2 mt-md-0">
                                <span class="badge ${o.orderStatus == 'DELIVERED' ? 'bg-success' : (o.orderStatus == 'CANCELLED' ? 'bg-danger' : 'bg-primary')} px-3 py-2">
                                    ${o.orderStatus}
                                </span>
                            </div>
                        </div>

                        <div class="card-body p-4">
                            <div class="row align-items-center">
                                <div class="col-md-9">
                                    <c:forEach var="item" items="${o.items}">
                                        <div class="d-flex align-items-center gap-3 mb-2">
                                            <img src="${not empty item.product.imageUrl ? item.product.imageUrl : 'https://placehold.co/60x60'}"
                                                 alt="${item.product.name}" class="rounded-2" style="width: 50px; height: 50px; object-fit: contain;">
                                            <div>
                                                <h6 class="mb-0 fw-semibold">
                                                    <a href="${pageContext.request.contextPath}/product-details?id=${item.productId}" class="text-dark text-decoration-none">
                                                        ${item.product.name}
                                                    </a>
                                                </h6>
                                                <span class="text-muted small">Qty: ${item.quantity} × <app:currency value="${item.price}" /></span>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="col-md-3 text-md-end mt-3 mt-md-0">
                                    <a href="${pageContext.request.contextPath}/order-details?id=${o.orderId}" class="btn btn-outline-dark btn-sm px-3">
                                        View Order Details &rarr;
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center py-5 bg-white rounded-4 shadow-sm">
                <i class="bi bi-box-seam text-muted" style="font-size: 4rem;"></i>
                <h4 class="fw-bold mt-3">No orders found</h4>
                <p class="text-muted">You haven't placed any orders yet.</p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary px-4 mt-2">Start Shopping</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
