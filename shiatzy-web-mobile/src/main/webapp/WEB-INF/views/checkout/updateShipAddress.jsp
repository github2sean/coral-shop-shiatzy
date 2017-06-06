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
            <div class="title">編輯地址 <a href="javascript:" onclick="self.location=document.referrer;">回上一步</a></div>
            <div class="recipients">
                <input type="hidden" value="${address.id}" name="id">
                <div class="title">收件人*</div>
                <div class="name" >姓* <input type="text" value="${address.firstName}" name="firstName"></div>
                <div class="name" >名* <input type="text" value="${address.lastName}" name="lastName"></div>
                <div class="appellation ">称谓*
                    <select name="title" id="title" class="" value="${address.title}">
                        <option value="女士">女士</option>
                        <option value="先生">先生</option>
                        <option value="小姐">小姐</option>
                        <option value="无">无</option>
                    </select>
                </div>
                <div class="tel">电话号码* <input type="tel" value="${address.phone}" name="phone"></div>
            </div>
            <div class="address">
                <div class="title">地址*</div>
                <div class="country">国别 / 区域*

                    <select name="countryId" id="countryId" class="">
                        <c:forEach var="row" items="${countryList}">
                            <option value="${row.id}" <c:if test="${row.id==address.countryId}">selected="selected"</c:if> >${row.name}</option>
                        </c:forEach>
                    </select>
                    </div>
                <div class="province">省/州* <input type="text" value="${address.province}" name="provinceId"> </div>
                <div class="city">城区* <input type="text" value="${address.city}" name="cityId"> </div>
                <div class="detailedAddress">详细地址: <input type="text" value="${address.address}" name="address"></div>
                <div class="more">地址更多信息: <input type="text" value="${address.memo}" name="memo"></div>
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

        var title = '${address.title}';
        $("select[name=title]").find("option").each(function () {
            console.log(title+" "+$(this).val());
            if(title==$(this).val()){
                $(this).attr("selected","selected");
            }
        });

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
            $.post("/checkout/updateShipAddress",data,function (data) {
                if(data.code==200){
                    layer.msg(data.message);
                }else{
                    layer.msg(data.message);
                }
            });
        });

    });
</script>
