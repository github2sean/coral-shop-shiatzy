<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>


<div class="order">
    <p style="float: left">退货详情</p>
    <a style="float: right;" href="/order/list">< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="verify-message-top">
        <h2>退货单号：${returnRequestDomain.orderNo}</h2>
        <div class="return-time" style="border-top:2px solid #cccccc">
            <p>退货申请,于<fmt:formatDate value="${returnRequestDomain.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" />
            提交，总计¥${preBackMoney-fee}的退款申请。
            </p>
        </div>
    </div>
    <div class="verify-message-middle">
        <h2>退货详情<span style="float: right">v</span></h2>
        <c:forEach var="row" items="${returnOrderItemList}">
        <div style="padding-left: 3rem;" class="verify-main">
            <img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="">
            <div style="margin-left: 6rem;" class="img-message">
                <h3>${row.goodsName}</h3>
                <h6>${row.goodsCode}</h6>
                <div style="display: inline-block;" class="size">
                    <p style="float:left;margin-right: 3.0918rem;">${row.goodsItemDomain.name}</p>
                    <p>${JSONObject.fromObject(row.skuSpecifications).getString("size")}号</p>
                </div>
                <p>数量：${row.num}</p>
                <p>单价　¥ ${row.goodsPrice}</p>
            </div>
        </div>
        <p style="padding-left: 3rem;" class="order-state">状态：

        <c:choose>
            <c:when test="${row.status==1}">
                待收货
            </c:when>
            <c:when test="${row.status==2}">
                已收货
            </c:when>
            <c:when test="${row.status==3}">
                接受退货
            </c:when>
            <c:when test="${row.status==4}">
                拒绝退货
            </c:when>
            <c:when test="${row.status==5}">
                取消退货
            </c:when>
        </c:choose>
        </p>
        </c:forEach>
    </div>


    <div style="margin-bottom: 7rem;" class="verify-message-middle">
        <p style="padding-left: 3rem;border-top: 2px solid #cccccc;line-height: 2.5rem" class="order-state">退货运费：<span class="fee" style="float: right">¥ -${fee}</span></p>
        <p style="text-align: right;border-top: 2px solid #cccccc;line-height: 2.5rem">退回总额：¥ ${preBackMoney-fee}</p>
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


    });

</script>