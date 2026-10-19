<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Order Placed Successfully — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-7">
            <div class="card border-0 shadow-sm rounded-4 text-center p-5">
                <div class="mb-4">
                    <span class="bg-success text-white p-3 rounded-circle d-inline-flex">
                        <i class="bi bi-check-lg" style="font-size: 2.5rem;"></i>
                    </span>
                </div>

                <h3 class="fw-bold mb-2">Thank you for your order!</h3>
                <p class="text-muted">Your order has been placed and is currently being processed.</p>

                <div class="p-3 bg-light rounded-3 my-4 text-start small">
                    <div class="row g-2">
                        <div class="col-sm-6">
                            <span class="text-muted d-block">Order Number:</span>
                            <span class="fw-bold fs-6">#SP-${order.orderId}</span>
                        </div>
                        <div class="col-sm-6">
                            <span class="text-muted d-block">Payment Method:</span>
                            <span class="fw-bold">${order.paymentMethod} (${order.paymentStatus})</span>
                        </div>
                        <div class="col-sm-6">
                            <span class="text-muted d-block">Transaction Ref:</span>
                            <span class="fw-bold">${not empty payment ? payment.transactionReference : 'COD-ORDER'}</span>
                        </div>
                        <div class="col-sm-6">
                            <span class="text-muted d-block">Amount Paid:</span>
                            <span class="fw-bold text-primary fs-6"><app:currency value="${order.totalAmount}" /></span>
                        </div>
                    </div>
                </div>

                <div class="d-flex flex-wrap gap-2 justify-content-center">
                    <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary px-4 py-2">
                        <i class="bi bi-box-seam me-1"></i> View Order History
                    </a>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-secondary px-4 py-2">
                        Continue Shopping
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
