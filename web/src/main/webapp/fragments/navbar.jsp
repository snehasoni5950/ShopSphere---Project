<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm py-2">
    <div class="container">
        <!-- Brand -->
        <a class="navbar-brand fw-bold d-flex align-items-center gap-2" href="${pageContext.request.contextPath}/">
            <span class="bg-primary text-white px-2 py-1 rounded-3 fs-5"><i class="bi bi-bag-heart-fill"></i></span>
            <span class="tracking-wide">Shop<span class="text-primary">Sphere</span></span>
        </a>

        <!-- Mobile Toggler -->
        <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <!-- Navigation Links -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-nav-item">
                    <a class="nav-link ${empty param.category && pageContext.request.servletPath == '/index.jsp' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/products">All Products</a>
                </li>

                <!-- Categories Dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                        Categories
                    </a>
                    <ul class="dropdown-menu shadow">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=1">Electronics</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=2">Fashion</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=3">Home & Kitchen</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=4">Books</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=5">Fitness & Sports</a></li>
                    </ul>
                </li>

                <!-- RTU Syllabus Demonstrations Dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-warning" href="#" role="button" data-bs-toggle="dropdown">
                        <i class="bi bi-mortarboard-fill"></i> RTU Demos
                    </a>
                    <ul class="dropdown-menu shadow">
                        <li><h6 class="dropdown-header">Syllabus Practical Modules</h6></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/demo/jsp-elements.jsp">Traditional JSP Elements</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/demo/jstl-xml.jsp">JSTL XML Processing</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/demo/jstl-sql.jsp">JSTL SQL Demonstration</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/demo/servlet-lifecycle">Servlet Lifecycle & Config</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/demo/url-protocol">URL & URLConnection</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/demo/jndi">JNDI Environment Lookup</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/demo/i18n-demo.jsp">Internationalization (I18N)</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/demo/applet-demo.jsp">Applet Historical Guide</a></li>
                    </ul>
                </li>
            </ul>

            <!-- Search Form -->
            <form class="d-flex mx-lg-3 my-2 my-lg-0 flex-grow-1" style="max-width: 360px;" action="${pageContext.request.contextPath}/products" method="get">
                <div class="input-group">
                    <input class="form-control form-control-sm bg-dark text-white border-secondary" type="search" name="q" placeholder="Search products, brands..." value="${param.q}">
                    <button class="btn btn-primary btn-sm" type="submit"><i class="bi bi-search"></i></button>
                </div>
            </form>

            <!-- Right utilities -->
            <ul class="navbar-nav ms-auto align-items-lg-center gap-2">
                <!-- Wishlist -->
                <li class="nav-item">
                    <a class="nav-link position-relative" href="${pageContext.request.contextPath}/wishlist" title="Wishlist">
                        <i class="bi bi-heart fs-5"></i>
                    </a>
                </li>

                <!-- Cart -->
                <li class="nav-item">
                    <a class="nav-link position-relative" href="${pageContext.request.contextPath}/cart" title="Shopping Cart">
                        <i class="bi bi-cart3 fs-5"></i>
                        <c:if test="${not empty sessionScope.cartCount && sessionScope.cartCount > 0}">
                            <span class="position-absolute top-1 start-100 translate-middle badge rounded-pill bg-danger" style="font-size: 0.65rem;">
                                ${sessionScope.cartCount}
                            </span>
                        </c:if>
                    </a>
                </li>

                <!-- User Account / Auth -->
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle d-flex align-items-center gap-2" href="#" role="button" data-bs-toggle="dropdown">
                                <span class="bg-primary text-white rounded-circle d-inline-flex justify-content-center align-items-center" style="width: 28px; height: 28px; font-size: 0.8rem;">
                                    ${sessionScope.user.name.substring(0, 1)}
                                </span>
                                <span>${sessionScope.user.name}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end shadow">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile"><i class="bi bi-person me-2"></i>My Profile</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/orders"><i class="bi bi-box-seam me-2"></i>My Orders</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/wishlist"><i class="bi bi-heart me-2"></i>Wishlist</a></li>
                                <c:if test="${sessionScope.user.admin}">
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item text-primary fw-bold" href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-speedometer2 me-2"></i>Admin Dashboard</a></li>
                                </c:if>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Logout</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm px-3" href="${pageContext.request.contextPath}/login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-primary btn-sm px-3" href="${pageContext.request.contextPath}/register">Register</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
