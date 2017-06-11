<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    .account-message span{font-size: 1.4rem;}
    .my-account-message ul li, .account-message ul li{
        margin-bottom: 2rem;
    }
    .my-account-message .deit-message, .account-message .deit-message{
        margin-top: -1rem;
    }
    .my-account-title{
        margin-bottom: 1rem;
    }
    .account-message li a{display: block}
</style>
<div class="order">
    <p style="float: left"><spring:message code="account.information"/></p>
    <a style="float: right;" href="/u/account/index">< <spring:message code="goBack"/></a>
</div>
<div class="my-account">
    <div style="display: inline-block;" class="my-account-title">
        <p><spring:message code="account.welcome"/>,</p>
        <c:if test="${empty customerDomain.lastName && empty customerDomain.firstName}"><p>XX</p></c:if>
        <c:if test="${not empty customerDomain}"><p>${customerDomain.lastName}${customerDomain.firstName}!</p></c:if>
    </div>
    <div class="account-message">
        <ul>
            <li>
                <a href="/u/account/toUpdate">
                    <span><spring:message code="account.personal.detail"/></span>
                    <span style="float: right;"><spring:message code="account.personal.edit"/> ></span>
                </a>
            </li>
            <li class="deit-message">
                <ol>
                    <li><spring:message code="account.personal.firstName"/>：${customerDomain.firstName}</li>
                    <li><spring:message code="account.personal.lastName"/>：${customerDomain.lastName}</li>
                    <li><spring:message code="account.personal.email"/>：${accountDomain.email}</li>
                    <li><spring:message code="account.personal.phoneNum"/>：${customerDomain.phone}</li>
                    <li><spring:message code="account.personal.address"/>：${customerAddressDomain.address}</li>
                </ol>
            </li>

            <c:if test="${not empty customerDomain.isArtClubMember}">
                <li>
                    <a href="#">
                        <span><spring:message code="account.personal.level"/>：
                        <c:choose>
                            <c:when test="${customerDomain.customerLevel==1}">
                                普通会员
                            </c:when>
                            <c:when test="${customerDomain.customerLevel==2}">
                                兰花卡会员
                            </c:when>
                            <c:when test="${customerDomain.customerLevel==3}">
                                墨竹卡会员
                            </c:when>
                            <c:when test="${customerDomain.customerLevel==4}">
                                牡丹卡会员
                            </c:when>
                        </c:choose>
                        </span>
                        <span style="float: right;"></span>
                    </a>
                </li>
            </c:if>

            <li>
                <a href="/u/account/toUpdateEmail">
                    <span><spring:message code="account.personal.updateEmail"/> </span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="/u/account/toUpdatePassword">
                    <span><spring:message code="account.personal.updatePassword"/> </span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="/u/account/toValidVip">
                    <span><spring:message code="account.personal.validVIP"/></span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="/u/account/initSubscribe">
                    <span><spring:message code="account.personal.subscriptions"/></span>
                    <span style="float: right;">></span>
                </a>
            </li>
        </ul>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">>　</span>
            <span style="float:left;"><spring:message code="account.personal.secret"/></span>
        </a>
    </div>



</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {

    });
</script>
