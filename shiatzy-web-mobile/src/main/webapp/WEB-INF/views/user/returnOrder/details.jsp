<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="com.dookay.coral.shop.order.enums.ReturnRequestStatusEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>


<div class="order">
    <p style="float: left"><spring:message code="return.detail.tiltle"/></p>
    <a style="float: right;" href="/u/order/list">< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="item-group">
        <h2 class="title"><spring:message code="return.detail.no"/>：${returnRequestDomain.orderNo}</h2>
        <div class="return-time" >
            <c:if test="${web:selectLanguage()=='en_US'}">
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
            <c:if test="${web:selectLanguage()!='en_US'}">
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
    <div class="item-group">
        <h4 class="title j_dropdown"><spring:message code="return.detail.tiltle"/> (状态：${ReturnRequestStatusEnum.valueOf(returnRequestDomain.status).description}) </h4>
        <div class="item">
            <div class="goods-list return-goods">
                <c:forEach var="item" items="${returnOrderItemList}">
                    <div class="goods-item clearfix ">
                        <div class="thumb">
                            <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
                        </div>
                        <div class="goods-info">
                            <div class="name">${item.goodsName}&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            <p class="code"><spring:message code="shoppingCart.no"/>：${item.goodsCode}</p>
                            <p class="color">${web:selectLanguage()=='en_US'?item.goodsItemDomain.enName:item.goodsItemDomain.name}</p>
                            <p><spring:message code="shoppingCart.size"/>
                                ：${web:selectLanguage()=='en_US'?item.sizeDomain.enName:item.sizeDomain.name}</p>
                            <p class=""><spring:message code="shoppingCart.number"/>：${item.num} &nbsp;</p>
                            <p class="${not empty item.goodsDisPrice?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/>：<font class="coinSymbol">
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
                            </font>&nbsp;<fmt:formatNumber value="${item.goodsPrice}" pattern="#,###"/></p>
                            <c:if test="${not empty item.goodsDisPrice}">
                                <p class="xzc-dis-price"><spring:message code="shoppingCart.disPrice"/>：<font class="coinSymbol">
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
                                </font>&nbsp;<fmt:formatNumber value="${item.goodsDisPrice}" pattern="#,###"/></p>
                            </c:if>
                        </div>
                    </div>

                    <c:set var="returnReason" value="${returnReasonMap.get(item.id.toString())}"></c:set>
                    <p class="status">退货理由：${returnReason}</p>
                </c:forEach>
            </div>
            <div style="font-size: 1.2rem" > <spring:message code="return.detail.fee"/>：<span class="fee" style="float: right">&nbsp;<font class="coinSymbol">
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
                        </font>&nbsp; <fmt:formatNumber value="${empty returnRequestDomain.shipFee?0:returnRequestDomain.shipFee}" pattern="#,###"/></span></div>
    </div>
    <div style="margin-bottom: 3rem;" class="verify-message-middle">

        <p style="text-align: right;border-top: 2px solid #cccccc;line-height: 2.5rem;padding-left: 3.9rem;margin-right: 0.9rem;"> <spring:message code="return.detail.total"/>：&nbsp;<font class="coinSymbol">
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
        </font>&nbsp;<fmt:formatNumber value="${preBackMoney-returnRequestDomain.shipFee}" pattern="#,###"/> </p>
    </div>
    </div>


    <div class="privacy">
        <a href="#" class="returnOrchange">
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