<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title></title>
    <!-- css -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/static/css/dookayui.css">
    <link rel="stylesheet" href="${ctx}/static/css/weui.css">
    <link rel="stylesheet" href="${ctx}/static/css/common.css">
    <link rel="stylesheet" href="${ctx}/static/css/dx.css">
    <link rel="stylesheet" href="${ctx}/static/css/dy.css">
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

        <div class="country-select" id="country-select">
            <p class="country-title">国家*</p>
            <ul class="country-con" style="">
                <c:forEach var="row" items="${countryList}">
                    <li><a href="#"  data-value="${row.id}">${row.name}</a></li>
                </c:forEach>
            </ul>
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

    <script src="${ctx}/static/js/dookayui.min.js" merge="true"></script>
    <script src="${ctx}/static/js/zepto.min.js"></script>
    <script src="${ctx}/static/js/weui.js"></script>
    <script src="${ctx}/static/js/common.js"></script>
    <script src="${ctx}/static/js/layer/layer.js"></script>
    <script>
        $(function(){
            commonApp.init();


            $(".country-select").find("li").click(function(){
                $(this).addClass("active").siblings().removeClass("active");
                var id =  $(this).attr("data-value");
                $.post("/passport/firstSelectCountry",{"countryId":id},function (data) {
                    if(data.code==200){
                        location.href = '/home/index';
                    }
                });
            });
        });
        window.onload=function(){
            layer.open({
                type:1,
                shade:0.8,
                title:false,
                closeBtn:0,
                content:$(".country-select"),
                area:['100%','100%'],
            });
        }
    </script>

