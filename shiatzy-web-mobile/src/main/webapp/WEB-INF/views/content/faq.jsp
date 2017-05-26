<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<div class="dx-CommonProblems">
    <div class="dx-title">常见问题 <a href="javascript:history.go(-1)">返回上页</a></div>
    <div class="content">
        <c:forEach var="itemDomain" items="${domainList}">
            <p class="first-title j_toggle"><a href="#">${sessionScope.language=='en_US'?itemDomain.en_title:itemDomain.title}</a></p>
            <c:forEach var="domainlist" items="${itemDomain.contentItemDomainList}">
                <ul class="second-title j_toggle2 hide">
                            <li><a href="#">${sessionScope.language=='en_US'?domainlist.en_title:domainlist.title}</a>
                                <p class="answer hide">${sessionScope.language=='en_US'?domainlist.en_content:domainlist.content}</p>
                            </li>

                </ul>
            </c:forEach>

        </c:forEach>
       <%-- <p class="first-title j_toggle"><a href="#">我的账户管理</a></p>--%>
        <%--<ul class="second-title j_toggle2 hide">
            <li><a href="#">怎样管理我的账号？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
            <li><a href="#">Art Club会员登录？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
        </ul>--%>
        <%--<p class="first-title j_toggle"><a href="#">配送时间和费用</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><a href="#">怎样管理我的账号？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
            <li><a href="#">Art Club会员登录？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="#">付款方式</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><a href="#">怎样管理我的账号？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
            <li><a href="#">Art Club会员登录？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="#">退货和退款</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><a href="#">怎样管理我的账号？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
            <li><a href="#">Art Club会员登录？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="#">在线购物指南</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><a href="#">怎样管理我的账号？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
            <li><a href="#">Art Club会员登录？</a>
                <p class="answer hide">在“我的账户”内，点击相关会员验证，输入您于会员申请时留存的手机号码，点击验证即可。
                    <br>会员信息登入将会使您的线上购物享有与线下购物一样的优惠政策，详情可查看“会员政策”说明。</p>
            </li>
        </ul>--%>
    </div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
    <script>
        $(function(){

            //常见问题页面JS
            //下拉菜单展开收起
            $(".j_toggle").on("click",function () {
                $(this).next().toggleClass("hide");
            });

            $(".j_toggle2>li").on("click",function () {
                $(this).find(".answer").toggleClass("hide");
            });

        });
    </script>

