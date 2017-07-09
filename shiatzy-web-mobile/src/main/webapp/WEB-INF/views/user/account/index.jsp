<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    .my-account-message span{font-size: 1.4rem;}
    .my-account-message ul li, .account-message ul li{
        margin-bottom: 2rem;
        font-size: 1.4rem;
    }
    .my-account-message ul li .arrow{
        float: right;margin-top: 6px;
    }
    .my-account-title{margin-bottom: 1rem;}
    .my-account-message li a{display: block}
</style>
<div class="order">
    <p style="float: left"><spring:message code="myAccount"/> </p>
    <a style="float: right;" href="/home/index" >< <spring:message code="goBack"/></a>
</div>
<div class="my-account">
    <div style="display: inline-block;" class="my-account-title">
        <p><spring:message code="account.welcome"/>,</p>
        <c:if test="${not empty customerDomain}"><p>${customerDomain.lastName}${customerDomain.firstName}!</p></c:if>
    </div>
    <div class="my-account-message">
        <ul>
            <li>
                <a href="/u/account/details">
                    <span ><svg><use xlink:href="#ac-info"></use></svg></span>
                    <span><spring:message code="account.information"/></span>
                    <span class="arrow">></span>
                </a>
            </li>
            <li>
                <a href="/checkout/listShipAddress?way=index">
                    <span><svg><use xlink:href="#ac-add"></use></svg></span>
                    <span><spring:message code="account.address"/></span>
                    <span class="arrow">></span>
                </a>
            </li>
            <li>
                <a href="/u/order/list">
                    <span><svg><use xlink:href="#ac-order"></use></svg></span>
                    <span><spring:message code="account.order"/></span>
                    <span class="arrow">></span>
                </a>
            </li>
            <li>
                <a href="/reservation/list">
                    <span><svg><use xlink:href="#ap-small"></use></svg></span>
                    <span><spring:message code="account.reservation"/></span>
                    <span class="arrow">></span>
                </a>
            </li>
            <li>
                <a href="/cart/wishlist">
                    <span><svg><use xlink:href="#heart"></use></svg></span>
                    <span><spring:message code="account.wish"/></span>
                    <span class="arrow">></span>
                </a>
            </li>

        </ul>
    </div>
    <div class="outline">
        <a type="button" class="loginOut" ><spring:message code="account.out"/></a>
    </div>



</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {
        $(".top-right-nav").find("li:eq(2)").addClass("active");
        console.log('${user_context}');


        $(".loginOut").click(function () {
            $.post("/passport/loginOut.do",function (data) {
                if(data.data==1){
                    location.href = "passport/toLogin.do";
                }
            });
        });

    });

</script>