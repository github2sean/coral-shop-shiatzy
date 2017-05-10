<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left">结 帐 / 详细</p>
    <a style="float: right;" href="javascript:history.go(-1)">< 回上页</a>
</div>
<div class="order-finish">
    <h3>订单总额：¥ ${order.orderTotal}</h3>
    <div class="delivery content">
        <h3>1. 配送</h3>

        <div class="delivery-message" style="margin-top: 2px">
            <span>快递运送</span>
            <p>${row.firstName}${row.lastName}</p>
            <p style="display: initial;">${row.address}</p>
            <a href="/checkout/listAddress">编辑 ></a>
        </div>

        <div class="drugstore">
            <p>门店取货</p>
            <a href="购物车.结算页.门店取货.html">编辑 ></a>
        </div>
    </div>
    <div class="delivery">
        <h3>2. 发票信息</h3>
        <div>
            <span class="mr-2"><label class="radiobox"><input type="radio" checked="checked" name="isNeed" id="noNeed"><i class="i-radiobox"></i>不需要发票</label></span>
            <span class="mr-2"><label class="radiobox"><input type="radio" name="isNeed" id="need"><i class="i-radiobox"></i>需要发票</label></span>
        </div>
        <p id="showBill" style="display: none;margin-left: 2.7rem;font-size: 1.0821rem;margin-top: 2.7053rem;border-bottom: 2px solid #cccccc;">
            发票抬头*<input id="billTitle" type="text" style="border: none;border-bottom: 2px solid #cccccc;width: 100%;float: right"></p>
    </div>
    <div class="delivery">
        <h3>3. 支付选项</h3>
        <ul>
            <li class=" payMethod" data-value="1">支付宝</li>
            <li class=" payMethod" data-value="2">信用卡（接受VISA，银联XXXX）</li>
            <li class=" payMethod" data-value="3">iPayLinks</li>
        </ul>
        <p>2件商品　v</p>
    </div>
    <div class="order-pay">
        <a href="#" class="submitBtn">订购付款</a>
        <span>提交订单即表示您同意接受 SHIATZY CHEN 公司条款 和 隐私权政策。</span>
    </div>
    <div class="pay-way">
        <p>付款方式</p>
        <div class="way-img">
            <a href="#"><img src="${ctx}/static/images/mastercard.png" alt=""></a>
            <a href="#"><img src="${ctx}/static/images/visa.png" alt=""></a>
            <a href="#"><img src="${ctx}/static/images/way_03.jpg" alt=""></a>
        </div>
    </div>
    <div class="order-safe">
        <p>安全购物</p>
        <div class="safe-img">
            <a style="margin-right: 1.5rem;" href="#"><img src="${ctx}/static/images/way_10.jpg" alt=""></a>
            <a href="#"><img src="${ctx}/static/images/way_07.jpg" alt=""></a>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){

        $("#need").click(function () {
            $("#showBill").show();
        });
        $("#billTitle").blur(function () {
            var $now = $(this);
            $.post("/checkout/isNeedBill",{"isNeed":1,"info":$now.val()},function (data) {
                if(data.code==200){
                }
                console.log(data);
            });
        });
        $("#noNeed").click(function () {
            $("#showBill").hide();
            $.post("/checkout/isNeedBill",{"isNeed":0,"info":""},function (data) {
                if(data.code==200){
                }
                console.log(data);
            });
        });
        $(".payMethod").click(function () {
            $(this).addClass("active").siblings("li").removeClass("active");
            var  id = $(this).attr("data-value");
            $.post("/checkout/setPaymentMethod",{"paymentId":id},function (data) {
                if(data.code==200){
                }
                console.log(data);
            });
        });

        $(".delivery-message").click(function () {
            var id = $(this).attr("data-value");
            $(this).css("background-color","#cccccc").siblings(".delivery-message").css("background-color","#f9f4f4");
            $.post("/checkout/setPaymentMethod",{"addressId":id},function (data) {
                if(data.code==200){
                }
                console.log(data);
            });
        });

        $(".submitBtn").click(function () {

            $.post("/checkout/submitOrder",function (data) {
                console.log(data);
                if(data.code==200){

                }
            });
        });

    });
</script>