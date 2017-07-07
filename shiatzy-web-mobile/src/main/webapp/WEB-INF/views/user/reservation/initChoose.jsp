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
<div class="order">
    <p class="pull-left"><spring:message code="reservation.selectStore"/></p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< <spring:message code="goBack"/></a>
</div>
<div class="order-main clearfix">
    <c:forEach var="row" items="${preOderItemList}">
            <div class="order-main-left goodsDiv">
                <p class="product-num"><spring:message code="shoppingCart.no"/>： ${row.leftItem.goodsCode}</p>
                <img src="${ImageModel.toFirst(row.leftItem.goodsItemDomain.thumb).file}" alt="" style="height:14rem;">
                <p class="product-name">${web:selectLanguage()=='en_US'?row.leftItem.goodsEnName:row.leftItem.goodsName}</p>
                <div class="color-size">
                    <p>${web:selectLanguage()=='en_US'?row.leftItem.goodsItemDomain.enName:row.leftItem.goodsItemDomain.name}</p>
                    <p>${JSONObject.fromObject(row.leftItem.skuSpecifications).getString("size")}</p>
                </div>
                <p class="price ${not empty row.leftItem.goodsDisPrice?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/>　<span class="do-pro-price" data-value="${row.leftItem.goodsPrice}">&nbsp;</span>　</p>
                <c:if test="${not empty row.leftItem.goodsDisPrice}">
                    <p class="price xzc-dis-price"><spring:message code="shoppingCart.disPrice"/>
                        <span class="do-pro-price" data-value="${row.leftItem.goodsDisPrice}">&nbsp;</span>　
                    </p>
                </c:if>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.leftItem.id}"><svg><use xlink:href="#bag"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_collect" data-value="${row.leftItem.id}"><svg><use xlink:href="#heart"></use></svg></a></li>
                    <li><a href="javascript:;" class="deleteBtn" data-value="${row.leftItem.id}"><svg><use xlink:href="#close" data-value="${row.leftItem.id}"></use></svg></a></li>
                </ul>
            </div>
            <c:if test="${not empty row.rightItem}">
                <div class="order-main-right goodsDiv">
                    <p class="product-num"><spring:message code="shoppingCart.no"/>： ${row.rightItem.goodsCode}</p>
                    <img src="${ImageModel.toFirst(row.rightItem.goodsItemDomain.thumb).file}" alt="" style="height: 14rem;">
                    <p class="product-name">${web:selectLanguage()=='en_US'?row.rightItem.goodsEnName:row.rightItem.goodsName}</p>
                    <div class="color-size">
                        <p>${web:selectLanguage()=='en_US'?row.leftItem.goodsItemDomain.enName:row.leftItem.goodsItemDomain.name}</p>
                        <p>${JSONObject.fromObject(row.rightItem.skuSpecifications).getString("size")}</p>
                    </div>
                    <p class="price ${not empty row.rightItem.goodsDisPrice?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/>　<span class="do-pro-price" data-value="${row.rightItem.goodsDisPrice}">&nbsp;</span></p>
                    <c:if test="${not empty row.rightItem.goodsDisPrice}">
                    <p class="price xzc-dis-price"><spring:message code="shoppingCart.disPrice"/><span class="do-pro-price" data-value="${row.rightItem.goodsDisPrice}">&nbsp;</span></p>
                    </c:if>

                    <ul class="do-list-icon">
                        <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.rightItem.id}"><svg><use xlink:href="#bag"></use></svg></a></li>
                        <li><a href="javascript:;" class="j_collect" data-value="${row.rightItem.id}"><svg><use xlink:href="#heart"></use></svg></a></li>
                        <li><a href="javascript:;" class="deleteBtn" data-value="${row.leftItem.id}"><svg><use xlink:href="#close" data-value="${row.rightItem.id}"></use></svg></a></li>
                    </ul>
                </div>
            </c:if>
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
            <a class="pl-2" id="chooseCountry">中国</a>

            <ul class="text-center model-select-option" style="display: none">
            <c:forEach var="row" items="${storeCountryList}">
                <li data-option="${row.id}" value="${row.id}" class="active <c:if test='${row.id!=1}'>hide</c:if>"><span>${row.name}</span></li>
            </c:forEach>
            </ul>
        </div>
    </div>
    <div class="model-select-box" >
        <div class="model-select-city" data-value="" id="citySelect">
            <a class="pl-2" id="chooseCity">请选择城市</a>

            <ul class="text-center model-select-option" id="cityFather" style="display: none">
                <c:forEach var="row" items="${storeCityList}">
                    <li data-option="${row.id}" value="${row.id}"><span>${row.name}</span></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="model-select-box" >
        <div class="model-select-store" data-value="" id="storeSelect">
            <a class="pl-2" id="chooseStore">请选择门市</a>

            <ul class="text-center model-select-option" id="storeFather" style="display: none">
            <c:forEach var="row" items="${storeList}" varStatus="num">
                <li data-option="${row.id}" value="${row.id}" data-value="${num.count-1}"><span>${row.name}</span></li>
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
        <a href="#" class="sendBtn btn btn-default" data-value="" style="line-height: 3rem;">送出预约</a>
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
                        $("#cityFather").append("<li data-option="+json[i].id+" value="+json[i].id+">"+json[i].name+"</li>");
                    }
                }else{
                    layer.msg("该国家下无门店");
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
                    $("#cityFather").append("<li data-option="+json[i].id+" value="+json[i].id+">"+json[i].name+"</li>");
                }
            }else{
                layer.msg("该国家下无门店");
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
                            $("#storeFather").append("<li data-value="+i+" data-option="+json[i].id+" value="+json[i].id+">"+json[i].name+"</li>");
                        }
                        $("#storeSelect").find(".model-select-store").addClass("active");
                        $("#storeSelect").find(".model-select-option").show();
                         $("#chooseStore").text("请选择门店");
                         $("#storeName").text("");
                        $("#storeAddress").text("");
                        $("#storeTime").text("");
                        $("#storeTel").text("");
                        $(".sendBtn").attr("data-value","");
                    }else{
                        layer.msg("该城市下无门店");
                    }
                });
            });
        });

        $("#storeSelect").on("click","li",function () {
            var index = $(this).attr("data-value");
            $(this).addClass("active").siblings().removeClass("active");
            var text = $(this).text();
            $(this).parent().siblings("#chooseStore").text(text);
            console.log(index);
            $(".storeInfo").show().find("#storeName").text("门店："+json[index].name)
                .siblings("#storeAddress").text("地址："+json[index].address).siblings("#storeTel").text("TEL："+json[index].tel);
            $(".sendBtn").attr("data-value",json[index].id);
        });

        $("#storeSelect").click(function () {
            $(this).toggleClass("active");
            var $option =  $(this).find(".model-select-option");
            $(this).find(".model-select-option").slideToggle(300).removeClass("hide");

        });

        $(".sendBtn").click(function () {
            var storeId = $(this).attr("data-value");
            if(storeId==''){
                layer.msg("请选择门店");
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
                    layer.msg("加入心愿单成功");
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
                    layer.msg("加入购物车成功");
                    if(typeof (isNull)=="undefined"){
                        location.href = "/boutique/list";
                    }
                }
            });
        });

    })
</script>
