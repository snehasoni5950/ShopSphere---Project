<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>
<%@ taglib prefix="app" uri="http://shopsphere.com/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="JSTL SQL Tag Library Demo — RTU Syllabus" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
        <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3 d-inline-block" style="width: fit-content;">
            RTU Syllabus Section 17: JSTL SQL
        </span>
        <h2 class="fw-bold mb-2">JSTL SQL Tag Library Demonstration</h2>
        <p class="text-secondary">
            Demonstrates how JSTL SQL tags (<code class="text-danger">&lt;sql:setDataSource&gt;</code> and <code class="text-danger">&lt;sql:query&gt;</code>)
            can execute direct database queries within a JSP page for academic demonstrations.
        </p>

        <!-- Syllabus demonstration of sql:setDataSource and sql:query -->
        <sql:setDataSource var="shopsphereDb"
                           driver="com.mysql.cj.jdbc.Driver"
                           url="jdbc:mysql://localhost:3306/shopsphere?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=UTC"
                           user="root"
                           password="root" />

        <c:catch var="sqlError">
            <sql:query dataSource="${shopsphereDb}" var="categoryQuery">
                SELECT category_id, name, description, status FROM categories ORDER BY category_id ASC
            </sql:query>
        </c:catch>

        <c:choose>
            <c:when test="${not empty sqlError}">
                <div class="alert alert-warning">
                    <h6 class="fw-bold"><i class="bi bi-exclamation-triangle-fill me-2"></i>Live Database Notice:</h6>
                    <p class="small mb-0">
                        Unable to connect via JSTL direct tags (${sqlError.message}).
                        In this project, data is normally managed securely via <strong>PreparedStatement</strong> inside <code>CategoryDAO</code>.
                    </p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive my-3">
                    <table class="table table-bordered table-striped align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>Category ID</th>
                                <th>Category Name</th>
                                <th>Description</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="row" items="${categoryQuery.rows}">
                                <tr>
                                    <td>#${row.category_id}</td>
                                    <td class="fw-bold">${row.name}</td>
                                    <td>${row.description}</td>
                                    <td><span class="badge ${row.status ? 'bg-success' : 'bg-secondary'}">${row.status ? 'Active' : 'Inactive'}</span></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="card bg-light border-0 p-4 rounded-3 mt-4">
            <h6 class="fw-bold text-dark"><i class="bi bi-check-circle-fill text-success me-2"></i>Architectural Comparison for Viva Examination:</h6>
            <div class="row g-3 mt-1 small">
                <div class="col-md-6">
                    <div class="p-3 bg-white rounded-3 border">
                        <strong class="text-danger">JSTL SQL (&lt;sql:query&gt;):</strong>
                        <ul class="mb-0 mt-2">
                            <li>Places SQL queries directly inside JSP view layer.</li>
                            <li>Tightly couples presentation with database schema.</li>
                            <li>Difficult to unit test and maintain in large applications.</li>
                            <li>Violates separation of concerns in MVC architecture.</li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="p-3 bg-white rounded-3 border">
                        <strong class="text-success">ShopSphere N-Tier Architecture (DAO + PreparedStatement):</strong>
                        <ul class="mb-0 mt-2">
                            <li>Strict separation: JSP &rarr; Servlet &rarr; Service &rarr; DAO &rarr; MySQL.</li>
                            <li>Protects against SQL Injection via PreparedStatement parameter binding.</li>
                            <li>Shared across Web, Java Swing Admin, Sockets, and RMI modules.</li>
                            <li>Conforms to J2EE / Jakarta EE enterprise software standards.</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
