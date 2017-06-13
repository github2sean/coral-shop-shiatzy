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
            <img src="${ctx}/static/images/Banner-1.jpg" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='墨竹卡会员'}">
            <img src="${ctx}/static/images/Banner-1.jpg" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='兰花卡会员'}">
            <img src="${ctx}/static/images/Banner-1.jpg" alt="">
        </c:when>
        <c:when test="${tempMemberDomain.cardType=='牡丹卡会员'}">
            <img src="${ctx}/static/images/Banner-1.jpg" alt="">
        </c:when>
    </c:choose>
    <p class="card-title ">夏姿门市 ART CLUB 會員</p>
    <p class="card-name ">${tempMemberDomain.cardType}</p>
    <p class="card-num ">会员卡号：${tempMemberDomain.cardNo}</p>
</div>
<div class="anew">
    <a href="/u/account/toValidVip">
        <p>重新认证</p>
    </a>
</div>
<div class="club-bottom" >
    <p>加入夏姿陈Art Club会员，可</p>
    <p>尊享xxxxxxxxxxxxxxxxxxxxxxx.</p>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {




    });

</script>
