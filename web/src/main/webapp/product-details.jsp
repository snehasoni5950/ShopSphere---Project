<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="${product.name} — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <nav aria-label="breadcrumb" class="mb-3">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/products">Products</a></li>
            <li class="breadcrumb-item active" aria-current="page">${product.name}</li>
        </ol>
    </nav>

    <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> ${param.success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="card border-0 shadow-sm p-4 mb-5 rounded-4">
        <div class="row g-4">
            <!-- Product Image -->
            <div class="col-md-5 text-center d-flex align-items-center justify-content-center bg-light rounded-3 p-4">
                <img src="${not empty product.imageUrl ? product.imageUrl : 'https://placehold.co/400x350?text=ShopSphere'}"
                     alt="${product.name}" class="img-fluid" style="max-height: 380px; object-fit: contain;">
            </div>

            <!-- Product Details -->
            <div class="col-md-7 d-flex flex-column">
                <span class="text-uppercase text-muted fw-semibold small mb-1">${product.brand}</span>
                <h2 class="fw-bold mb-2">${product.name}</h2>
                <div class="mb-3">
                    <span class="badge bg-secondary me-2">${product.categoryName}</span>
                    <app:stockBadge stock="${product.stock}" />
                </div>

                <div class="d-flex align-items-baseline gap-3 mb-3">
                    <h2 class="text-primary fw-bold mb-0"><app:currency value="${product.finalPrice}" /></h2>
                    <c:if test="${product.discount > 0}">
                        <h5 class="text-muted text-decoration-line-through mb-0"><app:currency value="${product.price}" /></h5>
                        <span class="badge bg-danger">Save ${product.discount}%</span>
                    </c:if>
                </div>

                <hr class="my-3">

                <h6 class="fw-bold">Product Description:</h6>
                <p class="text-secondary leading-relaxed mb-4">${product.description}</p>

                <div class="mt-auto">
                    <form action="${pageContext.request.contextPath}/cart" method="post" class="row g-3 align-items-center">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.productId}">

                        <div class="col-auto">
                            <label class="col-form-label fw-bold">Quantity:</label>
                        </div>
                        <div class="col-auto" style="width: 120px;">
                            <input type="number" name="quantity" class="form-control" value="1" min="1" max="${product.stock}" ${product.stock <= 0 ? 'disabled' : ''}>
                        </div>
                        <div class="col-auto flex-grow-1">
                            <button type="submit" class="btn btn-primary w-100 py-2 d-flex align-items-center justify-content-center gap-2"
                                    ${product.stock <= 0 ? 'disabled' : ''}>
                                <i class="bi bi-cart-plus-fill"></i> Add to Cart
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Customer Reviews Section -->
    <div class="card border-0 shadow-sm p-4 rounded-4">
        <h4 class="fw-bold mb-4"><i class="bi bi-chat-left-quote-fill text-primary me-2"></i>Customer Reviews & Ratings</h4>

        <div class="row g-4">
            <!-- Review Submission Form -->
            <div class="col-md-5 border-end pe-md-4">
                <h6 class="fw-bold mb-3">Write a Customer Review</h6>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <form action="${pageContext.request.contextPath}/review" method="post">
                            <input type="hidden" name="productId" value="${product.productId}">
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Rating (1 to 5 Stars):</label>
                                <select name="rating" class="form-select" required>
                                    <option value="5">⭐⭐⭐⭐⭐ - 5 Stars (Excellent)</option>
                                    <option value="4">⭐⭐⭐⭐ - 4 Stars (Good)</option>
                                    <option value="3">⭐⭐⭐ - 3 Stars (Average)</option>
                                    <option value="2">⭐⭐ - 2 Stars (Poor)</option>
                                    <option value="1">⭐ - 1 Star (Very Bad)</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Your Review Feedback:</label>
                                <textarea name="reviewText" rows="3" class="form-control" placeholder="Share your experience with this product..." required></textarea>
                            </div>
                            <button type="submit" class="btn btn-dark w-100">Submit Review</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info small">
                            <i class="bi bi-info-circle me-1"></i>
                            Please <a href="${pageContext.request.contextPath}/login" class="fw-bold">login</a> to write a review.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Existing Reviews List -->
            <div class="col-md-7 ps-md-4">
                <h6 class="fw-bold mb-3">Recent Customer Feedback (${reviews.size()})</h6>
                <c:choose>
                    <c:when test="${not empty reviews}">
                        <div class="d-flex flex-column gap-3">
                            <c:forEach var="rev" items="${reviews}">
                                <div class="p-3 bg-light rounded-3">
                                    <div class="d-flex justify-content-between align-items-center mb-1">
                                        <span class="fw-bold text-dark"><i class="bi bi-person-circle me-1"></i> ${rev.userName}</span>
                                        <tags:rating score="${rev.rating}" />
                                    </div>
                                    <p class="text-secondary small m-0">${rev.reviewText}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted small">No reviews submitted yet for this product. Be the first to review!</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
