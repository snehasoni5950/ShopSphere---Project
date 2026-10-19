<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Servlet Lifecycle & Configuration Demo — RTU Syllabus" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
        <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3 d-inline-block" style="width: fit-content;">
            RTU Syllabus Section 14: Servlet Lifecycle & Config
        </span>
        <h2 class="fw-bold mb-2">Servlet Lifecycle, ServletConfig & ServletContext</h2>
        <p class="text-secondary">
            Demonstrates how the Apache Tomcat web container manages the servlet lifecycle, initializes instance-specific
            <code>ServletConfig</code> parameters, and exposes application-wide <code>ServletContext</code> attributes.
        </p>

        <hr class="my-4">

        <div class="row g-4 mb-4">
            <!-- ServletConfig Card -->
            <div class="col-md-6">
                <div class="card h-100 border-0 bg-light p-4 rounded-3">
                    <h5 class="fw-bold text-primary mb-3"><i class="bi bi-gear-wide-connected me-2"></i>ServletConfig (Servlet-Specific)</h5>
                    <ul class="list-unstyled small mb-0">
                        <li class="mb-2"><strong>Servlet Name:</strong> <code class="text-dark">${servletName}</code></li>
                        <li class="mb-2"><strong>Init Param (moduleOwner):</strong> <span class="badge bg-primary">${moduleOwner}</span></li>
                        <li class="mb-2"><strong>Init Param (auditLevel):</strong> <span class="badge bg-dark">${auditLevel}</span></li>
                        <li class="mb-2"><strong>Instantiation / init() Time:</strong> <span class="text-muted">${initTime}</span></li>
                        <li class="mb-0"><strong>Total Invocations (Service Counter):</strong> <span class="badge bg-success fs-6">${requestCount}</span></li>
                    </ul>
                </div>
            </div>

            <!-- ServletContext Card -->
            <div class="col-md-6">
                <div class="card h-100 border-0 bg-light p-4 rounded-3">
                    <h5 class="fw-bold text-success mb-3"><i class="bi bi-globe me-2"></i>ServletContext (Application-Wide)</h5>
                    <ul class="list-unstyled small mb-0">
                        <li class="mb-2"><strong>Application Name Attribute:</strong> <strong>${appName}</strong></li>
                        <li class="mb-2"><strong>Web Server Container:</strong> <span class="badge bg-dark">${serverInfo}</span></li>
                        <li class="mb-2"><strong>Servlet Specification Version:</strong> Jakarta Servlet ${majorVersion}.${minorVersion}</li>
                        <li class="mb-2"><strong>Active User Sessions (Live):</strong> <span class="badge bg-danger fs-6">${applicationScope.activeUserCount}</span></li>
                        <li class="mb-0"><strong>Scope Lifetime:</strong> Persists from server startup to shutdown.</li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Lifecycle Diagram -->
        <h5 class="fw-bold mb-3"><i class="bi bi-arrow-repeat text-primary me-2"></i>Servlet Container Lifecycle Stages:</h5>
        <div class="d-flex flex-wrap gap-2 text-center small mb-4">
            <div class="p-3 bg-primary text-white rounded-3 flex-grow-1">1. Class Loading</div>
            <div class="p-3 bg-primary text-white rounded-3 flex-grow-1">2. Instantiation (Constructor)</div>
            <div class="p-3 bg-success text-white rounded-3 flex-grow-1">3. init(ServletConfig) [Once]</div>
            <div class="p-3 bg-warning text-dark rounded-3 flex-grow-1">4. service(req, resp) [Every Request]</div>
            <div class="p-3 bg-info text-white rounded-3 flex-grow-1">5. doGet() / doPost()</div>
            <div class="p-3 bg-danger text-white rounded-3 flex-grow-1">6. destroy() [On Shutdown]</div>
        </div>

        <div class="text-center">
            <a href="${pageContext.request.contextPath}/demo/servlet-lifecycle" class="btn btn-primary px-4">
                <i class="bi bi-arrow-clockwise me-1"></i> Trigger Another Lifecycle Request
            </a>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
