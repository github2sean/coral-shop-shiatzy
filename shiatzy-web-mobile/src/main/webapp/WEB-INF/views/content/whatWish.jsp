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
        <p class=" j_toggle"><a href="#"><spring:message code="wish.whatWish"/>?</a></p
        <%--<c:if test="${web:selectLanguage()!='en_US'}">
        <ul class="second-title j_toggle2 ">
            <li><p href="#">  在“我的账户”下点击“愿望清单”，可查看您所添加的所有愿望单品。您可任意移除或添加新的单品。
            </p>
            </li>
            <li><p href="#">  请注意：部分您所添加的商品可能售罄，但稍后重新配货后可能再次上线，可供购买。
            </p>
            </li>
        </ul>
        </c:if>
        <c:if test="${web:selectLanguage()=='en_US'}">
            <ul class="second-title j_toggle2 ">
                <li><p href="#"> In the "my account" and click on "wish list", you can see all desires that you add a single product. You can remove or add any new items.
                </p>
                </li>
                <li><p href="#">  Please note: some of you have added commodities may be sold, but later re distribution may be on the line again, available for purchase.
                </p>
                </li>
            </ul>
        </c:if>--%>
        <c:forEach var="itemDomain" items="${domainList}">
            <ul class="second-title j_toggle2 ">
                <c:forEach var="domainlist" items="${itemDomain.contentItemDomainList}">
                    <li>
                        <p class="answer">${web:selectLanguage()=='en_US'?domainlist.en_content:domainlist.content}</p>
                    </li>
                </c:forEach>
            </ul>
        </c:forEach>
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
         /*   $(".j_toggle").on("click",function () {
                $(this).next().toggleClass("hide");
            });

            $(".j_toggle2>li").on("click",function () {
                $(this).find(".answer").toggleClass("hide");
            });*/
        });
    </script>

