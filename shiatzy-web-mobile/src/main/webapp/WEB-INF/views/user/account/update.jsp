<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的帐户 / 个人信息</p>
    <a style="float: right;" href="/u/account/details">< 回上页</a>
</div>

<div class="setting">
    <form>
    <div class="data-setting">
        <h3>资料设定</h3>
        <ul>
            <li>
                <span>称谓*<input type="text"  value="${customerAddressDomain.title}" /></span>
                <span style="float:right;">v</span>
            </li>
            <li>
                <span>名字*<input type="text"  value="${customerDomain.lastName}" /></span>
                <span style="float:right;">v</span>
            </li>
            <li>姓氏*<input type="text"  value="${customerDomain.firstName}" /></li>
            <li>
                <span>生日</span>
                <ul style="float:right;" class="date">
                    <li>
                        <span>v</span>
                        <span>年</span>
                    </li>
                    <li>
                        <span>v</span>
                        <span>月</span>
                    </li>
                    <li>
                        <span>v</span>
                        <span>日</span>
                    </li>
                </ul>
            </li>
            <li>电话号码*<input type="text"  value="${customerDomain.phone}" /></li>
        </ul>
    </div>
    <div class="data-setting">
        <h3>地址设定</h3>
        <ul>
            <li>
                <span>国别 / 区域*</span>
                <span style="float:right;">v</span>
            </li>
            <li>
                <span>省/州*</span>
                <span style="float:right;">v</span>
            </li>
            <li>
                <span>城区*</span>
                <span style="float:right;">v</span>
            </li>
            <li>详细地址<input type="text"  value="${customerAddressDomain.adress}" /></li>
            <li>郵編<input type="text"  /></li>
        </ul>
    </div>
    <div class="complete">
        <a type="button" class="updateBtn">完成</a>
    </div>
</form>
    <script>
        $(function () {

            $("input").css({"border":"0px","margin-left":"10px"});

            $(".updateBtn").click(function () {

                console.log(1);

            });
        });

    </script>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

