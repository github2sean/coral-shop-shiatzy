<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-orderList clearfix">
    <div class="dx-title">订单详情<a style="float: right;" href="/u/account/index"><spring:message code="goBack"/></a></div>
    <div class="content">
        <div class="dx-reservation">订单列表</div>

        <c:if test="${empty orderList && empty returnList}">
            <div class="content ">
                <div id="toggleDiv2">
                    <div class="dx-collect">订单（0）</div>
                </div>
            </div>
        </c:if>
       <c:forEach var="row" items="${orderList}" varStatus="num">
        <a href="/order/details?orderId=${row.id}" class="dx-reservaList clearfix clearfix${num.count-1} <c:if test='${num.count>9}'>hide</c:if>">
            <div class="time"><fmt:formatDate value="${row.orderTime}" pattern="yyyy-MM-dd " type="date" dateStyle="long" /></div>
            <div class="orderNumber" style="padding-left: 5%">${row.orderNo}</div>
            <div class="status" style="padding-right: 1rem" data-value="${row.status}"></div>
        </a>
        </c:forEach>
        <%--<c:forEach var="row" items="${returnList}" begin="0" end="3">
            <a href="/returnOrder/details?orderId=${row.id}" class="dx-reservaList clearfix">
                <div class="time"><fmt:formatDate value="${row.orderTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long" /></div>
                <div class="orderNumber" style="padding-left: 5%">${row.orderNo}</div>
                <div class="status" style="padding-right: 1rem" data-value="">退货</div>
            </a>
        </c:forEach>--%>
        <c:if test="${(returnList.size()>4 && orderList.size()>9)}" >
            <div class="font-12 text-center do-load-list">
                <span class="link-down-before moreList">向下自动载入</span>
                <span class="overGoods" style="display: none">-已到底部-</span>
            </div>
        </c:if>
    </div>
    <div class="check clearfix"> <span>查看所有订单</span><a href="/reservation/list"> ></a></div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    var noMore = true;
    $(function () {
        $(".status").each(function () {
            var status = $(this).attr("data-value");
            if(status == '1'){
                $(this).text("未付款");
            }else if(status == '2'){
                $(this).text("已付款未配送");
            }else if(status == '3'){
                $(this).text("已配送");
            }else if(status == '4'){
                $(this).text("已收货");
            }else if(status == '-1'){
                $(this).text("已取消");
            }
        });

        $(".moreList").click(function () {

        });
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
            var size = '${orderList.size()}';
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