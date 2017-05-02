<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的账户 </p>
    <a style="float: right;" href="注册登录.登录.html">< 回上页</a>
</div>
<div class="my-account">
    <div style="display: inline-block;" class="my-account-title">
        <p style="margin-right: 1.7005rem;">欢迎您</p>
        <p>SHC</p>
    </div>
    <div class="my-account-message">
        <ul>
            <li>
                <a href="/u/account/details">
                    <span><svg><use xlink:href="#ac-info"></use></svg></span>
                    <span>个人信息</span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="/order/list">
                    <span><svg><use xlink:href="#ac-order"></use></svg></span>
                    <span>订单详情</span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="门市预约.商品列表.html">
                    <span><svg><use xlink:href="#ap-small"></use></svg></span>
                    <span>精品店预约详情</span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="/cart/wishlist">
                    <span><svg><use xlink:href="#heart"></use></svg></span>
                    <span>愿望清单</span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="我的账户.门市预约.列表.html">
                    <span><svg><use xlink:href="#ac-add"></use></svg></span>
                    <span>常用收件门市/地址设定</span>
                    <span style="float: right;">></span>
                </a>
            </li>
        </ul>
    </div>
    <div class="outline">
        <a type="button" class="loginOut" >登出</a>
    </div>



</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {
        $(".loginOut").click(function () {
            $.post("/passport/loginOut.do",function (data) {
                if(data.data==1){
                    location.href = "passport/toLogin.do";
                }
            });
        });

    });

</script>