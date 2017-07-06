<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    .reservation-details .status{font-size: 1.2rem;}
</style>
<div class="dx-orderDetails clearfix reservation-details">
    <div class="dx-title"><spring:message code="reservation.detail.title"/><a href="/reservation/list"><spring:message code="goBack"/></a></div>
    <div class="verify-message">
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


        <!--商品详情-->
        <div class="item-group" style="margin-top: 2rem">
            <h4 class="title j_dropdown active"><spring:message code="goods.detail.details"/> <span class="arrow">></span></h4>
            <div class=" goods-list clearfix item">

                <c:forEach var="item" items="${reservationDomain.reservationItemDomainList}">
                    <div class="goods-item clearfix ">
                        <div class="thumb">
                            <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
                        </div>
                        <div class="goods-info">
                            <div class="name">${item.goodsName}&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            <p class="code"><spring:message code="shoppingCart.no"/> ${item.goodsItemDomain.goodsNo}</p>
                            <p class="color">${web:selectLanguage()=='en_US'?item.goodsItemDomain.enName:item.goodsItemDomain.name}</p>
                            <p><spring:message code="shoppingCart.size"/>： &nbsp;${web:selectLanguage()=='en_US'?item.sizeDomain.enName:item.sizeDomain.name}</p>
                            <p><spring:message code="shoppingCart.number"/>：${item.num}&nbsp;&nbsp;&nbsp;&nbsp;</p>
                            <p class="${not empty item.goodsDisPrice?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/>：
                                &nbsp;<font class="coinSymbol">
                                    <c:choose>
                                        <c:when test="${reservationDomain.currentCode=='CNY'}">
                                            &nbsp;<spring:message code="coin.ZH"/>
                                        </c:when>
                                        <c:when test="${reservationDomain.currentCode=='USD'}">
                                            &nbsp;<spring:message code="coin.USA"/>
                                        </c:when>
                                        <c:when test="${reservationDomain.currentCode=='EUR'}">
                                            &nbsp;<spring:message code="coin.EU"/>
                                        </c:when>
                                    </c:choose>
                                </font>&nbsp; <fmt:formatNumber value="${item.goodsPrice}" pattern="#,###" /></p>
                            <c:if test="${not empty item.goodsDisPrice}">
                                <p class="xzc-dis-price"><spring:message code="shoppingCart.disPrice"/>：
                                    &nbsp;<font class="coinSymbol">
                                        <c:choose>
                                            <c:when test="${reservationDomain.currentCode=='CNY'}">
                                                &nbsp;<spring:message code="coin.ZH"/>
                                            </c:when>
                                            <c:when test="${reservationDomain.currentCode=='USD'}">
                                                &nbsp;<spring:message code="coin.USA"/>
                                            </c:when>
                                            <c:when test="${reservationDomain.currentCode=='EUR'}">
                                                &nbsp;<spring:message code="coin.EU"/>
                                            </c:when>
                                        </c:choose>
                                    </font>&nbsp; <fmt:formatNumber value="${item.goodsDisPrice}" pattern="#,###" /></p>

                            </c:if>

                        </div>
                    </div>
                    <p class="status"><spring:message code="return.detail.status"/> : <c:choose>
                        <c:when test="${item.status==0}"><span><spring:message code="reservation.list.submit"/></span></c:when>
                        <c:when test="${item.status==1}"><span><spring:message code="reservation.detail.success"/></span></c:when>
                    </c:choose></p>
                </c:forEach>

            </div>
        </div>

        <div class="dx-total" style="text-align: center;font-size: 1.4rem;padding-top: 0.5rem;">预计订单总额: &nbsp;<font class="coinSymbol">
            <c:choose>
                <c:when test="${reservationDomain.currentCode=='CNY'}">
                    &nbsp;<spring:message code="coin.ZH"/>
                </c:when>
                <c:when test="${reservationDomain.currentCode=='USD'}">
                    &nbsp;<spring:message code="coin.USA"/>
                </c:when>
                <c:when test="${reservationDomain.currentCode=='EUR'}">
                    &nbsp;<spring:message code="coin.EU"/>
                </c:when>
            </c:choose>
        </font>&nbsp; <span id="js_total"><fmt:formatNumber value="${reservationDomain.getTotal()}" pattern="#,###" /></span></div>
        <div class="dx-explain" style="color: #999;"><spring:message code="reservation.detail.endinfo"/></div>

        <div class="privacy">
            <a href="#">
                <span style="float:left;margin-left: -10px">&gt; </span>
                <span style="float: left;" class="privacyNotice"><spring:message code="customerServiceLine"/></span>
            </a>
        </div>
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
        $(".j_dropdown").on("click", function () {
            $(this).toggleClass("active");
            $(this).next().slideToggle();
        });

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