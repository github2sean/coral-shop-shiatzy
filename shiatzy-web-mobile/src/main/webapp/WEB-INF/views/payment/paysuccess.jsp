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

        font-size: 1.6rem;
        font-weight: bold;
        line-height: 2rem;
        text-align: center;
    }
    .order-finish .order-no{
        font-size: 1rem;
        text-align: center;
        color: #999999;
        margin-bottom: 2rem;
    }
    .order-group{border-bottom: 2px solid #cccccc;padding-top: 15px;}
</style>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<div class="order">
    <p style="float: left"><spring:message code="payment.failed.order"/></p>
    <a style="float: right;" href="/u/account/index">< <spring:message code="myAccount"/></a>
</div>
<div class=" order-finish">
    <h3 class="title"><spring:message code="payment.success.tips"/></h3>
    <p class="order-no"><spring:message code="payment.failed.orderNo"/>：${order.orderNo}</p>
    <%--商品详情--%>
    <div class="item-group">
        <h4 class="title j_dropdown"><spring:message code="goods.detail.details"/> </h4>
        <div class=" goods-list clearfix">
            <c:set var="orderDomain" value="${order}"></c:set>
            <c:forEach var="item" items="${order.orderItemDomainList}">
                <div class="goods-item clearfix ">
                    <div class="thumb">
                        <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
                    </div>
                    <div class="goods-info">
                        <div class="name">${web:selectLanguage()=='en_US'?item.goodsItemDomain.goods.enName:item.goodsItemDomain.goods.name}</div>
                        <p class="code"><spring:message code="shoppingCart.no" />：${item.goodsCode}</p>
                        <p class="color">${web:selectLanguage()=='en_US'?item.goodsItemDomain.enName:item.goodsItemDomain.name}</p>
                        <p><spring:message code="shoppingCart.size"/>： &nbsp;${web:selectLanguage()=='en_US'?item.sizeDomain.enName:item.sizeDomain.name}</p>
                        <p><spring:message code="shoppingCart.number"/>：${item.num}&nbsp;&nbsp;&nbsp;&nbsp;</p>
                        <p><spring:message code="shoppingCart.unitPrice"/>：
                            &nbsp;<font class="coinSymbol">
                                <c:choose>
                                    <c:when test="${orderDomain.currentCode=='CNY'}">
                                        &nbsp;<spring:message code="coin.ZH"/>
                                    </c:when>
                                    <c:when test="${orderDomain.currentCode=='USD'}">
                                        &nbsp;<spring:message code="coin.USA"/>
                                    </c:when>
                                    <c:when test="${orderDomain.currentCode=='EUR'}">
                                        &nbsp;<spring:message code="coin.EU"/>
                                    </c:when>
                                </c:choose>
                            </font>&nbsp;
                            <fmt:formatNumber value="${item.goodsPrice}" pattern="#,###" /></p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!--账单详情-->
    <div class="item-group bill-detail">
        <h4 class="title j_dropdown"><spring:message code="order.details.accountDetail"/> </h4>
        <div class="item" >
            <ul class="border">
                <li><spring:message code="order.details.preDiscount"/><span>
             &nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${orderDomain.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp;
             <fmt:formatNumber value="${orderDomain.goodsTotal}" pattern="#,###" /></span></li>
                <%--优惠券--%>
                <c:if test="${orderDomain.couponDiscount!=null}">
                    <li><spring:message code="order.details.couponDiscount"/><span data-value="${orderDomain.couponDiscount==null?0:orderDomain.couponDiscount}">
                &nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${orderDomain.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp;
                -<fmt:formatNumber value="${orderDomain.couponDiscount==null?0:orderDomain.couponDiscount}" pattern="#,###" /></span></li>
                </c:if>
                <%--会员优惠--%>
                <c:if test="${orderDomain.memberDiscount!=null}">
                    <li>Art Club&nbsp;<spring:message code="order.details.vipDiscount"/><span data-value="${orderDomain.memberDiscount==null?0:orderDomain.memberDiscount}">
                &nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${orderDomain.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp;
                -<fmt:formatNumber value="${orderDomain.memberDiscount==null?0:orderDomain.memberDiscount}" pattern="#,###" /></span></li>
                </c:if>
                <%--配送费用--%>
                <li><spring:message code="order.details.delivery"/><span>
             &nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${orderDomain.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp;
             <fmt:formatNumber value="${orderDomain.shipFee}" pattern="#,###" /></span></li>
            </ul>
            <p class="total-price"><spring:message code="payment.failed.total"/><span>
         &nbsp;<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${orderDomain.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${orderDomain.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp;
         <fmt:formatNumber value="${orderDomain.orderTotal}" pattern="#,###" /></span></p>
        </div>
    </div>

    <div class="order-details item-group">
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

    <!--配送信息-->
    <div class="item-group">
        <h4 class="title j_dropdown"><spring:message code="payment.failed.shipping"/> </h4>
        <div class="item">
            <ul>
                <c:if test="${order.shippingMethod==1 && not empty order.shippingMethod}">
                    <li>${order.shipFirstName} ${order.shipLastName}</li>

                    <li>${order.shipAddress}</li>
                    <c:if test="${not empty order.shipMemo}">
                        <li>${order.shipMemo}</li>
                    </c:if>
                    <li>${order.shipCity} ${order.shipPostalCode}</li>
                    <li>${order.shipCountry}</li>
                    <li>${order.shipPhone}</li>
                </c:if>
                <c:if test="${order.shippingMethod==2 && not empty order.shippingMethod}">
                    <li><spring:message code="payment.failed.shippingStore"/>：${order.shipShopId}</li>
                </c:if>
                <c:if test="${order.billRequired==0 || empty order.billRequired}">
                    <li><spring:message code="payment.failed.bill"/>：<spring:message code="payment.failed.noNeed"/></li>
                </c:if>
                <c:if test="${order.billRequired==1}">
                    <li><spring:message code="payment.failed.bill"/>：${order.billTitle}</li>
                </c:if>
            </ul>
        </div>
    </div>

    <div class="again-btn">
        <a href="/u/order/details?orderId=${order.id}" class="btn-default"><spring:message code="payment.failed.back"/></a>
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