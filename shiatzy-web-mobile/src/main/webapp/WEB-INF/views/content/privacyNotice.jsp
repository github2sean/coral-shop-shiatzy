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
    <style>
        .dx-CommonProblems .content .first-title>a:after{content: ""}
        .dx-CommonProblems .content .first-title{padding-left: 0;font-size: 1.4rem;font-weight: bold;}
        .dx-CommonProblems .content .second-title li p{padding: 0;}
    </style>
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
        <h2 style="text-align: center;">隐私声明</h2>
        <p class="" style=" font-size: 14px"><a href="javascript:void(0)">感谢您的信任，我们了解您十分关心我们如何使用和分享您的个人信息。我们将谨慎小心地处理您的个人信息。 此声明描述了我们的隐私政策。访问网站表明您已经同意隐私声明中所描述的条例。</a></p>

        <p class="" style=" font-size: 14px"><a href="javascript:void(0)">本网站为SHIATZY CHEN（夏姿▪陈）品牌所属创姿服饰（上海）有限公司（以下称“我们”、“我们的”）所有并运营。 “在线服务”指在线精品店： ONLINEBOUTIQUE.SHIATZYCHEN.COM 、我们在包括 Instagram、Facebook、Twitter、Pinterest 、微博和微信在内的其它网站或app等第三方社交媒体平台上所运营的页面。“我们的服务”意指我们所提供的在线服务。如果您使用了我们的任何服务，我们将在这份政策中把您称为“用户”或者“访客”。</a></p>
        <p class="" style=" font-size: 14px"><a href="javascript:void(0)">我们尊重访客的隐私权，并清楚对收集到的有关访客信息进行保护的重要性。此份“隐私政策”将说明我们如何收集、存储和使用您提供给我们的个人信息。</a></p>
        <p style="margin-bottom: 20px;margin-top: 10px;font-size: 16px;font-weight: 100"><strong>此份隐私政策将向您解释 :</strong></p>
        <p class="first-title j_toggle"><a href="javascript:void(0)">我们收集哪些个人信息以及如何收集？</a></p>
        <ul class="second-title j_toggle2 ">
            <li><p href="#"> 当您注册或订阅注册我们提供的服务或是进行下列行为时，我们将会收集您的个人信息：下订单、会员账号登录、使用我们的在线服务或通过任何方式与我们互动（比如通过社交媒体、点击我们在本网站或是其它网站投放的广告、咨询信息或寻求帮助、在其它撰写与我们的服务相关的博客或文章给予评论或其它反馈、参与我们或赞助商举办的活动、参加抽奖活动或特别促销活动、订阅我们的优惠提醒或其它通知、参与调查小组或填写调查问卷等）。当您注册、订阅或使用我们的服务时，即表示您同意我们在此份政策的规定内使用您的个人信息。我们可能还会与我们有业务往来的第三方合作伙伴获取您的相关信息。</p>
            </li>
            <li><p href="#"> 在我们所收集并保存的个人信息中可能包括您的姓名、电子邮箱地址、Art Club会员账号与积分、电话号码、手机号码、家庭住址、配送和用于支付的银行卡账单地址、IP 地址、搜索条件、购物历史、购物偏好、尺码、对于调查小组和调查问卷的反馈、所使用的浏览器（浏览器是您用来浏览网站的程序，比如 Internet Explorer、Firefox 或 Safari）、您访问网站的次数和时长、引荐 url（连接您到我们网站的网址）、cell ID（当您在手机或平板电脑上使用包含定位服务的功能时手机所使用通讯塔的特别编码）和其它位置信息（比如GPS 测量）、照片和您在使用我们的服务时所共享的其它内容、出生日期、密码细节、安全问题答案以及其它您可能提供给我们的信息。</p>
            </li>
            <li><p href="#"> 我们可能会对拨打给您的电话或您拨打给我们的电话进行录音或监听。这是出于安全以及内部培训的目的，并旨在为您带来更好的服务。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="javascript:void(0)">我们将收集哪些类型的用户信息？</a></p>
        <ul class="second-title j_toggle2 ">
            <li><p href="#">从用户处了解的信息可帮助我们进行个性化定制服务，并持续不断地提高您的购物体验。 以下是我们所收集的信息类型。 您提供的信息: 我们将接收和储存您在我们网站输入或以任何方式给予我们的信息。 这些信息包括您通过搜索、加入愿望清单、购买、参与比赛或问卷调查或者与客服沟通提供的信息。 例如，您会在以下情况提供您的个人信息：搜索产品、在本网站下订单、在我的帐号内提供信息（如果您通过多个电子邮件地址购物，您可能拥有多个帐户）、通过Art Club会员账号线上认证、通过手机、电子邮件或其他方式与我们沟通；完成问卷调查或比赛报名表、编辑愿望清单或购物车。 由于上述行为，您可能会向我们提供以下信息，例如姓名、地址和电话号码等信息、银行卡信息、所购产品收货人信息（包括地址和电话号码）、您的朋友和其他人的电子邮件地址；以及评价和发送给我们的电子邮件的内容。 您可选择不提供特定信息，但这样您可能无法使用我们的许多功能。 我们将出于响应您的请求、为您量身打造今后的购物体验、改善我们的在线精品店以及与您沟通联络等此类目的而使用您所提供的信息。</p>
            </li>
            <li><p href="#">自动信息: 您与我们沟通时，我们将接收和储存特定类型的信息。 例如，像许多网页一样，我们使用“Cookie”，您使用网页浏览器访问本网站或在其它网站通过广告以及其它内容访问本网站时，我们将收到特定类型的信息。 单击此处查看我们所接收的信息示例。</p>
            </li>
            <li><p href="#">电子邮件沟通: 为帮助我们为您提供更有帮助和更让您感兴趣的电子邮件，若您的电脑支持这些功能，当您打开来自本网站的电子邮件时我们通常会收到确认。 我们还会将我们的客户名单与我们从其他公司处获得的名单进行比较，以避免向我们的客户发送不必要的邮件。 若您不希望收到电子邮件或者其他邮件，请调整您的电子邮件设置点击退订链接来退订邮件。
                具体参考市场营销订阅和退订 。</p>
            </li>
            <li><p href="#">来自其它来源的信息: 我们可能从其他资源获取关于您的信息，并添至我们的帐户信息。我们收到的来自其他来源的信息示例包括来自运输商和其他第三方的最新交付和地址信息，用于更正我们的记录信息，方便下一次订单发货或者是更顺畅地与您沟通；搜索结果和链接，包括付费列表；征信机构的信用历史信息，用于帮助我们预防和检测欺诈行为。</p>
            </li>

        </ul>
        <p class="first-title j_toggle"><a href="javascript:void(0)">我们会分享所收到的信息吗？</a></p>
        <ul class="second-title j_toggle2 ">
            <li><p href="#">客户信息是我们业务的重要组成部分，我们并不从事出售客户信息的业务。 我们仅会按照以下所述条件公司内与集团内分享客户信息，即接收方须遵守本隐私声明，或实施的实践规范至少可提供与本隐私声明所述同等程度的保护。</p>
            </li>
            <li><p href="#">不受控制的附属业务: 我们密切关注附属企业业务。 在某些情况下，这些企业会直接通过本网站向您销售产品和服务。 在某些情况下，我们也会通过与这些企业合作，共同销售产品线。 您可清楚辨别您的交易中何时会涉及第三方，而我们会与此第三方分享与这些交易有关的客户信息。</p>
            </li>
            <li><p href="#">第三方服务提供商:我们聘用其他公司和个人替我们执行业务功能。 例如履行订单、交付包裹、发送邮政信件和电子邮件、删除客户名单中的重复信息、分析数据、提供营销帮助、提供搜索结果和链接（包括付费列表和链接）、处理银行卡付款以及提供客户服务。 他们可访问执行其职能所需的个人信息，但不会出于其他目的而使用该信息。</p>
            </li>
            <li><p href="#">促销活动: 有时我们以其他企业的名义向特定组群的客户发送促销活动。 这种情况下，我们不会向该企业提供您的姓名和地址。 如果您不希望收到此类促销活动，请调整您的电子邮件设置点击退订链接来退订邮件。具体参考市场营销订阅和退订 。</p>
            </li>
            <li><p href="#">SHIATZY CHEN（夏姿▪陈）在线精品店网站的保护条例: 我们会在以下情况下发布帐户和其他个人信息，我们认为该发布：符合法律规定、执行或应用使用条款和其他协议、或保护本网站、我们的用户或其他的权利、财产或的安全。 这一做法包括出于防范欺诈和降低信用风险目的而与其他公司和机构交换信息的行为。 但是，这一做法显然不包括出于违反本隐私声明中所作承诺的商业目的而出售、租赁、分享或以其他方式披露来自客户的身份识别信息的行为。</p>
            </li>
            <li><p href="#">征得您的同意: 除了以上所述，当您的信息可能被发往第三方时，您将收到通知，您将有机会选择不共享该信息。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="javascript:void(0)">我的个人信息的安全性如何？</a></p>
        <ul class="second-title j_toggle2 ">
            <li><p href="#">我们使用安全套接层 (SSL) 软件来对您输入的信息进行加密，从而确保您的信息在传输过程中的安全性。
                确认订单时我们只会显示你的信用卡号码的最后四位数字。</p>
            </li>
            <li><p href="#">当然，我们会在订单处理过程中将完整的信用卡号提供给相应的银行卡公司。请务必保护您的密码和计算机避免遭到未经授权的访问。 确保在使用公用计算机后退出账户。</p>
            </li>
        </ul>

        <p class="first-title j_toggle"><a href="javascript:void(0)">如何查看和更新您的个人信息？可访问哪些信息？</a></p>
        <ul class="second-title j_toggle2 ">
            <li><p href="#">您有权查看并更新个人资料。如果出于任何原因您担心我们保留的个人信息不正确，请访问本网站。登录后，使用主页上的“登录”菜单，您的个人信息将显示在“我的帐号”内，供您查看和更改。只有您，或应您的请求，客户服务团队代表您，才可以使用您的密码和用户 ID 通过我们的在线服务查看您的个人数据。每次购物时您都可以更改或删除已保存的银行卡的详细信息。您也可以通过添加或编辑账单地址，删除已保存的信用卡/借记卡详细信息。如果您愿意，可以发送电邮至 customercare@shiatzychen.com 电话是：4008 213 760 与我们联系，我们将为您修改个人详细信息。</p>
            </li>
            <li><p href="#">本网站允许您访问有关您的账户以及您与本网站互动的广泛信息，仅限于让您浏览或者在需要时更新该信息。
            </p>
            </li>
        </ul>

        <p class="first-title j_toggle"><a href="javascript:void(0)">市场营销订阅和退订</a></p>
        <ul class="second-title j_toggle2 ">
            <li><p href="#">您可以随时订阅/退订我们的品牌资讯和电商信息。如果您参加了促销活动或者参加了由我们举办或赞助的活动，我们或者我们的第三方合作伙伴可能会询问您是否愿意订阅市场营销内容。</p>
            </li>
            <li><p href="#">您有权要求我们不将您的个人信息用于商业目的。如果您改变了主意，在任何时候，您都有机会退订已订阅的任何服务或提醒。在我们发送给您的任何直接营销类邮件中，您都能找到相关的退订方法。您也可以登录“我的帐号”更改您的订阅设置或者联系我们的客户服务团队：电话：4008 213 760（仅限中国(不含港澳台)用户）或者电邮至 customercare@shiatzychen.com 电话是：4008 213 760。
            </p>
            </li>
        </ul>

        <p class="first-title j_toggle"><a href="javascript:void(0)">隐私条款、通知及修改</a></p>
        <ul class="second-title j_toggle2 ">
            <li><p href="#">我们可能会不定期更新这份隐私政策。建议您在向我们提供个人信息或使用我们网站之前仔细检查。</p>
            </li>
            <li><p href="#">最近更新日期： 2015 年 6月 30 日</p>
            </li>
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
    <script>
        $(function(){
            //常见问题页面JS
            //下拉菜单展开收起
           /* $(".j_toggle").on("click",function () {
                $(this).siblings(".j_toggle").next().addClass("");
                $(this).next().toggleClass("");
            });
            $(".j_toggle2>li").on("click",function () {
                $(this).find(".answer").toggleClass("");
            });*/
        });
    </script>

