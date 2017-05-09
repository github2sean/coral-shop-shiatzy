<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的账户</p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< 回上页</a>
</div>
<div class="my-account">
    <div style="display: inline-block;" class="my-account-title">
        <p style="margin-right: 1.7005rem;">欢迎您</p>
        <c:if test="${empty customerDomain}"><p>${user_context.userName}</p></c:if>
        <c:if test="${not empty customerDomain}"><p>${customerDomain.lastName}${customerDomain.firstName}</p></c:if>
    </div>
    <div class="account-message">
        <ul>
            <li>
                <a href="/u/account/toUpdate">
                    <span>帐户详情</span>
                    <span style="float: right;">编辑 ></span>
                </a>
            </li>
            <li class="deit-message">
                <ol>
                    <li>名字：${customerDomain.firstName}</li>
                    <li>姓氏：${customerDomain.lastName}</li>
                    <li>电邮：${accountDomain.email}</li>
                    <li>电话号码：${customerDomain.phone}</li>
                    <li>地址：${customerAddressDomain.address}</li>
                </ol>
            </li>
            <li>
                <a href="#">
                    <c:choose>
                        <c:when test="${customerDomain.isArtClubMember==0}">
                            <span>会员等级：非会员</span>
                        </c:when>
                        <c:when test="${customerDomain.isArtClubMember==1}">
                            <span>会员等级：${customerDomain.customerLevel}</span>
                        </c:when>
                    </c:choose>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="/u/account/toUpdateEmail">
                    <span>邮箱及密码信息修改 </span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="/u/account/toValidVip">
                    <span>ART CLUB会员认证 </span>
                    <span style="float: right;">></span>
                </a>
            </li>
            <li>
                <a href="#">
                    <span>我的订阅</span>
                    <span style="float: right;">></span>
                </a>
            </li>
        </ul>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">>　</span>
            <span style="float: left;">隐私权政策</span>
        </a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">>　</span>
            <span style="float: left;">隐私权政策</span>
        </a>
    </div>

    <script>
        $(function () {

            console.log(${accountDomain});
            console.log(${customerDomain});
        });
    </script>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

