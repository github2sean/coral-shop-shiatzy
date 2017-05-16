<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="unfinished">
    <div class="order-num clearfix">
        <h3>${message}</h3>
        <h4>￥ ${order.orderTotal}</h4>
        <p>订单编号<span>${order.orderNo}</span></p>
    </div>
    <a href="/order/details?orderId=${order.id}" class="btn btn-success">查看订单详情</a>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

