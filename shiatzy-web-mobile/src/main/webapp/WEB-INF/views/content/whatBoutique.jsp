<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title><%=request.getParameter("pageTitle")%>-<spring:message code="shiatzhChen"/></title>
    <!-- 核心样式 开始 -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/static/css/dookayui.css">
    <!-- 核心样式 结束 -->
    <!-- 页面样式 开始 -->
    <link rel="stylesheet" href="${ctx}/static/css/common.css">
    <link rel="stylesheet" href="${ctx}/static/css/dx.css">
    <link rel="stylesheet" href="${ctx}/static/css/dy.css">
    <link rel="stylesheet" href="${ctx}/static/js/plugins/validator/css/jquery.validator.css">
    <!-- 页面样式 结束 -->

    <!--设置浏览器根元素的值-->
    <script>
        (function (doc, win) {
            var docEl = doc.documentElement,
                    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
                    recalc = function () {
                        var clientWidth = docEl.clientWidth;
                        if (!clientWidth) return;
                        docEl.style.fontSize = 10 * (clientWidth / 320) + 'px';
                    };
            if (!doc.addEventListener) return;
            win.addEventListener(resizeEvt, recalc, false);
            doc.addEventListener('DOMContentLoaded', recalc, false);
        })(document, window);

    </script>
</head>
<div class="dx-CommonProblems">
    <div class="content">
        <c:if test="${web:selectLanguage()!='en_US'}">
        <ul class="second-title j_toggle2 ">
            <li><p href="#"> 1. 为了给您提供更为便捷的购物，在线商品部分提供精品店预约功能，您可在商品页面将喜爱的商品添加至“精品店预约”，于“精品店预约清单”内选择相关精品店进行线下体验。目前该服务仅限于中国大陆地区。</p>
            </li>
            <li><p href="#"> 2. 我们会将您预约清单内的商品配送至指定门店，便于您线下舒适快捷的体验，支付将于线下门店完成。</p>
            </li>
        </ul>
        </c:if>
        <c:if test="${web:selectLanguage()=='en_US'}">
        <ul class="second-title j_toggle2 ">
            <li><p href="#">  We offer ‘Store Reservation’ service for your convenient shopping. Click on ‘Store Reservation’ on the product detail page, you can add your favorite items to ‘Store Reservation List’ and choose your nearest offline store. We will deliver your reserved items to the designated store for your easy and convenient shopping. The order and payment will be completed offline.
            </p>
            </li>
            <li><p href="#">  Note: At present, ‘Store Reservation’ service is avaliable in China Mainland region only.</p>
            </li>
        </ul>
        </c:if>

    </div>
    <!-- 核心js插件开始 -->
    <script src="${ctx}/static/js/dookayui.min.js"></script>
    <script src="${ctx}/static/js/plugins/layer/layer.js"></script>
    <!-- 核心js插件 结束 -->
    <!-- 页面插件开始 -->
    <script src="${ctx}/static/js/common.js"></script>
    <script src="${ctx}/static/js/plugins/validator/jquery.validator.min.js"></script>
    <script src="${ctx}/static/js/plugins/validator/local/zh-CN.js"></script>
    <script src="${ctx}/static/js/backend.js"></script>
    <script>
        $(function(){
            //常见问题页面JS
            //下拉菜单展开收起
            $(".j_toggle").on("click",function () {
                $(this).next().toggleClass("hide");
            });

            $(".j_toggle2>li").on("click",function () {
                $(this).find(".answer").toggleClass("hide");
            });
        });
    </script>
</div>
