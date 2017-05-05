<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>


<div class="order">
    <p style="float: left">预约完成</p>
    <a style="float: right;" href="#">< 我的账户</a>
</div>
<div class="verify-message">
    <div class="verify-message-top">
        <h2>退货单号：${returnRequestDomain.orderNo}</h2>
        <div class="return-time">
            <p>退货申请${returnRequestDomain.createTime}，已于${returnRequestDomain.createTime}提交，总计¥ 11,504的退款申请。</p>
        </div>
    </div>
    <div class="verify-message-middle">
        <h2>退货详情</h2>

        <c:forEach var="row" items="${returnOrderItemList}">
        <div style="padding-left: 3rem;" class="verify-main">
            <img src="images/verify_01.png" alt="">
            <div style="margin-left: 6rem;" class="img-message">
                <h3>name暂无</h3>
                <h6>code暂无</h6>
                <div style="display: inline-block;" class="size">
                    <p style="float:left;margin-right: 3.0918rem;">黑色</p>
                    <p>M号</p>
                </div>
                <p>数量：${row.num}</p>
                <p>单价　¥ 暂无</p>
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
    <div style="margin-bottom: 7rem;" class="verify-message-bottom">
        <h2 style="text-align: right;padding-right: 1.5rem;">退回总额：¥ 12,000</h2>
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