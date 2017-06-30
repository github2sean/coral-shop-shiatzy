<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<!DOCTYPE html>
<html lang="zh-CN">
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

    <!-- 轮播 开始 -->
    <link rel="stylesheet" href="${ctx}/static/css/nivo_slider.css">
    <link rel="stylesheet" href="${ctx}/static/css/default.css">
    <!-- 轮播 结束 -->

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
<div style="display: none;">
    <svg xmlns="http://www.w3.org/2000/svg">
        <symbol id="ac-add" viewBox="0 0 22 22">
            <g> <path style="fill:#FFFFFF;stroke:#333333;stroke-width:0.5;" d="M17.078,8.98c0-3.303-2.676-5.98-5.979-5.98
		c-3.303,0-5.98,2.678-5.98,5.98c0,3.12,2.398,5.653,5.445,5.926L11.099,19l0.534-4.094C14.682,14.631,17.078,12.1,17.078,8.98z"/> <polygon style="fill:none;stroke:#333333;stroke-width:0.5;" points="11.099,8.163 9.75,6.813 8.4,7.892 8.4,9.511 11.099,12.209
		13.797,9.511 13.797,7.892 12.449,6.813 	"/> </g> <g> <circle style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" cx="132.76" cy="180.545" r="6.45"/> <line style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" x1="137.32" y1="185.105" x2="141.311" y2="189.096"/> </g>
        </symbol>
        <symbol id="ac-ap" viewBox="0 0 22 22">
            <g> <rect x="5.599" y="7.5" style="fill:#FFFFFF;" width="11" height="9"/> <line style="fill:none;stroke:#666666;stroke-width:0.5;stroke-linecap:round;" x1="16.465" y1="16.5" x2="16.465" y2="12.698"/> <line style="fill:none;stroke:#666666;stroke-width:0.5;stroke-linecap:round;" x1="5.732" y1="16.5" x2="5.732" y2="12.698"/> <line style="fill:none;stroke:#666666;stroke-width:0.5;stroke-linecap:round;" x1="17.324" y1="16.5" x2="4.874" y2="16.5"/> <g> <path style="fill:#FFFFFF;" d="M15.663,12.048c-0.799,0-1.239-0.586-1.257-0.611c-0.076-0.104-0.169-0.162-0.266-0.162
			c-0.095,0-0.189,0.058-0.266,0.162c-0.019,0.025-0.457,0.611-1.253,0.611c-0.801,0-1.239-0.584-1.257-0.609
			c-0.076-0.103-0.171-0.161-0.266-0.161c-0.097,0-0.191,0.058-0.267,0.161c-0.019,0.024-0.457,0.609-1.255,0.609
			c-0.8,0-1.236-0.586-1.255-0.611c-0.076-0.104-0.171-0.16-0.266-0.16s-0.189,0.056-0.265,0.16
			c-0.019,0.025-0.459,0.611-1.255,0.611c-1.056,0-1.914-0.967-1.914-2.154l1.041-3.702C5.812,5.666,6.359,5.25,6.91,5.25h8.376
			c0.55,0,1.099,0.414,1.249,0.942l1.029,3.634C17.574,11.081,16.717,12.048,15.663,12.048L15.663,12.048z"/> <path style="fill:#666666;" d="M15.287,5.5c0.436,0,0.888,0.342,1.007,0.761l1.03,3.634c0,1.049-0.744,1.902-1.662,1.902
			c-0.68,0-1.054-0.509-1.054-0.509c-0.128-0.175-0.298-0.263-0.467-0.263c-0.17,0-0.339,0.088-0.468,0.263
			c0,0-0.373,0.509-1.053,0.509c-0.683,0-1.054-0.506-1.054-0.506c-0.129-0.175-0.299-0.263-0.468-0.263
			c-0.17,0-0.339,0.087-0.468,0.263c0,0-0.373,0.506-1.053,0.506c-0.684,0-1.054-0.508-1.054-0.508
			c-0.128-0.176-0.298-0.264-0.468-0.264c-0.169,0-0.338,0.087-0.466,0.262c0,0-0.374,0.509-1.054,0.509
			c-0.918,0-1.662-0.854-1.662-1.902l1.03-3.634C6.021,5.842,6.475,5.5,6.909,5.5H15.287 M15.287,5H6.909
			C6.253,5,5.6,5.494,5.422,6.125l-1.03,3.633L4.374,9.825v0.069c0,1.325,0.97,2.402,2.162,2.402c0.736,0,1.262-0.448,1.457-0.713
			c0.032-0.044,0.058-0.057,0.065-0.059c0.004,0.001,0.03,0.015,0.063,0.059c0.193,0.265,0.718,0.713,1.458,0.713
			c0.737,0,1.262-0.446,1.456-0.71c0.034-0.046,0.06-0.058,0.065-0.059c0.004,0.001,0.031,0.013,0.064,0.059
			c0.194,0.264,0.718,0.71,1.458,0.71c0.736,0,1.262-0.448,1.456-0.713c0.033-0.045,0.06-0.058,0.065-0.059
			c0.003,0.001,0.029,0.014,0.063,0.058c0.195,0.266,0.721,0.714,1.457,0.714c1.192,0,2.162-1.078,2.162-2.402V9.825l-0.019-0.067
			l-1.03-3.634C16.596,5.494,15.942,5,15.287,5L15.287,5z"/> </g> </g>
        </symbol>
        <symbol id="ac-info" viewBox="0 0 22 22">
            <g> <circle style="fill:#FFFFFF;stroke:#666666;stroke-width:0.5;" cx="11.099" cy="11" r="7.5"/> <circle style="fill:#FFFFFF;stroke:#666666;stroke-width:0.5;" cx="11.099" cy="9.762" r="2.474"/> <path style="fill:#FFFFFF;stroke:#666666;stroke-width:0.5;" d="M11.098,12.236c-2.221,0-4.02,1.801-4.02,4.021
		c0,0.416,0.08,0.809,0.197,1.186C8.396,18.11,9.701,18.5,11.098,18.5c1.398,0,2.703-0.39,3.824-1.056
		c0.117-0.378,0.197-0.771,0.197-1.187C15.119,14.037,13.32,12.236,11.098,12.236z"/> </g> <g> <circle style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" cx="132.76" cy="180.545" r="6.45"/> <line style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" x1="137.32" y1="185.105" x2="141.311" y2="189.096"/> </g>
        </symbol>
        <symbol id="ac-order" viewBox="0 0 22 22">
            <g> <polygon style="fill:#FFFFFF;stroke:#666666;stroke-width:0.5;" points="13.1,5.5 5.599,5.5 5.599,16.5 16.599,16.5 16.599,9 	"/> <polyline style="fill:#FFFFFF;stroke:#666666;stroke-width:0.5;" points="13.1,5.5 13.1,9 16.599,9 	"/> </g> <g> <circle style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" cx="132.76" cy="180.545" r="6.45"/> <line style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" x1="137.32" y1="185.105" x2="141.311" y2="189.096"/> </g>
        </symbol>
        <symbol id="account" viewBox="0 0 22 22">
            <g> <path style="fill:none;stroke:#666666;stroke-width:0.75;stroke-linecap:round;stroke-linejoin:round;" d="M19.6,11
		c0-4.695-3.806-8.5-8.5-8.5C6.406,2.5,2.6,6.305,2.6,11c0,3.109,1.676,5.821,4.168,7.303c-0.133-0.428-0.226-0.873-0.226-1.345
		c0-2.515,2.04-4.556,4.557-4.556c-1.549,0-2.802-1.256-2.802-2.806c0-1.548,1.253-2.803,2.802-2.803
		c1.549,0,2.804,1.255,2.804,2.803c0,1.55-1.255,2.806-2.804,2.806c2.518,0,4.557,2.041,4.557,4.556
		c0,0.472-0.092,0.918-0.225,1.346C17.924,16.821,19.6,14.109,19.6,11z"/> <circle style="fill:#FFFFFF;stroke:#666666;stroke-width:0.75;stroke-linecap:round;stroke-linejoin:round;" cx="11.1" cy="9.597" r="2.803"/> <path style="fill:#FFFFFF;stroke:#666666;stroke-width:0.75;stroke-linecap:round;stroke-linejoin:round;" d="M11.099,12.402
		c-2.517,0-4.557,2.041-4.557,4.556c0,0.472,0.093,0.917,0.226,1.345c1.27,0.756,2.747,1.197,4.331,1.197
		c1.584,0,3.062-0.441,4.333-1.196c0.133-0.428,0.225-0.874,0.225-1.346C15.656,14.443,13.617,12.402,11.099,12.402z"/> <circle style="fill:#FFFFFF;stroke:#666666;stroke-width:0.5;stroke-linecap:round;stroke-linejoin:round;" cx="11.1" cy="9.597" r="2.803"/> </g>
        </symbol>
        <symbol id="ap-active" viewBox="0 0 22 22">
            <g> <rect x="5.599" y="7.5" style="fill:#666666;" width="11" height="9"/> <line style="fill:none;stroke:#FFFFFF;stroke-width:0.5;stroke-linecap:round;" x1="16.465" y1="16.5" x2="16.465" y2="12.698"/> <line style="fill:none;stroke:#FFFFFF;stroke-width:0.5;stroke-linecap:round;" x1="5.731" y1="16.5" x2="5.731" y2="12.698"/> <line style="fill:none;stroke:#FFFFFF;stroke-width:0.5;stroke-linecap:round;" x1="17.324" y1="16.5" x2="4.873" y2="16.5"/> <g> <path style="fill:#666666;" d="M15.663,12.048c-0.799,0-1.239-0.586-1.257-0.611c-0.076-0.104-0.169-0.162-0.266-0.162
			c-0.095,0-0.189,0.058-0.266,0.162c-0.019,0.025-0.457,0.611-1.253,0.611c-0.803,0-1.239-0.584-1.257-0.609
			c-0.076-0.103-0.171-0.161-0.266-0.161c-0.097,0-0.191,0.058-0.267,0.161c-0.019,0.024-0.457,0.609-1.255,0.609
			c-0.8,0-1.236-0.586-1.255-0.611c-0.076-0.104-0.171-0.16-0.266-0.16s-0.189,0.056-0.265,0.16
			c-0.019,0.025-0.459,0.611-1.255,0.611c-1.056,0-1.914-0.967-1.914-2.154l1.039-3.702c0.15-0.529,0.7-0.942,1.247-0.942h8.378
			c0.55,0,1.099,0.414,1.249,0.942l1.029,3.634C17.574,11.081,16.717,12.048,15.663,12.048L15.663,12.048z"/> <path style="fill:#FFFFFF;" d="M15.287,5.5c0.436,0,0.888,0.342,1.007,0.761l1.03,3.634c0,1.049-0.745,1.902-1.662,1.902
			c-0.68,0-1.054-0.509-1.054-0.509c-0.128-0.175-0.298-0.263-0.467-0.263c-0.17,0-0.339,0.088-0.468,0.263
			c0,0-0.373,0.509-1.053,0.509c-0.684,0-1.055-0.506-1.055-0.506c-0.129-0.175-0.299-0.263-0.468-0.263
			c-0.17,0-0.339,0.087-0.468,0.263c0,0-0.373,0.506-1.053,0.506c-0.684,0-1.054-0.508-1.054-0.508
			c-0.128-0.176-0.298-0.264-0.468-0.264c-0.169,0-0.338,0.087-0.466,0.262c0,0-0.374,0.509-1.054,0.509
			c-0.918,0-1.662-0.854-1.662-1.902l1.03-3.634C6.021,5.842,6.474,5.5,6.909,5.5H15.287 M15.287,5H6.909
			C6.253,5,5.6,5.494,5.422,6.125l-1.03,3.633L4.373,9.825v0.069c0,1.325,0.97,2.402,2.162,2.402c0.736,0,1.262-0.448,1.457-0.713
			c0.032-0.044,0.058-0.057,0.065-0.059c0.004,0.001,0.03,0.015,0.063,0.059c0.193,0.265,0.718,0.713,1.458,0.713
			c0.737,0,1.262-0.446,1.456-0.71c0.034-0.046,0.06-0.058,0.065-0.059c0.004,0.001,0.031,0.013,0.064,0.059
			c0.194,0.264,0.718,0.71,1.458,0.71c0.736,0,1.262-0.448,1.456-0.713c0.033-0.045,0.06-0.058,0.065-0.059
			c0.003,0.001,0.029,0.014,0.063,0.058c0.195,0.266,0.721,0.714,1.457,0.714c1.192,0,2.162-1.078,2.162-2.402V9.825l-0.019-0.067
			l-1.03-3.634C16.596,5.494,15.942,5,15.287,5L15.287,5z"/> </g> </g>
        </symbol>
        <symbol id="ap-small" viewBox="0 0 22 22">
            <g> <line style="fill:none;stroke:#666666;stroke-width:0.5;stroke-linecap:round;" x1="16.465" y1="16.5" x2="16.465" y2="12.698"/> <line style="fill:none;stroke:#666666;stroke-width:0.5;stroke-linecap:round;" x1="5.731" y1="16.5" x2="5.731" y2="12.698"/> <line style="fill:none;stroke:#666666;stroke-width:0.5;stroke-linecap:round;" x1="17.324" y1="16.5" x2="4.873" y2="16.5"/> <g> <path style="fill:#FFFFFF;" d="M15.663,12.048c-0.799,0-1.239-0.586-1.257-0.611c-0.076-0.104-0.169-0.162-0.266-0.162
			c-0.095,0-0.189,0.058-0.266,0.162c-0.019,0.025-0.457,0.611-1.253,0.611c-0.803,0-1.239-0.584-1.257-0.609
			c-0.076-0.103-0.171-0.161-0.266-0.161c-0.097,0-0.191,0.058-0.267,0.161c-0.019,0.024-0.457,0.609-1.255,0.609
			c-0.8,0-1.236-0.586-1.255-0.611c-0.076-0.104-0.171-0.16-0.266-0.16s-0.189,0.056-0.265,0.16
			c-0.019,0.025-0.459,0.611-1.255,0.611c-1.056,0-1.914-0.967-1.914-2.154l1.039-3.702c0.15-0.529,0.7-0.942,1.247-0.942h8.378
			c0.55,0,1.099,0.414,1.249,0.942l1.029,3.634C17.574,11.081,16.717,12.048,15.663,12.048L15.663,12.048z"/> <path style="fill:#666666;" d="M15.287,5.5c0.436,0,0.888,0.342,1.007,0.761l1.03,3.634c0,1.049-0.745,1.902-1.662,1.902
			c-0.68,0-1.054-0.509-1.054-0.509c-0.128-0.175-0.298-0.263-0.467-0.263c-0.17,0-0.339,0.088-0.468,0.263
			c0,0-0.373,0.509-1.053,0.509c-0.684,0-1.055-0.506-1.055-0.506c-0.129-0.175-0.299-0.263-0.468-0.263
			c-0.17,0-0.339,0.087-0.468,0.263c0,0-0.373,0.506-1.053,0.506c-0.684,0-1.054-0.508-1.054-0.508
			c-0.128-0.176-0.298-0.264-0.468-0.264c-0.169,0-0.338,0.087-0.466,0.262c0,0-0.374,0.509-1.054,0.509
			c-0.918,0-1.662-0.854-1.662-1.902l1.03-3.634C6.021,5.842,6.474,5.5,6.909,5.5H15.287 M15.287,5H6.909
			C6.253,5,5.6,5.494,5.422,6.125l-1.03,3.633L4.373,9.825v0.069c0,1.325,0.97,2.402,2.162,2.402c0.736,0,1.262-0.448,1.457-0.713
			c0.032-0.044,0.058-0.057,0.065-0.059c0.004,0.001,0.03,0.015,0.063,0.059c0.193,0.265,0.718,0.713,1.458,0.713
			c0.737,0,1.262-0.446,1.456-0.71c0.034-0.046,0.06-0.058,0.065-0.059c0.004,0.001,0.031,0.013,0.064,0.059
			c0.194,0.264,0.718,0.71,1.458,0.71c0.736,0,1.262-0.448,1.456-0.713c0.033-0.045,0.06-0.058,0.065-0.059
			c0.003,0.001,0.029,0.014,0.063,0.058c0.195,0.266,0.721,0.714,1.457,0.714c1.192,0,2.162-1.078,2.162-2.402V9.825l-0.019-0.067
			l-1.03-3.634C16.596,5.494,15.942,5,15.287,5L15.287,5z"/> </g> </g>
        </symbol>
        <symbol id="appointment-nav" viewBox="0 0 22 22">
            <g> <path style="fill:none;" d="M18.5,9.669l-1.285-4.533c-0.119-0.419-0.572-0.761-1.006-0.761H5.792
		c-0.436,0-0.889,0.342-1.006,0.761L3.5,9.669c0,0.86,0.42,1.603,1.034,1.994v5.962h12.93v-5.961
		C18.079,11.271,18.5,10.53,18.5,9.669z"/> </g> <g> <line style="fill:none;stroke:#666666;stroke-width:0.75;stroke-linecap:round;" x1="17.465" y1="17.625" x2="17.465" y2="13.046"/> <line style="fill:none;stroke:#666666;stroke-width:0.75;stroke-linecap:round;" x1="4.534" y1="17.625" x2="4.534" y2="13.046"/> <line style="fill:none;stroke:#666666;stroke-width:0.75;stroke-linecap:round;" x1="18.5" y1="17.625" x2="3.5" y2="17.625"/> <g> <path style="fill:#FFFFFF;" d="M16.499,12.335c-1.001,0-1.643-0.858-1.669-0.895c-0.052-0.07-0.111-0.109-0.165-0.109
			S14.552,11.37,14.5,11.44c-0.026,0.037-0.668,0.895-1.667,0.895c-1.002,0-1.642-0.858-1.668-0.893
			c-0.052-0.07-0.111-0.109-0.165-0.109c-0.054,0-0.113,0.04-0.165,0.109c-0.026,0.037-0.668,0.893-1.669,0.893
			c-1.002,0-1.64-0.86-1.667-0.897c-0.051-0.068-0.111-0.109-0.165-0.109c-0.053,0-0.113,0.041-0.165,0.111
			c-0.027,0.037-0.669,0.895-1.669,0.895c-1.309,0-2.375-1.195-2.375-2.667V9.617l0.015-0.051l1.284-4.532
			C4.585,4.456,5.186,4,5.791,4h10.418c0.601,0,1.201,0.453,1.366,1.033l1.284,4.532l0.015,0.051v0.052
			C18.874,11.14,17.808,12.335,16.499,12.335L16.499,12.335z"/> <path style="fill:#666666;" d="M16.208,4.375c0.436,0,0.888,0.342,1.007,0.761L18.5,9.669c0,1.265-0.897,2.292-2.002,2.292
			c-0.82,0-1.365-0.743-1.365-0.743c-0.129-0.175-0.299-0.263-0.468-0.263s-0.339,0.088-0.468,0.263c0,0-0.544,0.743-1.363,0.743
			c-0.823,0-1.366-0.74-1.366-0.74c-0.129-0.175-0.299-0.263-0.468-0.263c-0.17,0-0.339,0.087-0.468,0.263
			c0,0-0.544,0.741-1.365,0.741c-0.823,0-1.365-0.742-1.365-0.742c-0.128-0.176-0.298-0.264-0.468-0.264
			c-0.169,0-0.338,0.087-0.466,0.262c0,0-0.546,0.744-1.366,0.744c-1.105,0-2.002-1.027-2.002-2.292l1.285-4.533
			c0.118-0.418,0.571-0.761,1.006-0.761H16.208 M16.208,3.625H5.791c-0.774,0-1.517,0.562-1.728,1.307L2.778,9.464L2.75,9.565v0.104
			c0,1.677,1.234,3.042,2.752,3.042c0.916,0,1.56-0.581,1.833-0.883c0.273,0.304,0.915,0.883,1.833,0.883S10.729,12.13,11,11.829
			c0.273,0.303,0.916,0.882,1.834,0.882c0.916,0,1.56-0.582,1.831-0.884c0.273,0.303,0.917,0.884,1.833,0.884
			c1.518,0,2.752-1.365,2.752-3.042V9.564l-0.028-0.1l-1.285-4.533C17.725,4.186,16.981,3.625,16.208,3.625L16.208,3.625z"/> </g> </g>
        </symbol>
        <symbol id="appointment" viewBox="0 0 22 22">
            <g> <path style="fill:#FFFFFF;" d="M18.5,9.669l-1.285-4.533c-0.119-0.419-0.572-0.761-1.006-0.761H5.792
		c-0.436,0-0.889,0.342-1.006,0.761L3.5,9.669c0,0.86,0.42,1.603,1.034,1.994v5.962h12.93v-5.961
		C18.079,11.271,18.5,10.53,18.5,9.669z"/> </g> <g> <line style="fill:none;stroke:#666666;stroke-width:0.75;stroke-linecap:round;" x1="17.465" y1="17.625" x2="17.465" y2="13.046"/> <line style="fill:none;stroke:#666666;stroke-width:0.75;stroke-linecap:round;" x1="4.535" y1="17.625" x2="4.535" y2="13.046"/> <line style="fill:none;stroke:#666666;stroke-width:0.75;stroke-linecap:round;" x1="18.5" y1="17.625" x2="3.5" y2="17.625"/> <g> <path style="fill:#666666;" d="M16.208,4.375c0.436,0,0.888,0.342,1.007,0.761L18.5,9.669c0,1.265-0.897,2.292-2.002,2.292
			c-0.82,0-1.365-0.743-1.365-0.743c-0.129-0.175-0.299-0.263-0.468-0.263s-0.339,0.088-0.468,0.263c0,0-0.544,0.743-1.363,0.743
			c-0.823,0-1.366-0.74-1.366-0.74c-0.128-0.175-0.298-0.263-0.468-0.263c-0.17,0-0.339,0.087-0.468,0.263
			c0,0-0.544,0.741-1.365,0.741c-0.823,0-1.365-0.742-1.365-0.742c-0.128-0.176-0.298-0.264-0.468-0.264
			c-0.169,0-0.338,0.087-0.466,0.262c0,0-0.546,0.744-1.366,0.744c-1.105,0-2.002-1.027-2.002-2.292l1.285-4.533
			c0.118-0.418,0.571-0.761,1.006-0.761H16.208 M16.208,3.625H5.791c-0.774,0-1.517,0.562-1.728,1.307L2.779,9.464L2.75,9.565v0.104
			c0,1.677,1.234,3.042,2.752,3.042c0.916,0,1.56-0.581,1.833-0.883c0.273,0.304,0.915,0.883,1.833,0.883
			c0.917,0,1.561-0.581,1.833-0.881c0.273,0.302,0.916,0.881,1.834,0.881c0.916,0,1.56-0.582,1.831-0.884
			c0.273,0.303,0.917,0.884,1.833,0.884c1.518,0,2.752-1.365,2.752-3.042V9.564l-0.028-0.1l-1.285-4.533
			C17.725,4.186,16.981,3.625,16.208,3.625L16.208,3.625z"/> </g> </g>
        </symbol>
        <symbol id="bag-active" viewBox="0 0 22 22">
            <g> <path style="fill:#666666;" d="M16.278,8.829C16.013,7.97,14.078,5,11.1,5C8.372,5,6.139,7.796,5.908,8.832
		c-1.087,0.454-1.73,1.569-1.514,2.871l0.367,2.213C5.043,15.611,6.682,17,8.403,17h5.394c1.718,0,3.358-1.389,3.64-3.084
		l0.367-2.213C18.021,10.396,17.372,9.276,16.278,8.829z M7.547,8.619c-0.135,0-0.266,0.01-0.395,0.028
		C7.955,7.523,8.894,6.319,11.1,6.319c2.306,0,3.17,1.222,3.93,2.324c-0.123-0.017-0.248-0.024-0.381-0.024H7.547z"/></g>
        </symbol>
        <symbol id="bag" viewBox="0 0 22 22">
            <g> <path style="fill:none;stroke:#666666;stroke-width:0.75;" d="M17.219,8.394c-0.319-1.031-2.641-4.598-6.217-4.598
		c-3.275,0-5.957,3.357-6.234,4.602C3.463,8.942,2.69,10.281,2.95,11.844l0.44,2.657c0.34,2.036,2.307,3.703,4.373,3.703h6.477
		c2.063,0,4.031-1.667,4.369-3.703l0.441-2.657C19.311,10.276,18.531,8.931,17.219,8.394z M6.736,8.142
		c-0.162,0-0.319,0.012-0.475,0.033c0.965-1.35,2.092-2.794,4.74-2.794c2.77,0,3.807,1.466,4.719,2.79
		c-0.147-0.02-0.299-0.029-0.457-0.029H6.736z"/> <path style="fill:#FFFFFF;" d="M12.283,11.556h-0.933c-0.066-0.074-0.151-0.124-0.247-0.125h-0.206
		c-0.096,0.001-0.18,0.051-0.246,0.125H9.715c-0.283,0.002-0.508,0.229-0.51,0.512v1.309c0.002,0.285,0.227,0.507,0.51,0.509h0.933
		c0.066,0.077,0.152,0.128,0.25,0.128h0.206c0.097,0,0.183-0.051,0.251-0.128h0.929c0.283-0.002,0.51-0.223,0.514-0.509v-1.309
		C12.793,11.781,12.566,11.56,12.283,11.556z M9.926,12.276h0.59v0.889h-0.59V12.276z M12.076,13.165h-0.591v-0.889h0.591V13.165z"/> </g>
        </symbol>
        <symbol id="cart" viewBox="0 0 22 22">
            <g> <path style="fill:none;stroke:#666666;stroke-width:0.75;" d="M15.987,7.679c-0.319-1.031-2.641-4.598-6.217-4.598
		c-3.275,0-5.957,3.357-6.234,4.602c-1.305,0.545-2.077,1.884-1.817,3.446l0.44,2.657c0.34,2.036,2.307,3.703,4.373,3.703h6.477
		c2.063,0,4.031-1.667,4.369-3.703l0.441-2.657C18.079,9.562,17.3,8.216,15.987,7.679z M5.505,7.427
		c-0.162,0-0.319,0.012-0.475,0.033c0.965-1.35,2.092-2.794,4.74-2.794c2.77,0,3.807,1.466,4.719,2.79
		c-0.147-0.02-0.299-0.029-0.457-0.029H5.505z"/> <path style="fill:#FFFFFF;" d="M11.052,10.841h-0.933c-0.066-0.074-0.151-0.124-0.247-0.125H9.666
		c-0.096,0.001-0.18,0.051-0.247,0.125H8.483c-0.283,0.002-0.508,0.229-0.51,0.512v1.309c0.002,0.285,0.227,0.507,0.51,0.509h0.932
		c0.068,0.077,0.154,0.128,0.251,0.128h0.206c0.098,0,0.184-0.051,0.25-0.128h0.93c0.283-0.002,0.51-0.223,0.514-0.509v-1.309
		C11.562,11.066,11.335,10.845,11.052,10.841z M8.694,11.562h0.59v0.889h-0.59V11.562z M10.845,12.45h-0.591v-0.889h0.591V12.45z"/> <circle style="fill:#FFFFFF;stroke:#666666;stroke-width:0.75;" cx="16.208" cy="14.595" r="4.322"/> </g>
        </symbol>
        <symbol id="cart-nav" viewBox="0 0 22 22">
            <g> <path style="fill:none;stroke:#666666;stroke-width:0.75;" d="M17.219,8.394c-0.318-1.031-2.641-4.598-6.217-4.598
		c-3.275,0-5.957,3.357-6.234,4.602C3.462,8.942,2.69,10.281,2.95,11.844l0.44,2.657c0.34,2.036,2.307,3.703,4.373,3.703h6.477
		c2.063,0,4.031-1.667,4.369-3.703l0.441-2.657C19.311,10.276,18.531,8.931,17.219,8.394z M6.736,8.142
		c-0.162,0-0.319,0.012-0.475,0.033c0.965-1.35,2.092-2.794,4.74-2.794c2.77,0,3.807,1.466,4.719,2.79
		c-0.147-0.02-0.299-0.029-0.457-0.029H6.736z"/> <path style="fill:#fff;" d="M12.283,11.556H11.35c-0.067-0.074-0.151-0.124-0.247-0.125h-0.206
		c-0.096,0.001-0.18,0.051-0.247,0.125H9.714c-0.283,0.002-0.508,0.229-0.51,0.512v1.309c0.002,0.285,0.227,0.507,0.51,0.509h0.932
		c0.068,0.077,0.154,0.128,0.251,0.128h0.206c0.098,0,0.184-0.051,0.25-0.128h0.93c0.283-0.002,0.51-0.223,0.514-0.509v-1.309
		C12.793,11.781,12.566,11.56,12.283,11.556z M9.925,12.276h0.59v0.889h-0.59V12.276z M12.076,13.165h-0.591v-0.889h0.591V13.165z"/> </g>
        </symbol>
        <symbol id="close" viewBox="0 0 22 22">
            <g> <g> <line style="fill:none;stroke:#333333;stroke-width:0.5;" x1="6.099" y1="6" x2="16.099" y2="16"/> </g> <g> <line style="fill:none;stroke:#333333;stroke-width:0.5;" x1="6.099" y1="16" x2="16.099" y2="6"/> </g> </g>
        </symbol>
        <symbol id="heart" viewBox="0 0 22 22">
            <g> <polygon style="fill:none;stroke:#333333;stroke-width:0.5;" points="11.099,8.25 8.349,5.5 5.599,7.7 5.599,11 11.099,16.5
		16.599,11 16.599,7.7 13.849,5.5 	"/> </g> <g> <circle style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" cx="132.76" cy="180.545" r="6.45"/> <line style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" x1="137.32" y1="185.105" x2="141.311" y2="189.096"/> </g>
        </symbol>
        <symbol id="heart-red" viewBox="0 0 30 30">
            <g> <polygon style="fill:#80393f;stroke:#333333;stroke-width:0.5;" points="11.099,8.25 8.349,5.5 5.599,7.7 5.599,11 11.099,16.5
            16.599,11 16.599,7.7 13.849,5.5 	"/> </g> <g> <circle style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" cx="132.76" cy="180.545" r="6.45"/> <line style="fill:none;stroke:#FFFFFF;stroke-linecap:round;" x1="137.32" y1="185.105" x2="141.311" y2="189.096"/> </g>
        </symbol></svg>
