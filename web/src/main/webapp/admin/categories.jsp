<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Category Management — ShopSphere Admin" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/fragments/sidebar.jsp">
            <jsp:param name="activeTab" value="categories" />
        </jsp:include>

        <main class="col-12 col-md-9 col-lg-10 p-4">
            <h3 class="fw-bold mb-4">Categories Management</h3>

            <c:if test="${not empty param.success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${param.success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <div class="row g-4">
                <!-- Add Category Form -->
                <div class="col-md-4">
                    <div class="card border-0 shadow-sm rounded-4 p-4 bg-light">
                        <h5 class="fw-bold mb-3">Add Category</h5>
                        <form action="${pageContext.request.contextPath}/admin/categories" method="post">
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Category Name:</label>
                                <input type="text" name="name" class="form-control" placeholder="e.g. Footwear" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Description:</label>
                                <textarea name="description" rows="3" class="form-control" placeholder="Brief description..."></textarea>
                            </div>
                            <div class="form-check mb-3">
                                <input class="form-check-input" type="checkbox" name="status" id="catStatus" checked>
                                <label class="form-check-label small fw-bold" for="catStatus">Active</label>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Create Category</button>
                        </form>
                    </div>
                </div>

                <!-- Category List Table -->
                <div class="col-md-8">
                    <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                        <div class="table-responsive">
                            <table class="table align-middle table-hover mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Category Name</th>
                                        <th>Description</th>
                                        <th>Status</th>
                                        <th class="text-end pe-4">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="cat" items="${categories}">
                                        <tr>
                                            <td>#${cat.categoryId}</td>
                                            <td class="fw-bold">${cat.name}</td>
                                            <td class="text-secondary small">${cat.description}</td>
                                            <td>
                                                <span class="badge ${cat.status ? 'bg-success' : 'bg-secondary'}">
                                                    ${cat.status ? 'Active' : 'Inactive'}
                                                </span>
                                            </td>
                                            <td class="text-end pe-4">
                                                <a href="${pageContext.request.contextPath}/admin/categories?action=delete&id=${cat.categoryId}"
                                                   class="btn btn-outline-danger btn-sm"
                                                   onclick="return confirm('Delete category ${cat.name}?');">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
