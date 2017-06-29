<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<div class="order">
    <p style="float: left"><spring:message code="orderinfo.checkoutTitle"/> / <spring:message code="orderinfo.confirm.detail"/></p>
    <a style="float: right;" href="/checkout/orderInfo">< <spring:message code="goBack"/></a>
</div>
<div class="order-finish">
    <h3><spring:message code="orderinfo.confirm.total"/>：
        <span>
            <c:choose>
                <c:when test="${order.currentCode=='CNY'}">
                    <spring:message code="coin.ZH"/>
                </c:when>
                <c:when test="${order.currentCode=='USD'}">
                    <spring:message code="coin.USA"/>
                </c:when>
                <c:when test="${order.currentCode=='EUR'}">
                    <spring:message code="coin.EU"/>
                </c:when>
            </c:choose>
        </span>&nbsp;
        <span class=""><fmt:formatNumber value="${order.orderTotal}" pattern="#,###"/></span></h3>
        <div class="delivery content">
        <h3>1. <spring:message code="orderinfo.confirm.shipping"/></h3>
        <c:if test="${not empty order.storeDomain}">
            <div class="delivery-message" style="background-color: inherit;border: 2px solid #999999;border-bottom: none;">
                <span><spring:message code="orderinfo.confirm.expressDelivery"/></span>
                <p></p>
                <p style="display: initial;"></p>
                <a href="/checkout/listShipAddress"><spring:message code="orderinfo.confirm.select"/> ></a>
            </div>
            <div class="delivery-message" style="background-color: #cccccc;">
                <p><spring:message code="orderinfo.confirm.storePickup"/></p>
                    <p>${web:selectLanguage()=='en_US'?order.storeDomain.enTitle:order.storeDomain.name}</p>
                    <p>${web:selectLanguage()=='en_US'?order.storeDomain.enAddress:order.storeDomain.address}</p>
                <a href="/checkout/listStore"><spring:message code="orderinfo.confirm.select"/> ></a>
            </div>
        </c:if>
        <c:if test="${empty order.storeDomain}">
            <div class="delivery-message" style="margin-top: 2px">
                <span><spring:message code="orderinfo.confirm.expressDelivery"/></span>
                <p>${order.customerAddressDomain.title}</p>
                <p style="display: initial;">${order.customerAddressDomain.address}</p>
                <a href="/checkout/listShipAddress"><spring:message code="orderinfo.confirm.select"/> ></a>
            </div>
            <div class="drugstore">
                <p><spring:message code="orderinfo.confirm.storePickup"/></p>
                <a href="/checkout/listStore"><spring:message code="orderinfo.confirm.select"/> ></a>
            </div>
        </c:if>
    </div>
    <div class="delivery">
        <h3>2. <spring:message code="orderinfo.confirm.billinfo"/></h3>
        <div>
            <span class="mr-2"><label class="radiobox"><input type="radio" checked="checked" name="isNeed" id="noNeed"><i class="i-radiobox iconfont icon-duigou"></i><spring:message code="orderinfo.confirm.billinfo.noneed"/></label></span>
            <span class="mr-2"><label class="radiobox"><input type="radio" name="isNeed" id="need"><i class="i-radiobox iconfont icon-duigou"></i><spring:message code="orderinfo.confirm.billinfo.need"/></label></span>
        </div>
        <p id="showBill" style="display: none;margin-left: 2.3rem;font-size: 1.0821rem;margin-top: 2.7rem;border-bottom: 2px solid #cccccc;">
            <spring:message code="orderinfo.confirm.billinfo.title"/>*<input id="billTitle" type="text" style="border: none;border-bottom: 2px solid #cccccc;width: 100%;float: right"></p>
    </div>
    <div class="delivery">
        <h3>3. <spring:message code="orderinfo.confirm.payway"/></h3>
            <ul>
            <li class=" payMethod" data-value="3"><spring:message code="orderinfo.confirm.payway.credits"/></li>
            <li class=" payMethod" data-value="1"><spring:message code="orderinfo.confirm.payway.zfb"/></li>
            <li class=" payMethod" data-value="2"><spring:message code="orderinfo.confirm.payway.union"/></li>
            <li class=" payMethod" id="cod" data-value="4">货到付款(仅限中国大陆地区）</li>
        </ul>
        <p style="margin-bottom: .5rem" class="moreBtn">${cartList.size()}&nbsp;<spring:message code="orderinfo.confirm.numofgoods"/>　v</p>
        <div class="clearfix dx-shopping moreGoods" style="display:none;">
            <div class="dx-GoodsDetails">
                <c:forEach var="row" items="${cartList}">
                    <div class="goods clearfix goodsDiv">
                        <div class="goods-left">
                            <div class="pic"> <img  src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt=""></div>
                        </div>
                        <div class="goods-right" style="word-break: break-all;">
                            <div class="name" >${web:selectLanguage()=='en_US'?row.goodsEnName:row.goodsName}</div>
                            <div class="number"><spring:message code="shoppingCart.no"/>${row.goodsCode}</div>
                            <div class=""><spring:message code="shoppingCart.number"/>&nbsp;x${row.num}</div>
                            <div class="goods_color" data-value=${row.skuSpecifications}>${ web:selectLanguage()=='en_US'? row.goodsItemDomain.enName:row.goodsItemDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;<span>
                            <spring:message code="shoppingCart.size"/>: ${web:selectLanguage()=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}
                             </span>
                            </div>
                            <div class="preferential-price ${not empty row.goodsDisPrice?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/>&nbsp;
                                <span class="">
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
                                </span>&nbsp;
                                <span><fmt:formatNumber value="${row.goodsPrice}" pattern="#,###"/></span></div>
                            <c:if test="${not empty row.goodsDisPrice}">
                                <div class="preferential-price xzc-dis-price"><spring:message code="shoppingCart.disPrice"/>&nbsp;
                                <span class="">
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
                                </span>&nbsp;
                                    <span><fmt:formatNumber value="${row.goodsDisPrice}" pattern="#,###"/></span></div>

                            </c:if>


                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="order-pay">
        <a href="#" class="submitBtn"><spring:message code="orderinfo.confirm.dopay"/></a>
        <span><spring:message code="orderinfo.confirm.endinfo"/></span>
    </div>

    <style>
        .way-img img,.safe-img img{height:1.6rem;}
        .way-img a+a{margin-left: 5px;}

    </style>
    <div class="pay-way">
        <p><spring:message code="payment.failed.paymentWay"/></p>
        <div class="way-img">
            <a href="#"><img src="${ctx}/static/images/alipay.png" alt=""></a>
            <a href="#"><img src="${ctx}/static/images/online.jpg" alt=""></a>
            <a href="#"><img src="${ctx}/static/images/visa.png" alt=""></a>
            <a href="#" style="margin-left: 0px"><img src="${ctx}/static/images/mastercard.png" alt=""></a>
            <a href="#"><img src="${ctx}/static/images/jcb.jpg"  alt=""></a>
        </div>
    </div>
    <div class="order-safe">
        <p><spring:message code="orderinfo.confirm.safe"/></p>
        <div class="safe-img">
            <a style="margin-right: 1.5rem;" href="#"><img src="${ctx}/static/images/way_10.jpg" alt=""></a>
            <a href="#"><img src="${ctx}/static/images/way_07.jpg" alt=""></a>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>

    var noNeedBill = function () {
        $("#showBill").hide();
        $.post("/checkout/isNeedBill",{"isNeed":0,"info":""},function (data) {
            if(data.code==200){
            }
        });
    };

    $(function(){
        setPrice();
        noNeedBill();

        $(".moreBtn").click(function () {
            $(".moreGoods").slideToggle();
        });

        var isCheckPayWay = false;
        var isCheckSendWay = ${order.storeDomain==null&&order.customerAddressDomain==null}?false:true;
        $("#need").click(function () {
            $("#showBill").show();
        });
        $("#billTitle").blur(function () {
            var $now = $(this);
            $.post("/checkout/isNeedBill",{"isNeed":1,"info":$now.val()},function (data) {
                if(data.code==200){
                }
                console.log(data);
            });
        });
        $("#noNeed").click(function () {
            noNeedBill();
        });
        $(".payMethod").click(function () {

            if($(this).attr("data-value")==4){
                //验证是否已选择收货地址
                if(${not empty order.customerAddressDomain.address}){
                    var index = layer.load(2, {
                        shade: [0.4,'#fff'] //0.1透明度的白色背景
                    });
                    $.post("/checkout/submitOrder",{"payWay":"COD"},function (data) {
                        console.log(data);
                        //loading层
                        if(data.code==200){
                            layer.msg(data.message);
                            var orderId = data.data.id;
                            location.href = '/order/details?orderId='+orderId;
                        }
                    });
                }else{
                    layer.msg('请先选择收货地址');
                    return false;
                }
            }

            $(this).addClass("active").siblings("li").removeClass("active");
            var  id = $(this).attr("data-value");
            $.post("/checkout/setPaymentMethod",{"paymentId":id},function (data) {
                if(data.code==200){
                    isCheckPayWay = true;
                }
                console.log(data);
            });
        });

        $(".submitBtn").click(function () {
            console.log(isCheckPayWay+""+isCheckSendWay);
            //校验 运送方式 支付方式 发票信息
            if(!isCheckPayWay){
                layer.msg('<spring:message code="orderinfo.confirm.tip1"/>');
                return false;
            }else if(!isCheckSendWay){
                    layer.msg('<spring:message code="orderinfo.confirm.tip2"/>');
                return false;
            }
            $.post("/checkout/submitOrder",function (data) {
                console.log(data);
                if(data.code==200){
                    layer.msg(data.message);
                    var orderNo  = data.data.orderNo;
                    var payMethod = data.data.paymentMethod;
                    console.log(orderNo+" "+payMethod)
                    if(payMethod=='1'){
                        location.href="/payment/buildPayment?paymentMethod="+payMethod+"&orderNo="+orderNo;
                    }else if(payMethod=='2'){
                        location.href="/payment/initUnionPay?orderNo="+orderNo;
                    }else if(payMethod=='3'){
                        document.write(returnCitySN["cip"]+','+returnCitySN["cname"]);
                        var ip = returnCitySN["cip"];
                        location.href="/payment/buildIpayLinks?orderNo="+orderNo+"&ipAddress="+ip;
                    }

                }
            });
        });

    });
</script>
