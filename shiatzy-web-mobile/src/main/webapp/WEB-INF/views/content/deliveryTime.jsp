<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
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
    <c:if test="${web:selectLanguage()!='en_US'}">
    <div class="content">
        <p class="first-title j_toggle"><a href="#">你们提供哪些地区的配送服务？</a></p>
        <table class="table table-bordered hide">
            <thead style="font-size:12px ">
            <tr>
                <th>配送地区</th>
                <th>配送时间</th>
                <th>费用</th>
            </tr>
            </thead>
            <tbody style="font-size:10px ">
            <tr>
                <td>中国（不含港澳台）</td>
                <td>
                    周一至周五，早上9点至下午6点之间配送
                    下单后的3-7个工作日内收到货品
                </td>
                <td>免费</td>
            </tr>
            <tr>
                <td>美国/加拿大</td>
                <td>
                    周一至周五，早上9点至下午6点之间配送
                    下单后的3-7个工作日内收到货品
                </td>
                <td>RMB 340</td>
            </tr>
            <tr>
                <td>法国/意大利/英国/德国/奥地利/比利时/捷克/丹麦/芬兰/爱尔兰/卢森堡/摩纳哥/荷兰/波兰/葡萄牙/瑞典/瑞士/挪威/希腊</td>
                <td>
                    周一至周五，早上9点至下午6点之间配送
                    下单后的3-7个工作日内收到货品
                </td>
                <td>RMB 390</td>
            </tr>
            <tr>
                <td>澳大利亚/新西兰</td>
                <td>
                    周一至周五，早上9点至下午6点之间配送
                    下单后的3-7个工作日内收到货品
                </td>
                <td>RMB 303</td>
            </tr>
            <tr>
                <td>韩国/日本/新加坡/马来西亚/泰国/台湾/香港</td>
                <td>
                    周一至周五，早上9点至下午6点之间配送
                    下单后的3-7个工作日内收到货品
                </td>
                <td>RMB 243</td>
            </tr>
            <tr>
                <td>俄罗斯/埃及/冰岛</td>
                <td>
                    周一至周五，早上9点至下午6点之间配送
                    下单后的3-7个工作日内收到货品
                </td>
                <td>RMB 471</td>
            </tr>
            <tr>
                <td>其它地区</td>
                <td>
                    周一至周五，早上9点至下午6点之间配送
                    下单后的3-7个工作日内收到货品
                </td>
                <td>RMB 385</td>
            </tr>
            </tbody>
        </table>
        <ul class="second-title j_toggle2 hide tfoot">
            <li><p href="#">* 我们的配送时间从下单时刻算起，我们的仓库将在24-48小时内处理并配送您的商品。请注意，在我们促销期间及法定节假日期间，此时段可能会延长。<br/>
                * 有些商业地址周末可能不接收货件。为了确保周末送达，请检查您的送货地址。<br/>
                <span style="color: red">* 部分商品（珍惜皮革类等）可能因为各国家海关政策不同，不予入境，请您确保购买的商品符合本国及海关条例，在清关过程中发生的退货、拒收或者海关没收等行为，我们不会承担相应责任。</span>
            </p>
            </li>
        </ul>

        <p class="first-title j_toggle"><a href="#">是否可以配送到离我最近的门店？</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">我们为在线顾客提供线下精品店取货服务，尽享轻松地购物乐趣。<br/>
                目前此政策仅适用于中国(不含港澳台)所有店铺。详情可参考精品店取货政策。</p>
            </li>

        </ul>
        <p class="first-title j_toggle"><a href="#">是否可以配送到多个地址？</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">目前shop.shiatzychen.com 每次配送只能选择一个地址。如果想要配送到多个地址，我们建议您将每个地址的订单分开填写。</p>
            </li>
        </ul>

    </div>
    </c:if>
    <c:if test="${web:selectLanguage()=='en_US'}">
    <div class="content">
        <p class="first-title j_toggle"><a href="#">Do you offer what area delivery service?</a></p>
        <table class="table table-bordered hide">
            <thead style="font-size:12px ">
            <tr>
                <th>Distribution area </th>
                <th>Delivery time</th>
                <th>Expenses</th>
            </tr>
            </thead>
            <tbody style="font-size:10px ">
            <tr>
                <td>China (excluding Hong Kong and Macao) </td>
                <td>
                    Monday to Friday, 9 a.m. to 3-7 days after the order of the distribution between 6 points in the receipt of goods
                </td>
                <td>Free</td>
            </tr>
            <tr>
                <td>America / Canada </td>
                <td>
                    Monday to Friday, 9 a.m. to 3-7 days after the order of the distribution between 6 points in the receipt of goods
                </td>
                <td>RMB 340</td>
            </tr>
            <tr>
                <td>France / Italy / UK / Germany / Austria / Belgium / Czech / Denmark / Finland / Ireland / Luxemburg / Monaco / Holland / Poland / Portugal / Sweden / Norway / Switzerland / Greece </td>
                <td>
                    Monday to Friday, 9 a.m. to 3-7 days after the order of the distribution between 6 points in the receipt of goods
                </td>
                <td>RMB 390</td>
            </tr>
            <tr>
                <td>Australia / New Zealand</td>
                <td>
                    Monday to Friday, 9 a.m. to 3-7 days after the order of the distribution between 6 points in the receipt of goods
                </td>
                <td>RMB 303</td>
            </tr>
            <tr>
                <td>South Korea / Japan / Singapore / Malaysia / Thailand / Taiwan / Hongkong
                </td>
                <td>
                    Monday to Friday, 9 a.m. to 3-7 days after the order of the distribution between 6 points in the receipt of goods
                </td>
                <td>RMB 243</td>
            </tr>
            <tr>
                <td>Russia / Egypt / Iceland</td>
                <td>
                    Monday to Friday, 9 a.m. to 3-7 days after the order of the distribution between 6 points in the receipt of goods
                </td>
                <td>RMB 471</td>
            </tr>
            <tr>
                <td>Elsewhere </td>
                <td>
                    Monday to Friday, 9 a.m. to 3-7 days after the order of the distribution between 6 points in the receipt of goods
                </td>
                <td>RMB 385</td>
            </tr>
            </tbody>
        </table>
        <ul class="second-title j_toggle2 hide tfoot">
            <li><p href="#">* Our delivery time from order time date, goods our warehouse will be processed within 24-48 hours and distribution for you. Please note that during our promotional period and statutory holidays, this period may be extended. <br/>
                * Some business address weekend may not receive shipment. In order to ensure that the weekend service, please check your shipping address. <br/>
                <span style="color: red">*Some of the goods (such as leather treasure) probably because of different customs policy in different countries not entry, please make sure to buy goods in line with national and customs regulations in the clearance process of the return, or reject the customs confiscated and other acts, we will not bear the corresponding responsibility.
