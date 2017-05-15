<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">选择退货方式</p>
    <a style="float: right;" href="/returnOrder/returnOrderConsigneeInfo">< 上一页</a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3>选择退货方式</h3>
        <p><a href="#"><img src="images/questionMark.png" alt="">退货说明</a></p>
    </div>
    <p>订单编号：<span>${order.orderNo}</span></p>
    <p>订单日期：<span><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></p>
    <div class="order-finish">
        <div class="delivery">
            <%--<div class="delivery-message" style="position: relative;margin-top: 2px">
                <span>快递取件</span>
                <div style="position: absolute;top: 1.5rem;right: 0">
                </div>
                <p>联系人：</p>
                <p style="display: initial;">地址：</p>
                <a href="/returnOrder/listAddress">编辑 ></a>
            </div>
            <div class="drugstore">
                <p>退回门店</p>
                <a href="/returnOrder/listStoreCountry">编辑 ></a>
            </div>--%>

                <c:if test="${not empty return_order.storeDomain}">
                    <div class="delivery-message" style="background-color: inherit;border: 2px solid #999999;border-bottom: none;">
                        <span>快递运送</span>
                        <p></p>
                        <p style="display: initial;"></p>
                        <a href="/returnOrder/listAddress">选择 ></a>
                    </div>
                    <div class="delivery-message" style="height: 100px;background-color: #cccccc;">
                        <p>门店退货</p>
                        <p>${return_order.storeDomain.name}</p>
                        <p>${return_order.storeDomain.address}</p>
                        <a href="/returnOrder/listStoreCountry">选择 ></a>
                    </div>
                </c:if>
                <c:if test="${empty return_order.storeDomain}">
                    <div class="delivery-message" style="margin-top: 2px">
                        <span>快递运送</span>
                        <p>${return_order.customerAddressDomain.title}</p>
                        <p style="display: initial;">${return_order.customerAddressDomain.address}</p>
                        <a href="/returnOrder/listAddress">选择 ></a>
                    </div>
                    <div class="drugstore">
                        <p>门店退货</p>
                        <a href="/returnOrder/listStoreCountry">选择 ></a>
                    </div>
                </c:if>

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

        $(".sureBtn").click(function () {
            if(${return_order.customerAddressDomain==null&&return_order.storeDomain==null}){
             alert("请先选择地址和退货方式");
             return false;
             }
            location.href="/returnOrder/returnOrderConsigneeInfo";
        });


    });

</script>