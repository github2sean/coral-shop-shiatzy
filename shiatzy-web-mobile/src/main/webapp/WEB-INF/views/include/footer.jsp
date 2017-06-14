<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="javascript:;" id="j_back_top" class="back-top"><spring:message code="toTop"/></a>
<footer class="footer font-12">
    <ul class="do-list-btm">
        <li><a href="/u/account/index"><spring:message code="myAccount"/></a></li>
        <%--<li><a href="/content/faq?id="><spring:message code="commonQuestion"/></a></li>--%>
        <c:forEach var="item" items="${domainList}">
            <c:if test="${item.level==1 && item.isIndex == 1}">
            <li><a href="/content/faq?id=${item.id}">${sessionScope.language=='en_US'?item.en_title:item.title}</a></li>
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

<!-- 轮播 开始 -->
<script src="${ctx}/static/js/slider/nivo_slider.js"></script>
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
    });
</script>
<script>
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
                console.log("rate"+rate);
            }
            $(".do-pro-price").each(function () {
                var oldPri = $(this).attr("data-value")*1;
                $(this).text(coinSymbol+" "+((oldPri/rate).toFixed(2))).attr("data-rate",rate);
            }) ;
            $(".only-price").each(function () {
                var oldPri = $(this).text()*1;
                $(this).text(((oldPri/rate).toFixed(2))).attr("data-rate",rate);
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
                    cartNum = data.data;
                    $(".cart_num").removeClass("hide");
                    $(".cart_num").text(cartNum);console.log(cartNum);
                }else{
                    // layer.msg('更新购物车数量失败');
                }
            });
            $.post("/cart/querySessionCartNum",{"type":3},function (data) {
                if (data.code==200){
                    cartNum = data.data;
                    $(".boutique_num").removeClass("hide");
                    $(".boutique_num").text(cartNum);console.log(cartNum);
                    console.log("boutique:"+cartNum)
                }else{
                    // layer.msg('更新购物车数量失败');
                }
            });
        }else{
            $.post("/cart/getCartNum",{"type":1},function (data) {
                if (data.code==200){
                    cartNum = data.data;
                    $(".cart_num").text(cartNum);
                }else{
                    // layer.msg('更新购物车数量失败');
                }
            });
            $.post("/cart/getCartNum",{"type":3},function (data) {
                if (data.code==200){
                    cartNum = data.data;
                    console.log("bou:"+cartNum);
                    $(".boutique_num").text(cartNum);console.log(cartNum);
                }else{
                    // layer.msg('更新购物车数量失败');
                }
            });
        }
    }

    $('#slider').nivoSlider({
        effect: 'random',               // 过渡效果
        slices: 15,                     // 切片效果时的数量
        boxCols: 8,                     // 格子效果时的列数
        boxRows: 4,                     // 格式效果时的行数
        animSpeed: 1000,                // 图片过渡时间
        pauseTime: 2000,                // 图片显示时间
        startSlide: 0,                  // 从第几张图片开始（第一张为）
        directionNav: true,             // 是否显示图片方向切换按钮（上一页/下一页）
        controlNav: true,               // 是否显示图片导航控制按钮（,2,3... ）
        controlNavThumbs: false,        // 是否使用图片的缩略图做为导航控制按钮
        pauseOnHover: true,             // 鼠标县浮时是否停止动画
        manualAdvance: true,           // 是否手动切换
        prevText: 'Prev',               // 上一页方向切换按钮的显示文本
        nextText: 'Next',               // 下一页方向切换按钮的显示文本
        randomStart: false,             // 开始图片是否随机
        beforeChange: function(){},     // 图片切换前触发函数
        afterChange: function(){},      // 图片切换后触发函数
        slideshowEnd: function(){},     // 在所有图片显示完毕后触发函数
        lastSlide: function(){},        // 在最后一张图片显示完毕后触发函数
        afterLoad: function(){}         // 图片加载完毕后触发函数
    });
    $(function () {

        $("#sendSMS").click(function () {

            var phone = '8615068614497';
            var data ={"phone":phone,"content":"你好！测试短信"};
            $.post("${ctx}/send/sendMsg",data,function (data) {

                console.log(data)
            });

        });

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
            }else{
                $.post("/commom/subscribe",{"email":email},function (data) {
                    if (data.code==200){
                        layer.msg("订阅成功");
                    }else{
                        layer.msg("订阅失败");
                    }
                });
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
                shade: [0.5,'#000'] //0.1透明度的白色背景
            });
        });

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
</script>
<!-- js页面应用 结束 -->
</body>
</html>