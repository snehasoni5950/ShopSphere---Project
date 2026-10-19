<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Create Account — ShopSphere" />
</jsp:include>
<body class="bg-light">
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-12 col-md-7 col-lg-6">
            <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
                <div class="text-center mb-4">
                    <span class="bg-primary text-white p-3 rounded-circle d-inline-flex mb-3">
                        <i class="bi bi-person-plus-fill fs-3"></i>
                    </span>
                    <h3 class="fw-bold mb-1">Join ShopSphere</h3>
                    <p class="text-muted small">Create your customer account to start shopping</p>
                </div>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show small" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i> ${errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Full Name:</label>
                        <input type="text" name="name" class="form-control" value="${name}" placeholder="e.g. Sneha Soni" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label small fw-bold">Email Address:</label>
                        <input type="email" name="email" class="form-control" value="${email}" placeholder="e.g. user@example.com" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label small fw-bold">Mobile Number:</label>
                        <input type="tel" name="mobile" class="form-control" value="${mobile}" placeholder="e.g. 9829012345">
                    </div>

                    <div class="row g-2 mb-4">
                        <div class="col-md-6">
                            <label class="form-label small fw-bold">Password:</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label small fw-bold">Confirm Password:</label>
                            <input type="password" name="confirmPassword" class="form-control" required>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold">Create My Account</button>
                </form>

                <div class="mt-4 pt-3 border-top text-center small text-muted">
                    Already have an account? <a href="${pageContext.request.contextPath}/login" class="text-primary fw-semibold text-decoration-none">Sign In</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