</div>
<c:if test="${not empty pushContent}">
<div class="site-notice">
        ${web:selectLanguage()=='en_US'?pushContent.enContent:pushContent.content}
    <a href="javascript:;" class="j_cls_notice iconfont icon-iconfontcha"></a>
</div>
</c:if>
<%--<div class="site-notice">
    #情人節這樣過# 愛情，本該甘於平淡，卻又不平淡；出於平凡，卻又不平凡。即日起至2月11日23：59分，夏姿•陳邀請你來講述你平凡又不平淡的愛情故事。
    <a href="javascript:;" class="j_cls_notice iconfont icon-iconfontcha"></a>
</div>--%>
<header class="relative" >
    <div class="top-bar" style="position: relative">
    <a class="btn-left iconfont" id="j_show_nav" href="#">&#xe76d;</a>
    <span class="do-logo-w"><a href="/home/index"><img src="${ctx}/static/images/logo.png" alt=""></a></span>
    <ul class="top-right-nav">
        <li class="cart"><a href="/cart/list"><svg><use xlink:href="#cart-nav"></use></svg><span class="do-num cart_num hide">${cartNumber==null?0:cartNumber}</span></a></li>
        <li class="boutique"><a href="/boutique/list"><svg><use xlink:href="#appointment-nav"></use></svg><span class="do-num boutique_num hide">0</span></a></li>
        <li class="account"><a href="/u/account/index"><svg><use xlink:href="#account"></use></svg></a></li>
    </ul>
    </div>
