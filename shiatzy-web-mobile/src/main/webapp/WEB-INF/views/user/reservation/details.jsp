<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<div class="dx-orderDetails clearfix">
    <div class="dx-title"><spring:message code="reservation.detail.title"/><a href="/reservation/list"><spring:message code="goBack"/></a></div>
    <div class="content">
        <div class="dx-reservation"><spring:message code="reservation.detail"/></div>
        <div class="orderNumber"><spring:message code="reservation.detail.no"/>：${reservationDomain.reservationNo}</div>
        <div class="dx-details">
            <div class="date"><spring:message code="order.details.time"/> : <span><fmt:formatDate value="${reservationDomain.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></div>
            <div class="retail"><spring:message code="reservation.detail.store"/> : <span>${web:selectLanguage()=='en_US'?reservationDomain.storeDomain.enTitle:reservationDomain.storeDomain.name}</span> <a href="javascript:void(0)" class="alertMap"><spring:message code="reservation.detail.map"/></a></div>
            <div class="site"><spring:message code="account.personal.address"/>: <span>${reservationDomain.storeDomain.address}</span></div>
            <div class="telephone"><spring:message code="account.personal.phoneNum"/> : ${reservationDomain.tel}<span></span></div>
            <div class="time"><spring:message code="reservation.detail.openTime"/> : ${reservationDomain.storeDomain.time}<span></span></div>
            <div class="status"><spring:message code="order.details.status"/>  : <span>
            <c:choose>
                <c:when test="${reservationDomain.status==0}"><spring:message code="reservation.list.submit"/> </c:when>
            </c:choose>
            </span></div>
          <%--  <div class="retentionTime"><spring:message code="reservation.detail.saveEndTime"/>: <span><fmt:formatDate value="${reservationDomain.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></div>
            <div class="remake"><spring:message code="reservation.detail.mem"/> : <span>${reservationDomain.note}</span></div>--%>
        </div>


        <div class="dx-GoodsDetails">
            <div class="title"><spring:message code="goods.detail.details"/></div>
            <c:forEach var="row" items="${reservationDomain.reservationItemDomainList}">
            <div class="goods clearfix">
                <div class="goods-left">
                    <div class="pic"><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt=""></div>
                   <div class="status"><spring:message code="return.detail.status"/> : <c:choose>
                       <c:when test="${row.status==0}"><span><spring:message code="reservation.list.submit"/></span></c:when>
                       <c:when test="${row.status==1}"><span><spring:message code="reservation.detail.success"/></span></c:when>
                   </c:choose></div>
                </div>
                <div class="goods-right">
                    <div class="name">${row.goodsName}</div>
                    <div class="number">${row.goodsItemDomain.goodsNo}</div>
                    <div class="color">${web:selectLanguage()=='en_US'?row.goodsItemDomain.enName:row.goodsItemDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;<span>${web:selectLanguage()=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}</span></div>
                    <div class="quantity" data-value="${row.num}"><spring:message code="shoppingCart.number"/>:<span>${row.num}</span></div>
                    <div class="price" data-value="${row.goodsItemDomain.price}"><spring:message code="shoppingCart.unitPrice"/>&nbsp; &yen; <span><fmt:formatNumber value="${row.goodsItemDomain.price}" pattern="#,###"/></span></div>
                </div>
            </div>
            </c:forEach>

        </div>
        <div class="dx-total" style="text-align: right;font-size: 1.2rem;margin-top: 15px;"><spring:message code="payment.failed.orderTotal"/> : &yen; <span id="js_total"></span></div>
        <div class="dx-explain"><spring:message code="reservation.detail.endinfo"/></div>
        <div class="dx-instructions"><a href="#do-online-service"><spring:message code="customerServiceLine"/></a></div>
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
            $("#js_total").html(" &nbsp;"+fmoney(total.toFixed(0),0));
        })
    };
    $(function () {
        clsTotal();


        $(".alertMap").click(function () {

            var html = "<div id='allmap' style='width: 100%;height: 500px'></div>";
            layer.open({
                type: 1,
                title:'<spring:message code="reservation.detail.baidumap"/>',
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