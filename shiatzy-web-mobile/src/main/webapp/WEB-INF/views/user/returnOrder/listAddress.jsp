<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">预约完成</p>
    <a style="float: right;" href="#">< 我的账户</a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3>选择退货方式</h3>
        <p><a href="#"><img src="images/questionMark.png" alt="">退货说明</a></p>
    </div>
    <p>订单编号：<span>12345AABBCC</span></p>
    <p>订单日期：<span>2017-1-15</span></p>
    <div class="order-finish">
        <div class="delivery">
            <c:forEach var="row" items="${addressList}">
            <div class="delivery-message" style="position: relative;margin-top: 2px">
                <span>快递取件</span>
                <div style="position: absolute;top: 1.5rem;right: 0">
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="addressId" class="addressId" value="${row.id}" ><i class="i-radiobox"></i></label></span>
                </div>
                <p>${row.firstName}${row.lastName}</p>
                <p style="display: initial;">${row.address}</p>
                <a href="购物车.结算页.物流配送.编辑.html">编辑 ></a>
            </div>
            </c:forEach>
            <div class="drugstore">
                <p>退回门店</p>
                <a href="我的账户.门市预约.列表.html">编辑 ></a>
            </div>
        </div>
    </div>
    <div class="return-btn">
        <a href="#" class="sureBtn">< 確認</a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">> </span>
            <span style="float: left;">收到商品后7天内享有轻松退货政策</span>
        </a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">> </span>
            <span style="float: left;">隐私权政策</span>
        </a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    $(function () {


        var backWay = '';
        var addressId = '';
        if($(".addressId").attr('checked')) {
            addressId = $(this).val();
            backWay = 1;
            alert(1);
        };

        $(".sureBtn").click(function () {
            /*if(backWay==''|| addressId==''){
                alert("请先选择地址和退货方式");
                return false;
            }
            var data = {"backWay":backWay,"addressId":addressId};
             */
            $.post("/returnOrder/sureReturnWay",{"backWay":1,"addressId":1},function (data) {
                if(data.code==200){
                    location.href = "/returnOrder/returnOrderConsigneeInfo";
                }
            });

        });


    });

</script>