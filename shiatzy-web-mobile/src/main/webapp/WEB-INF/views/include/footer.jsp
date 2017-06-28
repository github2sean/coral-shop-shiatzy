<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<a href="javascript:;" id="j_back_top" class="back-top"><spring:message code="toTop"/></a>
<footer class="footer font-12">
    <ul class="do-list-btm">
        <li><a href="/u/account/index"><spring:message code="myAccount"/></a></li>
        <%--<li><a href="/content/faq?id="><spring:message code="commonQuestion"/></a></li>--%>
        <c:forEach var="item" items="${domainList}">
            <c:if test="${item.level==1 && item.isIndex == 1}">
            <li><a href="/content/faq?id=${item.id}">${web:selectLanguage()=='en_US'?item.en_title:item.title}</a></li>
            </c:if>
        </c:forEach>
        <li><a href="/content/specialService"><spring:message code="specialService"/></a></li>
    </ul>
    <ul class="do-list-btm j_drop_down">
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
        <li>
            <a href="/home/listShippingCountry" id="trigger_select_country">
                <select id="select_country" style="width: 100%;background: transparent;border: 0;font-size: 1.1rem;">
                    <option><spring:message code="selectOtherCountriesORRegions"/></option>
                    <c:forEach var="country" items="${web:countryList()}" begin="0">
                        <option value="${country.id}">${web:selectLanguage()=='en_US'?country.enName:country.name}</option>
                    </c:forEach>
                </select></a>
        </li>
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
                <input type="text" style="width: 64%" placeholder="<spring:message code="inputEmail"/>" class="do-fill-email"onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message code="inputEmail"/>'">
                <button class="do-btn-subscribe"><spring:message code="subscribe"/></button>
            </form>

        </div>
        <p id="subscribe_msg"></p>
    </div>
    <a name="do-online-service">
        <div class="do-online-service"  >
        <p class="do-online-t"><spring:message code="customerServiceLine"/></p>
        <p><spring:message code="chinaPhone"/> 4008 213 760</p>
        <p>(<spring:message code="chinaOnly"/>)</p>
        <p><spring:message code="onlineTime"/></p>
        <p class="mt-1"><spring:message code="onlineEmail"/></p>
        <p>(<spring:message code="allRegions"/>)</p>
    </div>
    </a>
    <div class="text-center do-copy"><spring:message code="ending"/></div>
</footer>
</div>
</div>

<!--选择国家或地区-->
<div class="country-select" id="country-select" style="display: none">
    <h3 class="country-title">选择国家或地区</h3>
    <ul class="country-con" style="">
        <c:forEach var="row" items="${web:countryList()}">
            <li><a href="#"  data-value="${row.id}">${web:selectLanguage()=='en_US'?row.enName:row.name}</a></li>
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
<script src="${ctx}/static/js/jquery.formatCurrency-1.4.0.js"></script>
<c:if test="${web:selectLanguage()=='en_US'}">
    <script src="${ctx}/static/js/plugins/validator/local/en_US.js"></script>
</c:if>
<c:if test="${web:selectLanguage()!='en_US'}">
    <script src="${ctx}/static/js/plugins/validator/local/zh-CN.js"></script>
</c:if>
<script src="${ctx}/static/js/backend.js"></script>
<!-- 页面插件结束 -->

<!-- 轮播 开始 -->
<script src="https://cdn.bootcss.com/bxslider/4.2.12/jquery.bxslider.min.js"></script>

<!-- 轮播 结束 -->

<!-- js页面应用 开始 -->
<script>
    $(function () {
        commonApp.init();
        backendApp.init();
        <%--关闭提示--%>
        $(".j_cls_notice").on("click touchstart",function(){
            $(this).parent().fadeOut();
        })
        $("#trigger_select_country").click(function (e) {
            e.preventDefault();
            $("#select_country").trigger("change");
        })
    });