</header>
<div class="do-nav-panel" id="j_nav_panel">
    <div class="do-nav-head">
        <a class="btn-left iconfont" id="j_close_nav" href="#">&#xe67d;</a>
        <span class="do-logo-w"><a href="/home/index"><img src="${ctx}/static/images/logo.png" alt=""></a></span>
    </div>
    <div class="do-nav-cnt">
        <div class="do-search">
            <form action="/goods/search" method="get" id="contentForm">
                <input type="text" id="searchKey" placeholder="<spring:message code="searchKey"/>" class="j_search" name="goodsName"onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message code="searchKey"/>'"/>
                <button class="iconfont" id="searchBtn">&#xe610;</button>
            </form>
        </div>
        <ul class="guide-nav j_drop_down">
            <c:forEach var="level1" items="${web:categoryList()}" begin="0">
            <li data-image="${ImageModel.toFirst(level1.thumb).file}">
                <div class="search-rec-img">
                    <img src="${ImageModel.toFirst(level1.thumb).file}" alt="">
                </div>
                <a>${web:selectLanguage()=='en_US'?level1.enName:level1.name}
                    <ul class="guide-sub-nav">
                    <c:forEach var="level2" items="${level1.children}">
                        <li><a href="/goods/list?categoryId=${level2.id}">${web:selectLanguage()=='en_US'?level2.enName:level2.name}</a></li>
                    </c:forEach>
                    </ul>
                </a>
            </li>
            </c:forEach>
            <li><a href="/goods/onSale">SALE</a></li>
        </ul>
        <ul class="do-list-lang">
            <li><a href="/u/account/index"><spring:message code="myAccount"/></a></li>
            <li class="do-login">
                <a href="/passport/toLogin"><spring:message code="login"/></a> | <a href="/passport/toRegister"><spring:message code="register"/></a></li>
            <li>
                <a href="javascript:void(0)">
                    <select class="select_country" value="1" style="height: 100%;width: 100%;background: #2a2a2a;border: 0;font-size: 1.1rem;">
                        <option value="-1"><spring:message code="selectOtherCountriesORRegions"/></option>
                        <c:forEach var="country" items="${web:countryList()}" begin="0">
                            <option value="${country.id}" <c:if test="${web:selectCountry()==country.id}">selected="selected"</c:if> >${web:selectLanguage()=='en_US'?country.enName:country.name}</option>
                        </c:forEach>
                    </select>
                </a></li>
        </ul>
        <ul class="do-list-lang j_drop_down">
            <li class=""><a href="javascript:void(0);">
               <spring:message code="selectLanguage"/>
            </a>
                <ul class="do-sub-list-btm">
                    <li><a class="language" data-value="zh_CN" href="javascript:void(0);" style="text-decoration: underline"><spring:message code="language.cn" /></a></li>
                    <li><a class="language" data-value="en_US" href="javascript:void(0);" style="text-decoration: underline"><spring:message code="language.en" /></a></li>
                </ul>
            </li>
        </ul>
        <div class="do-online-service">
            <p class="do-online-t"><spring:message code="customerServiceLine"/></p>
            <p><spring:message code="chinaPhone"/>4008 213 760</p>
            <p>(<spring:message code="chinaOnly"/>)</p>
            <p><spring:message code="onlineTime"/></p>
            <p class="mt-1"><spring:message code="onlineEmail"/></p>
            <p>(<spring:message code="allRegions"/>)</p>
        </div>
    </div>
</div>
<div class="main-content j_scroll_body">
