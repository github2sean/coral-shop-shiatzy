<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-orderList clearfix">
    <div class="dx-title"><spring:message code="reservation.list.title"/> <a style="float: right;" href="/u/account/index">< <spring:message code="goBack"/></a></div>
    <div class="content">
     <%--   <div class="dx-reservation"><spring:message code="reservation.list.reservationorders"/></div>--%>
        <c:if test="${empty reservationList}">
            <div class="content dx-wish dx-shopping">
                <div id="toggleDiv3">
                    <div class="message"><p><spring:message code="reservation.list.reservationorder"/>（0）</p></div>
                </div>
            </div>
        </c:if>
        <c:forEach var="row" items="${reservationList}">
        <a href="/reservation/details?reservationId=${row.id}" class="dx-reservaList clearfix">
            <div class="time"><fmt:formatDate value="${row.createTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long" /></div>
            <div class="orderNumber">${row.reservationNo}</div>
            <div class="status">
                <c:choose>
                    <c:when test="${row.status==0}">
                        <spring:message code="reservation.list.submit"/>
                    </c:when>
                    <c:when test="${row.status==1}">
                        <spring:message code="reservation.list.finish"/>
                    </c:when>
                </c:choose>
                </div>
        </a>
        </c:forEach>
    </div>
    <c:if test="${not empty reservationList &&reservationList.size()>8}">
    <p class="text-center" id="auto_load"><span style="display:inline-block;transform:rotate(90deg);-webkit-transform:rotate(90deg)">&gt;&nbsp;</span><spring:message code="order.list.loadmore"/></p>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    $(function () {
        $(".top-right-nav").find("li:eq(2)").addClass("active");
        $(".dx-reservaList:gt(7)").hide();
        var num = 7;
        var base = $(window).height()/2;
        $(window).on("scroll",function(){
            var top1 = $(this).scrollTop();
            var top2 = $("#auto_load").offset().top;
            if(top1+base>top2){
                    num+=7;
                  if(num>=$(".dx-reservaList").length) {
                         $("#auto_load").fadeOut();    
                  } else{
                      $(".dx-reservaList:lt("+num+")").fadeIn();
                  } 
            }
        });

        /*$(".status").each(function () {
            var status = $(this).attr("data-value");
            if(status == '1'){
                $(this).text("待付款");
            }else if(status == '2'){
                $(this).text("已支付");
            }else if(status == '3'){
                $(this).text("已发货");
            }else if(status == '4'){
                $(this).text("已收货");
            }else if(status == '-1'){
                $(this).text("已取消");
            }
        });*/

    });

</script>