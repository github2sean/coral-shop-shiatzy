<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left">结 帐 / 详细</p>
    <a style="float: right;" href="购物车.结算页.无优惠代码.html">< 回上页</a>
</div>
<div class="order-finish">
    <h3>订单总额：¥ 11,504</h3>
    <div class="delivery">
        <h3>1. 配送</h3>
        <div class="delivery-message">
            <span>快递运送</span>
            <p>資訊部</p>
            <p style="display: initial;">新北市五股区五权六路40号</p>
            <a href="购物车.结算页.物流配送.编辑.html">编辑 ></a>
        </div>
        <div class="drugstore">
            <p>门店取货</p>
            <a href="购物车.结算页.门店取货.html">编辑 ></a>
        </div>
    </div>
    <div class="delivery">
        <h3>2. 发票信息</h3>
        <div>
            <span class="mr-2"><label class="radiobox"><input type="checkbox"><i class="i-radiobox"></i>不需要发票</label></span>
        </div>
        <div>
            <span class="mr-2"><label class="radiobox"><input type="checkbox"><i class="i-radiobox"></i>需要发票</label></span>
        </div>
        <p style="margin-left: 2.7rem;font-size: 1.0821rem;margin-top: 2.7053rem;border-bottom: 2px solid #cccccc;">发票抬头*：</p>
    </div>
    <div class="delivery">
        <h3>3. 支付选项</h3>
        <ul>
            <li class="active">信用卡（接受VISA，银联XXXX）</li>
            <li>支付宝</li>
            <li>iPayLinks</li>
        </ul>
        <p>2件商品　v</p>
    </div>
    <div class="order-pay">
        <a href="我的账户.我的订单.订单详情.已支付.html">订购付款</a>
        <span>提交订单即表示您同意接受 SHIATZY CHEN 公司条款 和 隐私权政策。</span>
    </div>
    <div class="pay-way">
        <p>付款方式</p>
        <div class="way-img">
            <a href="#"><img src="images/mastercard.png" alt=""></a>
            <a href="#"><img src="images/visa.png" alt=""></a>
            <a href="#"><img src="images/way_03.jpg" alt=""></a>
        </div>
    </div>
    <div class="order-safe">
        <p>安全购物</p>
        <div class="safe-img">
            <a style="margin-right: 1.5rem;" href="#"><img src="images/way_10.jpg" alt=""></a>
            <a href="#"><img src="images/way_07.jpg" alt=""></a>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){


    });
</script>
