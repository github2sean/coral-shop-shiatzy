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
    <p>订单编号：<span>2017-1-15</span></p>
    <div class="return-commodity clearfix">
        <div>
            <span class="mr-2">
                <label class="radiobox">
                    <input type="checkbox">
                    <i class="i-radiobox"></i>
                    <img src="images/goods-pic01.jpg" alt="">
                </label>
            </span>
        </div>
        <div>
            <p>玉镯提包系列黑色刺绣托特包<span>v</span></p>
            <span>产品编号 1B1184 Z</span>
        </div>
    </div>
    <div class="return-list clearfix">
        <p>请勾选退货理由（可复选）</p>
        <ul>
            <li>尺寸</li>
            <li>
                <label class="radiobox"><input type="checkbox" name="size"><i class="i-radiobox"></i>太大</label>
            </li>
            <li>
                <label class="radiobox"><input type="checkbox" name="size"><i class="i-radiobox"></i>太小</label>
            </li>
            <li>
                <label class="radiobox"><input type="checkbox" name="size"><i class="i-radiobox"></i>太长</label>
            </li>
            <li>
                <label class="radiobox"><input type="checkbox" name="size"><i class="i-radiobox"></i>太短</label>
            </li>
        </ul>
        <ul>
            <li>服务</li>
            <li>
                <label class="radiobox"><input type="checkbox" name="service"><i class="i-radiobox"></i>错误商品</label>
            </li>
            <li>
                <label class="radiobox"><input type="checkbox" name="service"><i class="i-radiobox"></i>货运过长</label>
            </li>
        </ul>
        <ul>
            <li>品质</li>
            <li>
                <label class="radiobox"><input type="checkbox"  name="quality"><i class="i-radiobox"></i>瑕疵品</label>
            </li>
            <li>
                <label class="radiobox"><input type="checkbox" name="qulity"><i class="i-radiobox"></i>与图片不符</label>
            </li>
        </ul>
        <ul>
            <li>其他</li>
            <li>
                <label class="radiobox"><input type="checkbox"  name="others"><i class="i-radiobox"></i>面料</label>
            </li>
            <li>
                <label class="radiobox"><input type="checkbox" name="others"><i class="i-radiobox"></i>风格不合适</label>
            </li>
             <li>
                <label class="radiobox"><input type="checkbox" name="others"><i class="i-radiobox"></i>改变心意</label>
            </li>
        </ul>
    </div>
    <div style="padding-left: 4rem;" class="return-commodity clearfix">
        <img style="float: left;" src="images/goods-pic01.jpg" alt="">
        <div style="margin-top: 3.5rem;margin-left: 0.7rem;">
            <p>玉镯提包系列黑色刺绣托特包<span style="margin-left: 6rem;">v</span></p>
            <span>产品编号 1B1184 Z</span>
        </div>
    </div>
    <div class="return-btn">
        <a href="我的账户.退货申请.我要退货.html">< 確認</a>
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
    function clsTotal() {
        var total = 0;
        $(".goods").find(".goods-right").each(function () {
            var num =  ($(this).find(".quantity").attr("data-value"))*1;
            var price  = ($(this).find(".price").attr("data-value"))*1;
            total +=num * price;
            $("#js_total").html(" &nbsp;"+total.toFixed(2));
        })
    };
    $(function () {
        clsTotal();

    });

</script>