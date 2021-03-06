<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    .black{
        color: black;
    }

</style>
<div class="back-up clearfix">
    <a href="/u/account/details">
        <a style="float: right;" href="/u/account/details" ><h3>< <spring:message code="goBack"/></h3></a>
    </a>
</div>
<div class="club-card <c:if test="${tempMemberDomain.cardType=='CN-C'}">club-card-lighter</c:if> ">
    <c:choose>
        <c:when test="${tempMemberDomain.cardType=='CN-D'}">
            <img src="${ctx}/static/images/SC_other1.png" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='CN-B'}">
            <img src="${ctx}/static/images/SC_bamboo1.png" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='CN-C'}">
            <img src="${ctx}/static/images/SC_fish1.png" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='CN-A'}">
            <img src="${ctx}/static/images/SC_peony1.png" alt="">
        </c:when>

    </c:choose>
    <p class="card-title ">ART CLUB <spring:message code="vip.valid.card.title"/></p>

    <c:choose>
        <c:when test="${tempMemberDomain.cardType=='CN-D'}">
            <p class="card-name "> <spring:message code="account.personal.card.common"/></p>
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='CN-B'}">
            <p class="card-name "> <spring:message code="account.personal.card.bamboo"/></p>
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='CN-C'}">
            <p class="card-name "> <spring:message code="account.personal.card.fish"/></p>
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='CN-A'}">
            <p class="card-name "> <spring:message code="account.personal.card.peony"/></p>
        </c:when>
    </c:choose>
    <c:if test="${tempMemberDomain.cardType!='CN-D'}">
        <p class="card-num "><spring:message code="vip.valid.card.number"/>：${tempMemberDomain.cardNo}</p>
    </c:if>
</div>
<div class="anew">
    <a href="/u/account/toValidVip">
        <p><spring:message code="vip.valid.card.revalid"/></p>
    </a>
</div>
<div class="club-bottom" >
    <p><spring:message code="account.personal.card.point"/>：${tempMemberDomain.point}</p>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {
        console.log("CARD:"+'${tempMemberDomain.cardType}');
    });

</script>
