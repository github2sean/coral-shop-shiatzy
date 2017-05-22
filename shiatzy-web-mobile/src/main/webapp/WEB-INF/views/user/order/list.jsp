<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-orderList clearfix">
    <div class="dx-title">我的帐户 / 订单详情  <a style="float: right;" href="/u/account/index">回上页</a></div>
    <div class="content">
        <div class="dx-reservation">我的订单</div>

        <c:if test="${empty orderList}">
            <div class="content ">
                <div id="toggleDiv2">
                    <div class="dx-collect">订单（0）</div>
                </div>
            </div>
        </c:if>
       <c:forEach var="row" items="${orderList}">
        <a href="/order/details?orderId=${row.id}" class="dx-reservaList clearfix">
            <div class="time"><fmt:formatDate value="${row.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></div>
            <div class="orderNumber">${row.orderNo}</div>
            <div class="status" data-value="${row.status}"></div>
        </a>
        </c:forEach>
        <div class="dx-reservation">我的退货单</div>
        <c:if test="${empty returnList}">
            <div class="content ">
                <div id="toggleDiv3">
                    <div class="dx-collect">退货单（0）</div>
                </div>
            </div>
        </c:if>
        <c:forEach var="row" items="${returnList}">
            <a href="/returnOrder/details?orderId=${row.id}" class="dx-reservaList clearfix">
                <div class="time"><fmt:formatDate value="${row.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></div>
                <div class="orderNumber">${row.orderNo}</div>
                <div class="status" data-value=""></div>
            </a>
        </c:forEach>

    </div>
    <div class="check clearfix"> <span>查看所有预约單</span><a href="/reservation/list"> ></a></div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    $(function () {


        $(".status").each(function () {
            var status = $(this).attr("data-value");
            if(status == '1'){
                $(this).text("待支付");
            }else if(status == '2'){
                $(this).text("已支付");
            }else if(status == '3'){
                $(this).text("已发货");
            }else if(status == '4'){
                $(this).text("已收货");
            }else if(status == '-1'){
                $(this).text("已取消");
            }
        });


    });

</script>