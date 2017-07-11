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
                <li><a href="mailto:customercare@shiatzychen.com"><spring:message code="contactUs"/></a></li>
                <li><a href="#" class="privacyNotice"><spring:message code="privacyPolicy"/></a></li>
            </ul>
        </li>
    </ul>
    <ul class="do-list-lang j_drop_down">
        <li>
            <a href="javascript:void(0)" id="trigger_select_country">
                <select class="select_country" value="1" style="height: 100%;width: 100%;background: #000  ;border: 0;font-size: 1.1rem;">
                    <option value="-1"><spring:message code="selectOtherCountriesORRegions"/></option>
                    <c:forEach var="country" items="${web:countryList()}" begin="0">
                        <option value="${country.id}" <c:if test="${web:selectCountry()==country.id}">selected="selected"</c:if> >${web:selectLanguage()=='en_US'?country.enName:country.name}</option>
                    </c:forEach>
                </select></a>
        </li>
        <li><a href="javascript:void(0);"><spring:message code="selectLanguage"/></a>
            <ul class="do-sub-list-btm">
                <li><a class="language" id="CHINA" data-value="zh_CN" href="javascript:void(0);" style="text-decoration: underline"><spring:message code="language.cn" /></a></li>
                <li><a class="language" id="OTHER" data-value="en_US" href="javascript:void(0);" style="text-decoration: underline"><spring:message code="language.en" /></a></li>
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
    <h3 class="country-title">${web:selectLanguage()=='en_US'?"Choose your location":"选择国家或地区"}</h3>
    <ul class="country-con" style="">
        <c:forEach var="row" items="${web:countryList()}">
            <li><a href="#"  data-value="${row.id}">${web:selectLanguage()=='en_US'?row.enName:row.name}</a></li>
        </c:forEach>
    </ul>
</div>

<div class="form-item item-line">
    <label></label>
    <div class="pc-box">
        <input type="hidden" name="bank_id" id="bankId" value="">
        <span id="showBank"></span>
    </div>
</div>

<script src="${ctx}/static/js/iosSelect.js"></script>
<script src="${ctx}/static/js/iscroll.js"></script>
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
<script src="${ctx}/static/js/jquery.bxslider.min.js"></script>

<!-- 轮播 结束 -->



<!-- js页面应用 开始 -->
<script>
    $(function () {
        commonApp.init();
        backendApp.init();
        <%--关闭提示--%>
        $(".j_cls_notice").on("click touchstart",function(){
            $(this).parent().fadeOut();
        });
        $("#trigger_select_country").on("click",function (e) {
            //e.preventDefault();
            //$("#select_country").trigger("change");
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

                if(obj.rateType == 1){
                    coinSymbol = '<spring:message code="coin.USA"/>';
                }else if(obj.rateType == 2){
                    coinSymbol = '<spring:message code="coin.EU"/>';
                }
                rate = obj.rate;

            }


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


    function clickLanguage(id) {
        if("1"==id||"14"==id||"31"==id){
            postToSelectLanguage("zh_CN");
        }else{
            postToSelectLanguage("en_US");
        }
    }
    function postToSelectLanguage(language) {
        $.post("/home/selectLanguage",{"nowLanguage":language},function (data) {
            if(data.code==200){
                var old = location.search;
                if(old==''){
                    old = old  +"?lang="+language;
                }else if(old.indexOf("?lang=")!=-1){
                    old = "?lang="+language;

                }else if(old.indexOf("&lang=")!=-1){
                    old = old.substr(0,old.indexOf("&lang="));
                    old = old+"&lang="+language;
                }else{
                    old = old+"&lang="+language;
                }
                location.href = location.href.indexOf("checkout/confirm")!=-1?"/checkout/orderInfo":old;
            }
        });
    }

    $(function () {

        $(".select_country").change(function(){
            var id =  $(this).find("option:selected").attr("value");
            $.post("/home/chooseShippingCountry",{"shippingCountryId":id},function (data) {
                if(data.code==200){
                    clickLanguage(id);
                }
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
                //layer.msg("${web:t("请输入搜索关键字","Choose your location")}");
                return false;
            }
            $("#contentForm").submit();
        });
        console.log("sessionScopeLanguage:"+'${sessionScope.language}'+"  cookieLanguage:"+'${web:selectLanguage()}');

        $(".language").click(function () {
            var language = $(this).attr("data-value");
            postToSelectLanguage(language);
        });

        $(".do-btn-subscribe").click(function () {
            var email = $(".do-fill-email").val();
            var reg =/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
            if(email==''){
                $("#subscribe_msg").text("<spring:message code="subscribe.message.info"/>");
                return false;
            }else if(!reg.test(email)){
                $("#subscribe_msg").text("<spring:message code="subscribe.message.warn"/>");
                return false;
            }else{
                $.post("/common/subscribe",{"email":email},function (data) {
                    if (data.code==200){
                        $("#subscribe_msg").text("<spring:message code="subscribe.message.success"/>");
                    }else{
                        $("#subscribe_msg").text("<spring:message code="subscribe.message.fail"/>");
                    }
                });
            }
            return false;
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


        //隐私政策
        $(".privacyNotice").click(function(){
            layer.open({
                type: 2,
                title: '<spring:message code="privacyPolicy"/>',
                closeBtn: 1, //不显示关闭按钮
                skin:'d-dialog',
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

        //退货政策
        $(".returnOrchange").click(function () {
            layer.open({
                type: 2,
                title: '<spring:message code="privacyPolicy"/>',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                skin:'d-dialog',
                area: ['100%', '100%'],
                content: ['${ctx}/content/returnOrchange?id=27'],//iframe的url，no代表不显示滚动条
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

    <c:if test="${empty web:selectCountry() || web:selectCountry()<1}">

    var showBankDom = document.querySelector('#showBank');
    var bankIdDom = document.querySelector('#bankId');
    var data = new Array();
    $(".country-select").find("a").each(function (index) {
        var id  = $(this).attr("data-value");
        var name = $(this).text();
        data[index] = {"id":id,"value":name};
    });
    window.onload=function(){
        var bankId = showBankDom.dataset['id'];
        var bankName = showBankDom.dataset['value'];

        var bankSelect = new IosSelect(1,
                [data],
                {
                    container: '.container',
                    title: '${web:t("选择您所在的地区","Choose your location")}',
                    itemHeight: 50,
                    itemShowCount: 5,
                    oneLevelId: bankId,
                    callback: function (selectOneObj) {
                        bankIdDom.value = selectOneObj.id;
                        showBankDom.innerHTML = selectOneObj.value;
                        showBankDom.dataset['id'] = selectOneObj.id;
                        showBankDom.dataset['value'] = selectOneObj.value;
                        var id = selectOneObj.id;
                        $.post("/home/chooseShippingCountry",{"shippingCountryId":id},function (data) {
                         if(data.code==200){
                           clickLanguage(id);
                         }
                         });
                    }
                });
        $(".sure").html("${web:t("确认","DONE")}");
        $(".close").hide();
    };


    </c:if>


</script>
<!-- js页面应用 结束 -->
</body>
</html>