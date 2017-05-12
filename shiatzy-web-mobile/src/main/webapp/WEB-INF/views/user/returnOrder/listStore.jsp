<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left">门市退货设定</p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">回上页</a>
</div><h6>选择退货门市</h6>
<div class="dx-pickUp">
        <div class="model-select-box">
            <div class="model-select-country" data-value="" id="countrySelect">
                <span class="pl-2" id="chooseCountry">请选择国家</span>
                <span class="pull-right">v</span>
                <ul class="text-center model-select-option" style="display: none">
                    <c:forEach var="row" items="${storeCountryList}">
                        <li data-option="${row.id}" value="${row.id}" class="">${row.name}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="model-select-box" >
            <div class="model-select-city" data-value="" id="citySelect">
                <span class="pl-2" id="chooseCity">请选择省/州</span>
                <span class="pull-right">v</span>
                <ul class="text-center model-select-option" id="cityFather" style="display: none">
                    <c:forEach var="row" items="${storeCityList}">
                        <li data-option="${row.id}" value="${row.id}">${row.name}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="model-select-box" >
            <div class="model-select-store" data-value="" id="storeSelect">
                <span class="pl-2" id="chooseStore">请选择门市</span>
                <span class="pull-right">v</span>
                <ul class="text-center model-select-option" id="storeFather" style="display: none">
                    <c:forEach var="row" items="${storeList}" varStatus="num">
                        <li data-option="${row.id}" value="${row.id}" data-value="${num.count-1}">${row.name}</li>
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
        <div class="submit-btn saveBtn" data-value="">
            <a href="#">< 确认</a>
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

        $("#countrySelect").click(function () {
            var $option =  $(this).find(".model-select-option");
            $(this).find(".model-select-option").slideToggle(300).removeClass("hide");
            $option.find("li").click(function () {
                $(this).addClass("active").siblings().removeClass("active");
                var text = $(this).text();
                $(this).parent().siblings("#chooseCountry").text(text);
                //初始化选择城市
                $.post("/checkout/initCity",{"countryId":$(this).attr("data-option")},function (data) {
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


        var json = "";
        $("#citySelect").click(function () {
            var $option =  $(this).find(".model-select-option");
            $(this).find(".model-select-option").slideToggle(300).removeClass("hide");
            $option.find("li").on("click",function () {
                $(this).addClass("active").siblings().removeClass("active");
                var text = $(this).text();
                $(this).parent().siblings("#chooseCity").text(text);
                //初始化选择城市
                $.post("/checkout/initStore",{"cityId":$(this).attr("data-option")},function (data) {
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
                    }else{
                        layer.msg("该城市下无门店");
                    }
                });
            });
        });

        $("#storeSelect").click(function () {
            var $option =  $(this).find(".model-select-option");
            $(this).find(".model-select-option").slideToggle(300).removeClass("hide");
            $option.find("li").on("click",function () {
                var index = $(this).attr("data-value");
                $(this).addClass("active").siblings().removeClass("active");
                var text = $(this).text();
                $(this).parent().siblings("#chooseStore").text(text);
                console.log(index);
                $(".storeInfo").show().find("#storeName").text("门店："+json[index].name)
                        .siblings("#storeAddress").text("地址："+json[index].address).siblings("#storeTel").text("TEL："+json[index].tel);
                $(".saveBtn").attr("data-value",json[index].id);
            });

        });

        $(".saveBtn").click(function () {
            var storeId = $(this).attr("data-value");
            if(storeId==''){
                layer.msg("请选择门店");
                return false;
            }
            $.post("/returnOrder/sureReturnWay",{"backWay":2,"addressId":storeId},function (data) {
                if(data.code==200){
                    location.href = "/returnOrder/chooseReturnWay";
                }else{
                    layer.msg(data.message);
                }
            })
        });



    })
</script>
