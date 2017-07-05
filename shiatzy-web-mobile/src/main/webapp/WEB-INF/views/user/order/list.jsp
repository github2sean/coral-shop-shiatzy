<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="com.dookay.coral.shop.order.enums.OrderStatusEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<div class="order">
    <p style="float: left"><spring:message code="order.detail"/></p>
    <a style="float: right;" href="/u/account/index" >< <spring:message code="goBack"/></a>
</div>
<div class="dx-orderList clearfix">
    <div class="content" >
        <c:if test="${empty orderList.list}">
            <div class="content dx-wish dx-shopping">
                <div id="toggleDiv2">
                    <a href="/home/index"> <div class="message"><p><spring:message code="order.order"/>(0)</p></div></a>
                </div>
            </div>
        </c:if>
       <c:forEach var="row" items="${orderList.list}" varStatus="num">
        <a href="/u/order/details?orderId=${row.id}" class="dx-reservaList clearfix clearfix${num.count-1} <c:if test='${num.count>9}'>hide</c:if>">
            <div class="time"><fmt:formatDate value="${row.orderTime}" pattern="yyyy-MM-dd " type="date" dateStyle="long" /></div>
            <div class="orderNumber" style="padding-left: 5%">${row.orderNo}</div>
            <div class="status" style="padding-right: 1rem" >
            <c:choose>
                <c:when test="${row.status==1}">
                    <spring:message code="order.status.waitPay"/>
                </c:when>
                <c:when test="${row.status==2}">
                    <spring:message code="order.status.paid"/>
                </c:when>
                <c:when test="${row.status==3}">
                    <spring:message code="order.status.send"/>
                </c:when>
                <c:when test="${row.status==4}">
                    <spring:message code="order.status.reach"/>
                </c:when>
                <c:when test="${row.status==-1}">
                    <spring:message code="order.status.cancel"/>
                </c:when>
            </c:choose>
            </div>
        </a>
        </c:forEach>
        <c:if test="${orderList.list.size()>9}" >
            <div class="font-12 text-center do-load-list">
                <span class="link-down-before moreList" id="auto_load"><spring:message code="order.list.loadmore"/></span>
                <span class="overGoods" style="display: none">-<spring:message code="order.list.nomore"/>-</span>
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

        $(".top-right-nav").find("li:eq(2)").addClass("active");
        $(".moreList").click(function () {

        });


        var num = 9;
        var base = $(window).height()/2;
        $(window).on("scroll",function(){
            var top1 = $(this).scrollTop();
            var top2 = $("#auto_load").offset().top;
            if(top1+base>top2){
                num+=9;
                if(num>=$(".dx-reservaList").length) {
                    $("#auto_load").hide();
                    $(".overGoods").show();
                } else{
                    $(".dx-reservaList:lt("+(num+1)+")").removeClass("hide");
                }
            }
        });

       /* var count = 1;
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
        };*/

    });
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
</script>