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
    .dx-EditAddress .recipients div, .dx-EditAddress .address div{
        font-size: 1.2rem;
    }
    .dx-EditAddress .content .btn{
        font-size: 1.2rem;
    }
</style>
<form class="addressForm" method="post" action="/checkout/addAddress">
    <div class="dx-EditAddress">
        <div class="content">
            <div class="title"><spring:message code="update.delivery"/> <a href="javascript:" onclick="self.location=document.referrer;"><spring:message code="goBack"/></a></div>
            <div class="recipients">
                <input type="hidden" value="${address.id}" name="id">
                <div class="title"><spring:message code="add.delivery.shippingman"/>*</div>
                <div class="name form-item2" ><spring:message code="account.personal.firstName"/>* <input type="text" value="${address.firstName}" name="firstName" data-rule="<spring:message code='account.personal.firstName'/>:required;"></div>
                <div class="name form-item2" ><spring:message code="account.personal.lastName"/>* <input type="text" value="${address.lastName}" name="lastName" data-rule="<spring:message code='account.personal.lastName'/>:required;"></div>
                <div class="appellation "><spring:message code="account.personal.update.title"/>
                    <select name="title" id="title" class="" value="${address.title}">
                        <option value="女士"><spring:message code="add.delivery.ms"/></option>
                        <option value="先生"><spring:message code="add.delivery.mr"/></option>
                        <option value="小姐"><spring:message code="add.delivery.miss"/></option>
                        <option value="无"><spring:message code="add.delivery.none"/></option>
                    </select>
                </div>
                <div class="tel form-item2"><spring:message code="delivery.address.phone"/>* <input type="tel" value="${address.phone}" name="phone" data-rule="<spring:message code='delivery.address.phone'/>:required;mobile"></div>
            </div>
            <div class="address">
                <div class="title"><spring:message code="account.personal.address"/></div>
                <div class="country"><spring:message code="add.delivery.country"/>/ <spring:message code="add.delivery.zone"/>*

                    <select name="countryId" id="countryId" class="">
                        <c:forEach var="row" items="${countryList}">
                            <option value="${row.id}" <c:if test="${row.id==address.countryId}">selected="selected"</c:if> >${web:selectLanguage()=='en_US'?row.enName:row.name}</option>
                        </c:forEach>
                    </select>
                    </div>
                <div class="province form-item2"><spring:message code="account.personal.update.state"/>* <input type="text" value="${address.province}" name="province" data-rule="<spring:message code='account.personal.update.state'/>:required;"> </div>
                <div class="city form-item2"><spring:message code="account.personal.update.city"/>* <input type="text" value="${address.city}" name="city" data-rule="<spring:message code='account.personal.update.city'/>:required;"> </div>
                <div class="detailedAddress form-item2"><spring:message code="add.delivery.detialAddress"/>* <input type="text" value="${address.address}" name="address" data-rule="<spring:message code='add.delivery.detialAddress'/>:required;"></div>
                <div class="more"><spring:message code="add.delivery.moreinfo"/> <input type="text" value="${address.memo}" name="memo"></div>
                <div class="more form-item2"><spring:message code="account.personal.update.postalCode"/>* <input type="text" value="${address.postalCode}" name="postalCode" data-rule="<spring:message code='account.personal.update.postalCode'/>:required;"></div>
            </div>
            <a href="#"  class="btn completeBtn"><spring:message code="orderinfo.update"/></a>
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

            var isNull = false;
            $(".form-item2").find("input").each(function () {
                if($(this).val()==''){
                    isNull =true;
                }
            });
            if(!isNull){
                var $form = $(".addressForm");
                var data = $form.serializeArray();
                $.post("/checkout/updateShipAddress",data,function (data) {
                    if(data.code==200){
                        layer.msg('<spring:message code="update.delivery.success"/>');
                        setTimeout('self.location=document.referrer;',2000);
                    }else{
                        layer.msg(data.message);
                    }
                });
            }else {
                $(".addressForm").submit();
            }

        });

    });
</script>
