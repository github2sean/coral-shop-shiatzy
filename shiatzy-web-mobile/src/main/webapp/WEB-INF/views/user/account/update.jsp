<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的帐户 / 个人信息</p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< 回上衣页</a>
</div>

<div class="setting">
    <form class="updateAccountForm" method="post" action="/u/account/update">
    <div class="data-setting">
        <h3>资料设定</h3>
        <ul>
            <li>
                <span>称谓*<input type="text"  value="${customerAddressDomain.title}" name="customerAddressDomain.title" id="title"/></span>
                <span style="float:right;"></span>
            </li>
            <li>
                <span>名字*<input type="text"  value="${customerDomain.firstName}" name="customerDomain.firstName" id="lastName"/></span>
                <span style="float:right;"></span>
            </li>
            <li>姓氏*<input type="text"  value="${customerDomain.lastName}" name="customerDomain.lastName" id="firstName"/></li>
            <li>
                生日
                <input type="date"   name="customerDomain.birthday" value="${customerDomain.birthday}"/>
            </li>
            <li>电话号码*<input type="text"  value="${customerDomain.phone}" name="customerDomain.phone" id="phone"/></li>
        </ul>
    </div>
    <div class="data-setting">
        <h3>地址设定</h3>
        <ul>
            <li>
                <span>国别 / 区域*</span>
                <label  style="float: right;border: 1px;">
                    <select name="customerAddressDomain.countryId" style="border: 1px;margin-bottom: 5px;" id="countryId">
                        <option value="-1">请选择</option>
                        <option value="1">中国</option>
                        <option value="2">美国</option>
                    </select>
                </label>
            </li>
            <li>
                <span>省/州*</span>
                <input type="text" name="customerAddressDomain.province" id="province" value="${customerAddressDomain.province}">
            </li>
            <li>
                <span>城区*</span>
                <input type="text" name="customerAddressDomain.city" id="city" value="${customerAddressDomain.city}">
            </li>
            <li>详细地址<input type="text"  value="${customerAddressDomain.address}" name="customerAddressDomain.address"/></li>
            <li>邮编<input type="text"  value="${customerAddressDomain.postalCode}"/>
                <input type="hidden"  value="${customerAddressDomain.id}" name="addressId"/></li>
        </ul>
    </div>
    <div id="showInfo" style="display: none;color: red;text-align: center;width: 100%"></div>
    <div class="complete">
        <a type="button" class="updateBtn">完成</a>
    </div>
</form>

</div>
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
            $("#showInfo").show().text("姓氏不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //名字验证
    function lastName()
    {
        var lastName=$('#lastName').val();
        if(lastName == '')
        {
            $("#showInfo").show().text("名字不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //称谓验证
    function title()
    {
        var title=$('#title').val();
        if(title == '')
        {
            $("#showInfo").show().text("称谓不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //电话验证
    function phone()
    {
        var phone=$('#phone').val();
        if(phone == '')
        {
            $("#showInfo").show().text("电话不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //国家验证
    function countryId()
    {
        var  myselect =document.getElementById("countryId");
        var countryId = myselect.value;
        if(countryId == '')
        {
            $("#showInfo").show().text("国家/区域不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //省州验证
    function province()
    {
        var province=$('#province').val();
        if(province == '')
        {
            $("#showInfo").show().text("省州不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //地区验证
    function city()
    {
        var city=$('#city').val();
        if(city == '')
        {
            $("#showInfo").show().text("地区不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    $(function () {
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

        $("input").css({"border":"0px","margin-left":"10px"});
        $("select").css({"border":"0px","margin-right":"10px"});
        $(".updateBtn").click(function () {
            var  $updateAccountForm = $(".updateAccountForm");
            var  data = $updateAccountForm.serializeArray();

            $.post("/u/account/update",data,function (data) {
                console.log(data.message);
                $("#showInfo").show().text(data.message);
                location.href="/u/account/details";
            });
        });
    });

</script>
