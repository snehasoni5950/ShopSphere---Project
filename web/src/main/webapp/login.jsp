<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Sign In — ShopSphere" />
</jsp:include>
<body class="bg-light">
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-12 col-md-6 col-lg-5">
            <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
                <div class="text-center mb-4">
                    <span class="bg-primary text-white p-3 rounded-circle d-inline-flex mb-3">
                        <i class="bi bi-person-fill fs-3"></i>
                    </span>
                    <h3 class="fw-bold mb-1">Welcome Back</h3>
                    <p class="text-muted small">Sign in to your ShopSphere account</p>
                </div>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show small" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i> ${errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <c:if test="${not empty param.msg}">
                    <div class="alert alert-info alert-dismissible fade show small" role="alert">
                        ${param.msg}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Email Address:</label>
                        <input type="email" name="email" id="loginEmail" class="form-control"
                               value="${not empty email ? email : (not empty cookie.remembered_email.value ? cookie.remembered_email.value : 'sneha@example.com')}" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label small fw-bold">Password:</label>
                        <input type="password" name="password" id="loginPass" class="form-control" value="sneha123" required>
                    </div>

                    <div class="d-flex justify-content-between align-items-center mb-4 small">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="rememberMe" id="rememberMe" checked>
                            <label class="form-check-label text-muted" for="rememberMe">Remember me</label>
                        </div>
                        <a href="#" class="text-decoration-none text-primary">Forgot password?</a>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold">Sign In</button>
                </form>

                <div class="mt-4 pt-3 border-top text-center small text-muted">
                    Don't have an account? <a href="${pageContext.request.contextPath}/register" class="text-primary fw-semibold text-decoration-none">Create one</a>
                </div>

                <!-- Viva Quick Credentials Bar -->
                <div class="mt-4 p-3 bg-light rounded-3 border">
                    <span class="d-block small fw-bold text-muted mb-2"><i class="bi bi-key-fill text-warning me-1"></i> Quick Test Logins for Viva Demo:</span>
                    <div class="d-flex gap-2">
                        <button type="button" class="btn btn-sm btn-outline-dark flex-grow-1"
                                onclick="document.getElementById('loginEmail').value='admin@shopsphere.com'; document.getElementById('loginPass').value='admin123';">
                            Fill Admin
                        </button>
                        <button type="button" class="btn btn-sm btn-outline-primary flex-grow-1"
                                onclick="document.getElementById('loginEmail').value='sneha@example.com'; document.getElementById('loginPass').value='sneha123';">
                            Fill Customer
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
