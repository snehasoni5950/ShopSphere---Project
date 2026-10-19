<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Secure Checkout — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <h3 class="fw-bold mb-4"><i class="bi bi-shield-lock-fill text-success me-2"></i>Checkout & Payment</h3>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i> ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="row g-4">
        <!-- Left: Address & Payment Selection -->
        <div class="col-lg-7">
            <form action="${pageContext.request.contextPath}/checkout" method="post" id="checkoutForm">
                <!-- 1. Shipping Address -->
                <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h5 class="fw-bold m-0"><i class="bi bi-geo-alt-fill text-primary me-2"></i>1. Delivery Address</h5>
                        <a href="${pageContext.request.contextPath}/profile" class="small text-primary text-decoration-none fw-semibold">+ Manage Addresses</a>
                    </div>

                    <c:choose>
                        <c:when test="${not empty addresses}">
                            <div class="d-flex flex-column gap-2">
                                <c:forEach var="addr" items="${addresses}" varStatus="loop">
                                    <div class="form-check p-3 border rounded-3 bg-light">
                                        <input class="form-check-input ms-0 me-3" type="radio" name="addressId" id="addr_${addr.addressId}"
                                               value="${addr.addressId}" ${loop.first ? 'checked' : ''} required>
                                        <label class="form-check-label" for="addr_${addr.addressId}">
                                            <span class="badge bg-secondary mb-1">${addr.addressType}</span>
                                            <div class="fw-bold">${sessionScope.user.name}</div>
                                            <div class="small text-secondary">${addr.formattedAddress}</div>
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-warning small mb-0">
                                <i class="bi bi-exclamation-circle me-1"></i> No saved delivery address found.
                                <a href="${pageContext.request.contextPath}/profile" class="fw-bold">Add an address in your profile</a> first.
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- 2. Payment Method -->
                <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
                    <h5 class="fw-bold mb-3"><i class="bi bi-credit-card-2-front-fill text-primary me-2"></i>2. Payment Method</h5>
                    <div class="d-flex flex-column gap-2">
                        <div class="form-check p-3 border rounded-3">
                            <input class="form-check-input ms-0 me-3" type="radio" name="paymentMethod" id="payUPI" value="UPI" checked>
                            <label class="form-check-label" for="payUPI">
                                <span class="fw-bold"><i class="bi bi-phone me-1 text-primary"></i> Instant UPI / QR Code</span>
                                <div class="small text-muted">Google Pay, PhonePe, Paytm, BHIM</div>
                            </label>
                        </div>
                        <div class="form-check p-3 border rounded-3">
                            <input class="form-check-input ms-0 me-3" type="radio" name="paymentMethod" id="payCard" value="CARD">
                            <label class="form-check-label" for="payCard">
                                <span class="fw-bold"><i class="bi bi-credit-card me-1 text-primary"></i> Credit / Debit Card</span>
                                <div class="small text-muted">Visa, Mastercard, RuPay, Maestro</div>
                            </label>
                        </div>
                        <div class="form-check p-3 border rounded-3">
                            <input class="form-check-input ms-0 me-3" type="radio" name="paymentMethod" id="payCOD" value="COD">
                            <label class="form-check-label" for="payCOD">
                                <span class="fw-bold"><i class="bi bi-cash-stack me-1 text-success"></i> Cash on Delivery (COD)</span>
                                <div class="small text-muted">Pay in cash or UPI upon package arrival</div>
                            </label>
                        </div>
                    </div>
                </div>

                <!-- Hidden coupon code passed from right panel -->
                <input type="hidden" name="couponCode" value="${appliedCoupon.code}">

                <button type="submit" class="btn btn-primary btn-lg w-100 py-3 fw-bold rounded-3 shadow-sm"
                        ${empty addresses ? 'disabled' : ''}>
                    Place Order & Pay <app:currency value="${total}" /> <i class="bi bi-arrow-right ms-2"></i>
                </button>
            </form>
        </div>

        <!-- Right: Coupon & Order Summary -->
        <div class="col-lg-5">
            <!-- Coupon Card -->
            <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
                <h6 class="fw-bold mb-3"><i class="bi bi-ticket-perforated-fill text-warning me-2"></i>Have a Coupon Code?</h6>
                <form action="${pageContext.request.contextPath}/checkout" method="get" class="d-flex gap-2">
                    <input type="text" name="coupon" class="form-control text-uppercase" placeholder="e.g. WELCOME50, FESTIVE10" value="${appliedCoupon.code}">
                    <button type="submit" class="btn btn-dark">Apply</button>
                </form>
                <c:if test="${not empty appliedCoupon}">
                    <div class="alert alert-success mt-3 mb-0 small">
                        <i class="bi bi-check-circle me-1"></i> Coupon <strong>${appliedCoupon.code}</strong> applied! You saved <app:currency value="${discount}" />.
                    </div>
                </c:if>
                <c:if test="${not empty couponError}">
                    <div class="alert alert-danger mt-3 mb-0 small">
                        <i class="bi bi-x-circle me-1"></i> ${couponError}
                    </div>
                </c:if>
                <div class="mt-2 text-muted" style="font-size: 0.75rem;">
                    Try sample codes: <strong>WELCOME50</strong> (₹50 off) or <strong>FESTIVE10</strong> (10% off)
                </div>
            </div>

            <!-- Items & Price Summary -->
            <div class="card border-0 shadow-sm rounded-4 p-4">
                <h5 class="fw-bold mb-3">Order Breakdown</h5>
                <div class="d-flex flex-column gap-2 mb-3">
                    <c:forEach var="item" items="${cartItems}">
                        <div class="d-flex justify-content-between align-items-center small">
                            <span class="text-truncate me-2" style="max-width: 240px;">
                                ${item.product.name} <span class="text-muted">x${item.quantity}</span>
                            </span>
                            <span class="fw-semibold"><app:currency value="${item.subtotal}" /></span>
                        </div>
                    </c:forEach>
                </div>

                <hr>

                <div class="d-flex justify-content-between mb-2 small">
                    <span class="text-muted">Subtotal:</span>
                    <span class="fw-semibold"><app:currency value="${subtotal}" /></span>
                </div>
                <div class="d-flex justify-content-between mb-2 small text-success">
                    <span>Coupon Discount:</span>
                    <span class="fw-semibold">- <app:currency value="${discount}" /></span>
                </div>
                <div class="d-flex justify-content-between mb-3 small">
                    <span class="text-muted">Delivery Charges:</span>
                    <span class="text-success fw-bold">FREE</span>
                </div>

                <hr>

                <div class="d-flex justify-content-between align-items-baseline">
                    <span class="fs-5 fw-bold">Total Amount:</span>
                    <span class="fs-4 fw-bold text-primary"><app:currency value="${total}" /></span>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
