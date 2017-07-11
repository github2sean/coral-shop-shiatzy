<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<style>
    .model-select-box span{font-size: 1.4rem;}
    .model-select-box, .model-select-box ul li{font-size: 1.4rem}
    .order-stores h1{margin-bottom: 2rem;}
    .model-select-box .model-select-store, .model-select-box .model-select-country, .model-select-box .model-select-city{
        margin-bottom: 1.5rem;
    }
    .model-select-box a:after{
        content: ">";
        float: right;
        font-size: 1.4rem;
        -webkit-transition: -webkit-transform .5s;
        transition: -webkit-transform .5s;
        transition: transform .5s;
        transition: transform .5s, -webkit-transform .5s;}
    .model-select-box .active a:after{
        -webkit-transform: rotate(90deg);
        transform: rotate(90deg);
        display: block;}
</style>
<style>
    .goods-item:nth-of-type(even) {
        border-right: 0;
    }
    .goods-item:nth-of-type(odd) {
    }
    .goods-item{
        float: left;
        padding: 1rem 0;
        border-right: 1px solid #ccc;
        text-align: center;
        font-size: 1rem;
        width: 50%;
        border-bottom: 1px solid #ccc;
        position: relative;
        height: 25rem;
        padding: 0 .6rem;
    }
    .goods-item .product-name{height: 4rem;overflow: hidden}
    .goods-item .do-list-icon{ top: 3rem;}
    .goods-item .pic {text-align: center;}
    .goods-item .pic img{    max-height: 100%;width: 10rem;}
</style>
<div class="order">
    <p class="pull-left"><spring:message code="reservation.selectStore"/></p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< <spring:message code="goBack"/></a>
</div>
<div class="goods-list clearfix">
    <c:forEach var="row" items="${submitCartList}">
        <div class="goods-item goodsDiv">
            <p class="product-num"><spring:message code="shoppingCart.no"/>${row.goodsCode}</p>
            <div class="pic">
                <a href="/goods/details/${row.goodsItemDomain.id}"><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt=""></a>
            </div>
            <p class="product-name">${web:selectLanguage()=='en_US'?row.goodsEnName:row.goodsName}</p>
            <div class="color-size">
                <span>${web:selectLanguage()=='en_US'?row.goodsItemDomain.enName:row.goodsItemDomain.name}</span>
                <span><spring:message
                        code="shoppingCart.size"/>:&nbsp;${web:selectLanguage()=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}</span>
                &nbsp;&nbsp;
                <c:if test="${row.stock>0}"><span class="sellOut" data-value="${row.id}"></span></c:if>
                <c:if test="${row.stock<1}"><span class="sellOut hasOut" data-value="${row.id}">（<spring:message code="sellout" />）</span></c:if>
            </div>
            <p style="margin-bottom: 0px;" class="price ${not empty row.goodsDisPrice?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/>&nbsp;
                <span class="do-pro-price" data-value="${row.goodsPrice}">&nbsp;</span>
            </p>
            <c:if test="${not empty  row.goodsDisPrice}">
                <p class="price xzc-dis-price"><spring:message code="shoppingCart.disPrice"/>&nbsp;
                    <span class="do-pro-price" data-value="${row.goodsDisPrice}">&nbsp;</span>
                </p>
            </c:if>

            <ul class="do-list-icon">
                <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.id}"
                       <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if> >
                    <svg>
                        <use xlink:href="#bag"></use>
                    </svg>
                </a></li>
                <li><a href="javascript:;" class="j_collect" data-value="${row.id}"
                       <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if>  >
                    <svg>
                        <use xlink:href="#heart"></use>
                    </svg>
                </a></li>
                <li><a href="" class="deleteBtn" data-value="${row.id}"
                       <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if> >
                    <svg>
                        <use xlink:href="#close"></use>
                    </svg>
                </a></li>
            </ul>
        </div>
    </c:forEach>
</div>


<div class="order-price hide" >
    <c:if test="web:selectLanguage()=='en_US'">
    <p>预计订单总额：¥ </p>
    </c:if>
    <c:if test="web:selectLanguage()!='en_US'">
        <p>Total：¥ </p>
    </c:if>
