<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    h3.title {
        height: 4.2rem;
        margin-left: 1rem;
        font-size: 1.4rem;
        line-height: 4.2rem;
        text-align: left;
    }
    .order-group{border-bottom: 2px solid #cccccc;padding-top: 15px;}
</style>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<div class="order">
    <p style="float: left"><spring:message code="payment.failed.order"/></p>
    <a style="float: right;" href="/u/account/index">< <spring:message code="myAccount"/></a>
</div>
<div class="unfinished">
    <h3><spring:message code="payment.failed.tips"/></h3>
    <p><spring:message code="payment.failed.orderNo"/>&nbsp;&nbsp;${order.orderNo}</p>
    <div class="again-btn">
        <a href="javascript:void(0)" id="payBtn"><spring:message code="payment.failed.repay"/></a>
    </div>

    <h5 style="border-bottom: solid 1px #cccccc"><spring:message code="payment.failed.orderDetail"/></h5>
    <c:forEach var="row" items="${order.orderItemDomainList}">
        <c:set var="item" value="${row.goodsItemDomain}"></c:set>
        <div class="verify-message-middle">
            <div class="verify-main">
                <img src="${ImageModel.toFirst(item.thumb).file}" alt="${ImageModel.toFirst(item.thumb).file}">
                <div class="img-message">
                    <h3>${web:selectLanguage()=='en_US'?item.goods.enName:item.goods.name}</h3>
                    <h6><spring:message code="shoppingCart.no"/> ${row.goodsCode}</h6>
                    <div style="display: inline-block;" class="size">
                        <p style="float:left;margin-right: 3.0918rem;">${web:selectLanguage()=='en_US'?item.enName:item.name}</p>
                        <p><spring:message code="shoppingCart.size"/>&nbsp;${JSONObject.fromObject(row.skuSpecifications).get("size")}</p>
                    </div>
                    <p><spring:message code="shoppingCart.number"/>：${row.num}</p>
                    <p><spring:message code="shoppingCart.unitPrice"/>　&nbsp;<font class="coinSymbol">
                        <c:choose>
                            <c:when test="${order.currentCode=='CNY'}">
                                &nbsp;<spring:message code="coin.ZH"/>
                            </c:when>
                            <c:when test="${order.currentCode=='USD'}">
                                &nbsp;<spring:message code="coin.USA"/>
                            </c:when>
                            <c:when test="${order.currentCode=='EUR'}">
                                &nbsp;<spring:message code="coin.EU"/>
                            </c:when>
                        </c:choose>
                    </font>&nbsp;  <fmt:formatNumber value="${row.goodsPrice}" pattern="#,###"/></p>
                </div>
            </div>
        </div>
    </c:forEach>

    <div class="order-details">
        <h4><spring:message code="payment.failed.orderTotal"/><span>v</span></h4>
        <ul>
            <c:forEach var="row" items="${order.orderItemDomainList}">
                <li>${web:selectLanguage()=='en_US'?row.goodsItemDomain.goods.enName:row.goodsItemDomain.goods.name}<span>&nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${order.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${order.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${order.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp; <fmt:formatNumber value="${row.goodsPrice}" pattern="#,###"/></span></li>
            </c:forEach>

            <li><spring:message code="payment.failed.disAmt"/><span>&nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${order.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${order.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${order.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp; -<fmt:formatNumber value="${empty order.couponDiscount?0:order.couponDiscount}" pattern="#,###"/></span></li>
            <li><spring:message code="payment.failed.memAmt"/><span>&nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${order.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${order.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${order.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp; -<fmt:formatNumber value="${empty order.memberDiscount?0:order.memberDiscount}" pattern="#,###"/></span></li>
            <%--<li>关税和税收<span>¥ 100</span></li>--%>
            <li><spring:message code="payment.failed.fee"/><span>&nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${order.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${order.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${order.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp; <fmt:formatNumber value="${empty order.shipFee?0:order.shipFee}" pattern="#,###"/></span></li>
        </ul>
        <p><spring:message code="payment.failed.total"/><span>&nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${order.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${order.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${order.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp;<fmt:formatNumber value="${order.orderTotal}" pattern="#,###"/> </span></p>
    </div>
    <div class="order-details">
        <h4><spring:message code="payment.failed.paymentWay"/></h4>
        <c:if test="${order.paymentMethod==1}">
            <p><spring:message code="payment.failed.zfb"/></p>
        </c:if>
        <c:if test="${order.paymentMethod==2}">
            <p><spring:message code="payment.failed.union"/></p>
        </c:if>
        <c:if test="${order.paymentMethod==3}">
            <p>VISA</p>
        </c:if>

    </div>
    <div class="delivery-detail">
        <h4><spring:message code="payment.failed.shipping"/></h4>
        <c:if test="${order.shippingMethod==1 && not empty order.shippingMethod}">
            <p><spring:message code="payment.failed.shippingAddress"/>：${order.shipAddress}</p>
        </c:if>
        <c:if test="${order.shippingMethod==2 && not empty order.shippingMethod}">
            <p><spring:message code="payment.failed.shippingStore"/>：${order.shipShopId}</p>
        </c:if>

        <c:if test="${order.billRequired==0 || empty order.billRequired}">
            <p><spring:message code="payment.failed.bill"/>：<spring:message code="payment.failed.noNeed"/></p>
        </c:if>
        <c:if test="${order.billRequired==1}">
            <p><spring:message code="payment.failed.bill"/>：${order.billTitle}</p>
        </c:if>

    </div>
    <div class="again-btn hide">
        <a href="#"><spring:message code="payment.failed.back"/></a>
    </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {

                //检查地址的正确性不然会报错
                    var payMethod = '${order.paymentMethod}';
                    var orderNo = '${order.orderNo}';
                    console.log(payMethod);
                    if(payMethod == 1){
                        url = "/payment/buildPayment?paymentMethod="+payMethod+"&orderNo="+orderNo;
                    }else if(payMethod == 2){
                        url = "/payment/initUnionPay?orderNo="+orderNo;
                    } else if(payMethod == 3){
                        var ip = returnCitySN["cip"];
                        url = "/payment/buildIpayLinks?orderNo="+orderNo+"&ipAddress="+ip;
                    }
                    console.log(url);
                    $("#payBtn").attr("href",url);
    });

</script>