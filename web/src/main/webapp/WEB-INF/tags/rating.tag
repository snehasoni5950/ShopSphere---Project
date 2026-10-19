<%@ tag description="Rating Stars Component" pageEncoding="UTF-8"%>
<%@ attribute name="score" required="true" type="java.lang.Number" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<span class="text-warning rating-stars">
    <c:forEach var="i" begin="1" end="5">
        <c:choose>
            <c:when test="${score >= i}">
                <i class="bi bi-star-fill"></i>
            </c:when>
            <c:otherwise>
                <i class="bi bi-star"></i>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <span class="text-muted small ms-1">(${score})</span>
</span>
