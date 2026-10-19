<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="col-12 col-md-3 col-lg-2 bg-light p-3 border-end min-vh-100">
    <div class="d-flex align-items-center gap-2 mb-4">
        <i class="bi bi-shield-check text-primary fs-4"></i>
        <span class="fw-bold fs-6">Admin Panel</span>
    </div>

    <ul class="nav nav-pills flex-column gap-1">
        <li class="nav-item">
            <a class="nav-link ${param.activeTab == 'dashboard' ? 'active' : 'text-dark'}" href="${pageContext.request.contextPath}/admin/dashboard">
                <i class="bi bi-speedometer2 me-2"></i> Dashboard
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.activeTab == 'products' ? 'active' : 'text-dark'}" href="${pageContext.request.contextPath}/admin/products">
                <i class="bi bi-box-seam me-2"></i> Products
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.activeTab == 'categories' ? 'active' : 'text-dark'}" href="${pageContext.request.contextPath}/admin/categories">
                <i class="bi bi-tags me-2"></i> Categories
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.activeTab == 'orders' ? 'active' : 'text-dark'}" href="${pageContext.request.contextPath}/admin/orders">
                <i class="bi bi-cart-check me-2"></i> Orders
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.activeTab == 'users' ? 'active' : 'text-dark'}" href="${pageContext.request.contextPath}/admin/users">
                <i class="bi bi-people me-2"></i> Customers
            </a>
        </li>
        <li class="nav-item mt-4 pt-3 border-top">
            <a class="nav-link text-primary" href="${pageContext.request.contextPath}/">
                <i class="bi bi-arrow-left-circle me-2"></i> Back to Store
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-danger" href="${pageContext.request.contextPath}/logout">
                <i class="bi bi-box-arrow-right me-2"></i> Logout
            </a>
        </li>
    </ul>
</div>
