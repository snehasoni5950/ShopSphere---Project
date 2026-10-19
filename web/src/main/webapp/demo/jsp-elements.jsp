<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%!
    // 1. JSP Declaration: Declares class-level fields and methods in generated servlet
    private int pageVisitCounter = 0;

    public synchronized int getNextVisit() {
        return ++pageVisitCounter;
    }

    public String getFormattedDate() {
        return new Date().toString();
    }
%>

<%
    // 2. JSP Scriptlet: Executed inside _jspService() method for each incoming HTTP request
    String studentName = "RTU Student Viva Candidate";
    String courseCode = "RTU-CS-601: Advanced Java & Enterprise Technologies";
    int currentVisit = getNextVisit();
%>

<%-- 3. JSP Comment: Never sent to client browser or rendered in HTML source --%>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="JSP Traditional Elements Demo — RTU Syllabus" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
        <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3 d-inline-block" style="width: fit-content;">
            RTU Syllabus Section 16
        </span>
        <h2 class="fw-bold mb-2">Traditional JSP Elements & Lifecycle Demonstration</h2>
        <p class="text-secondary">
            Under the RTU syllabus, this page directly showcases the traditional syntax elements of JavaServer Pages,
            contrasting them with modern Jakarta Expression Language (EL) and JSTL.
        </p>

        <hr class="my-4">

        <div class="row g-4">
            <!-- 1. Declarations -->
            <div class="col-md-6">
                <div class="p-3 bg-light rounded-3 h-100 border">
                    <h5 class="fw-bold text-primary"><i class="bi bi-braces me-2"></i>1. JSP Declaration (<code class="text-danger">&lt;%! ... %&gt;</code>)</h5>
                    <p class="small text-muted">
                        Variables declared in declaration tags become member variables of the translated servlet class (outside <code>_jspService()</code>).
                    </p>
                    <pre class="bg-dark text-white p-2 rounded small"><code>&lt;%! 
  private int pageVisitCounter = 0;
  public synchronized int getNextVisit() { return ++pageVisitCounter; }
%&gt;</code></pre>
                    <div class="alert alert-info py-2 small mb-0">
                        <strong>Live Declaration Evaluation:</strong> Total lifetime hits on this JSP instance:
                        <span class="badge bg-primary fs-6"><%= pageVisitCounter %></span>
                    </div>
                </div>
            </div>

            <!-- 2. Scriptlets -->
            <div class="col-md-6">
                <div class="p-3 bg-light rounded-3 h-100 border">
                    <h5 class="fw-bold text-primary"><i class="bi bi-code-slash me-2"></i>2. JSP Scriptlet (<code class="text-danger">&lt;% ... %&gt;</code>)</h5>
                    <p class="small text-muted">
                        Code within scriptlets is placed directly into the servlet's <code>_jspService(HttpServletRequest, HttpServletResponse)</code> method.
                    </p>
                    <pre class="bg-dark text-white p-2 rounded small"><code>&lt;%
  String studentName = "RTU Student Viva Candidate";
  String courseCode = "RTU-CS-601: Advanced Java";
%&gt;</code></pre>
                    <div class="alert alert-secondary py-2 small mb-0">
                        <strong>Executed in request scope:</strong> Request hit #<%= currentVisit %>
                    </div>
                </div>
            </div>

            <!-- 3. Expressions -->
            <div class="col-md-6">
                <div class="p-3 bg-light rounded-3 h-100 border">
                    <h5 class="fw-bold text-primary"><i class="bi bi-chat-quote me-2"></i>3. JSP Expression (<code class="text-danger">&lt;%= ... %&gt;</code>)</h5>
                    <p class="small text-muted">
                        The expression is evaluated, converted to String, and written directly to the response output stream via <code>out.print(...)</code>.
                    </p>
                    <ul class="small mb-0">
                        <li>Student Identifier: <strong><%= studentName %></strong></li>
                        <li>Syllabus Subject: <strong><%= courseCode %></strong></li>
                        <li>Server Timestamp: <strong><%= getFormattedDate() %></strong></li>
                    </ul>
                </div>
            </div>

            <!-- 4. Comments & Directives -->
            <div class="col-md-6">
                <div class="p-3 bg-light rounded-3 h-100 border">
                    <h5 class="fw-bold text-primary"><i class="bi bi-slash-square me-2"></i>4. Comments & Directives</h5>
                    <p class="small text-muted">
                        JSP Comments (<code>&lt;%-- ... --%&gt;</code>) are ignored during compilation, unlike standard HTML comments (<code>&lt;!-- ... --&gt;</code>).
                    </p>
                    <pre class="bg-dark text-white p-2 rounded small"><code>&lt;%-- This is a hidden server-side JSP comment --%&gt;
&lt;%@ page import="java.util.Date" %&gt;
&lt;%@ taglib prefix="c" uri="jakarta.tags.core" %&gt;</code></pre>
                </div>
            </div>
        </div>

        <div class="mt-4 pt-3 border-top">
            <h6 class="fw-bold"><i class="bi bi-diagram-3-fill text-success me-2"></i>JSP Lifecycle Architecture (Viva Concept):</h6>
            <ol class="small text-secondary mb-0">
                <li><strong>Translation Phase:</strong> Tomcat Jasper translates <code>.jsp</code> into Java source code (e.g. <code>jsp_elements_jsp.java</code>).</li>
                <li><strong>Compilation Phase:</strong> <code>javac</code> compiles the generated <code>.java</code> into bytecode <code>.class</code>.</li>
                <li><strong>Loading & Instantiation:</strong> Web container classloader loads the servlet class.</li>
                <li><strong>Initialization:</strong> Container calls <code>jspInit()</code> once.</li>
                <li><strong>Request Handling:</strong> Every client request calls <code>_jspService(request, response)</code>.</li>
                <li><strong>Destruction:</strong> On server shutdown or reload, container calls <code>jspDestroy()</code>.</li>
            </ol>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
