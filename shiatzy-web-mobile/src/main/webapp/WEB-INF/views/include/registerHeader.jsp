<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title><%=request.getParameter("pageTitle")%>-保融</title>
    <!-- 核心样式 开始 -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
    <!-- 核心样式 结束 -->
    <!-- 页面样式 开始 -->
    <link rel="stylesheet" href="${ctx}/static/js/plugins/jquery-bxslider/jquery.bxslider.css">
    <link rel="stylesheet" href="${ctx}/static/js/flatpickr/flatpickr.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/dookayui.css">
    <link rel="stylesheet" href="${ctx}/static/css/main.css">
    <link rel="stylesheet" href="${ctx}/static/css/dx.css">
    <link rel="stylesheet" href="${ctx}/static/css/dy.css">
    <!-- 页面样式 结束 -->
    <!--[if lt IE 8]>
    <script src="${ctx}/static/js/versiontips.js"></script>
    <![endif]-->
    <!--[if lt IE 9]>
    <script src="${ctx}/static/js/html5shiv.min.js"></script>
    <script src="${ctx}/static/js/respond.min.js"></script>
    <![endif]-->
    <!-- 核心js插件开始 -->
    <script src="${ctx}/static/js/dookayui.min.js"></script>
    <script src="${ctx}/static/js/plugins/layer/layer.js"></script>
    <script src="${ctx}/static/js/lodash.min.js"></script>
    <script src="${ctx}/static/js/plugins/layer/layer.js"></script>
    <script src="${ctx}/static/js/flatpickr/l10n/zh.js"></script>
    <script src="${ctx}/static/js/flatpickr/flatpickr.min.js"></script>
    <script src="${ctx}/static/js/plugins/jquery-bxslider/jquery.bxslider.js"></script>
    <!-- 核心js插件 结束 -->
    <!-- 页面插件开始 -->
    <script src="${ctx}/static/js/common.js"></script>
    <script src="${ctx}/static/js/plugins/validator/jquery.validator.min.js"></script>
    <script src="${ctx}/static/js/plugins/validator/local/zh-CN.js"></script>
    <script src="${ctx}/static/js/backend.js"></script>
    <!-- 页面插件结束 -->
    <!-- js页面应用 开始 -->
    <script>
        $(function () {
            commonApp.init();
            backendApp.init();
        });
    </script>
    <!-- js页面应用 结束 -->
</head>
<body>