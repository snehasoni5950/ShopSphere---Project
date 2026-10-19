<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="My Profile — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-4">
    <h3 class="fw-bold mb-4"><i class="bi bi-person-circle text-primary me-2"></i>My Profile & Addresses</h3>

    <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> ${param.success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="row g-4">
        <!-- Profile Info Card -->
        <div class="col-lg-5">
            <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
                <h5 class="fw-bold mb-3">Account Information</h5>
                <form action="${pageContext.request.contextPath}/profile" method="post">
                    <input type="hidden" name="action" value="updateProfile">
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Full Name:</label>
                        <input type="text" name="name" class="form-control" value="${user.name}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Email Address:</label>
                        <input type="email" class="form-control bg-light" value="${user.email}" readonly>
                        <div class="form-text small">Email cannot be modified.</div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Mobile Number:</label>
                        <input type="tel" name="mobile" class="form-control" value="${user.mobile}">
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Role:</label>
                        <div><span class="badge bg-primary px-3 py-2">${user.role}</span></div>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Update Profile</button>
                </form>
            </div>
        </div>

        <!-- Saved Addresses & Add New Address -->
        <div class="col-lg-7">
            <!-- Saved Addresses -->
            <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
                <h5 class="fw-bold mb-3">Saved Addresses</h5>
                <c:choose>
                    <c:when test="${not empty addresses}">
                        <div class="d-flex flex-column gap-3">
                            <c:forEach var="addr" items="${addresses}">
                                <div class="p-3 bg-light rounded-3 border">
                                    <span class="badge bg-secondary mb-1">${addr.addressType}</span>
                                    <p class="mb-0 small fw-semibold text-dark">${addr.formattedAddress}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted small">No addresses saved yet.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Add Address Form -->
            <div class="card border-0 shadow-sm rounded-4 p-4">
                <h5 class="fw-bold mb-3">+ Add New Address</h5>
                <form action="${pageContext.request.contextPath}/profile" method="post">
                    <input type="hidden" name="action" value="addAddress">
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Street Address Line:</label>
                        <input type="text" name="addressLine" class="form-control" placeholder="House/Flat No, Apartment, Street" required>
                    </div>
                    <div class="row g-2 mb-3">
                        <div class="col-md-6">
                            <label class="form-label small fw-bold">City:</label>
                            <input type="text" name="city" class="form-control" placeholder="e.g. Jaipur" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label small fw-bold">State:</label>
                            <input type="text" name="state" class="form-control" placeholder="e.g. Rajasthan" required>
                        </div>
                    </div>
                    <div class="row g-2 mb-3">
                        <div class="col-md-6">
                            <label class="form-label small fw-bold">Pincode:</label>
                            <input type="text" name="pincode" class="form-control" placeholder="e.g. 302017" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label small fw-bold">Address Type:</label>
                            <select name="addressType" class="form-select">
                                <option value="HOME">Home</option>
                                <option value="WORK">Work / Office</option>
                                <option value="OTHER">Other</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-dark w-100">Save Address</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