</div>
<div class="order-stores">
    <h1><spring:message code="reservation.selectStore"/></h1>
    <div class="model-select-box">
        <div class="model-select-country j_collapse" data-value="" id="countrySelect">
            <a class="pl-2" id="chooseCountry">${web:t("中国","China" )}</a>

            <ul class="text-center model-select-option" style="display: none">
            <c:forEach var="row" items="${storeCountryList}">
                <li data-option="${row.id}" value="${row.id}" class="active <c:if test='${row.id!=1}'>hide</c:if>"><span>${web:t(row.name,row.enName )}</span></li>
            </c:forEach>
            </ul>
        </div>
    </div>
    <div class="model-select-box" >
        <div class="model-select-city" data-value="" id="citySelect">
            <a class="pl-2" id="chooseCity">${web:t("请选择城市","Choose City")}</a>

            <ul class="text-center model-select-option" id="cityFather" style="display: none">
                <c:forEach var="row" items="${storeCityList}">
                    <li data-option="${row.id}" value="${row.id}"><span>${web:t(row.name,row.enName )}</span></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="model-select-box" >
        <div class="model-select-store" data-value="" id="storeSelect">
            <a class="pl-2" id="chooseStore">${web:t("请选择门市","Choose Store")}</a>

            <ul class="text-center model-select-option" id="storeFather" style="display: none">
            <c:forEach var="row" items="${storeList}" varStatus="num">
                <li data-option="${row.id}" value="${row.id}" data-value="${num.count-1}"><span>${web:t(row.name,row.enName )}</span></li>
            </c:forEach>
            </ul>
        </div>
    </div>
    <div class="stores-introduce storeInfo">
        <h2 id="storeName"></h2>
        <p id="storeAddress"></p>
        <p id="storeTime"></p>
        <p id="storeTel"></p>
    </div>
    <div class="submit-btn">
        <a href="#" class="sendBtn btn btn-default" data-value="" style="line-height: 3rem;">${web:t("送出预约","SEND")}</a>
    </div>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>

    //下拉列表
    function selectModel() {
        var $box = $('div.model-select-box');
        var $option = $('ul.model-select-option', $box);
        var $txt = $('div.model-select-store', $box);
        var speed = 10;
        $txt.click(function (e) {
            $option.not($(this).siblings('ul.model-select-option')).slideUp(speed, function () {
                int($(this));
            });
            $(this).siblings('ul.model-select-option').slideToggle(speed, function () {
                int($(this));
            });
            return false;
        });
        $option.find('li').each(function (index, element) {
                    if ($(this).hasClass('seleced')) {
                        $(this).addClass('data-selected');
                    }
                })
                .mousedown(function () {
                    $(this).parent().siblings('div.model-select-store').text($(this).text())
                            .attr('data-value', $(this).attr('data-option'));
                    $option.slideUp(speed, function () {
                        int($(this));
                    });
                    $(this).addClass('seleced data-selected').siblings('li').removeClass('seleced data-selected');
                    return false;
                })
                .mouseover(function () {
                    $(this).addClass('seleced').siblings('li').removeClass('seleced');
                });
        $(document).click(function (e) {
            $option.slideUp(speed, function () {
                int($(this));
            });
        });
        function int(obj) {
            obj.find('li.data-selected').addClass('seleced').siblings('li').removeClass('seleced');
        }
    };
    $(function () {
        setPrice();
        $("#countrySelect").click(function () {
            $(this).toggleClass("active");
            var $option =  $(this).find(".model-select-option");
            $(this).find(".model-select-option").slideToggle(300).removeClass("hide");
            $option.find("li").click(function () {
                $(this).addClass("active").siblings().removeClass("active");
                var text = $(this).text();
                $(this).parent().siblings("#chooseCountry").text(text);
                //初始化选择城市
                $.post("/reservation/chooseCity",{"countryId":$(this).attr("data-option")},function (data) {
                var cityJson = data.data;
                console.log(cityJson);
                if(data.code==200 && cityJson!=null){
                    $("#cityFather").children("li").each(function () {
                        $(this).remove();
                    });
                    var json = eval(cityJson)
                    for(var i=0; i<json.length; i++)
                    {
                        var name = ${web:t("json[i].name","json[i].enName")};
                        $("#cityFather").append("<li data-option="+json[i].id+" value="+json[i].id+">"+name+"</li>");
                    }
                }else{
                    layer.msg("${web:t("该国家下无门店","No store in this location")}");
                }
            });
            });
        });
        $.post("/reservation/chooseCity",{"countryId":1},function (data) {
            var cityJson = data.data;
            console.log(cityJson);
            if(data.code==200 && cityJson!=null){
                $("#cityFather").children("li").each(function () {
                    $(this).remove();
                });
                var json = eval(cityJson)
                for(var i=0; i<json.length; i++)
                {
                    var name = ${web:t("json[i].name","json[i].enName")};
                    $("#cityFather").append("<li data-option="+json[i].id+" value="+json[i].id+">"+name+"</li>");
                }
            }else{
                layer.msg("${web:t("该国家下无门店","No store in this location")}");
            }
        });



        var json = "";
        $("#citySelect").click(function () {
            $(this).toggleClass("active");

            var $option =  $(this).find(".model-select-option");
            $(this).find(".model-select-option").slideToggle(300).removeClass("hide");
            $option.find("li").on("click",function () {
                $(this).addClass("active").siblings().removeClass("active");
                var text = $(this).text();
                $(this).parent().siblings("#chooseCity").text(text);
                //初始化选择城市
                $.post("/reservation/chooseStore",{"cityId":$(this).attr("data-option")},function (data) {
                    var storeJson = data.data;
                    console.log(storeJson);
                    if(data.code==200 && storeJson!=null){
                        $("#storeFather").children("li").each(function () {
                            $(this).remove();
                        });
                         json = eval(storeJson)
                        for(var i=0; i<json.length; i++)
                        {
                            var name = ${web:t("json[i].name","json[i].enTitle")};
                            $("#storeFather").append("<li data-value="+i+" data-option="+json[i].id+" value="+json[i].id+">"+name+"</li>");
                        }
                        $("#storeSelect").find(".model-select-store").addClass("active");
                        $("#storeSelect").find(".model-select-option").show();
                         $("#chooseStore").text("${web:t("请选择门店", "Please Choose Store")}");
                         $("#storeName").text("");
                        $("#storeAddress").text("");
                        $("#storeTime").text("");
                        $("#storeTel").text("");
                        $(".sendBtn").attr("data-value","");
                    }else{
                        layer.msg("${web:t("该城市下无门店", "No store in this city")}");
                    }
                });
            });
        });

        $("#storeSelect").on("click","li",function () {
            var i = $(this).attr("data-value");
            $(this).addClass("active").siblings().removeClass("active");
            var text = $(this).text();
            $(this).parent().siblings("#chooseStore").text(text);

            var name = ${web:t("json[i].name","json[i].enTitle")};
            var address = ${web:t("json[i].address","json[i].enAddress")};
            $(".storeInfo").show().find("#storeName").text("${web:t("门店","Store" )}："+name)
                .siblings("#storeAddress").text("${web:t("地址","Address" )}："+address).siblings("#storeTel").text("TEL："+json[i].tel);
            $(".sendBtn").attr("data-value",json[i].id);
        });

        $("#storeSelect").click(function () {
            $(this).toggleClass("active");
            var $option =  $(this).find(".model-select-option");
            $(this).find(".model-select-option").slideToggle(300).removeClass("hide");

        });

        $(".sendBtn").click(function () {
            var storeId = $(this).attr("data-value");
            if(storeId==''){
                layer.msg("${web:t("请选择门店","Please choose store" )}");
                return false;
            }
            $.post("/reservation/submitPreOrder",{"storeId":storeId},function (data) {
                if(data.code==200){
                    location.href = "/reservation/details?reservationId="+data.data;
                }else {
                    layer.msg(data.message);
                }
            });
        });

        $(".deleteBtn").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            $.post("/cart/removeFromCart",{"shoppingCartItemId":id},function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    if(typeof (isNull)=="undefined"){
                        location.href = "/boutique/list";
                    }
                }
            });
        });
        $(".j_collect").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            var data = {"shoppingCartItemId":id};
            $.post("/cart/boutiqueToWish",data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("${web:t("加入心愿单成功","Add to wish list successfully")}");
                    if(typeof (isNull)=="undefined"){
                        location.href = "/boutique/list";
                    }
                }
            });
        });
        $(".j_bag").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            var data = {"shoppingCartItemId":id};
            $.post("/cart/boutiqueToCart",data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("${web:t("加入购物袋成功","Add to shoppingcart  successfully")}");
                    if(typeof (isNull)=="undefined"){
                        location.href = "/boutique/list";
                    }
                }
            });
        });

    })
</script>
