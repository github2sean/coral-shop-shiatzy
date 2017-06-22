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
        <a style="float: right;" href=”#” onClick="javascript :history.back(-1);"><h3>< <spring:message code="goBack"/></h3></a>
    </a>
</div>
<div class="club-card" >
    <c:choose>
        <c:when test="${tempMemberDomain.cardType=='普通会员'}">
            <img src="${ctx}/static/images/SC_other1.png" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='墨竹卡会员'}">
            <img src="${ctx}/static/images/SC_bamboo1.png" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='兰花卡会员'}">
            <img src="${ctx}/static/images/SC_fish1.png" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='牡丹卡会员'}">
            <img src="${ctx}/static/images/SC_peony1.png" alt="">
        </c:when>
    </c:choose>
    <p class="card-title ">ART CLUB <spring:message code="vip.valid.card.title"/></p>

    <c:choose>
        <c:when test="${tempMemberDomain.cardType=='普通会员'}">
            <p class="card-name "> <spring:message code="account.personal.card.common"/></p>
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='墨竹卡会员'}">
            <p class="card-name "> <spring:message code="account.personal.card.bamboo"/></p>
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='兰花卡会员'}">
            <p class="card-name "> <spring:message code="account.personal.card.fish"/></p>
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='牡丹卡会员'}">
            <p class="card-name "> <spring:message code="account.personal.card.peony"/></p>
        </c:when>
    </c:choose>
    <p class="card-num "><spring:message code="vip.valid.card.number"/>：${tempMemberDomain.cardNo}</p>
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




    });

</script>
