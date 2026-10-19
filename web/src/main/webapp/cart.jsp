<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Your Shopping Cart — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <h3 class="fw-bold mb-4"><i class="bi bi-cart3 text-primary me-2"></i>Shopping Cart</h3>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${not empty cartItems}">
            <div class="row g-4">
                <!-- Cart Items Table -->
                <div class="col-lg-8">
                    <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                        <div class="table-responsive">
                            <table class="table align-middle mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th scope="col" class="ps-4">Product</th>
                                        <th scope="col">Price</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Subtotal</th>
                                        <th scope="col" class="text-end pe-4">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${cartItems}">
                                        <tr>
                                            <td class="ps-4">
                                                <div class="d-flex align-items-center gap-3">
                                                    <img src="${not empty item.product.imageUrl ? item.product.imageUrl : 'https://placehold.co/80x80'}"
                                                         alt="${item.product.name}" class="rounded-3" style="width: 60px; height: 60px; object-fit: contain;">
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
                                            <td><app:currency value="${item.product.finalPrice}" /></td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/cart" method="post" class="d-inline-flex align-items-center">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="cartId" value="${item.cartId}">
                                                    <input type="number" name="quantity" class="form-control form-control-sm text-center"
                                                           value="${item.quantity}" min="1" max="${item.product.stock}" style="width: 70px;"
                                                           onchange="this.form.submit()">
                                                </form>
                                            </td>
                                            <td class="fw-bold text-primary"><app:currency value="${item.subtotal}" /></td>
                                            <td class="text-end pe-4">
                                                <form action="${pageContext.request.contextPath}/cart" method="post" class="d-inline">
                                                    <input type="hidden" name="action" value="remove">
                                                    <input type="hidden" name="cartId" value="${item.cartId}">
                                                    <button type="submit" class="btn btn-outline-danger btn-sm" title="Remove Item">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Order Summary Column -->
                <div class="col-lg-4">
                    <div class="card border-0 shadow-sm rounded-4 p-4 sticky-top" style="top: 80px;">
                        <h5 class="fw-bold mb-3">Order Summary</h5>
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Total Items:</span>
                            <span class="fw-bold">${sessionScope.cartCount}</span>
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <span class="text-muted">Subtotal:</span>
                            <span class="fs-5 fw-bold text-primary"><app:currency value="${subtotal}" /></span>
                        </div>
                        <p class="text-muted small">Taxes & shipping calculated at checkout.</p>
                        <hr>
                        <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary w-100 py-2 fw-semibold">
                            Proceed to Checkout <i class="bi bi-arrow-right ms-1"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-secondary w-100 mt-2 py-2 small">
                            Continue Shopping
                        </a>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center py-5 bg-white rounded-4 shadow-sm">
                <i class="bi bi-cart-x text-muted" style="font-size: 4rem;"></i>
                <h4 class="fw-bold mt-3">Your Cart is Empty</h4>
                <p class="text-muted">Looks like you haven't added anything to your cart yet.</p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary px-4 mt-2">Start Shopping</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
