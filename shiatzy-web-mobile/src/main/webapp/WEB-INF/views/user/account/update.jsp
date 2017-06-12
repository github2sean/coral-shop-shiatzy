<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    select, input {
        border: none;
        margin-left: 10px;
    }
</style>

<div class="order">
    <p style="float: left"><spring:message code="myAccount"/> / <spring:message
            code="account.personal.update.enter"/></p>
    <a style="float: right;" href="/u/account/details">< <spring:message code="goBack"/></a>
</div>

<div class="setting">
    <form class="j_ajaxForm" method="post" action="/u/account/update">
        <div class="data-setting">
            <h3><spring:message code="account.personal.update.info"/></h3>
            <ul>
                <li class="form-item2">
                    <span><spring:message code="account.personal.update.title"/>*
                        <input type="text"
                               value="${customerAddressDomain.title}"
                               name="customerAddressDomain.title"
                               id="title"
                               data-rule="${customerAddressDomain.title}:required;"/></span>
                    <span style="float:right;"></span>
                </li>
                <li class="form-item2">

                    <span><spring:message code="account.personal.firstName"/>*
                        <input type="text"
                               value="${customerDomain.firstName}"
                               name="customerDomain.firstName"
                               id="lastName"
                               data-rule="${customerDomain.firstName}:required;"/></span>
                    <span style="float:right;"></span>

                </li>
                <li class="form-item2"><spring:message code="account.personal.lastName"/>*
                    <input type="text"
                           value="${customerDomain.lastName}"
                           name="customerDomain.lastName"
                           id="firstName"
                           data-rule="${customerDomain.lastName}:required;"/></li>
                <li class="form-item2">
                    <spring:message code="account.personal.update.birthday"/>
                    <input type="date" name="customerDomain.birthday"
                           value="<fmt:formatDate value="${customerDomain.birthday}" type="date" pattern="yyyy-MM-dd"/>"
                           data-rule="${customerDomain.birthday}:required;"/>
                </li>
                <li class="form-item2"><spring:message code="account.personal.phoneNum"/>*
                    <input type="text"
                           value="${customerDomain.phone}"
                           name="customerDomain.phone" id="phone"
                           data-rule="${customerDomain.phone}:required;"/>
                </li>
            </ul>
        </div>
        <div class="data-setting">
            <h3><spring:message code="account.personal.update.shipAddress"/></h3>
            <ul>
                <li>
                    <span><spring:message code="account.personal.update.country"/>*</span>
                    <label style="margin-left: 10px;border: 1px;">
                        <select name="customerAddressDomain.countryId" style="border: 1px;margin-bottom: 5px;"
                                id="countryId">
                            <option value="-1"><spring:message code="account.personal.update.select"/></option>
                            <c:forEach items="${countryList}" var="row">
                                <option value="${row.id}"
                                        <c:if test="${customerAddressDomain.countryId==row.id}">selected="selected"</c:if> >${sessionScope.language=='en_US'?row.enName: row.name}</option>
                            </c:forEach>
                        </select>
                    </label>
                </li>
                <li>
                    <span><spring:message code="account.personal.update.state"/>*</span>
                    <input type="text" name="customerAddressDomain.province" id="province"
                           value="${customerAddressDomain.province}"
                           data-rule="${customerAddressDomain.province}:required;">
                </li>
                <li>
                    <span><spring:message code="account.personal.update.city"/>*</span>
                    <input type="text" name="customerAddressDomain.city" id="city"
                           value="${customerAddressDomain.city}"
                           data-rule="${customerAddressDomain.city}:required;">
                </li>
                <li><span style="margin-top: 0"><spring:message code="account.personal.address"/>*</span>
                    <input style="width: 70%;" type="text" name="customerAddressDomain.address"
                           value="${customerAddressDomain.address}"
                           data-rule="${customerAddressDomain.address}:required;">
                    </input>
                </li>
                <li><spring:message code="account.personal.update.postalCode"/><input type="text"
                                                                                      value="${customerAddressDomain.postalCode}"/>
                    <input type="hidden" value="${customerAddressDomain.id}" name="addressId"/></li>
            </ul>
        </div>
        <div id="showInfo" style="display: none;color: red;text-align: center;width: 100%"></div>
        <div class="complete">
            <button type="submit" class="btn btn-default"><spring:message code="account.personal.update.enter"/></button>
        </div>
    </form>

</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    //姓氏验证
    function firstName() {
        var firstName = $('#firstName').val();
        if (firstName == '') {
            $("#showInfo").show().text("姓氏不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //名字验证
    function lastName() {
        var lastName = $('#lastName').val();
        if (lastName == '') {
            $("#showInfo").show().text("名字不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //称谓验证
    function title() {
        var title = $('#title').val();
        if (title == '') {
            $("#showInfo").show().text("称谓不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //电话验证
    function phone() {
        var phone = $('#phone').val();
        if (phone == '') {
            $("#showInfo").show().text("电话不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //国家验证
    function countryId() {
        var myselect = document.getElementById("countryId");
        var countryId = myselect.value;
        if (countryId == '') {
            $("#showInfo").show().text("国家/区域不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //省州验证
    function province() {
        var province = $('#province').val();
        if (province == '') {
            $("#showInfo").show().text("省州不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    //地区验证
    function city() {
        var city = $('#city').val();
        if (city == '') {
            $("#showInfo").show().text("地区不能为空！");
            return false;
        }
        $("#showInfo").hide();
        return true;
    }
    $(function () {
        $(".top-right-nav").find("li:eq(2)").addClass("active");

        $('#firstName').focus(function () {

        }).blur(firstName);
        $('#lastName').focus(function () {

        }).blur(lastName);
        $('#title').focus(function () {

        }).blur(title);
        $('#countryId').focus(function () {

        }).blur(countryId);
        $('#province').focus(function () {

        }).blur(province);
        $('#city').focus(function () {

        }).blur(city);
        $('#phone').focus(function () {

        }).blur(phone);

      /*  $(".updateBtn").click(function () {
            var $updateAccountForm = $(".updateAccountForm");
            var data = $updateAccountForm.serializeArray();

            $.post("/u/account/update", data, function (data) {
                console.log(data.message);
                $("#showInfo").show().text(data.message);
                location.href = "/u/account/details";
            });
        });*/
    });

</script>
