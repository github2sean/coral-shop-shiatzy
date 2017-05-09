<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left">门市预约</p>
    <a style="float: right;" href="/u/account/index">X</a>
</div>

<c:if test="${empty preOderItemList}">
    <div class="content dx-wish">
        <div id="toggleDiv2">
            <div class="dx-collect">精品店（0）</div>
        </div>
    </div>
</c:if>


<c:forEach var="row" items="${preOderItemList}">
<div class="order-main clearfix">
    <div class="order-main-left">
        <p class="product-num">产品编号 ${row.leftItem.goodsCode}</p>
        <img src="images/order_01.png" alt="">
        <p class="product-name">${row.leftItem.goodsName}</p>
        <div class="color-size">
            <p>黑色</p>
            <p>M号</p>
        </div>
        <p class="price">单价　¥ ${row.leftItem.goodsPrice}</p>
        <ul class="do-list-icon">
            <li><a href="javascript:;" class="j_appointment"><svg><use xlink:href="#ap-small"></use></svg></a></li>
            <li><a href="javascript:;" class="j_collect"><svg><use xlink:href="#heart"></use></svg></a></li>
            <li><a href=""><svg><use xlink:href="#close"></use></svg></a></li>
        </ul>
    </div>
    <c:if test="${not empty row.rightItem}">
    <div class="order-main-right">
        <p class="product-num">产品编号 ${row.rightItem.goodsCode}</p>
        <img src="images/order_02.png" alt="">
        <p class="product-name">${row.rightItem.goodsName}</p>
        <div class="color-size">
            <p>黑色</p>
            <p>M号</p>
        </div>
        <p class="price">单价　¥ ${row.rightItem.goodsPrice}</p>
        <ul class="do-list-icon">
            <li><a href="javascript:;" class="j_appointment"><svg><use xlink:href="#ap-small"></use></svg></a></li>
            <li><a href="javascript:;" class="j_collect"><svg><use xlink:href="#heart"></use></svg></a></li>
            <li><a href=""><svg><use xlink:href="#close"></use></svg></a></li>
        </ul>
    </div>
    </c:if>
</div>
</c:forEach>
<div class="explain">

    <div class="choose-store">
        <a href="/reservation/initChoose">选择预约门市</a>
    </div>
    <ul>
        <li><a href="#">购物说明<span>></span></a></li>
        <li><a href="#">使用条款<span>></span></a></li>
        <li><a href="#">配送规则<span>></span></a></li>
    </ul>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {
        //console.log('${goodsList}');
    });
</script>