<%@ page import="com.dookay.coral.common.model.ImageModel" %>
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
    <p style="float: left"><spring:message code="order.detail"/></p>
    <a style="float: right;" href="/order/list" >< <spring:message code="goBack"/></a>
</div>
<div class="unfinished">
    <h3 class="title"><spring:message code="order.detail"/></h3>
    <div class="order-group">
        <spring:message code="order.details.no"/>：${orderDomain.orderNo}
    </div>
    <div class="order-group">
        <p><spring:message code="order.details.time"/>：<fmt:formatDate value="${orderDomain.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></p>
        <p><spring:message code="order.details.status"/>：
            <c:choose>
                <c:when test="${orderDomain.status==1}"><spring:message code="order.status.waitPay"/>&nbsp;
                    <a href="" id="cancelBtn" class="btn btn-primary hide" style="background-color: #2b2b2b;color: white"><spring:message code="order.details.cancel"/></a>
                </c:when>
                <c:when test="${orderDomain.status==2}"><spring:message code="order.status.paid"/></c:when>
                <c:when test="${orderDomain.status==3}"><spring:message code="order.status.send"/></c:when>
                <c:when test="${orderDomain.status==4}"><spring:message code="order.status.reach"/></c:when>
                <c:when test="${orderDomain.status==-1}"><spring:message code="order.status.cancel"/></c:when>
            </c:choose>
        </p>
    </div>
    <c:if test="${orderDomain.status==1&&orderDomain.paymentMethod!=4}">
    <div class="order-group">
        <a id="rePay" style="display: block" href="javascript:void(0);"><spring:message code="payment.failed.repay"/><span style="float:right;" class="rotateicon">></span></a>
        <div style="display: none;margin-bottom: 15px;" class="rePayWay">
            <spring:message code="payment.failed.paymentWay"/>: <br>
            <p><label class="radiobox"><input data-value="1" style="vertical-align:middle; margin-top:-1px; margin-bottom:1px;"  type="radio" name="payMethod" checked="checked"/><i class="i-radiobox iconfont icon-duigou"></i><spring:message code="orderinfo.confirm.payway.zfb"/></label></p>
            <p> <label class="radiobox"><input data-value="2" style="vertical-align:middle; margin-top:-1px; margin-bottom:1px;"  type="radio" name="payMethod"/><i class="i-radiobox iconfont icon-duigou"></i><spring:message code="orderinfo.confirm.payway.union"/></label></p>
            <p> <label class="radiobox"><input data-value="3" style="vertical-align:middle; margin-top:-1px; margin-bottom:1px;"  type="radio" name="payMethod"/><i class="i-radiobox iconfont icon-duigou"></i><spring:message code="orderinfo.confirm.payway.credits"/></label></p>
            <div class="text-left">
                <a href="/payment/buildPayment?paymentMethod=1&orderNo=${orderDomain.orderNo}" id="payBtn" class="btn btn-submit" style="background-color: #2b2b2b;color: white"><spring:message code="order.details.pay"/></a>
            </div>
        </div>
    </div>
    </c:if>
    <c:if test="${orderDomain.status==3 || orderDomain.status==4}">
    <div class="order-group">
        <p><a id="queryExpress" href="javascript:void(0);"><spring:message code="order.details.searchExpress"/><span style="float:right;" class="rotateicon">></span></a></p>
        <div class="logisticInfo" style="display: none;width: 100%;overflow: hidden;background-color: #F1F1F1">
            <div></div>
        </div>
    </div>
    </c:if>

    <div class="verify-message-middle">
        <h2><spring:message code="goods.detail.details"/></h2>
        <c:forEach var="item" items="${orderItemList}">
            <div class="verify-main">
                <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
                <div class="img-message">
                    <h3>${item.goodsDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;</h3>
                    <h6>${item.goodsCode}</h6>
                    <div style="display: inline-block;" class="size">
                        <p style="float:left;margin-right: 3.0918rem;">${sessionScope.language=='en_US'?item.goodsItemDomain.enName:item.goodsItemDomain.name}</p>
                        <p><spring:message code="shoppingCart.size"/> &nbsp;${sessionScope.language=='en_US'?item.sizeDomain.enName:item.sizeDomain.name}</p>
                    </div>
                    <p><spring:message code="shoppingCart.number"/>：${item.num}&nbsp;&nbsp;&nbsp;&nbsp;</p>
                    <p><spring:message code="shoppingCart.unitPrice"/>　
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
    <div class="order-details">
        <h4><spring:message code="order.details.accountDetail"/></h4>
        <ul>
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
            <li><spring:message code="payment.failed.fee"/><span>
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
        <p><spring:message code="payment.failed.total"/><span>
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
         <fmt:formatNumber value="${orderDomain.orderTotal-orderDomain.couponDiscount-orderDomain.memberDiscount+orderDomain.shipFee}" pattern="#,###" /></span></p>
    </div>
    <div class="information">
        <h4><spring:message code="payment.failed.shippingAddress"/></h4>
        <p>${orderDomain.shipAddress}</p>
        <p>${orderDomain.shipTitle}</p>
        <c:if test="${ orderDomain.status!=null && orderDomain.status!=1 && orderDomain.status!=-1 && orderDomain.canReturnNum>0}">
            <div class="information" style="margin-top: 2rem">
                <p ><a href="/returnOrder/initReturnOrder?orderId=${orderDomain.id}"><spring:message code="order.details.applyReturn"/><span style="float: right">></span></a></p>
            </div>
        </c:if>

        <c:if test="${not empty orderDomain.returnRequestDomain}">

            <a href="/returnOrder/details?orderId=${orderDomain.returnRequestDomain.id}"><p style="margin-top: 2rem;height: 3rem;line-height: 3rem;border-top:2px solid #cccccc;border-bottom:2px solid #cccccc"><spring:message code="order.details.searchReturn"/> <span style="float: right">></span></p></a>

        </c:if>

    </div>

    <div class="privacy">
        <a href="#">
            <span style="float:left;margin-left: -10px">> </span>
            <span style="float: left;"><spring:message code="order.details.7day"/></span>
        </a>
    </div>
    <div class="privacy">
        <a href="#" class="privacyNotice">
            <span style="float:left;margin-left: -10px">> </span>
            <span style="float: left;"><spring:message code="privacyPolicy"/></span>
        </a>
    </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {

        $("#rePay").click(function () {
            $(this).css("color","#333");
            $(".rePayWay").slideToggle();
        });
        var payMethod = 1;
        var orderNo = '${orderDomain.orderNo}';
        var url = "/payment/buildPayment?paymentMethod="+payMethod+"&orderNo="+orderNo;

        //检查地址的正确性不然会报错
        $.post("/checkout/checkAddress",{"addressId":"${orderDomain.shipAddressId}"},function (data) {
            if(data.code==200){
                $("input[name = 'payMethod']").click(function () {
                    payMethod = $(this).attr("data-value");
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
            }else {
                $("#payBtn").attr('disabled',"true").removeAttr("href").click(function () {
                    layer.msg("<spring:message code="order.details.addressErro"/>");
                });
            }
        });

        $("#cancelBtn").click(function () {
            console.log(1);
            var id = '${orderDomain.id}';
            $.post("/order/cancel",{"orderId":id},function (data) {
                console.log(data);
                if(data.code==200){
                    location.reload();
                }
            })

        });

        var num = 1;
        $("#queryExpress").click(function () {
            num++;
            $(this).css("color","#333");
            var id = '${orderDomain.id}';
            var $childDiv = $(".logisticInfo").children("div");
            if(num%2==0){
                //loading层
                var index = layer.load(2, {
                    shade: [0.4,'#fff'] //0.1透明度的白色背景
                });
            }
            $childDiv.each(function () {
                $(this).remove();
            });
            $.post("/order/queryExpress",{"orderId":id},function (data) {
                if(data.code==200){
                    var trace =JSON.parse(data.data).Traces;
                    if(trace.length>0){
                        for(var i=0;i<trace.length;i++){
                            $(".logisticInfo").append("<div style='float: right;width: 90%;overflow: hidden;border-bottom: #cccccc dashed 1px;margin-top: 1rem' >"+trace[i].AcceptTime+"<br/>"+ trace[i].AcceptStation+"</div>");
                        }

                    }
                    layer.closeAll('loading');
                }
            })
            $(".logisticInfo").slideToggle();
        });

    });

</script>