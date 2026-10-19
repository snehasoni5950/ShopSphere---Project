<%@ tag description="Product Card Component" pageEncoding="UTF-8"%>
<%@ attribute name="product" required="true" type="com.shopsphere.model.Product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
    <div class="card h-100 shadow-sm border-0 product-card position-relative">
        <c:if test="${product.discount > 0}">
            <span class="position-absolute top-0 start-0 badge rounded-pill bg-danger m-2 px-2 py-1">
                -${product.discount}% OFF
            </span>
        </c:if>

        <a href="${pageContext.request.contextPath}/product-details?id=${product.productId}" class="text-decoration-none">
            <div class="product-img-wrapper d-flex align-items-center justify-content-center bg-light" style="height: 220px; overflow: hidden;">
                <img src="${not empty product.imageUrl ? product.imageUrl : 'https://placehold.co/400x300?text=ShopSphere'}"
                     class="card-img-top img-fluid"
                     alt="${product.name}"
                     style="max-height: 200px; object-fit: contain;">
            </div>
        </a>

        <div class="card-body d-flex flex-column">
            <span class="text-muted small text-uppercase mb-1">${product.brand}</span>
            <h6 class="card-title fw-bold text-truncate" title="${product.name}">
                <a href="${pageContext.request.contextPath}/product-details?id=${product.productId}" class="text-dark text-decoration-none">
                    ${product.name}
                </a>
            </h6>

            <div class="mt-auto">
                <div class="d-flex align-items-baseline gap-2 mb-2">
                    <span class="fs-5 fw-bold text-primary"><app:currency value="${product.finalPrice}"/></span>
                    <c:if test="${product.discount > 0}">
                        <span class="text-muted text-decoration-line-through small"><app:currency value="${product.price}"/></span>
                    </c:if>
                </div>

                <div class="mb-3">
                    <app:stockBadge stock="${product.stock}"/>
                </div>

                <div class="d-flex gap-2">
                    <form action="${pageContext.request.contextPath}/cart" method="post" class="flex-grow-1">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <input type="hidden" name="quantity" value="1">
                        <button type="submit" class="btn btn-primary btn-sm w-100 d-flex align-items-center justify-content-center gap-1"
                                ${product.stock <= 0 ? 'disabled' : ''}>
                            <i class="bi bi-cart-plus"></i> Add
                        </button>
                    </form>

                    <form action="${pageContext.request.contextPath}/wishlist" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <button type="submit" class="btn btn-outline-secondary btn-sm" title="Add to Wishlist">
                            <i class="bi bi-heart"></i>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
