<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="com.dookay.coral.shop.order.enums.OrderStatusEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<div class="order">
    <p style="float: left">订单详情</p>
    <a style="float: right;" href="/u/account/index" >< <spring:message code="goBack"/></a>
</div>
<div class="dx-orderList clearfix">
    <div class="content" >
        <c:if test="${empty orderList.list}">
            <div class="content dx-wish dx-shopping">
                <div id="toggleDiv2">
                    <a href="/home/index"> <div class="message"><p>订单(0)</p></div></a>
                </div>
            </div>
        </c:if>
       <c:forEach var="row" items="${orderList.list}" varStatus="num">
        <a href="/order/details?orderId=${row.id}" class="dx-reservaList clearfix clearfix${num.count-1} <c:if test='${num.count>9}'>hide</c:if>">
            <div class="time"><fmt:formatDate value="${row.orderTime}" pattern="yyyy-MM-dd " type="date" dateStyle="long" /></div>
            <div class="orderNumber" style="padding-left: 5%">${row.orderNo}</div>
            <div class="status" style="padding-right: 1rem" >${OrderStatusEnum.valueOf(row.status).description}</div>
        </a>
        </c:forEach>
        <c:if test="${orderList.list.size()>9}" >
            <div class="font-12 text-center do-load-list">
                <span class="link-down-before moreList">向下自动载入</span>
                <span class="overGoods" style="display: none">-已到底部-</span>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    var noMore = true;
    $(function () {
        var count = 1;
        var turnLen = 9;
        //浏览器屏幕的高度
        var h=window.innerHeight
                || document.documentElement.clientHeight
                || document.body.clientHeight;
        console.log("h:"+h);
        var moveHeight = ($(".dx-reservaList").height())*9;//Li 的高度
        moveHeight = parseInt(moveHeight);
        //滚动条垂直滚动的距离；
        window.onscroll = function(){
            console.log("top:"+getScrollTop()+" moveHeight:"+moveHeight);
            var scrollTop = getScrollTop();
            scrollTop = parseInt(scrollTop);
            var pageNum = Math.abs(scrollTop%moveHeight);
            if(0==pageNum){
                console.log("加载下一页了");
                loadMoreOrder();
                count++;
            }else if(getWindowHeight()+scrollTop==getScrollHeight()){
                console.log("加载下一页了");
                loadMoreOrder();
                console.log("底部");
                count++;
            }
        };

        function loadMoreOrder() {
            var size = '${orderList.list.size()}';
            var lastStr = ".clearfix"+(size-1);
            if(size<=9){
                return false;
            }
            $(".content").find("a").each(function (index) {
                for(var i=0;i<turnLen;i++){
                    var str = ".clearfix"+(count*turnLen+i);
                    console.log(str+" "+index);
                    $(str).removeClass("hide");
                }
                if(!$(lastStr).hasClass("hide")){
                    noMore = false;
                }
                if(!noMore){
                    $(".do-load-list").find(".moreList").hide().siblings(".overGoods").show();
                }
            });
        }
    });
</script>