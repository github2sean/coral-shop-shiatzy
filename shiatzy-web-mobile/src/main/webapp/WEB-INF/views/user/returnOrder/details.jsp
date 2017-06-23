<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>


<div class="order">
    <p style="float: left"><spring:message code="return.detail.tiltle"/></p>
    <a style="float: right;" href="/order/list">< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="verify-message-top">
        <h2><spring:message code="return.detail.no"/>：${returnRequestDomain.orderNo}</h2>
        <div class="return-time" style="border-top:2px solid #cccccc">
            <c:if test="${sessionScope.language=='en_US'}">
                <p>
                    The return application was submitted at <fmt:formatDate value="${returnRequestDomain.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" />. A total of &nbsp;<font class="coinSymbol">
                    <c:choose>
                        <c:when test="${returnRequestDomain.orderDomain.currentCode=='CNY'}">
                            &nbsp;<spring:message code="coin.ZH"/>
                        </c:when>
                        <c:when test="${returnRequestDomain.orderDomain.currentCode=='USD'}">
                            &nbsp;<spring:message code="coin.USA"/>
                        </c:when>
                        <c:when test="${returnRequestDomain.orderDomain.currentCode=='EUR'}">
                            &nbsp;<spring:message code="coin.EU"/>
                        </c:when>
                    </c:choose>
                </font>&nbsp;<fmt:formatNumber value="${preBackMoney-fee}" pattern="#,###"/> refund.
                </p>
            </c:if>
            <c:if test="${sessionScope.language!='en_US'}">
                <p>退货申请,于<fmt:formatDate value="${returnRequestDomain.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" />
                    提交，总计&nbsp;<font class="coinSymbol">
                        <c:choose>
                            <c:when test="${returnRequestDomain.orderDomain.currentCode=='CNY'}">
                                &nbsp;<spring:message code="coin.ZH"/>
                            </c:when>
                            <c:when test="${returnRequestDomain.orderDomain.currentCode=='USD'}">
                                &nbsp;<spring:message code="coin.USA"/>
                            </c:when>
                            <c:when test="${returnRequestDomain.orderDomain.currentCode=='EUR'}">
                                &nbsp;<spring:message code="coin.EU"/>
                            </c:when>
                        </c:choose>
                    </font>&nbsp;<fmt:formatNumber value="${preBackMoney-fee}" pattern="#,###"/>的退款申请。
                </p>
            </c:if>
        </div>
    </div>
    <div class="verify-message-middle">
        <h2><spring:message code="return.detail.tiltle"/><span style="float: right">v</span></h2>
        <c:forEach var="row" items="${returnOrderItemList}">
        <div style="padding-left: 3rem;" class="verify-main">
            <img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="">
            <div style="margin-left: 6rem;" class="img-message">
                <h3>${row.goodsName}</h3>
                <h6>${row.goodsCode}</h6>
                <div style="display: inline-block;" class="size">
                    <p style="float:left;margin-right: 3.0918rem;">${sessionScope.language=='en_US'?row.goodsItemDomain.enName:row.goodsItemDomain.name}</p>
                    <p><spring:message code="shoppingCart.size"/>&nbsp;${sessionScope.language=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}</p>
                </div>
                <p><spring:message code="shoppingCart.number"/>：${row.num}</p>
                <p><spring:message code="shoppingCart.unitPrice"/>　&nbsp;<font class="coinSymbol">
                    <c:choose>
                        <c:when test="${returnRequestDomain.orderDomain.currentCode=='CNY'}">
                            &nbsp;<spring:message code="coin.ZH"/>
                        </c:when>
                        <c:when test="${returnRequestDomain.orderDomain.currentCode=='USD'}">
                            &nbsp;<spring:message code="coin.USA"/>
                        </c:when>
                        <c:when test="${returnRequestDomain.orderDomain.currentCode=='EUR'}">
                            &nbsp;<spring:message code="coin.EU"/>
                        </c:when>
                    </c:choose>
                </font>&nbsp; <fmt:formatNumber value="${row.goodsPrice}" pattern="#,###"/></p>
            </div>
        </div>
        <p style="padding-left: 3rem;" class="order-state"><spring:message code="return.detail.status"/>：

        <c:choose>
            <c:when test="${row.status==1}">
                <spring:message code="return.detail.status.waitReseive"/>
            </c:when>
            <c:when test="${row.status==2}">
                <spring:message code="return.detail.status.Reseived"/>
            </c:when>
            <c:when test="${row.status==3}">
                <spring:message code="return.detail.status.acceptBack"/>
            </c:when>
            <c:when test="${row.status==4}">
                <spring:message code="return.detail.status.refuseBack"/>
            </c:when>
            <c:when test="${row.status==5}">
                <spring:message code="return.detail.status.cancelBack"/>
            </c:when>
        </c:choose>
        </p>
        </c:forEach>
    </div>


    <div style="margin-bottom: 7rem;" class="verify-message-middle">
        <p style="padding-left: 3rem;border-top: 2px solid #cccccc;line-height: 2.5rem" class="order-state"> <spring:message code="return.detail.fee"/>：<span class="fee" style="float: right">&nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${returnRequestDomain.orderDomain.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${returnRequestDomain.orderDomain.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${returnRequestDomain.orderDomain.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp; -<fmt:formatNumber value="${fee}" pattern="#,###"/></span></p>
        <p style="text-align: right;border-top: 2px solid #cccccc;line-height: 2.5rem"> <spring:message code="return.detail.total"/>：&nbsp;<font class="coinSymbol">
            <c:choose>
                <c:when test="${returnRequestDomain.orderDomain.currentCode=='CNY'}">
                    &nbsp;<spring:message code="coin.ZH"/>
                </c:when>
                <c:when test="${returnRequestDomain.orderDomain.currentCode=='USD'}">
                    &nbsp;<spring:message code="coin.USA"/>
                </c:when>
                <c:when test="${returnRequestDomain.orderDomain.currentCode=='EUR'}">
                    &nbsp;<spring:message code="coin.EU"/>
                </c:when>
            </c:choose>
        </font>&nbsp; <fmt:formatNumber value="${preBackMoney-fee}" pattern="#,###"/></p>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;margin-left: -10px">> </span>
            <span style="float: left;"><spring:message code="order.details.7day"/></span>
        </a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;margin-left: -10px">> </span>
            <span style="float: left;" class="privacyNotice"><spring:message code="privacyPolicy"/></span>
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