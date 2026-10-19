<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="URL & URLConnection Demo — RTU Syllabus" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
        <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3 d-inline-block" style="width: fit-content;">
            RTU Syllabus Section 24: Content & Protocol Handlers
        </span>
        <h2 class="fw-bold mb-2">java.net URL & URLConnection Protocol Handler</h2>
        <p class="text-secondary">
            Demonstrates network protocol and content handlers using Java's standard <code>java.net.URL</code> and <code>URLConnection</code> classes.
        </p>

        <form action="${pageContext.request.contextPath}/demo/url-protocol" method="get" class="my-4">
            <div class="input-group">
                <input type="url" name="url" class="form-control" placeholder="https://example.com" value="${urlInput}" required>
                <button type="submit" class="btn btn-primary px-4">Inspect URL Protocol &amp; Headers</button>
            </div>
        </form>

        <div class="row g-4">
            <!-- URL Components -->
            <div class="col-md-6">
                <div class="card border-0 bg-light p-4 rounded-3 h-100">
                    <h5 class="fw-bold text-primary mb-3"><i class="bi bi-link-45deg me-2"></i>Parsed URL Anatomy</h5>
                    <ul class="list-unstyled small mb-0">
                        <c:forEach var="entry" items="${urlDetails}">
                            <li class="mb-2"><strong>${entry.key}:</strong> <code class="text-dark">${entry.value}</code></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <!-- HTTP Headers -->
            <div class="col-md-6">
                <div class="card border-0 bg-light p-4 rounded-3 h-100">
                    <h5 class="fw-bold text-success mb-3"><i class="bi bi-list-check me-2"></i>Remote HTTP Headers</h5>
                    <div style="max-height: 280px; overflow-y: auto;">
                        <ul class="list-unstyled small mb-0">
                            <c:forEach var="h" items="${headerFields}">
                                <li class="mb-1">
                                    <strong>${not empty h.key ? h.key : 'Status Line'}:</strong>
                                    <span class="text-secondary">${h.value}</span>
                                </li>
                            </c:forEach>
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
