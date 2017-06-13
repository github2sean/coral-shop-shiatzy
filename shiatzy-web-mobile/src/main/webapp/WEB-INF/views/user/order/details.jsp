<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<div class="order">
    <p style="float: left">订单详情</p>
    <a style="float: right;" href="/order/list" >< <spring:message code="goBack"/></a>
</div>
<div class="unfinished">
    <div class="order-num clearfix">
        <h3>订单詳情</h3>
        <p>订单编号<span>${orderDomain.orderNo}</span></p>
    </div>
    <div class="order-date">
        <p>订单日期：<fmt:formatDate value="${orderDomain.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></p>
        <p>订单状态：
            <c:choose>
                <c:when test="${orderDomain.status==1}">待支付&nbsp;
                    <a href="" id="cancelBtn" class="btn btn-primary hide" style="background-color: #2b2b2b;color: white">取消</a>
                </c:when>
                <c:when test="${orderDomain.status==2}">已支付</c:when>
                <c:when test="${orderDomain.status==3}">已发货</c:when>
                <c:when test="${orderDomain.status==4}">已收货</c:when>
                <c:when test="${orderDomain.status==-1}">已取消</c:when>
            </c:choose>
        </p>
        <c:if test="${orderDomain.status==1}">
            <p><a id="rePay" href="javascript:void(0);">重新支付<span style="float:right;" class="rotateicon">></span></a></p>
            <p style="display: none" class="rePayWay">
                选择支付方式: <br>
                <label><input data-value="1" style="vertical-align:middle; margin-top:-1px; margin-bottom:1px;"  type="radio" name="payMethod" checked="checked"/><i class="i-radiobox iconfont icon-duigou"></i>支付宝</label>
                <label><input data-value="2" style="vertical-align:middle; margin-top:-1px; margin-bottom:1px;"  type="radio" name="payMethod"/><i class="i-radiobox iconfont icon-duigou"></i>银联</label>
                <label><input data-value="3" style="vertical-align:middle; margin-top:-1px; margin-bottom:1px;"  type="radio" name="payMethod"/><i class="i-radiobox iconfont icon-duigou"></i>ipaylinks</label>
                <div class="text-right">
                    <a href="/payment/buildPayment?paymentMethod=1&orderNo=${orderDomain.orderNo}" id="payBtn" class="btn btn-submit" style="margin-left:20px;background-color: #2b2b2b;color: white">支付</a>
                </div>
            </p>     
        </c:if>
        <c:if test="${orderDomain.status==3 || orderDomain.status==4}">
            <p><a id="queryExpress" href="javascript:void(0);">查看配送状态<span style="float:right;" class="rotateicon">></span></a></p>
            <div class="logisticInfo" style="display: none;width: 100%;overflow: hidden;background-color: #F1F1F1">
                <div></div>
            </div>
        </c:if>
    </div>
    <div class="verify-message-middle">
        <h2>商品详情<span style="float: right">v</span></h2>
        <c:forEach var="item" items="${orderItemList}">
            <div class="verify-main">
                <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
                <div class="img-message">
                    <h3>${item.goodsName}&nbsp;&nbsp;&nbsp;&nbsp;</h3>
                    <h6>${item.goodsCode}</h6>
                    <div style="display: inline-block;" class="size">
                        <p style="float:left;margin-right: 3.0918rem;">${item.goodsItemDomain.name}</p>
                        <p>${sessionScope.language=='en_US'?item.sizeDomain.enName:item.sizeDomain.name}号</p>
                    </div>
                    <p>数量：${item.num}&nbsp;&nbsp;&nbsp;&nbsp;</p>
                    <p>单价　
                        &nbsp;<font class="coinSymbol">
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
                        </font>&nbsp;
                     ${item.goodsPrice}</p>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="order-details">
        <h4>帐单详情<span>v</span></h4>
        <ul>
            <li>优惠前<span>
             &nbsp;<font class="coinSymbol">
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
                        </font>&nbsp;
             ${orderDomain.goodsTotal}</span></li>
            <li>优惠应用<span data-value="${orderDomain.couponDiscount==null?0:orderDomain.couponDiscount}">
                &nbsp;<font class="coinSymbol">
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
                        </font>&nbsp;
                -${orderDomain.couponDiscount==null?0:orderDomain.couponDiscount}</span></li>
            <li>Art Club会员优惠<span data-value="${orderDomain.memberDiscount==null?0:orderDomain.memberDiscount}">
                &nbsp;<font class="coinSymbol">
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
                        </font>&nbsp;
                -${orderDomain.memberDiscount==null?0:orderDomain.memberDiscount}</span></li>
            <li>运费<span>
             &nbsp;<font class="coinSymbol">
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
                        </font>&nbsp;
             ${orderDomain.shipFee}</span></li>
        </ul>
        <p>总计<span>
         &nbsp;<font class="coinSymbol">
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
                        </font>&nbsp;
         ${orderDomain.orderTotal-orderDomain.couponDiscount-orderDomain.memberDiscount+orderDomain.shipFee}</span></p>
    </div>
    <div class="information">
        <h4>配送信息<span>v</span></h4>
        <p>${orderDomain.shipAddress}</p>
        <p>${orderDomain.shipTitle}</p>
        <c:if test="${ orderDomain.status!=null && orderDomain.status!=1 && orderDomain.status!=-1 && orderDomain.canReturnNum>0}">
            <div class="information" style="margin-top: 2rem">
                <p ><a href="/returnOrder/initReturnOrder?orderId=${orderDomain.id}">申请退货<span style="float: right">></span></a></p>
            </div>
        </c:if>

        <c:if test="${not empty orderDomain.returnRequestDomain}">

            <a href="/returnOrder/details?orderId=${orderDomain.returnRequestDomain.id}"><p style="margin-top: 2rem;height: 3rem;line-height: 3rem;border-top:2px solid #cccccc;border-bottom:2px solid #cccccc">查看退货详情 <span style="float: right">></span></p></a>

        </c:if>

    </div>

    <div class="privacy">
        <a href="#">
            <span style="float:left;">> </span>
            <span style="float: left;">收到商品后7天内享有轻松退货政策</span>
        </a>
    </div>
    <div class="privacy">
        <a href="#" class="privacyNotice">
            <span style="float:left;">> </span>
            <span style="float: left;">隐私权政策</span>
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
                    layer.msg("地址信息不全");
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