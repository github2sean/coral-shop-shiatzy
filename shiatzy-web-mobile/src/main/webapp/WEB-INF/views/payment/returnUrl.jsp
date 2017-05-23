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

        <p style="margin-top: 20px">订单编号：<span>${order.orderNo}</span> <br/></p>

        <div style="width: 100%;text-align: center">
            <img src="/static/images/oxp-check.png" style="width: 60px;height: 60px;margin: 0 auto">
        </div>
        <h3 style="color: #4cae4c;font-size: 30px;text-align: center;margin-top: 10px">${message}</h3>
        <h6 style="color: #4cae4c;font-size: 25px;text-align: center;margin-top: 15px">￥ ${order.orderTotal}</h6>
        <div style="width: 100%;text-align: center;margin-top: 45px;">
            <a style="width: 100%;background-color: black;color: whitesmoke;" href="/order/details?orderId=${order.id}" class="btn btn-default">查看订单详情</a>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    $(function () {
        var order = '${order}';
        if(order==''){
            $("h4").hide().siblings("p").hide();
        }
    });

</script>