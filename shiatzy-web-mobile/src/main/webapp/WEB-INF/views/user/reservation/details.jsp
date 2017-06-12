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
            <div class="retail">预约门店 : <span>${reservationDomain.storeDomain.name}</span> <a href="javascript:void(0)" class="alertMap">地图</a></div>
            <div class="site">地址 : <span>${reservationDomain.storeDomain.address}</span></div>
            <div class="telephone">电话 : ${reservationDomain.tel}<span></span></div>
            <div class="time">营业时间 : ${reservationDomain.storeDomain.time}<span></span></div>
            <div class="status">订单状态 : <span>
            <c:choose>
                <c:when test="${reservationDomain.status==0}">已提交</c:when>
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
                   <div class="status">状态 : <c:choose>
                       <c:when test="${row.status==0}"><span>已提交</span></c:when>
                       <c:when test="${row.status==1}"><span>预约成功</span></c:when>
                   </c:choose></div>
                </div>
                <div class="goods-right">
                    <div class="name">${row.goodsName}</div>
                    <div class="number">${row.goodsItemDomain.goodsNo}</div>
                    <div class="color">${row.goodsItemDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;<span>${sessionScope.language=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}号</span></div>
                    <div class="quantity" data-value="${row.num}">数量:<span>${row.num}</span></div>
                    <div class="price" data-value="${row.goodsItemDomain.price}">单价&nbsp; &yen; <span>${row.goodsItemDomain.price}</span></div>
                </div>
            </div>
            </c:forEach>

        </div>
        <div class="dx-total" style="text-align: right;font-size: 1.2rem;margin-top: 15px;">订单总额 : &yen; <span id="js_total"></span></div>
        <div class="dx-explain">您的订单将于1-3个工作日内尽快配送至指定门店，请耐心等待。精品店预约订单遵循门店销售规则，不享有在线购物的“7天轻松退货”政策。</div>
        <div class="dx-instructions"><a href="#do-online-service">在线客户服务</a></div>
        <!--<div class="dx-privacy"><a href="#">隐私权政策</a></div>-->
    </div>

</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=GgHjw11mitCe2jvbdaYB31EzxOIxNCj8"></script>

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


        $(".alertMap").click(function () {

            var html = "<div id='allmap' style='width: 100%;height: 500px'></div>";
            layer.open({
                type: 1,
                title:'百度地图',
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '555px'], //宽高
                content: html
            });

            var localPoint = parseInt('${reservationDomain.storeDomain.localPoint}') ;
            console.log(localPoint)
            var city = '${reservationDomain.storeDomain.cityId}';
            var address = '${reservationDomain.storeDomain.address}';
            // 百度地图API功能
            var map = new BMap.Map("allmap");    // 创建Map实例
            var point = new BMap.Point(${reservationDomain.storeDomain.localPoint});
            var marker = new BMap.Marker(point);  // 创建标注
            map.addOverlay(marker);              // 将标注添加到地图中
            map.centerAndZoom(point, 15);  // 初始化地图,设置中心点坐标和地图级别
            map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

            var opts = {
                width : 200,     // 信息窗口宽度
                height: 100,     // 信息窗口高度
                title : "${reservationDomain.storeDomain.name}" , // 信息窗口标题
                enableMessage:true,//设置允许信息窗发送短息
                message:"亲耐滴，晚上一起吃个饭吧？戳下面的链接看下地址喔~"
            };

            var infoWindow = new BMap.InfoWindow("地址："+address, opts);  // 创建信息窗口对象
            marker.addEventListener("click", function(){
                map.openInfoWindow(infoWindow,point); //开启信息窗口
            });

        });

    });



</script>