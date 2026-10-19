<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shopsphere.util.I18nUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    String currentLang = (String) session.getAttribute("lang");
    if (currentLang == null) currentLang = "en";

    String title = I18nUtil.getMessage("app.title", currentLang);
    String navProducts = I18nUtil.getMessage("nav.products", currentLang);
    String navCart = I18nUtil.getMessage("nav.cart", currentLang);
    String navOrders = I18nUtil.getMessage("nav.orders", currentLang);
    String btnAddToCart = I18nUtil.getMessage("product.addToCart", currentLang);
    String formattedPrice = I18nUtil.formatCurrency(26990.00, currentLang);

    Locale loc = "hi".equalsIgnoreCase(currentLang) ? new Locale("hi", "IN") : Locale.ENGLISH;
    DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, loc);
    String formattedDate = df.format(new Date());
%>

<!DOCTYPE html>
<html lang="<%= currentLang %>">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="Internationalization (I18N) Demo — RTU Syllabus" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
        <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3 d-inline-block" style="width: fit-content;">
            RTU Syllabus Section 23: Internationalization
        </span>
        <h2 class="fw-bold mb-2">Java Internationalization (I18N) & Localization (L10N)</h2>
        <p class="text-secondary">
            Demonstrates <code>java.util.Locale</code>, <code>ResourceBundle</code>, character encoding, and localized currency/date formatting.
        </p>

        <!-- Language Switcher Bar -->
        <div class="d-flex align-items-center gap-3 p-3 bg-light rounded-3 my-4">
            <span class="fw-bold small text-muted">Select Language / भाषा चुनें:</span>
            <a href="${pageContext.request.contextPath}/i18n?lang=en"
               class="btn btn-sm ${currentLang == 'en' ? 'btn-primary' : 'btn-outline-primary'} px-3">
                English (en_US)
            </a>
            <a href="${pageContext.request.contextPath}/i18n?lang=hi"
               class="btn btn-sm ${currentLang == 'hi' ? 'btn-success' : 'btn-outline-success'} px-3">
                हिंदी (hi_IN)
            </a>
            <span class="ms-auto badge bg-dark">Active Locale: <%= loc.toString() %></span>
        </div>

        <h5 class="fw-bold mb-3">ResourceBundle Translated Keys:</h5>
        <div class="table-responsive">
            <table class="table table-bordered align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Resource Key</th>
                        <th>Resolved String in Locale (<%= currentLang.toUpperCase() %>)</th>
                        <th>Resource Source File</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><code>app.title</code></td>
                        <td class="fw-bold"><%= title %></td>
                        <td><code>messages_<%= currentLang %>.properties</code></td>
                    </tr>
                    <tr>
                        <td><code>nav.products</code></td>
                        <td><%= navProducts %></td>
                        <td><code>messages_<%= currentLang %>.properties</code></td>
                    </tr>
                    <tr>
                        <td><code>nav.cart</code></td>
                        <td><%= navCart %></td>
                        <td><code>messages_<%= currentLang %>.properties</code></td>
                    </tr>
                    <tr>
                        <td><code>nav.orders</code></td>
                        <td><%= navOrders %></td>
                        <td><code>messages_<%= currentLang %>.properties</code></td>
                    </tr>
                    <tr>
                        <td><code>product.addToCart</code></td>
                        <td><button class="btn btn-primary btn-sm"><%= btnAddToCart %></button></td>
                        <td><code>messages_<%= currentLang %>.properties</code></td>
                    </tr>
                    <tr>
                        <td><code>Formatted Currency (NumberFormat)</code></td>
                        <td class="fs-5 fw-bold text-success"><%= formattedPrice %></td>
                        <td><code>NumberFormat.getCurrencyInstance(locale)</code></td>
                    </tr>
                    <tr>
                        <td><code>Formatted Date (DateFormat)</code></td>
                        <td class="fw-semibold text-primary"><%= formattedDate %></td>
                        <td><code>DateFormat.getDateInstance(FULL, locale)</code></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