</script>
<script>

    function fmoney(s, n) {
        var parma = n;
        n = n > 0 && n <= 20 ? n : 2;
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
        t = "";
        for (i = 0; i < l.length; i++) {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
        }
        if(parma==0){
            return t.split("").reverse().join("");
        }else{
            return t.split("").reverse().join("") + "." + r;
        }
    }
    function rmoney(s) {
        return parseFloat(s.replace(/[^\d\.-]/g, ""));
    }

    function setPrice() {
        $.post("/home/queryCurrentRate",function (data) {
            var coinSymbol = '￥';
            var rate = 1;
            if (data.code==200){
                var obj = data.data;
                var type = obj.rateType;
                console.log("type2"+type);
                if(obj.rateType == 1){
                    coinSymbol = '<spring:message code="coin.USA"/>';
                }else if(obj.rateType == 2){
                    coinSymbol = '<spring:message code="coin.EU"/>';
                }
                rate = obj.rate;
                console.log("rate1:"+rate);
            }

            console.log("rate2:"+rate);
            $(".do-pro-price").each(function () {
                var oldPri = $(this).attr("data-value")*1;
                $(this).text(coinSymbol+" "+fmoney((oldPri/rate).toFixed(0),0)).attr("data-rate",rate);

            }) ;
            $(".only-price").each(function () {
                var oldPri = $(this).text()*1;
                $(this).text(fmoney((oldPri/rate).toFixed(0),0)).attr("data-rate",rate);
            }) ;

            $(".coinSymbol").text(coinSymbol);
        });
    }
    function queryCurrentRate() {
        $.post("/home/queryCurrentRate",function (data) {
            if (data.code==200){
               var country = data.data;
                var json = JSON.parse(data);
                console.log(json.rate);
                return data.data.rate;
            }
        });
    };
    function setCartNum() {
        var nowNum =0;
        var cartNum;
        //判断是否登录
        if('${isGuest}'!='onLine'){
            $.post("/cart/querySessionCartNum",{"type":1},function (data) {
                if (data.code==200){
                    cartNum = parseInt(data.data);
                    if(cartNum>0){
                        $(".cart_num").removeClass("hide");
                    }

                    $(".cart_num").text(cartNum);
                    console.log("cart:"+cartNum);
                }else{
                    // layer.msg('更新购物车数量失败');
                }
            });
            $.post("/cart/querySessionCartNum",{"type":3},function (data) {
                if (data.code==200){
                    cartNum = parseInt(data.data);
                    if(cartNum>0){
                        $(".boutique_num").removeClass("hide");
                    }

                    $(".boutique_num").text(cartNum);
                    console.log("boutique:"+cartNum)
                }else{
                    // layer.msg('更新购物车数量失败');
                }
            });
        }else{
            $.post("/cart/getCartNum",{"type":1},function (data) {
                if (data.code==200){
                    cartNum = data.data;
                    if(cartNum>0){
                        $(".cart_num").removeClass("hide");
                    }
                    $(".cart_num").text(cartNum);
                    console.log("cart:"+cartNum);
                }else{
                    // layer.msg('更新购物车数量失败');
                }
            });
            $.post("/cart/getCartNum",{"type":3},function (data) {
                if (data.code==200){
                    cartNum = data.data;
                    if(cartNum>0){
                        $(".boutique_num").removeClass("hide");
                    }
                    console.log("bou:"+cartNum);
                    $(".boutique_num").text(cartNum);console.log(cartNum);
                }else{
                    // layer.msg('更新购物车数量失败');
                }
            });
        }
    }

    $('.slider').bxSlider({
        pager:false,
        controls:false,
        auto:true
    });

    $(function () {

        $("#j_back_top").click(function () {
            var speed=500;//滑动的速度
            $('body,html').animate({ scrollTop: 0 }, speed);
            return false;
        });
        setCartNum();
        $("#searchBtn").click(function () {

           var key = $("#searchKey").val();
            if(key==''){
                layer.msg("请输入搜索关键字");
                return false;
            }
            $("#contentForm").submit();
        });
        console.log("sessionScopeLanguage:"+'${sessionScope.language}'+"  cookieLanguage:"+'${web:selectLanguage()}');
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
                $("#subscribe_msg").text("请输入邮件地址");
                return false;
            }else if(!reg.test(email)){
                $("#subscribe_msg").text("请输入正确的邮件地址");
                return false;
            }else{
                $.post("/common/subscribe",{"email":email},function (data) {
                    if (data.code==200){
                        $("#subscribe_msg").text("已成功订阅！");
                    }else{
                        $("#subscribe_msg").text(data.message);
                    }
                });
            }
            return false;
        });


        //iframe窗
        $(".privacyNotice").click(function(){
            layer.open({
                type: 2,
                title: '<spring:message code="privacyPolicy"/>',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                area: ['100%', '100%'],
                content: ['${ctx}/content/privacyNotice'],//iframe的url，no代表不显示滚动条
                shade: [0.3,'#000'], //0.1透明度的白色背景

                success: function(layero, index){
                    $('html').css("height","100%").css("overflow","hidden");
                    $('body').css("height","100%").css("overflow","hidden");
                },
                end:function () {
                    $('html').css("height","auto").css("overflow","auto");
                    $('body').css("height","auto").css("overflow","auto");
                }
            });
        });

        //弹出层
        $(".country-select").find("li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
            var id =  $(this).find("a").attr("data-value");
            $.post("/home/chooseShippingCountry",{"shippingCountryId":id},function (data) {
                if(data.code==200){
                    var index = layer.index; //获取当前弹层的索引号
                    layer.close(index); //关闭当前弹层
                }
            });
        });

        //退货政策
        $(".returnOrchange").click(function () {
            layer.open({
                type: 2,
                title: '退货政策',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                area: ['100%', '100%'],
                content: ['${ctx}/content/returnOrchange'],//iframe的url，no代表不显示滚动条
                shade: [0.3,'#000'], //0.1透明度的白色背景

                success: function(layero, index){
                    $('html').css("height","100%").css("overflow","hidden");
                    $('body').css("height","100%").css("overflow","hidden");
                },
                end:function () {
                    $('html').css("height","auto").css("overflow","auto");
                    $('body').css("height","auto").css("overflow","auto");
                }
            });
        })
    });


    //滚动条在Y轴上的滚动距离
    function getScrollTop(){
        var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
        if(document.body){
            bodyScrollTop = document.body.scrollTop;
        }
        if(document.documentElement){
            documentScrollTop = document.documentElement.scrollTop;
        }
        scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
        return scrollTop;
    }

    //文档的总高度
    function getScrollHeight(){
        var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
        if(document.body){
            bodyScrollHeight = document.body.scrollHeight;
        }
        if(document.documentElement){
            documentScrollHeight = document.documentElement.scrollHeight;
        }
        scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
        return scrollHeight;
    }
    //浏览器视口的高度
    function getWindowHeight(){
        var windowHeight = 0;
        if(document.compatMode == "CSS1Compat"){
            windowHeight = document.documentElement.clientHeight;
        }else{
            windowHeight = document.body.clientHeight;
        }
        return windowHeight;
    }
    //

    <c:if test="${empty web:selectCountry()}">
        layer.config({
            extend: 'selectCountry/style.css', //加载您的扩展样式
            skin: 'layer-ext-selectCountry'
        });
        window.onload=function(){

            layer.open({
                type:1,
                shade:0,
                title:false,
                closeBtn:0,
                skin: 'selectCountry',
                content:$(".country-select"),
                area:['100%','100%'],
                success: function(layero, index){
                    $('html').css("height","100%").css("overflow","hidden");
                    $('body').css("height","100%").css("overflow","hidden");
                },
                end:function () {
                    $('html').css("height","auto").css("overflow","auto");
                    $('body').css("height","auto").css("overflow","auto");
                    document.body.style.overflow = "auto";
                }
            });
            document.body.style.overflow = "hidden";
        }
    </c:if>
</script>
<!-- js页面应用 结束 -->
</body>
</html>