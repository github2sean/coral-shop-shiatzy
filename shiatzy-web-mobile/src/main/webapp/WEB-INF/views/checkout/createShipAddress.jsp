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
            <div class="title">新增地址 <a href="javascript:" onclick="self.location=document.referrer;">回上一步</a></div>
            <div class="recipients">
                <div class="title">收件人*</div>
                <div class="name" >姓* <input type="text" value="${address.firstName}" name="firstName"></div>
                <div class="name" >名* <input type="text" value="${address.lastName}" name="lastName"></div>
                <div class="appellation j_appelPopup">称谓* <input type="text" value="${address.title}" name="title"> <span> ></span></div>
                <div class="tel">电话号码* <input type="tel" value="${address.phone}" name="phone"></div>
            </div>
            <div class="address">
                <div class="title">地址*</div>
                <div class="country">国别 / 区域* <input type="text" value="${address.countryId}" name="countryId"> <span> ></span></div>
                <div class="province">省/州* <input type="text" value="${address.province}" name="province"> <span> ></span></div>
                <div class="city">城区* <input type="text" value="${address.city}" name="city"> <span> ></span></div>
                <div class="detailedAddress">详细地址 <input type="text" value="${address.address}" name="address"></div>
                <div class="more">地址更多信息 <input type="text" value="${address.memo}" name="memo"></div>
            </div>
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
    $(function(){
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
