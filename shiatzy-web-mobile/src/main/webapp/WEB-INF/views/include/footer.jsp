<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="javascript:;" id="j_back_top" class="back-top"><spring:message code="toTop"/></a>
<footer class="footer font-12">
    <ul class="do-list-btm j_drop_down">
        <li><a href="/u/account/index"><spring:message code="myAccount"/></a></li>
        <%--<li><a href="/content/faq?id="><spring:message code="commonQuestion"/></a></li>--%>
        <c:forEach var="item" items="${domainList}">
            <c:if test="${item.level==1 && item.isIndex == 1}">
            <li><a href="/content/faq?id=${item.id}">${sessionScope.language=='en_US'?item.en_title:item.title}</a></li>
            </c:if>
        </c:forEach>
        <li><a href="/content/specialService"><spring:message code="specialService"/></a></li>
        <li>
            <a href="javascript:;"><spring:message code="aboutUs"/></a>
            <ul class="do-sub-list-btm">
                <li><a href="http://brand.shiatzychen.com/"><spring:message code="officialSite"/></a></li>
                <li><a href="#"><spring:message code="contactUs"/></a></li>
                <li><a href="#" class="privacyNotice"><spring:message code="privacyPolicy"/></a></li>
            </ul>
        </li>
    </ul>
    <ul class="do-list-lang j_drop_down">
        <li><a href="/home/listShippingCountry"><spring:message code="selectOtherCountriesORRegions"/></a></li>
        <li><a href="javascript:void(0);"><spring:message code="selectLanguage"/></a>
            <ul class="do-sub-list-btm">
                <li><a class="language" data-value="zh_CN" href="javascript:void(0);" style="text-decoration: underline"><spring:message code="language.cn" /></a></li>
                <li><a class="language" data-value="en_US" href="javascript:void(0);" style="text-decoration: underline"><spring:message code="language.en" /></a></li>
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
        <%--关闭提示--%>
        $(".j_cls_notice").on("click touchstart",function(){
            $(this).parent().fadeOut();
        })
    });
</script>
<script>
    function setCartNum() {
        var nowNum =0;
        var cartNum;

        //判断是否登录
        $.post("/cart/getCartNum",function (data) {
            if (data.code==200){
                cartNum = data.data;
                $(".cart_num").text(cartNum);
            }else{
               // layer.msg('更新购物车数量失败');
            }
        });
        /*$.post("/cart/freshCartNum",{"num":nowNum},function (data) {
            if (data.code!=200){
                layer.msg('更新购物车数量失败');
            }
        });*/
    }
    $(function () {
        setCartNum();

        $("#searchBtn").click(function () {

           var key = $("#searchKey").val();
            if(key==''){
                layer.msg("请输入搜索关键字");
                return false;
            }
            $("#contentForm").submit();
        });
        console.log('${sessionScope.language}');
        $(".language").click(function () {
            var language = $(this).attr("data-value");
            $.post("/home/selectLanguage",{"nowLanguage":language},function (data) {
                console.log(data);
                if(data.code==200){
                    var old = location.search;

                    console.log("old:"+old +" "+old.indexOf("?lang="))
                    if(old==''){
                        old = old  +"?lang="+language;
                    }else if(old.indexOf("?lang=")!=-1){
                       old = "?lang="+language;
                        console.log(old);
                    }else if(old.indexOf("&lang=")!=-1){
                        old = old.substr(0,old.indexOf("&lang="));
                        old = old+"&lang="+language;
                    }else{
                        old = old+"&lang="+language;
                    }
                    location.href = old;
                }
            });
        });

        $(".do-btn-subscribe").click(function () {

            var email = $(".do-fill-email").val();
            var reg =/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
            if(email==''){
                layer.msg("请输入邮件地址");
                return false;
            }else if(!reg.test(email)){
                layer.msg("请输入正确的邮件地址");
                return false;
            }

        });


        //iframe窗
        $(".privacyNotice").click(function(){
            layer.open({
                type: 2,
                title: '<spring:message code="privacyPolicy"/>',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                area: ['100%', '80%'],
                content: ['${ctx}/content/privacyNotice'],//iframe的url，no代表不显示滚动条
                shadeClose: true
            });
        });

    });

</script>
<!-- js页面应用 结束 -->
</body>
</html>