<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<style>
    select{
        border:none;
        width: 100px;
        text-align: center;
        padding-left: 20px;
    }
    input{
    padding-left: 20px;
    }
</style>

<form class="addressForm" method="post" action="/checkout/addAddress">
    <div class="dx-EditAddress">
        <div class="content">
            <div class="title">新增地址 <a href="javascript:" onclick="self.location=document.referrer;">回上一步</a></div>
            <div class="recipients">
                <div class="title">收件人*</div>
                <div class="name" >姓* <input type="text" value="${address.firstName}" name="firstName" id="firstName"></div>
                <div class="name" >名* <input type="text" value="${address.lastName}" name="lastName" id="lastName"></div>
                <div class="appellation ">称谓*
                    <select name="title" id="title" class="">
                            <option value="女士">女士</option>
                            <option value="先生">先生</option>
                            <option value="小姐">小姐</option>
                            <option value="无">无</option>
                    </select>
                    <%--<input type="text" value="${address.title}" name="title" id="title"> <span> ></span></div>--%>
                 </div>
                <div class="tel">电话号码* <input type="tel" value="${address.phone}" name="phone" id="phone"></div>
            </div>

            <div class="address">
                <div class="title">地址*</div>
                <div class="country">国别 / 区域*

                    <select name="countryId" id="countryId" class="">
                        <c:forEach var="row" items="${countryList}">
                        <option value="${row.id}">${row.name}</option>
                        </c:forEach>
                    </select>

                    </div>
                <div class="province">省/州* <input type="text" value="${address.province}" name="province" id="province"> </div>
                <div class="city">城区* <input type="text" value="${address.city}" name="city" id="city"> </div>
                <div class="detailedAddress">详细地址 <input type="text" value="${address.address}" name="address"></div>
                <div class="more">地址更多信息 <input type="text" value="${address.memo}" name="memo"></div>
            </div>
            <div class="remind"></div>
            <a href="#"  class="btn completeBtn">完成</a>
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

        $('.appellation-popup').children("li:not(:first-child)").click(function (){
            var li=$(this).text();
            $('#title').val(li);
            layer.close(layer.index);
        });

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

        //校验暂未做
        $(".completeBtn").click(function () {
            var $form = $(".addressForm");
            var data = $form.serialize();
            $.post("/checkout/createShipAddress",data,function (data) {
                console.log(data);
                if(data.code==200){
                    layer.msg("添加成功");
                }
            },"json");
        });

    });
</script>
