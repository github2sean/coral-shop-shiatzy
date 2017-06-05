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
       <c:forEach var="row" items="${orderList}" >
        <a href="/order/details?orderId=${row.id}" class="dx-reservaList clearfix">
            <div class="time"><fmt:formatDate value="${row.orderTime}" pattern="yyyy-MM-dd " type="date" dateStyle="long" /></div>
            <div class="orderNumber" style="padding-left: 5%">${row.orderNo}</div>
            <div class="status" style="padding-right: 1rem" data-value="${row.status}"></div>
        </a>
        </c:forEach>
        <c:forEach var="row" items="${orderList}">
            <a href="/order/details?orderId=${row.id}" class="dx-reservaList clearfix moreData hide">
                <div class="time"><fmt:formatDate value="${row.orderTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long" /></div>
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
        <c:if test="${(returnList.size()>4 && orderList.size()>4)}" >
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

        //浏览器屏幕的高度
        var h=window.innerHeight
                || document.documentElement.clientHeight
                || document.body.clientHeight;
        console.log("h:"+h);
        //滚动条垂直滚动的距离；
        window.onscroll = function(){
            function getScrollTop(){
                var scrollTop=0;
                if(document.documentElement&&document.documentElement.scrollTop)
                {
                    scrollTop=document.documentElement.scrollTop;
                }
                else if(document.body)
                {
                    scrollTop=document.body.scrollTop;
                }
                return scrollTop;
            };
            //滚动条的高度；
            var scroll=getScrollTop();
            console.log("scroll:"+scroll)
            //屏幕的实际高度；
            var h=window.innerHeight
                    || document.documentElement.clientHeight
                    || document.body.clientHeight;
            //加载按钮到屏幕的高度；
            var b=$(".addmore").offset().top;
            //屏幕高度与滚动条的和
            var x=scroll+h;
            //如果屏幕高度+滚动条高度的和大于 加载按钮距浏览器顶部的值，则执行加载内容函数；
            if(x>b){
                console.log(b);
            };
        };

    });
</script>