</span>
            </p>
            </li>
        </ul>

        <p class="first-title j_toggle"><a href="#"></a>Whether the distribution to the nearest store?
        </p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">We provide customers with online offline boutique collection service, enjoy easy shopping fun.
                <br/>
                At present, this policy applies only to all stores in China (excluding Hong Kong, Macao and Taiwan)
            </p>
            </li>

        </ul>
        <p class="first-title j_toggle"><a href="#">Whether can the distribution to a plurality of address?
        </a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">At present, shop.shiatzychen.com can only select one address per delivery. If you want to distribute to multiple addresses, we suggest you fill out the order for each address separately </p>
            </li>
        </ul>

    </div>
    </c:if>
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
        $(function () {
            //常见问题页面JS
            //下拉菜单展开收起
            $(".j_toggle").on("click", function () {
                $(this).next().toggleClass("hide");
            });

            $(".j_toggle:first").on("click", function () {
                $(this).siblings(".tfoot").toggleClass("hide");
            });

            $(".j_toggle:not(:first)").on("click", function () {
                $(this).siblings(".tfoot").addClass("hide");
                $(this).siblings("table").addClass("hide");
            });

            $(".j_toggle2>li").on("click", function () {
                $(this).find(".answer").toggleClass("hide");
            });
        });
    </script>

