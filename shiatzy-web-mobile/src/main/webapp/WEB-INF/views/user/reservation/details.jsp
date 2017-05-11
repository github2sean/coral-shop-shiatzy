<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-orderDetails clearfix">
    <div class="dx-title">精品店预约订单详情<a href="/reservation/list">返回上页</a></div>
    <div class="content">
        <div class="dx-reservation">预约单详情</div>
        <div class="orderNumber">预约单编号 ${reservationDomain.reservationNo}</div>
        <div class="dx-details">
            <div class="date">订单日期 : <span><fmt:formatDate value="${reservationDomain.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></div>
            <div class="retail">预约门店 : <span>${reservationDomain.storeDomain.name}</span> <a href="#">地图</a></div>
            <div class="site">地址 : <span>${reservationDomain.storeDomain.address}</span></div>
            <div class="telephone">电话 : ${reservationDomain.tel}<span></span></div>
            <div class="time">营业时间 : ${reservationDomain.storeDomain.time}<span></span></div>
            <div class="status">订单状态 : <span>
            <c:choose>
                <c:when test="${reservationDomain.status==0}">处理中</c:when>
            </c:choose>
            </span></div>
            <div class="retentionTime">订单保留至: <span><fmt:formatDate value="${reservationDomain.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></div>
            <div class="remake">备注栏位 : <span>${reservationDomain.note}</span></div>
        </div>
        <div class="dx-GoodsDetails">
            <div class="title">商品详情</div>
            <c:forEach var="row" items="${reservationDomain.reservationItemDomainList}">
            <div class="goods clearfix">
                <div class="goods-left">
                    <div class="pic"><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt=""></div>
                    <%--<div class="status">状态 : <span>预约成功</span></div>--%>
                </div>
                <div class="goods-right">
                    <div class="name">${row.goodsName}</div>
                    <div class="number">${row.goodsItemDomain.goodsNo}</div>
                    <div class="color">${row.goodsItemDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;<span>${JSONObject.fromObject(row.specifications).getString("size")}号</span></div>
                    <div class="quantity" data-value="${row.num}">数量:<span>${row.num}</span></div>
                    <div class="price" data-value="${row.goodsItemDomain.price}">单价&nbsp; &yen; <span>${row.goodsItemDomain.price}</span></div>
                </div>
            </div>
            </c:forEach>

        </div>
        <div class="dx-total" style="text-align: right;font-size: 18px;">预计订单总额 : &yen; <span id="js_total"></span></div>
        <div class="dx-explain">您的订单将于1-3个工作日内尽快配送至指定门店，请耐心等待。精品店预约订单遵循门店销售规则，不享有在线购物的“7天轻松退货”政策。</div>
        <div class="dx-instructions"><a href="#">在线客户服务</a></div>
        <!--<div class="dx-privacy"><a href="#">隐私权政策</a></div>-->
    </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    function clsTotal() {
        var total = 0;
        $(".goods").find(".goods-right").each(function () {
            var num =  ($(this).find(".quantity").attr("data-value"))*1;
            var price  = ($(this).find(".price").attr("data-value"))*1;
            total +=num * price;
            $("#js_total").html(" &nbsp;"+total.toFixed(2));
        })
    };
    $(function () {
        clsTotal();

    });

</script>