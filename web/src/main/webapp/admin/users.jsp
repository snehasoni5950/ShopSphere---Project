<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Customer Accounts — ShopSphere Admin" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/fragments/sidebar.jsp">
            <jsp:param name="activeTab" value="users" />
        </jsp:include>

        <main class="col-12 col-md-9 col-lg-10 p-4">
            <h3 class="fw-bold mb-4">Registered Customers & Accounts</h3>

            <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                <div class="table-responsive">
                    <table class="table align-middle table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>User ID</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Mobile</th>
                                <th>Role</th>
                                <th>Account Status</th>
                                <th>Registered On</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td>#${u.userId}</td>
                                    <td class="fw-bold">${u.name}</td>
                                    <td>${u.email}</td>
                                    <td>${not empty u.mobile ? u.mobile : '-'}</td>
                                    <td>
                                        <span class="badge ${u.admin ? 'bg-danger' : 'bg-primary'} px-2 py-1">
                                            ${u.role}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge bg-success-subtle text-success px-2 py-1">
                                            ${u.status}
                                        </span>
                                    </td>
                                    <td class="small text-muted">${u.createdAt}</td>
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
