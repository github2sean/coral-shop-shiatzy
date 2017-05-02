<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的帐户 / 订单详情</p>
    <a style="float: right;" href="我的账户.我的订单.订单详情.列表.html">< 回上页</a>
</div>
<div class="unfinished">
    <div class="order-num clearfix">
        <h3>商品詳情</h3>
        <p>订单编号<span>${orderDomain.orderNo}</span></p>
    </div>
    <div class="order-date">
        <p>订单日期：${orderDomain.orderTime}</p>
        <p>订单状态：处理中</p>
        <p><a href="#">查看递送状态<span style="float:right;">></span></a></p>
    </div>
    <div class="verify-message-middle">
        <h2>商品详情</h2>
        <c:forEach var="item" items="${orderItemList}">
        <div class="verify-main">
            <img src="images/verify_01.png" alt="">
            <div class="img-message">
                <h3>${item.goodsName}</h3>
                <h6>${item.goodsCode}</h6>
                <div style="display: inline-block;" class="size">
                    <p style="float:left;margin-right: 3.0918rem;">黑色</p>
                    <p>M号</p>
                </div>
                <p>数量：${item.num}</p>
                <p>单价　¥ ${item.goodsPrice}</p>
            </div>
        </div>
        </c:forEach>
    </div>
    <div class="order-details">
        <h4>帐单详情<span>v</span></h4>
        <ul>
            <li>玉镯提包系列黑色刺绣托特包<span>¥ 11,504</span></li>
            <li>优惠应用<span>¥ -50</span></li>
            <li>Art Club会员优惠<span>¥ -10</span></li>
            <li>关税和税收<span>¥ 100</span></li>
            <li>运费<span>¥ 50</span></li>
        </ul>
        <p>总计<span>¥ 12,000</span></p>
    </div>
    <div class="information">
        <h4>配送信息<span>v</span></h4>
        <p>新北市五股区五权六路40号</p>
        <p>資訊部</p>
        <div class="clearfix">
            <span class="mr-2"><label class="radiobox"><input type="checkbox">申请退货<i class="i-radiobox"></i></label></span>
            <p><a href="我的账户.退货申请.我要退货.退货理由.html">退货说明<span>></span></a></p>
        </div>
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
