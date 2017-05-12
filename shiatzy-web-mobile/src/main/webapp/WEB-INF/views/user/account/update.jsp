<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的帐户 / 个人信息</p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< 回上页</a>
</div>

<div class="setting">
    <form class="updateAccountForm" method="post" action="/u/account/update">
    <div class="data-setting">
        <h3>资料设定</h3>
        <ul>
            <li>
                <span>称谓*<input type="text"  value="${customerAddressDomain.title}" name="customerAddressDomain.title"/></span>
                <span style="float:right;"></span>
            </li>
            <li>
                <span>名字*<input type="text"  value="${customerDomain.firstName}" name="customerDomain.firstName"/></span>
                <span style="float:right;"></span>
            </li>
            <li>姓氏*<input type="text"  value="${customerDomain.lastName}" name="customerDomain.lastName"/></li>
            <li>
                生日
                <input type="date"   name=""/>
            </li>
            <li>电话号码*<input type="text"  value="${customerDomain.phone}" name="customerDomain.phone"/></li>
        </ul>
    </div>
    <div class="data-setting">
        <h3>地址设定</h3>
        <ul>
            <li>
                <span>国别 / 区域*</span>
                <label  style="float: right;border: 1px;">
                    <select name="customerAddressDomain.countryId" style="border: 1px;margin-bottom: 5px;">
                        <option value="-1">请选择</option>
                        <option value="1">中国</option>
                        <option value="2">美国</option>
                    </select>
                </label>
            </li>
            <li>
                <span>省/州*</span>
                <input type="text" name="customerAddressDomain.province">
            </li>
            <li>
                <span>城区*</span>
                <input type="text" name="customerAddressDomain.city">
            </li>
            <li>详细地址<input type="text"  value="${customerAddressDomain.address}" name="customerAddressDomain.address"/></li>
            <li>邮编<input type="text"  /><input type="hidden"  value="${customerAddressDomain.id}" name="addressId"/></li>
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
    $(function () {

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
