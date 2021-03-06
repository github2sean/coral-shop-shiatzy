<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<style>
    h3.title {
        font-size: 1.4rem;
        font-weight: bold;
        line-height: 2rem;
        text-align: left;
        margin-left: 2rem;
    }
</style>
<div class="order">
    <p style="float: left"><spring:message code="return.list.store.title"/></p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);"><spring:message code="goBack"/></a>
</div>
<h3 class="title"><spring:message code="return.list.store.selectstore"/></h3>
<div class="dx-pickUp">
        <div class="model-select-box">
            <div class="model-select-country" data-value="" id="countrySelect">
                <span class="pl-2" id="chooseCountry"><spring:message code="store.list.china"/></span>
                <span class="pull-right">v</span>
                <ul class="text-center model-select-option" style="display: none">
                    <c:forEach var="row" items="${storeCountryList}">
                        <c:if test="${row.name=='中国'}">
                            <li data-option="${row.id}" value="${row.id}" >${web:selectLanguage()=='en_US'?row.enName:row.name}</li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="model-select-box" >
            <div class="model-select-city" data-value="" id="citySelect">
                <span class="pl-2" id="chooseCity"><spring:message code="store.list.select.state"/></span>
                <span class="pull-right">v</span>
                <ul class="text-center model-select-option" id="cityFather" style="display: none">
                    <c:forEach var="row" items="${storeCityList}">
                        <li data-option="${row.id}" value="${row.id}">${web:selectLanguage()=='en_US'?row.enName:row.name}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="model-select-box" >
            <div class="model-select-store" data-value="" id="storeSelect">
                <span class="pl-2" id="chooseStore"><spring:message code="store.list.select.store"/></span>
                <span class="pull-right">v</span>
                <ul class="text-center model-select-option" id="storeFather" style="display: none">
                    <c:forEach var="row" items="${storeList}" varStatus="num">
                        <li data-option="${row.id}" value="${row.id}" data-value="${num.count-1}">${web:selectLanguage()=='en_US'?row.enTitle:row.name}</li>
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
        <div class="submit-btn saveBtn" id="save_store" data-value="">
            <a href="#" class="btn-default">< <spring:message code="store.list.enter"/></a>
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

        var isEn = ${web:selectLanguage()=='en_US'};

        $.post("/checkout/initCity",{"countryId":1},function (data) {
            var cityJson = data.data;
            console.log(cityJson);
            if(data.code==200 && cityJson!=null){
                $("#cityFather").children("li").each(function () {
                    $(this).remove();
                });
                var json = eval(cityJson)
                for(var i=0; i<json.length; i++)
                {
                    var name = isEn?json[i].enName:json[i].name;
                    $("#cityFather").append("<li data-option="+json[i].id+" value="+json[i].id+">"+name+"</li>");
                }
            }else{
                layer.msg("<spring:message code='store.list.noStoreInCountry'/>");
            }
        });



        /*$("#countrySelect").click(function () {
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
                            var name = isEn?json[i].enName:json[i].name;
                            $("#cityFather").append("<li data-option="+json[i].id+" value="+json[i].id+">"+name+"</li>");
                        }
                    }else{
                        layer.msg("<spring:message code='store.list.noStoreInCountry'/>");
                    }
                });
            });
        });*/


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
                            var name = isEn?json[i].enTitle:json[i].name;
                            $("#storeFather").append("<li data-value="+i+" data-option="+json[i].id+" value="+json[i].id+">"+name+"</li>");
                        }
                        $("#storeSelect").find(".model-select-store").addClass("active");
                        $("#storeSelect").find(".model-select-option").show();
                        $("#chooseStore").text('<spring:message code="store.list.select.store"/>');
                        $("#storeName").text("");
                        $("#storeAddress").text("");
                        $("#storeTime").text("");
                        $("#storeTel").text("");

                        $(".sendBtn").attr("data-value","");
                    }else{
                        layer.msg("<spring:message code='store.list.noStoreInCountry'/>");
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

            var name = isEn?json[index].enTitle:json[index].name;
            var address = isEn?json[index].enAddress:json[index].address;

            $(".storeInfo").show().find("#storeName").text("<spring:message code='store.list.store'/>："+name)
                .siblings("#storeAddress").text("<spring:message code='delivery.address'/>："+address).siblings("#storeTel").text("TEL："+json[index].tel);
            $("#save_store").attr("data-value",json[index].id);
            console.log(json[index].id);
        });

        $("#storeSelect").click(function () {
            var $option =  $(this).find(".model-select-option");
            $(this).find(".model-select-option").slideToggle(300).removeClass("hide");
        });

        $(".saveBtn").click(function () {
            var storeId = $(this).attr("data-value");
            if(storeId==''){
                layer.msg("<spring:message code="store.list.select.store"/>");
                return false;
            }
            $.post("/u/returnOrder/sureReturnWay",{"backWay":2,"addressId":storeId},function (data) {
                if(data.code==200){
                    location.href = "/u/returnOrder/chooseReturnWay";
                }else{
                    layer.msg(data.message);
                }
            })
        });



    })
</script>
