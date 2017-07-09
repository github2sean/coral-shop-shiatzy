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
    <%-- <div class="content">
         <p class="first-title j_toggle"><a href="#">退货政策</a></p>
         <ul class="second-title j_toggle2 hide">
             <li><p href="#"> 1. 商品必须在收到后7天以内退回。超过此期限的退货将由SHIATZY CHEN （夏姿·陈）决定是否接受。</p>
             </li>
             <li><p href="#"> 2. 商品必须未经穿着、修改、洗涤或破损。</p>
             </li>
             <li><p href="#"> 3. 退货商品必须保留所有标签并以原包装退回。</p>
             </li>
             <li><p href="#"> 4．退货商品必须随附开具发票（若要求），否则可能会导致延迟退款或不符合退款条件。5. 商品必须以原始状态放置于原装盒内。如果所退原装盒受损或非原装盒，我们将收取相应的礼盒费用人民币100元。</p>
             </li>
             <li><p href="#"> 5. 请注意:发货、配送费用及关税（如产生）均不可退款。货到付款的包裹会被拒收或者货到付款的金额可能会从您的退款金额中扣除。</p>
             </li>
             <li><p href="#"> 6. 标注特惠商品（不可退换）不支持退货。  </p>
             </li>
             <li><p href="#"> 7. 请注意：线下门店与线上精品店的退货政策有所不同，如您选择的精品店预约功能线下支付购买的商品，将不享有7天无理由退货，退货条款遵循线下门店政策。  </p>
             </li>
             <li><p href="#"> 8. 部分商品（珍稀皮革类等）可能因为各国家海关政策不予入境，请您确保购买的商品符合本国及海关条例，在清关过程中发生的退货、拒收或者海关没收等行为，我们不会承担相应责任和费用赔偿。  </p>
             </li>
         </ul>
         <p class="first-title j_toggle"><a href="#">换货政策</a></p>
         <ul class="second-title j_toggle2 hide">
             <li><p href="#">为使您享受到最高效率的优质服务，我们建议您对原订单直接进行退货操作。<br/>
                 您只需再下一份订单即可以最快速度获得正确尺码、颜色的商品。一旦我们收到您的退货，就会将退款金额于最多10个工作日内退回到您的原支付卡内。</p>
             </li>

         </ul>
         <p class="first-title j_toggle"><a href="#">接受退款</a></p>
         <ul class="second-title j_toggle2 hide">
             <li><p href="#">中国境内的所有退货，将根据您在退货时提交的银行卡信息，您的退款将以转账形式退还至您的银行卡中。<br/>
                 国际订单的所有退货，退款将会原卡原退。<br/>
                 请注意，鉴于不同支付方式所需的处理时间不同，退款金额将在10个工作日内退至您的账户。</p>
             </li>
         </ul>
         <p class="first-title j_toggle"><a href="#">库存异常订单的处理办法</a></p>
         <ul class="second-title j_toggle2 hide">
             <li><p href="#">夏姿·陈（SHIATZY CHEN）在线精品店（Shop.shiatzychen.com）一直致力于为消费者提供最优质的服务和线上购物体验，但在无可避免的少数情况下，夏姿·陈（SHIATZY CHEN）在线精品店（Shop.shiatzychen.com）在接受您的订单后，有可能会出现无货或无法配送的情况，致使订单不能继续履行完成配送作业。<br/>
                 针对该种情况，您已支付的货款我们会和您确认后，以转账或在线原路返回的方式全额无息退款给您，并考虑到您的购物感受，我们会按照您的实付价格（不含邮费）的10%（每笔最高不超过人民币500元）对您进行补偿，该补偿金将以优惠券代码的形式发送到您的注册邮箱中，在购物中可进行直接消费抵扣。</p>
             </li>
         </ul>

     </div>--%>
    <div class="content">

        <c:forEach var="itemDomain" items="${domainList}">
            <c:if test="${itemDomain.title !=null}">
                <p class="first-title j_toggle"><a
                        href="#">${web:selectLanguage()=='en_US'?itemDomain.en_title:itemDomain.title}</a></p>
            </c:if>
            <ul class="second-title j_toggle2 hide">
                <c:forEach var="domainlist" items="${itemDomain.contentItemDomainList}">
                    <li class="j_collapse">
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
        $(function () {
            //常见问题页面JS
            //下拉菜单展开收起
            $(".j_toggle").on("click", function () {
                $(this).next().toggleClass("hide");
            });

            $(".j_toggle2>li").on("click", function () {
                $(this).find(".answer").toggleClass("hide");
            });
        });
    </script>

