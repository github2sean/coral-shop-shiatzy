<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<a href="javascript:;" id="j_back_top" class="back-top"><spring:message code="toTop"/></a>
<footer class="footer font-12">
    <ul class="do-list-btm j_drop_down">
        <li><a href="/u/account/index"><spring:message code="myAccount"/></a></li>
        <li><a href="/content/faq"><spring:message code="commonQuestion"/></a></li>
        <li><a href=""><spring:message code="specialService"/></a></li>
        <li>
            <a href="javascript:;"><spring:message code="aboutUs"/></a>
            <ul class="do-sub-list-btm">
                <li><a href=""><spring:message code="officialSite"/></a></li>
                <li><a href=""><spring:message code="contactUs"/></a></li>
                <li><a href=""><spring:message code="privacyPolicy"/></a></li>
            </ul>
        </li>
    </ul>
    <ul class="do-list-lang do-list-btm j_drop_down">
        <li><a href="/home/listShippingCountry"><spring:message code="selectOtherCountriesORRegions"/></a></li>
        <li><a href="#"><spring:message code="selectLanguage"/></a>
            <ul class="do-sub-list-btm">
                <li><a href="?lang=zh_CN" style="text-decoration: underline"><spring:message code="language.cn" /></a></li>
                <li><a href="?lang=en_US" style="text-decoration: underline"><spring:message code="language.en" /></a></li>
            </ul>
        </li>
    </ul>
    <div class="do-subscribe-w">
        <p><spring:message code="subscribeForUpdate"/></p>
        <div class="do-subscribe-from">
            <form action="">
                <input type="text" placeholder="<spring:message code="inputEmail"/>" class="do-fill-email"onfocus="this.placeholder=''" onblur="this.placeholder='输入您的电子邮箱'">
                <button class="do-btn-subscribe"><spring:message code="subscribe"/></button>
            </form>
        </div>
    </div>
    <div class="do-online-service">
        <p class="do-online-t"><spring:message code="customerServiceLine"/></p>
        <p><spring:message code="chinaPhone"/> 4008 213 760</p>
        <p>(<spring:message code="chinaOnly"/>)</p>
        <p><spring:message code="onlineTime"/></p>
        <p class="mt-1"><spring:message code="onlineEmail"/></p>
        <p>(<spring:message code="allRegions"/>)</p>
    </div>
    <div class="text-center do-copy"><spring:message code="ending"/></div>
</footer>
</div>
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
<!-- 页面插件结束 -->
<!-- js页面应用 开始 -->
<script>
    $(function () {
        commonApp.init();
        backendApp.init();
    });
</script>
<script>
    $(function () {
        $("#searchBtn").click(function () {
            $("#contentForm").submit();
        });
    });

</script>
<!-- js页面应用 结束 -->
</body>
</html>