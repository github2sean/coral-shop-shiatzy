<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<form class="addressForm" method="post" action="/checkout/addAddress">
<div class="dx-EditAddress">
    <div class="content">
        <div class="title">新增地址 <a href="购物车.结算页.物流配送.选择.html">回上一步</a></div>
        <div class="recipients">
            <div class="title">收件人*</div>
            <div class="name" >姓* <input type="text" name="addressModel.firstName" id="firstName"></div>
            <div class="name" >名* <input type="text" name="addressModel.lastName" id="lastName"></div>
            <div class="appellation j_appelPopup">称谓* <input type="text" name="addressModel.title" id="title"> <span> ></span></div>
            <div class="tel">电话号码* <input type="tel" name="addressModel.phone" id="phone"></div>
        </div>
        <div class="address">
            <div class="title">地址*</div>
            <div class="country">国别 / 区域* <input type="text" name="addressModel.countryId" id="countryId"> <span> ></span></div>
            <div class="province">省/州* <input type="text" name="addressModel.provinceId" id="province"> <span> ></span></div>
            <div class="city">城区* <input type="text" name="addressModel.cityId" id="city"> <span> ></span></div>
            <div class="detailedAddress">详细地址 <input type="text" name="addressModel.address"></div>
            <div class="more">地址更多信息 <input type="text" name="addressModel.memo"></div>
        </div>
        <div class="remind"></div>
        <a href="#" class="btn completeBtn">完成</a>
    </div>
</div>
</form>
<!--称谓弹框-->
<ul class="appellation-popup">
    <li class="title">称谓*</li>
    <li>先生</li>
    <li class="active">女士</li>
    <li>小姐</li>
    <li>无</li>
</ul>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    //姓氏验证
    function firstName()
    {
        var firstName=$('#firstName').val();
        if(firstName == '')
        {
            $(".remind").show().css("color","red").text("姓氏不能为空！");
            return false;
        }
        $(".remind").hide();
        return true;
    }
    //名字验证
    function lastName()
    {
        var lastName=$('#lastName').val();
        if(lastName == '')
        {
            $(".remind").show().css("color","red").text("名字不能为空！");
            return false;
        }
        $(".remind").hide();
        return true;
    }
    //称谓验证
    function title()
    {
        var title=$('#title').val();
        if(title == '')
        {
            $(".remind").show().css("color","red").text("称谓不能为空！");
            return false;
        }
        $(".remind").hide();
        return true;
    }
    //电话验证
    function phone()
    {
        var phone=$('#phone').val();
        if(phone == '')
        {
            $(".remind").show().css("color","red").text("电话不能为空！");
            return false;
        }
        $(".remind").hide();
        return true;
    }
    //国家验证
    function countryId()
    {
        var countryId=$('#countryId').val();
        if(countryId == '')
        {
            $(".remind").show().css("color","red").text("国家/区域不能为空！");
            return false;
        }
        $(".remind").hide();
        return true;
    }
    //省州验证
    function province()
    {
        var province=$('#province').val();
        if(province == '')
        {
            $(".remind").show().css("color","red").text("省州不能为空！");
            return false;
        }
        $(".remind").hide();
        return true;
    }
    //地区验证
    function city()
    {
        var city=$('#city').val();
        if(city == '')
        {
            $(".remind").show().css("color","red").text("地区不能为空！");
            return false;
        }
        $(".remind").hide();
        return true;
    }
    $(function(){
        $('#firstName').focus(function(){

        }).blur(firstName);
        $('#lastName').focus(function(){

        }).blur(lastName);
        $('#title').focus(function(){

        }).blur(title);
        $('#countryId').focus(function(){

        }).blur(countryId);
        $('#province').focus(function(){

        }).blur(province);
        $('#city').focus(function(){

        }).blur(city);
        $('#phone').focus(function(){

        }).blur(phone);
        commonApp.init();

        //点击称谓弹窗
        $(".j_appelPopup").on("click",function () {
            layer.open({
                type:1,
                title: false,
                content:$(".appellation-popup"),
                shade:0.8,
                area:['30rem','20rem']
            });
        });

        //关闭弹窗
        $(".j_appelPopup>li").on("click",function () {
            layer.close(layer.index);
        });

        $(".completeBtn").click(function () {

            var $form = $(".addressForm");
            var data = $form.serializeArray();
            $.post("/checkout/addAddress",data,function (data) {
                if(data.code==200){
                    location.href = "${ctx}/checkout/listAddress";
                }else{
                    alert(data.message)
                }
            });
        });

    });
</script>
