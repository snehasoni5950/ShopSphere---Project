<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Admin Dashboard — ShopSphere" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <jsp:include page="/fragments/sidebar.jsp">
            <jsp:param name="activeTab" value="dashboard" />
        </jsp:include>

        <!-- Main Content -->
        <main class="col-12 col-md-9 col-lg-10 p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h3 class="fw-bold mb-1">Administrative Dashboard</h3>
                    <span class="text-muted small">Welcome, ${sessionScope.user.name}! Overview of sales, inventory and users.</span>
                </div>
                <span class="badge bg-success px-3 py-2">System Active</span>
            </div>

            <!-- Stat Cards Row -->
            <div class="row g-3 mb-4">
                <div class="col-sm-6 col-xl-3">
                    <div class="card stat-card p-3 bg-white border-start border-4 border-primary">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <span class="text-muted small fw-semibold text-uppercase">Total Orders</span>
                                <h3 class="fw-bold mt-1 mb-0">${totalOrders}</h3>
                            </div>
                            <span class="bg-primary-subtle text-primary p-3 rounded-circle"><i class="bi bi-cart-check fs-4"></i></span>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-xl-3">
                    <div class="card stat-card p-3 bg-white border-start border-4 border-success">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <span class="text-muted small fw-semibold text-uppercase">Total Revenue</span>
                                <h3 class="fw-bold mt-1 mb-0"><app:currency value="${totalRevenue}" /></h3>
                            </div>
                            <span class="bg-success-subtle text-success p-3 rounded-circle"><i class="bi bi-currency-rupee fs-4"></i></span>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-xl-3">
                    <div class="card stat-card p-3 bg-white border-start border-4 border-info">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <span class="text-muted small fw-semibold text-uppercase">Catalog Items</span>
                                <h3 class="fw-bold mt-1 mb-0">${totalProducts}</h3>
                            </div>
                            <span class="bg-info-subtle text-info p-3 rounded-circle"><i class="bi bi-box-seam fs-4"></i></span>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-xl-3">
                    <div class="card stat-card p-3 bg-white border-start border-4 border-warning">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <span class="text-muted small fw-semibold text-uppercase">Registered Users</span>
                                <h3 class="fw-bold mt-1 mb-0">${totalUsers}</h3>
                            </div>
                            <span class="bg-warning-subtle text-warning p-3 rounded-circle"><i class="bi bi-people fs-4"></i></span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Low Stock Inventory Alerts -->
            <div class="card border-0 shadow-sm rounded-4 p-4 mb-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="fw-bold m-0"><i class="bi bi-exclamation-triangle-fill text-warning me-2"></i>Low-Stock Inventory Monitor (&le; 5 units)</h5>
                    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-primary btn-sm">Manage Products</a>
                </div>

                <c:choose>
                    <c:when test="${not empty lowStockProducts}">
                        <div class="table-responsive">
                            <table class="table align-middle table-hover mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Product Name</th>
                                        <th>Brand</th>
                                        <th>Category</th>
                                        <th>Price</th>
                                        <th>Current Stock</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="lp" items="${lowStockProducts}">
                                        <tr>
                                            <td>#${lp.productId}</td>
                                            <td class="fw-bold">${lp.name}</td>
                                            <td>${lp.brand}</td>
                                            <td>${lp.categoryName}</td>
                                            <td><app:currency value="${lp.finalPrice}" /></td>
                                            <td><span class="badge bg-danger fs-6">${lp.stock}</span></td>
                                            <td><app:stockBadge stock="${lp.stock}" /></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-success small mb-0"><i class="bi bi-check-circle me-1"></i> All products currently have healthy inventory levels (&gt; 5 units).</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
