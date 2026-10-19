<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="JNDI Resource Lookup Demo — RTU Syllabus" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
        <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3 d-inline-block" style="width: fit-content;">
            RTU Syllabus Section 24: JNDI & Naming
        </span>
        <h2 class="fw-bold mb-2">Java Naming and Directory Interface (JNDI) Lookup</h2>
        <p class="text-secondary">
            Demonstrates how enterprise J2EE web applications access container-managed resources (DataSource connection pools,
            mail sessions, environment entries) via standard JNDI context naming.
        </p>

        <div class="alert alert-info py-2 small mb-4">
            <i class="bi bi-info-circle-fill me-2"></i> ${statusMessage}
        </div>

        <h5 class="fw-bold mb-3"><i class="bi bi-folder-symlink me-2 text-primary"></i>Configured JNDI Environment Entries (<code class="text-danger">java:comp/env/*</code>):</h5>
        <div class="table-responsive">
            <table class="table table-bordered align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>JNDI Resource Name</th>
                        <th>Bound Object / Type</th>
                        <th>Standard Enterprise Purpose</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${jndiEntries}">
                        <tr>
                            <td class="fw-bold text-primary"><i class="bi bi-database me-2"></i>${entry.key}</td>
                            <td><code>${entry.value}</code></td>
                            <td class="small text-muted">
                                Container managed resource decoupled from application code.
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="card bg-light border-0 p-4 rounded-3 mt-4">
            <h6 class="fw-bold text-dark"><i class="bi bi-code-square text-primary me-2"></i>Standard JNDI Java Code Pattern:</h6>
            <pre class="bg-dark text-white p-3 rounded mb-0 small"><code>Context initCtx = new InitialContext();
Context envCtx  = (Context) initCtx.lookup("java:comp/env");
DataSource ds   = (DataSource) envCtx.lookup("jdbc/ShopSphereDS");
Connection conn = ds.getConnection(); // Container-managed pool connection</code></pre>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
