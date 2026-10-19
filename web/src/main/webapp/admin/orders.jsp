<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Order Processing — ShopSphere Admin" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/fragments/sidebar.jsp">
            <jsp:param name="activeTab" value="orders" />
        </jsp:include>

        <main class="col-12 col-md-9 col-lg-10 p-4">
            <h3 class="fw-bold mb-4">Customer Orders & Fulfillment</h3>

            <c:if test="${not empty param.success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${param.success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                <div class="table-responsive">
                    <table class="table align-middle table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Order ID</th>
                                <th>Customer</th>
                                <th>Date</th>
                                <th>Total</th>
                                <th>Payment</th>
                                <th>Order Status</th>
                                <th class="text-end pe-4">Update Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${orders}">
                                <tr>
                                    <td class="fw-bold">#SP-${o.orderId}</td>
                                    <td>
                                        <div class="fw-semibold">${o.userName}</div>
                                        <div class="text-muted small">${o.userEmail}</div>
                                    </td>
                                    <td class="small text-muted">${o.createdAt}</td>
                                    <td class="fw-bold text-primary"><app:currency value="${o.totalAmount}" /></td>
                                    <td>
                                        <span class="badge ${o.paymentStatus == 'PAID' ? 'bg-success' : 'bg-warning text-dark'}">
                                            ${o.paymentMethod} (${o.paymentStatus})
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge ${o.orderStatus == 'DELIVERED' ? 'bg-success' : (o.orderStatus == 'CANCELLED' ? 'bg-danger' : 'bg-primary')} px-2 py-1">
                                            ${o.orderStatus}
                                        </span>
                                    </td>
                                    <td class="text-end pe-4">
                                        <form action="${pageContext.request.contextPath}/admin/orders" method="post" class="d-inline-flex gap-2">
                                            <input type="hidden" name="orderId" value="${o.orderId}">
                                            <select name="status" class="form-select form-select-sm" style="width: 140px;">
                                                <option value="PLACED" ${o.orderStatus == 'PLACED' ? 'selected' : ''}>PLACED</option>
                                                <option value="CONFIRMED" ${o.orderStatus == 'CONFIRMED' ? 'selected' : ''}>CONFIRMED</option>
                                                <option value="SHIPPED" ${o.orderStatus == 'SHIPPED' ? 'selected' : ''}>SHIPPED</option>
                                                <option value="DELIVERED" ${o.orderStatus == 'DELIVERED' ? 'selected' : ''}>DELIVERED</option>
                                                <option value="CANCELLED" ${o.orderStatus == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                                            </select>
                                            <button type="submit" class="btn btn-dark btn-sm">Update</button>
                                        </form>
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
