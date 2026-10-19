<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Product Management — ShopSphere Admin" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/fragments/sidebar.jsp">
            <jsp:param name="activeTab" value="products" />
        </jsp:include>

        <main class="col-12 col-md-9 col-lg-10 p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-bold mb-0">Products Catalog Management</h3>
                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#productFormCollapse">
                    <i class="bi bi-plus-lg me-1"></i> Add / Edit Product
                </button>
            </div>

            <c:if test="${not empty param.success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${param.success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Collapsible Add/Edit Form -->
            <div class="collapse ${not empty editProduct ? 'show' : ''} mb-4" id="productFormCollapse">
                <div class="card border-0 shadow-sm rounded-4 p-4 bg-light">
                    <h5 class="fw-bold mb-3">${not empty editProduct ? 'Edit Product' : 'Add New Product'}</h5>
                    <form action="${pageContext.request.contextPath}/admin/products" method="post">
                        <input type="hidden" name="productId" value="${not empty editProduct ? editProduct.productId : 0}">

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label small fw-bold">Product Name:</label>
                                <input type="text" name="name" class="form-control" value="${editProduct.name}" required>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-bold">Brand:</label>
                                <input type="text" name="brand" class="form-control" value="${editProduct.brand}">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-bold">Category:</label>
                                <select name="categoryId" class="form-select" required>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.categoryId}" ${editProduct.categoryId == cat.categoryId ? 'selected' : ''}>
                                            ${cat.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-12">
                                <label class="form-label small fw-bold">Description:</label>
                                <textarea name="description" rows="2" class="form-control">${editProduct.description}</textarea>
                            </div>

                            <div class="col-md-3">
                                <label class="form-label small fw-bold">Price (₹):</label>
                                <input type="number" step="0.01" name="price" class="form-control" value="${not empty editProduct ? editProduct.price : 999.0}" required>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-bold">Discount (%):</label>
                                <input type="number" step="0.01" name="discount" class="form-control" value="${not empty editProduct ? editProduct.discount : 0.0}">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-bold">Stock Quantity:</label>
                                <input type="number" name="stock" class="form-control" value="${not empty editProduct ? editProduct.stock : 10}" required>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-bold">Image URL:</label>
                                <input type="text" name="imageUrl" class="form-control" value="${editProduct.imageUrl}">
                            </div>

                            <div class="col-12 d-flex justify-content-between align-items-center">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="status" id="prodStatus" ${empty editProduct || editProduct.status ? 'checked' : ''}>
                                    <label class="form-check-label fw-bold small" for="prodStatus">Active / Published</label>
                                </div>
                                <div class="d-flex gap-2">
                                    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-secondary btn-sm">Cancel</a>
                                    <button type="submit" class="btn btn-primary btn-sm px-4">Save Product</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Product Table -->
            <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                <div class="table-responsive">
                    <table class="table align-middle table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Product</th>
                                <th>Category</th>
                                <th>Price</th>
                                <th>Discount</th>
                                <th>Final</th>
                                <th>Stock</th>
                                <th>Status</th>
                                <th class="text-end pe-4">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${products}">
                                <tr>
                                    <td>#${p.productId}</td>
                                    <td>
                                        <div class="d-flex align-items-center gap-2">
                                            <img src="${not empty p.imageUrl ? p.imageUrl : 'https://placehold.co/50x50'}"
                                                 alt="${p.name}" class="rounded-2" style="width: 40px; height: 40px; object-fit: contain;">
                                            <div>
                                                <div class="fw-bold small">${p.name}</div>
                                                <div class="text-muted" style="font-size: 0.75rem;">${p.brand}</div>
                                            </div>
                                        </div>
                                    </td>
                                    <td><span class="badge bg-secondary">${p.categoryName}</span></td>
                                    <td><app:currency value="${p.price}" /></td>
                                    <td>${p.discount}%</td>
                                    <td class="fw-bold text-primary"><app:currency value="${p.finalPrice}" /></td>
                                    <td><app:stockBadge stock="${p.stock}" /></td>
                                    <td>
                                        <span class="badge ${p.status ? 'bg-success' : 'bg-secondary'}">
                                            ${p.status ? 'Active' : 'Inactive'}
                                        </span>
                                    </td>
                                    <td class="text-end pe-4">
                                        <div class="btn-group btn-group-sm">
                                            <a href="${pageContext.request.contextPath}/admin/products?action=edit&id=${p.productId}"
                                               class="btn btn-outline-secondary" title="Edit Product">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/products?action=delete&id=${p.productId}"
                                               class="btn btn-outline-danger" title="Delete Product"
                                               onclick="return confirm('Are you sure you want to delete this product?');">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
