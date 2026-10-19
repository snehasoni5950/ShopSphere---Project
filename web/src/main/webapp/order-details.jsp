<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Order #SP-${order.orderId} Details — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <nav aria-label="breadcrumb" class="mb-3">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/orders">Orders</a></li>
            <li class="breadcrumb-item active" aria-current="page">Order #SP-${order.orderId}</li>
        </ol>
    </nav>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold mb-1">Order #SP-${order.orderId}</h3>
            <span class="text-muted small">Placed on ${order.createdAt}</span>
        </div>
        <button onclick="window.print()" class="btn btn-outline-secondary btn-sm"><i class="bi bi-printer me-1"></i> Print Invoice</button>
    </div>

    <!-- Status Badge & Timeline -->
    <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
        <h6 class="fw-bold mb-3">Order Status: <span class="badge bg-primary px-3 py-2 ms-2">${order.orderStatus}</span></h6>
        <div class="progress" style="height: 6px;">
            <div class="progress-bar bg-success" role="progressbar"
                 style="width: ${order.orderStatus == 'DELIVERED' ? '100%' : (order.orderStatus == 'SHIPPED' ? '70%' : (order.orderStatus == 'CONFIRMED' ? '40%' : '15%'))};">
            </div>
        </div>
        <div class="d-flex justify-content-between small text-muted mt-2">
            <span>Order Placed</span>
            <span>Confirmed</span>
            <span>Shipped</span>
            <span>Delivered</span>
        </div>
    </div>

    <div class="row g-4">
        <!-- Item List -->
        <div class="col-lg-8">
            <div class="card border-0 shadow-sm rounded-4 overflow-hidden mb-4">
                <div class="card-header bg-light py-3 px-4 fw-bold">Items in this Order</div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table align-middle mb-0">
                            <tbody>
                                <c:forEach var="item" items="${order.items}">
                                    <tr>
                                        <td class="ps-4">
                                            <div class="d-flex align-items-center gap-3">
                                                <img src="${not empty item.product.imageUrl ? item.product.imageUrl : 'https://placehold.co/60x60'}"
                                                     alt="${item.product.name}" class="rounded-2" style="width: 50px; height: 50px; object-fit: contain;">
                                                <div>
                                                    <h6 class="mb-0 fw-bold">
                                                        <a href="${pageContext.request.contextPath}/product-details?id=${item.productId}" class="text-dark text-decoration-none">
                                                            ${item.product.name}
                                                        </a>
                                                    </h6>
                                                    <span class="text-muted small">${item.product.brand}</span>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="text-muted">Qty: ${item.quantity}</td>
                                        <td><app:currency value="${item.price}" /></td>
                                        <td class="text-end pe-4 fw-bold text-primary"><app:currency value="${item.subtotal}" /></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- Order Information Summary -->
        <div class="col-lg-4">
            <!-- Shipping Info -->
            <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
                <h6 class="fw-bold mb-3"><i class="bi bi-geo-alt-fill text-primary me-2"></i>Delivery Address</h6>
                <p class="small text-secondary mb-0">
                    <c:choose>
                        <c:when test="${not empty order.shippingAddress}">
                            ${order.shippingAddress.formattedAddress}
                        </c:when>
                        <c:otherwise>Standard Digital/Default Address</c:otherwise>
                    </c:choose>
                </p>
            </div>

            <!-- Payment Info -->
            <div class="card border-0 shadow-sm rounded-4 p-4">
                <h6 class="fw-bold mb-3"><i class="bi bi-credit-card-2-front-fill text-primary me-2"></i>Payment Details</h6>
                <div class="d-flex justify-content-between small mb-2">
                    <span class="text-muted">Payment Method:</span>
                    <span class="fw-semibold">${order.paymentMethod}</span>
                </div>
                <div class="d-flex justify-content-between small mb-2">
                    <span class="text-muted">Payment Status:</span>
                    <span class="badge ${order.paymentStatus == 'PAID' ? 'bg-success' : 'bg-warning text-dark'}">${order.paymentStatus}</span>
                </div>
                <c:if test="${not empty payment}">
                    <div class="d-flex justify-content-between small mb-2">
                        <span class="text-muted">Transaction Ref:</span>
                        <span class="fw-bold text-truncate" style="max-width: 140px;">${payment.transactionReference}</span>
                    </div>
                </c:if>
                <hr>
                <div class="d-flex justify-content-between align-items-center">
                    <span class="fw-bold">Total Paid:</span>
                    <span class="fs-5 fw-bold text-primary"><app:currency value="${order.totalAmount}" /></span>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
