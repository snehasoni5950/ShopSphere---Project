<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Java Applet Architecture Demo — RTU Syllabus" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
        <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3 d-inline-block" style="width: fit-content;">
            RTU Syllabus Section 3: Applets Concept &amp; Architecture
        </span>
        <h2 class="fw-bold mb-2">Java Applet Lifecycle &amp; Historical Architecture</h2>
        <p class="text-secondary">
            In compliance with the RTU syllabus specification, this page covers the complete architecture of
            <code>java.applet.Applet</code> and <code>javax.swing.JApplet</code>, their lifecycle transitions, and the evolution into modern web standards.
        </p>

        <hr class="my-4">

        <!-- Applet Lifecycle Interactive Simulator -->
        <div class="row g-4 align-items-center mb-5">
            <div class="col-lg-6">
                <h5 class="fw-bold mb-3"><i class="bi bi-play-circle text-primary me-2"></i>Interactive Applet Canvas Simulator</h5>
                <div class="p-3 bg-dark text-white rounded-4 shadow-sm" style="min-height: 260px;">
                    <div class="d-flex justify-content-between border-bottom border-secondary pb-2 mb-3">
                        <span class="small text-muted"><i class="bi bi-window me-1"></i> ShopSphereBannerApplet.class (Simulated AWT/Swing Canvas)</span>
                        <span class="badge bg-success" id="appletState">Running</span>
                    </div>
                    <canvas id="appletCanvas" width="460" height="180" class="w-100 bg-black rounded-3"></canvas>
                </div>
                <div class="d-flex gap-2 mt-3">
                    <button class="btn btn-sm btn-outline-success" onclick="triggerAppletState('start')">Simulate start()</button>
                    <button class="btn btn-sm btn-outline-warning" onclick="triggerAppletState('stop')">Simulate stop()</button>
                    <button class="btn btn-sm btn-outline-danger" onclick="triggerAppletState('destroy')">Simulate destroy()</button>
                    <button class="btn btn-sm btn-outline-primary" onclick="triggerAppletState('init')">Simulate init()</button>
                </div>
            </div>

            <div class="col-lg-6">
                <h5 class="fw-bold mb-3"><i class="bi bi-diagram-3 text-success me-2"></i>The 5 Essential Applet Methods:</h5>
                <div class="d-flex flex-column gap-2 small">
                    <div class="p-2 bg-light rounded border">
                        <strong>1. init():</strong> Invoked by browser/appletviewer once to initialize variables, load images, and set UI layout.
                    </div>
                    <div class="p-2 bg-light rounded border">
                        <strong>2. start():</strong> Invoked immediately after <code>init()</code>, and every time the user revisits the page or tab.
                    </div>
                    <div class="p-2 bg-light rounded border">
                        <strong>3. paint(Graphics g):</strong> Invoked when the applet canvas needs redrawing (e.g. <code>g.drawString(...)</code>, <code>g.drawRect(...)</code>).
                    </div>
                    <div class="p-2 bg-light rounded border">
                        <strong>4. stop():</strong> Invoked whenever the browser window is minimized or navigated away to suspend active animations/threads.
                    </div>
                    <div class="p-2 bg-light rounded border">
                        <strong>5. destroy():</strong> Invoked before applet reclamation to release system resources, threads, and memory buffers.
                    </div>
                </div>
            </div>
        </div>

        <!-- Academic & Viva Context -->
        <div class="p-4 bg-light rounded-4 border">
            <h5 class="fw-bold text-dark"><i class="bi bi-mortarboard-fill text-warning me-2"></i>Viva Examination Guide on Applet Evolution:</h5>
            <p class="small text-secondary mb-2">
                <strong>Q: Why are traditional Applets deprecated in modern enterprise systems?</strong><br>
                A: Applets relied on the NPAPI (Netscape Plugin Application Programming Interface) in web browsers.
                Due to security vulnerabilities (sandbox escapes), all modern browsers (Chrome, Edge, Firefox, Safari)
                completely removed NPAPI plugin support. Modern Java projects utilize server-side Jakarta Servlets,
                JSP, RESTful APIs, and HTML5/JavaScript on the client for secure, cross-platform execution.
            </p>
        </div>
    </div>
</div>

<script>
    let angle = 0;
    let animId = null;
    let state = 'Running';

    function drawCanvas() {
        const c = document.getElementById('appletCanvas');
        if (!c) return;
        const ctx = c.getContext('2d');
        ctx.fillStyle = "#111827";
        ctx.fillRect(0, 0, c.width, c.height);

        if (state === 'Running') {
            ctx.fillStyle = "#38bdf8";
            ctx.font = "bold 16px 'Plus Jakarta Sans', sans-serif";
            ctx.fillText("ShopSphere Java Enterprise Store (RTU Demo)", 40, 60);

            ctx.fillStyle = "#94a3b8";
            ctx.font = "12px monospace";
            ctx.fillText("g.drawString(\"Active AWT Paint Loop\", x, y);", 40, 95);

            ctx.save();
            ctx.translate(390, 80);
            ctx.rotate(angle);
            ctx.fillStyle = "#f59e0b";
            ctx.fillRect(-15, -15, 30, 30);
            ctx.restore();

            angle += 0.04;
            animId = requestAnimationFrame(drawCanvas);
        } else {
            ctx.fillStyle = "#ef4444";
            ctx.font = "bold 16px sans-serif";
            ctx.fillText("Applet State: " + state + " (Animation Suspended)", 60, 90);
        }
    }

    function triggerAppletState(newState) {
        state = newState.charAt(0).toUpperCase() + newState.slice(1);
        if (state === 'Start' || state === 'Init') state = 'Running';
        document.getElementById('appletState').innerText = state;
        document.getElementById('appletState').className = (state === 'Running') ? 'badge bg-success' : 'badge bg-warning text-dark';
        if (state === 'Running' && !animId) {
            animId = requestAnimationFrame(drawCanvas);
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        drawCanvas();
    });
</script>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
