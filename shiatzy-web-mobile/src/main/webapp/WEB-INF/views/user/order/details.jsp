<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的帐户 / 订单详情</p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< 回上页</a>
</div>
<div class="unfinished">
    <div class="order-num clearfix">
        <h3>商品詳情</h3>
        <p>订单编号<span>${orderDomain.orderNo}</span></p>
    </div>
    <div class="order-date">
        <p>订单日期：<fmt:formatDate value="${orderDomain.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></p>
        <p>订单状态：<c:choose>
            <c:when test="${orderDomain.status==1}">待支付&nbsp;
                <a href="#" id="payBtn" style="background-color: #4CAF50; border: none;color: white;padding: 5px 8px;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;border-radius: 5px">支付</a>
            </c:when>
            <c:when test="${orderDomain.status==2}">已支付</c:when>
            <c:when test="${orderDomain.status==3}">已发货</c:when>
            <c:when test="${orderDomain.status==4}">已收货</c:when>
            <c:when test="${orderDomain.status==-1}">已取消</c:when>
        </c:choose></p>
        <p><a href="#">查看递送状态<span style="float:right;">></span></a></p>
    </div>
    <div class="verify-message-middle">
        <h2>商品详情</h2>
        <c:forEach var="item" items="${orderItemList}">
        <div class="verify-main">
            <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
            <div class="img-message">
                <h3>${item.goodsName}&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${item.status==1}"><font style="color: red">(退货中)</font></c:if></h3>
                <h6>${item.goodsCode}</h6>
                <div style="display: inline-block;" class="size">
                    <p style="float:left;margin-right: 3.0918rem;">${item.goodsItemDomain.name}</p>
                    <p>${JSONObject.fromObject(item.skuSpecifications).getString("size")}号</p>
                </div>
                <p>数量：${item.num}&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${item.status==1}"><span style="color: red">已退：${item.returnNum}</span></c:if></p>
                <p>单价　¥ ${item.goodsPrice}</p>
            </div>
        </div>
        </c:forEach>
    </div>
    <div class="order-details">
        <h4>帐单详情<span>v</span></h4>
        <ul>
            <li>优惠前<span>¥ ${orderDomain.goodsTotal}</span></li>
            <li>优惠应用<span data-value="${orderDomain.couponDiscount==null?0:orderDomain.couponDiscount}">¥ -${orderDomain.couponDiscount==null?0:orderDomain.couponDiscount}</span></li>
            <li>Art Club会员优惠<span data-value="${orderDomain.memberDiscount==null?0:orderDomain.memberDiscount}">¥ -${orderDomain.memberDiscount==null?0:orderDomain.memberDiscount}</span></li>
            <li>运费<span>¥ ${orderDomain.shipFee}</span></li>
        </ul>
        <p>总计<span>¥ ${orderDomain.orderTotal-orderDomain.couponDiscount-orderDomain.memberDiscount+orderDomain.shipFee}</span></p>
    </div>
    <div class="information">
        <h4>配送信息<span>v</span></h4>
        <p>${orderDomain.shipAddress}</p>
        <p>${orderDomain.shipTitle}</p>
        <c:if test="${ orderDomain.status!=null && orderDomain.status!=1 && orderDomain.status!=-1 && orderDomain.canReturnNum>0}">
        <div class="clearfix">
            <span class="mr-2">
                <label class="radiobox"><input type="checkbox">申请退货
                <a href="/returnOrder/initReturnOrder?orderId=${orderDomain.id}"><i class="i-radiobox returnBtn"></i></a>
                </label>
            </span>
            <p><a href="#">退货说明<span>></span></a></p>
        </div>
        </c:if>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">> </span>
            <span style="float: left;">收到商品后7天内享有轻松退货政策</span>
        </a>
    </div>
    <div class="privacy">
        <a href="#">
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

        $("#payBtn").click(function () {


        });

    });

</script>