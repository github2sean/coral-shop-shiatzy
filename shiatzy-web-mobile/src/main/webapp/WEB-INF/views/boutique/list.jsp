<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-orderList clearfix">
    <div class="dx-title">精品店预约详情 <a href="我的账户.首页.html">返回上页</a></div>
    <div class="content">
        <div class="dx-reservation">预约订单</div>
        <a href="我的账户.门市预约.详情.html" class="dx-reservaList clearfix">
            <div class="time">2017-01-15</div>
            <div class="orderNumber">12345AABBCC</div>
            <div class="status">已提交</div>
        </a>
        <a href="我的账户.门市预约.详情.html" class="dx-reservaList clearfix">
            <div class="time">2017-01-15</div>
            <div class="orderNumber">12345AABBCC</div>
            <div class="status">已取消</div>
        </a>
        <a href="我的账户.门市预约.详情.html" class="dx-reservaList clearfix">
            <div class="time">2017-01-15</div>
            <div class="orderNumber">12345AABBCC</div>
            <div class="status">处理中</div>
        </a>
    </div>
    <div class="check clearfix"><span>查看所有订单</span><a href="#">></a></div>
</div>
<script>

    $(function () {
        //console.log('${goodsList}');
    });
</script>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

