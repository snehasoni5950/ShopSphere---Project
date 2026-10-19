<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/fragments/header.jsp">
    <jsp:param name="pageTitle" value="JSTL XML Tag Library Demo — RTU Syllabus" />
</jsp:include>
<body>
<jsp:include page="/fragments/navbar.jsp" />

<div class="container my-5">
    <div class="card border-0 shadow-sm rounded-4 p-4 p-md-5">
        <span class="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold mb-3 d-inline-block" style="width: fit-content;">
            RTU Syllabus Section 17: JSTL XML
        </span>
        <h2 class="fw-bold mb-2">JSTL XML Tag Library Processing</h2>
        <p class="text-secondary">
            Demonstrates how JSTL XML tags (<code class="text-danger">&lt;x:parse&gt;</code>, <code class="text-danger">&lt;x:forEach&gt;</code>, <code class="text-danger">&lt;x:out&gt;</code>)
            parse and query structured XML supplier feeds directly in the view layer using XPath expressions.
        </p>

        <!-- Sample XML Feed in JSTL variable -->
        <c:set var="xmlCatalog">
            <inventorySupplier vendor="ShopSphere Global Feed" generated="2026-10-17">
                <item sku="SKU-801">
                    <title>Sony Wireless Noise Canceling Headphones</title>
                    <category>Electronics</category>
                    <units>25</units>
                    <wholesalePrice>21500.00</wholesalePrice>
                </item>
                <item sku="SKU-802">
                    <title>Dell XPS 15 InfinityEdge Touch Laptop</title>
                    <category>Electronics</category>
                    <units>12</units>
                    <wholesalePrice>122000.00</wholesalePrice>
                </item>
                <item sku="SKU-803">
                    <title>Ultraboost Light Running Footwear</title>
                    <category>Fashion</category>
                    <units>40</units>
                    <wholesalePrice>8500.00</wholesalePrice>
                </item>
                <item sku="SKU-804">
                    <title>Espresso Steam Wand Coffee Machine</title>
                    <category>Home &amp; Kitchen</category>
                    <units>18</units>
                    <wholesalePrice>6200.00</wholesalePrice>
                </item>
            </inventorySupplier>
        </c:set>

        <x:parse xml="${xmlCatalog}" var="parsedDoc" />

        <div class="alert alert-success p-3 rounded-3 mb-4">
            <h6 class="fw-bold mb-1"><i class="bi bi-filetype-xml me-2"></i>Parsed XML Feed Metadata via XPath:</h6>
            <div class="small">Vendor: <strong><x:out select="$parsedDoc/inventorySupplier/@vendor" /></strong> |
            Date: <strong><x:out select="$parsedDoc/inventorySupplier/@generated" /></strong></div>
        </div>

        <h5 class="fw-bold mb-3">Itemized XML Inventory Feed (XPath Iteration)</h5>
        <div class="table-responsive">
            <table class="table table-bordered align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>SKU</th>
                        <th>Product Title</th>
                        <th>Category</th>
                        <th>Available Units</th>
                        <th>Wholesale Price (₹)</th>
                    </tr>
                </thead>
                <tbody>
                    <x:forEach select="$parsedDoc/inventorySupplier/item" var="elem">
                        <tr>
                            <td class="fw-bold text-primary"><x:out select="$elem/@sku" /></td>
                            <td><x:out select="$elem/title" /></td>
                            <td><span class="badge bg-secondary"><x:out select="$elem/category" /></span></td>
                            <td><span class="badge bg-info text-dark"><x:out select="$elem/units" /></span></td>
                            <td class="fw-bold">₹<x:out select="$elem/wholesalePrice" /></td>
                        </tr>
                    </x:forEach>
                </tbody>
            </table>
        </div>

        <div class="mt-4 p-3 bg-light rounded-3 border">
            <h6 class="fw-bold text-dark"><i class="bi bi-info-circle-fill text-primary me-2"></i>Viva Exam Explanation:</h6>
            <p class="small text-muted mb-0">
                While JSTL XML provides powerful tag-based XPath evaluation, enterprise Java architectures (J2EE/Jakarta EE)
                standardize on separate backend Service and DAO layers with Jackson or JAXB parsers for optimal scalability,
                maintaining presentation decoupling.
            </p>
        </div>
    </div>
</div>

<jsp:include page="/fragments/footer.jsp" />
</body>
</html>
