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
        <h2 style="text-align: center;">隐私声明</h2>
        <p class="" style=" font-size: 14px"><a href="javascript:void(0)">感谢您的信任，我们了解您十分关心我们如何使用和分享您的个人信息。我们将谨慎小心地处理您的个人信息。
            此声明描述了我们的隐私政策。访问网站表明您已经同意隐私声明中所描述的条例。</a></p>

        <p class="" style=" font-size: 14px"><a href="javascript:void(0)">本网站为SHIATZY
            CHEN（夏姿▪陈）品牌所属创姿服饰（上海）有限公司（以下称“我们”、“我们的”）所有并运营。 “在线服务”指在线精品店： ONLINEBOUTIQUE.SHIATZYCHEN.COM 、我们在包括
            Instagram、Facebook、Twitter、Pinterest
            、微博和微信在内的其它网站或app等第三方社交媒体平台上所运营的页面。“我们的服务”意指我们所提供的在线服务。如果您使用了我们的任何服务，我们将在这份政策中把您称为“用户”或者“访客”。</a></p>
        <p class="" style=" font-size: 14px"><a href="javascript:void(0)">我们尊重访客的隐私权，并清楚对收集到的有关访客信息进行保护的重要性。此份“隐私政策”将说明我们如何收集、存储和使用您提供给我们的个人信息。</a>
        </p>
        <p style="margin-bottom: 20px;margin-top: 10px;font-size: 16px;font-weight: 100"><strong>此份隐私政策将向您解释 :</strong>
        </p>
        <p class="first-title j_toggle"><a href="javascript:void(0)">我们收集哪些个人信息以及如何收集？</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">
                当您注册或订阅注册我们提供的服务或是进行下列行为时，我们将会收集您的个人信息：下订单、会员账号登录、使用我们的在线服务或通过任何方式与我们互动（比如通过社交媒体、点击我们在本网站或是其它网站投放的广告、咨询信息或寻求帮助、在其它撰写与我们的服务相关的博客或文章给予评论或其它反馈、参与我们或赞助商举办的活动、参加抽奖活动或特别促销活动、订阅我们的优惠提醒或其它通知、参与调查小组或填写调查问卷等）。当您注册、订阅或使用我们的服务时，即表示您同意我们在此份政策的规定内使用您的个人信息。我们可能还会与我们有业务往来的第三方合作伙伴获取您的相关信息。</p>
            </li>
            <li><p href="#"> 在我们所收集并保存的个人信息中可能包括您的姓名、电子邮箱地址、Art Club会员账号与积分、电话号码、手机号码、家庭住址、配送和用于支付的银行卡账单地址、IP
                地址、搜索条件、购物历史、购物偏好、尺码、对于调查小组和调查问卷的反馈、所使用的浏览器（浏览器是您用来浏览网站的程序，比如 Internet Explorer、Firefox 或
                Safari）、您访问网站的次数和时长、引荐 url（连接您到我们网站的网址）、cell ID（当您在手机或平板电脑上使用包含定位服务的功能时手机所使用通讯塔的特别编码）和其它位置信息（比如GPS
                测量）、照片和您在使用我们的服务时所共享的其它内容、出生日期、密码细节、安全问题答案以及其它您可能提供给我们的信息。</p>
            </li>
            <li><p href="#"> 我们可能会对拨打给您的电话或您拨打给我们的电话进行录音或监听。这是出于安全以及内部培训的目的，并旨在为您带来更好的服务。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="javascript:void(0)">我们将收集哪些类型的用户信息？</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">从用户处了解的信息可帮助我们进行个性化定制服务，并持续不断地提高您的购物体验。 以下是我们所收集的信息类型。 您提供的信息:
                我们将接收和储存您在我们网站输入或以任何方式给予我们的信息。 这些信息包括您通过搜索、加入愿望清单、购买、参与比赛或问卷调查或者与客服沟通提供的信息。
                例如，您会在以下情况提供您的个人信息：搜索产品、在本网站下订单、在我的帐号内提供信息（如果您通过多个电子邮件地址购物，您可能拥有多个帐户）、通过Art
                Club会员账号线上认证、通过手机、电子邮件或其他方式与我们沟通；完成问卷调查或比赛报名表、编辑愿望清单或购物车。
                由于上述行为，您可能会向我们提供以下信息，例如姓名、地址和电话号码等信息、银行卡信息、所购产品收货人信息（包括地址和电话号码）、您的朋友和其他人的电子邮件地址；以及评价和发送给我们的电子邮件的内容。
                您可选择不提供特定信息，但这样您可能无法使用我们的许多功能。 我们将出于响应您的请求、为您量身打造今后的购物体验、改善我们的在线精品店以及与您沟通联络等此类目的而使用您所提供的信息。</p>
            </li>
            <li><p href="#">自动信息: 您与我们沟通时，我们将接收和储存特定类型的信息。
                例如，像许多网页一样，我们使用“Cookie”，您使用网页浏览器访问本网站或在其它网站通过广告以及其它内容访问本网站时，我们将收到特定类型的信息。 单击此处查看我们所接收的信息示例。</p>
            </li>
            <li><p href="#">电子邮件沟通: 为帮助我们为您提供更有帮助和更让您感兴趣的电子邮件，若您的电脑支持这些功能，当您打开来自本网站的电子邮件时我们通常会收到确认。
                我们还会将我们的客户名单与我们从其他公司处获得的名单进行比较，以避免向我们的客户发送不必要的邮件。 若您不希望收到电子邮件或者其他邮件，请调整您的电子邮件设置点击退订链接来退订邮件。
                具体参考市场营销订阅和退订 。</p>
            </li>
            <li><p href="#">来自其它来源的信息:
                我们可能从其他资源获取关于您的信息，并添至我们的帐户信息。我们收到的来自其他来源的信息示例包括来自运输商和其他第三方的最新交付和地址信息，用于更正我们的记录信息，方便下一次订单发货或者是更顺畅地与您沟通；搜索结果和链接，包括付费列表；征信机构的信用历史信息，用于帮助我们预防和检测欺诈行为。</p>
            </li>

        </ul>
        <p class="first-title j_toggle"><a href="javascript:void(0)">我们会分享所收到的信息吗？</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">客户信息是我们业务的重要组成部分，我们并不从事出售客户信息的业务。
                我们仅会按照以下所述条件公司内与集团内分享客户信息，即接收方须遵守本隐私声明，或实施的实践规范至少可提供与本隐私声明所述同等程度的保护。</p>
            </li>
            <li><p href="#">不受控制的附属业务: 我们密切关注附属企业业务。 在某些情况下，这些企业会直接通过本网站向您销售产品和服务。 在某些情况下，我们也会通过与这些企业合作，共同销售产品线。
                您可清楚辨别您的交易中何时会涉及第三方，而我们会与此第三方分享与这些交易有关的客户信息。</p>
            </li>
            <li><p href="#">第三方服务提供商:我们聘用其他公司和个人替我们执行业务功能。
                例如履行订单、交付包裹、发送邮政信件和电子邮件、删除客户名单中的重复信息、分析数据、提供营销帮助、提供搜索结果和链接（包括付费列表和链接）、处理银行卡付款以及提供客户服务。
                他们可访问执行其职能所需的个人信息，但不会出于其他目的而使用该信息。</p>
            </li>
            <li><p href="#">促销活动: 有时我们以其他企业的名义向特定组群的客户发送促销活动。 这种情况下，我们不会向该企业提供您的姓名和地址。
                如果您不希望收到此类促销活动，请调整您的电子邮件设置点击退订链接来退订邮件。具体参考市场营销订阅和退订 。</p>
            </li>
            <li><p href="#">SHIATZY CHEN（夏姿▪陈）在线精品店网站的保护条例:
                我们会在以下情况下发布帐户和其他个人信息，我们认为该发布：符合法律规定、执行或应用使用条款和其他协议、或保护本网站、我们的用户或其他的权利、财产或的安全。
                这一做法包括出于防范欺诈和降低信用风险目的而与其他公司和机构交换信息的行为。
                但是，这一做法显然不包括出于违反本隐私声明中所作承诺的商业目的而出售、租赁、分享或以其他方式披露来自客户的身份识别信息的行为。</p>
            </li>
            <li><p href="#">征得您的同意: 除了以上所述，当您的信息可能被发往第三方时，您将收到通知，您将有机会选择不共享该信息。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="javascript:void(0)">我的个人信息的安全性如何？</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">我们使用安全套接层 (SSL) 软件来对您输入的信息进行加密，从而确保您的信息在传输过程中的安全性。
                确认订单时我们只会显示你的信用卡号码的最后四位数字。</p>
            </li>
            <li><p href="#">当然，我们会在订单处理过程中将完整的信用卡号提供给相应的银行卡公司。请务必保护您的密码和计算机避免遭到未经授权的访问。 确保在使用公用计算机后退出账户。</p>
            </li>
        </ul>

        <p class="first-title j_toggle"><a href="javascript:void(0)">如何查看和更新您的个人信息？可访问哪些信息？</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">
                您有权查看并更新个人资料。如果出于任何原因您担心我们保留的个人信息不正确，请访问本网站。登录后，使用主页上的“登录”菜单，您的个人信息将显示在“我的帐号”内，供您查看和更改。只有您，或应您的请求，客户服务团队代表您，才可以使用您的密码和用户
                ID 通过我们的在线服务查看您的个人数据。每次购物时您都可以更改或删除已保存的银行卡的详细信息。您也可以通过添加或编辑账单地址，删除已保存的信用卡/借记卡详细信息。如果您愿意，可以发送电邮至
                customercare@shiatzychen.com 电话是：4008 213 760 与我们联系，我们将为您修改个人详细信息。</p>
            </li>
            <li><p href="#">本网站允许您访问有关您的账户以及您与本网站互动的广泛信息，仅限于让您浏览或者在需要时更新该信息。
            </p>
            </li>
        </ul>

        <p class="first-title j_toggle"><a href="javascript:void(0)">市场营销订阅和退订</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">您可以随时订阅/退订我们的品牌资讯和电商信息。如果您参加了促销活动或者参加了由我们举办或赞助的活动，我们或者我们的第三方合作伙伴可能会询问您是否愿意订阅市场营销内容。</p>
            </li>
            <li><p href="#">
                您有权要求我们不将您的个人信息用于商业目的。如果您改变了主意，在任何时候，您都有机会退订已订阅的任何服务或提醒。在我们发送给您的任何直接营销类邮件中，您都能找到相关的退订方法。您也可以登录“我的帐号”更改您的订阅设置或者联系我们的客户服务团队：电话：4008
                213 760（仅限中国(不含港澳台)用户）或者电邮至 customercare@shiatzychen.com 电话是：4008 213 760。
            </p>
            </li>
        </ul>

        <p class="first-title j_toggle"><a href="javascript:void(0)">隐私条款、通知及修改</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">我们可能会不定期更新这份隐私政策。建议您在向我们提供个人信息或使用我们网站之前仔细检查。</p>
            </li>
            <li><p href="#">最近更新日期： 2015 年 6月 30 日</p>
            </li>
        </ul>
    </div>
    </c:if>
    <c:if test="${web:selectLanguage()=='en_US'}">
        <div class="content">
            <h2 style="text-align: center;">Privacy </h2>
            <p class="" style=" font-size: 14px"><a href="javascript:void(0)">Thank you for your trust, we know you are very concerned about how we use and share your personal information. We will deal with your personal information carefully. This statement describes our privacy policy. Visit the website that you have agreed to describe the privacy statement in the regulations.
            </a></p>

            <p class="" style=" font-size: 14px"><a href="javascript:void(0)">This site is SHIATZY Chen (Xiazi after Chen) brand belongs to a garment (Shanghai) Co., Ltd. position (hereinafter referred to as "we", "us") and all operations. "Online service" refers to the online boutique: onlineboutique.shiatzychen.com, we include instagram, Facebook, twitter, pinterest, the operation of other sites or app and three party social media platform of micro-blog and WeChat, on the page. "Our service" means we provide online services. If you use any of our services, we will give you call "user" or "visitors" in this policy.
            </a></p>
            <p class="" style=" font-size: 14px"><a href="javascript:void(0)">We respect the privacy of visitors, and the importance of clear visitor information collected are protected. The "privacy policy" will show us how to collect, store and use the personal information you provide to us.
            </a>
            </p>
            <p style="margin-bottom: 20px;margin-top: 10px;font-size: 16px;font-weight: 100"><strong>This privacy policy will explain to you: </strong>
            </p>
            <p class="first-title j_toggle"><a href="javascript:void(0)">What personal information we collect and how to collect?
            </a></p>
            <ul class="second-title j_toggle2 hide">
                <li><p href="#">
                    When you register or subscription registration service we provide or make the following acts, we will collect your personal information: order member account login, we use the online service or interact with us by any means (such as through social media, we click on this website or other website advertising, consulting information or seek help in other writing related to our blog service articles or give comments or other feedback, we participate in or sponsor activities, participate in sweepstakes or special promotions, subscribe to our preferential reminder or other notice, to participate in the investigation team or fill in the questionnaire). When you register, or subscription use our services, you are agreeing to use your personal information in this part of provisions of our policy. We may also have business dealings with us third Your partner to obtain relevant information.
                </p>
                </li>
                <li><p href="#">We are in the collection and preservation of personal information may include your name, email address, art club member account and integral, telephone number, mobile phone number, home address, distribution and payment for bank card billing address, IP address, search conditions, shopping history, shopping preferences, size for feedback survey group and questionnaire, the use of the browser (browser is used to browse your site procedures, such as Internet, explorer, Firefox or Safari), the number and length of your visit to the site, introduce URL (your connection to our web site), cell ID (especially when you use the encoding function contains the location service in the mobile phone or tablet computer on the use of mobile phone communication tower) and other location information (such as the GPS measurement), photos and other content, you are using our services shared by birth date, password details, security. You may answer questions and other information available to us.
                </p>
                </li>
                <li><p href="#">We may call on your phone or you call our telephone recording or listening. This is for security and internal training, and to provide you with better service.
                </p>
                </li>
            </ul>
            <p class="first-title j_toggle"><a href="javascript:void(0)">We will collect what types of user information?
            </a></p>
            <ul class="second-title j_toggle2 hide">
                <li><p href="#">Learn from the user's information can help us to customize services, and continuously improve your shopping experience. The following is the type of information we have collected. The information you provide: we will receive and store your input on our website or in any way to give us information. This information includes your search through, join the wish list, purchase, to participate in the competition or surveys or provide communication and customer service information. For example, you can provide your personal information in the following situations: search product, on the site under the orders, to provide information on my account (if your email address through a number of shopping, you may have multiple accounts art club), through membership account online authentication through mobile phone, email or other means to communicate with us; to complete the survey or competition, edit the wish list or shopping cart Because of the above behavior, you may provide the following information to us, such as your name, address and phone number and other information, bank card information, the purchase of the product information (including the consignee address and telephone number), your friends and other people's email address; and content evaluation and send us e-mail. You can choose not to provide specific information, but you may not be able to use our many features. We will be in response to your request, tailored to your future shopping experience, improve our online boutique and your communication and liaison purposes and use the information you provide.
                </p>
                </li>
                <li><p href="#">Automatic information: you communicate with us, we will receive and store certain types of information. For example, as many web pages, we use the "cookie", you use a web browser to visit this website or visit this website at other sites through advertising and other content, we will receive a particular type of information. Click here to view we have received the sample information.
                </p>
                </li>
                <li><p href="#">Email communication: to help us to provide more help for you and make you more interested in the email, if your computer supports these functions, when you open the email from this site when we usually receive confirmation. We will be our customer list and compare with our company to obtain from the other list, in order to avoid to send our customers unnecessary mail. If you do not wish to receive e-mail or other mail, please adjust your email settings click unsubscribe link to unsubscribe. Refer to marketing subscribe and unsubscribe.
                </p>
                </li>
                <li><p href="#">From other sources of information: we may obtain information about you from other sources, and add to our account information. We have received from other sources of information include transportation and other third parties from taking the latest delivery and address information for correct record of our information, convenient next order delivery or it is better to communicate with you; search results and links, including pay list; credit history information of credit institutions, to help us to prevent and detect fraud.
                </p>
                </li>

            </ul>
            <p class="first-title j_toggle"><a href="javascript:void(0)">We will share the information received?
            </a></p>
            <ul class="second-title j_toggle2 hide">
                <li><p href="#">Customer information is an important part of our business, we are not engaged in the sale of customer information service. We will only share customer information in accordance with the following conditions within the company and the group, i.e. the receiver must comply with the privacy statement, or the implementation of the code of practice at least can provide protection and the privacy statement of the same level.
                </p>
                </li>
                <li><p href="#">The affiliated business out of control: we pay close attention to the affiliated business. In some cases, these enterprises will be sold directly to you through this website products and services. In some cases, we will be through cooperation with these enterprises, joint sales product line. You can clearly identify you when will the transaction involving third party, and we will share with the third party transactions associated with these customer information.
                </p>
                </li>
                <li><p href="#">The third party service provider: we employ other companies and individuals to perform functions on our behalf. For example, the execution of the order, delivery of postal parcels, sending letters and emails, repeat information, delete the list of customers in the analysis of data, provide marketing help, providing search results and links (including the payment list and links), and provide the bank card payment processing customer service. They can access the required to perform the functions of personal information, but not for other purposes and to use the information.
                </p>
                </li>
                <li><p href="#">Promotion: sometimes we in the name of other enterprises to specific groups of customers to send promotional activities. In this case, we will not provide your name and address to the enterprise. If you do not wish to receive such promotions, please adjust your email settings click unsubscribe link to unsubscribe. Refer to marketing and subscription unsubscribe.
                </p>
                </li>
                <li><p href="#">SHIATZY Chen (Xiazi after Chen) Regulations on the protection of online boutique website: we will publish accounts and other personal information in the following circumstances, we think the issue: in accordance with the law, perform or application of the terms of use and other agreements, or the protection of the site, we use households or other property rights, or the safety. This approach includes for the prevention of fraud and reduce credit risk to exchange information with other companies and agencies. However, this approach is clearly not included for violation of commitments made in this privacy statement for business purposes for sale, lease, share or disclose the identity information from customers in other ways behavior.
                </p>
                </li>
                <li><p href="#">Your permission: in addition to the above, when your information may be sent to the third party, you will receive notice, you will have the opportunity to choose not to share the information.
                </p>
                </li>
            </ul>
            <p class="first-title j_toggle"><a href="javascript:void(0)">How safe is my personal information?
            </a></p>
            <ul class="second-title j_toggle2 hide">
                <li><p href="#">We use secure sockets layer (SSL) software to enter your information is encrypted, so as to ensure the safety of your information in the transmission process. To confirm the order we will display only the last four digits of your credit card number.
                </p>
                </li>
                <li><p href="#">Of course, we will provide the corresponding bank card company in order to deal with in the process of complete credit card. Please be sure to protect your computer passwords and avoid unauthorized access. To exit the account in the use of public computer.
                </p>
                </li>
            </ul>

            <p class="first-title j_toggle"><a href="javascript:void(0)">How to view and update your personal information can be accessed? What information?
            </a></p>
            <ul class="second-title j_toggle2 hide">
                <li><p href="#">
                    You have the right to check and update personal data. If for any reason you are worried that we retain personal information is not correct, please visit the website. After login page, use the "login" menu, your personal information will be displayed in the "my account", for you to view and change. Only you, or you should request the customer service team on behalf of you, you can use the password and the user ID to view your personal data through our online shopping service. Every time when you can change or delete the saved information with bank card. You can add or edit the billing address, delete saved credit card / debit card details. If you like, can send email to the customercare@shiatzychen.com telephone number is: 4008213760 to contact us, we will modify the detailed personal information for you.
                </p>
                </li>
                <li><p href="#">This site allows you to access your account and your extensive information to this website and interactive, only allows you to browse or update the information in need.

                </p>
                </li>
            </ul>

            <p class="first-title j_toggle"><a href="javascript:void(0)">Marketing subscribe and unsubscribe
            </a></p>
            <ul class="second-title j_toggle2 hide">
                <li><p href="#">You can subscribe / unsubscribe from our brand information and business information. If you participate in promotional activities or participated in or sponsored activities organized by our partners, we or our third party may ask you if you want to subscribe to the marketing content.
                </p>
                </li>
                <li><p href="#">
                    You have the right to ask us not to use your personal information for commercial purposes. If you change your mind, at any time, you have the opportunity to unsubscribe subscribed any service or reminder. We send in any direct marketing of your mail, you can find the relevant method unsubscribe you can. Login "my account" change your subscription settings or contact our customer service team: Tel: 4008213760 (only Chinese (excluding Hong Kong) users) or email to customercare@shiatzychen.com phone number is 4008213760.

                </p>
                </li>
            </ul>

            <p class="first-title j_toggle"><a href="javascript:void(0)">Privacy provisions, notices and modify </a></p>
            <ul class="second-title j_toggle2 hide">
                <li><p href="#">We may not update this Privacy Policy periodically. We recommend that you provide us with personal information or check carefully before using our website.
                </p>
                </li>
                <li><p href="#">Last updated: June 30, 2016 </p>
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
                $(this).siblings(".j_toggle").next().addClass("hide");
                $(this).next().toggleClass("hide");
            });
            $(".j_toggle2>li").on("click", function () {
                $(this).find(".answer").toggleClass("hide");
            });
        });
    </script>
