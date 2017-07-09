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
    .item .title.active .arrow{
        -webkit-transform: rotate(90deg);
        transform: rotate(90deg);
        display: block;
    }
</style>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<div class="order">
    <p style="float: left"><spring:message code="order.detail"/></p>
    <a style="float: right;" href="/u/order/list" >< <spring:message code="goBack"/></a>
</div>
<div class="unfinished">
    <h3 class="title"><spring:message code="order.detail"/></h3>
    <div class="item-group">
        <h4 class="title">
            <spring:message code="order.details.no"/>：${orderDomain.orderNo}
        </h4>
        <div class="item border">
            <p><spring:message code="order.details.time"/>：<fmt:formatDate value="${orderDomain.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></p>
            <p><spring:message code="order.details.status"/>：
                <c:choose>
                    <c:when test="${orderDomain.status==1}"><spring:message code="order.status.waitPay"/>
                        <a href="javascript:void(0)" id="cancelBtn" class="btn btn-primary hide" style="margin-left: 2rem;background-color: #2b2b2b;color: white"><spring:message code="order.details.cancel"/></a>
                    </c:when>
                    <c:when test="${orderDomain.status==2}"><spring:message code="order.status.paid"/></c:when>
                    <c:when test="${orderDomain.status==3}"><spring:message code="order.status.send"/>
                        <a href="javascript:void(0)" id="receivedBtn" class="btn btn-primary hide"   style="margin-left: 2rem;background-color: #2b2b2b;border-radius:1;color: white"><spring:message code="order.details.reseived"/></a>
                    </c:when>
                    <c:when test="${orderDomain.status==4}"><spring:message code="order.status.reach"/></c:when>
                    <c:when test="${orderDomain.status==5}"><spring:message code="order.status.returned"/></c:when>
                    <c:when test="${orderDomain.status==6}"><spring:message code="order.status.refunded"/></c:when>
                    <c:when test="${orderDomain.status==-1}"><spring:message code="order.status.cancel"/></c:when>
                </c:choose>
            </p>
        </div>
        <!-- 重新付款 -->
        <c:if test="${orderDomain.status==1&&orderDomain.paymentMethod!=4}">
        <div class="item" >
            <h4 id="rePay" class="btn-item title" style="display: block" href="javascript:void(0);"><spring:message code="payment.failed.repay"/><span style="float:right;" class="rotateicon arrow">></span></h4>
            <div class="new-pay-way rePayWay" style="display: none;">
                <h3 style="text-align: left"><spring:message code="payment.failed.paymentWay"/></h3>
                <ul id="j_m_payment2" style="border-bottom: none">
                    <li class="active ${web:selectCountry()!=1?'hide':''}"><label for="alipay2"><input type="radio" data-value="1" name="methodOfPayment" id="alipay2"  ><spring:message code="orderinfo.confirm.payway.zfb"/></label></li>
                    <li class="${web:selectCountry()!=1?'hide':''}"><label for="unionPay2"><input type="radio" data-value="2" name="methodOfPayment" id="unionPay2" ><spring:message code="orderinfo.confirm.payway.union"/></label></li>
                    <li class="${web:selectCountry()!=1?'active':''}"><label for="iPay2"><input type="radio" data-value="3" name="methodOfPayment" id="iPay2"  ><spring:message code="orderinfo.confirm.payway.credits"/></label></li>
                </ul>
                <a href="/payment/buildPayment?paymentMethod=1&orderNo=${orderDomain.orderNo}" id="payBtn" class="pay-btn"><spring:message code="order.details.pay"/></a>
            </div>
        </div>

        </c:if>

        <!-- 查看配送状态 -->
        <c:if test="${orderDomain.status==3 || orderDomain.status==4}">
        <div class="item">
            <a id="queryExpress" class="btn-item title" href="javascript:void(0);"><spring:message code="order.details.searchExpress"/><span style="float:right;" class="rotateicon arrow">></span></a>
            <ul class="logisticInfo" style="display: none;width: 100%;overflow: hidden;">

            </ul>
        </div>
        </c:if>
    </div>
    <!--商品详情-->
    <div class="item-group">
        <h4 class="title j_dropdown"><spring:message code="goods.detail.details"/> <span class="arrow">></span></h4>
        <div class=" goods-list clearfix " style="display: none">
            <c:forEach var="item" items="${orderItemList}">
                <div class="goods-item clearfix ">
                    <div class="thumb">
                        <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
                    </div>
                    <div class="goods-info">
                        <div class="name">${item.goodsName}&nbsp;&nbsp;&nbsp;&nbsp;</div>
                        <p class="code"><spring:message code="shoppingCart.no" />：${item.goodsCode}</p>
                        <p class="color">${web:selectLanguage()=='en_US'?item.goodsItemDomain.enName:item.goodsItemDomain.name}</p>
                        <p><spring:message code="shoppingCart.size"/>： &nbsp;${web:selectLanguage()=='en_US'?item.sizeDomain.enName:item.sizeDomain.name}</p>
                        <p><spring:message code="shoppingCart.number"/>：${item.num}&nbsp;&nbsp;&nbsp;&nbsp;</p>
                        <p class="${not empty item.goodsDisPrice?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/>：
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
                        <c:if test="${not empty item.goodsDisPrice}">
                            <p class="xzc-dis-price"><spring:message code="shoppingCart.disPrice"/>：
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
                                    <fmt:formatNumber value="${item.goodsDisPrice}" pattern="#,###" /></span></p>
                        </c:if>

                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!--账单详情-->
    <div class="item-group bill-detail">
        <h4 class="title j_dropdown"><spring:message code="order.details.accountDetail"/> <span class="arrow">></span></h4>
        <div class="item" style="display: none">
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
    <!--配送信息-->
    <div class="item-group">
        <h4 class="title j_dropdown"><spring:message code="order.details.deliveryInfo"/> <span class="arrow">></span></h4>

        <div class="item"  style="display: none">
            <ul>

                <c:if test="${web:selectCountry()!=1}">
                    <li>${orderDomain.shipTitle}  ${orderDomain.shipFirstName}${orderDomain.shipLastName}</li>
                </c:if>
                <c:if test="${web:selectCountry()==1}">
                    <li>${orderDomain.shipLastName}${orderDomain.shipFirstName} ${orderDomain.shipTitle}</li>
                </c:if>

                <li>${orderDomain.shipAddress}</li>
                <c:if test="${not empty orderDomain.shipMemo}">
                    <li>${orderDomain.shipMemo}</li>
                </c:if>
                <li>${orderDomain.shipCity} ${order.shipPostalCode}</li>
                <li>${web:selectLanguage()=='en_US'?orderDomain.shippingCountryDomain.enName:orderDomain.shippingCountryDomain.name}</li>
                <li>${orderDomain.shipPhone}</li>
            </ul>
        </div>
    </div>
   <!--申请退货-->
    <c:if test="${ orderDomain.status!=null && orderDomain.status==3 && orderDomain.canReturnNum<=0}">
    <div class="item-group">
            <div class="item">
                <a class="btn-item"  href="/u/returnOrder/initReturnOrder?orderId=${orderDomain.id}">
                    <spring:message code="order.details.applyReturn"/><span style="float: right">></span></a>
            </div>
    </div>
    </c:if>
    <!--查看退货详情-->
    <c:if test="${not empty orderDomain.returnRequestDomain}">
    <div class="item-group">
            <div class="item">
            <a class="btn-item" href="/u/returnOrder/details?orderId=${orderDomain.returnRequestDomain.id}">
                <spring:message code="order.details.searchReturn"/> <span style="float: right">></span>
            </a>
            </div>
    </div>
    </c:if>
    <div class="privacy">
        <a href="#" class="returnOrchange">
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
        var orderNo = '${orderDomain.orderNo}';
        if(${web:selectCountry()!=1}){
            var ip = returnCitySN["cip"];
            url = "/payment/buildIpayLinks?orderNo="+orderNo+"&ipAddress="+ip;
            $("#payBtn").attr("href",url);
        }

        $(".j_dropdown").on("click", function () {
            $(this).toggleClass("active");
            $(this).next().slideToggle();
        });
        $("#rePay").click(function () {
            $(this).toggleClass("active");
            $(".rePayWay").slideToggle();
        });

        var payMethod = 1;

        var url = "/payment/buildPayment?paymentMethod="+payMethod+"&orderNo="+orderNo;

        //检查地址的正确性不然会报错
        $.post("/checkout/checkAddress",{"addressId":"${orderDomain.shipAddressId}"},function (data) {
            if(data.code==200){
                $("input[name = 'methodOfPayment']").click(function () {
                    payMethod = $(this).attr("data-value");
                    $(this).parents("li").addClass("active").siblings("li").removeClass("active");
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
        $("#receivedBtn").click(function () {
            var id = '${orderDomain.id}';
            $.post("${ctx}/u/order/receipt",{"orderId":id},function (data) {
                console.log(data);
                if(data.code==200){
                    location.reload();
                }
            })
        });

        var num = 1;
        $("#queryExpress").click(function () {
            $(this).toggleClass("active");
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
            $.post("/u/order/queryExpress",{"orderId":id},function (data) {
                if(data.code==200){
                    var trace =JSON.parse(data.data).Traces;
                    if(trace.length>0){
                        $(".logisticInfo").empty();
                        for(var i=0;i<trace.length;i++){
                            $(".logisticInfo").append("<li >"+trace[i].AcceptTime+"<br/>"+ trace[i].AcceptStation+"</li>");
                        }
                    }
                    layer.closeAll('loading');
                }
            })
            $(".logisticInfo").slideToggle();
        });

    });

</script>