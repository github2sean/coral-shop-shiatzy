<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<style>
    .first-title .second-title{display: none}
    .first-title.active .second-title {display: block}
    .first-title.active .second-title li .answer{display: none}
    .first-title.active .second-title li.active .answer{display: block}
</style>
<div class="dx-CommonProblems">
    <div class="dx-title"><spring:message code="specialService"/> <a href="javascript:history.go(-1)"><spring:message code="goBack"/></a></div>
    <div class="content">
        <ul>
                <li class="first-title j_collapse">
                    <a href="#"><spring:message code="service.line1.name"/></a>
                    <ul class="second-title">
                        <li><p href="#"> 1.<spring:message code="service.line1.line1"/></p>
                        </li>
                        <li><p href="#"> 2.<spring:message code="service.line1.line2"/></p>
                        </li>
                        <li><p href="#"> 3.<spring:message code="service.line1.line3"/></p>
                        </li>
                        <li><p href="#"> 4.<spring:message code="service.line1.line4"/><span style="float: right"> ></span></p>
                            <p class="answer hide">&nbsp;&nbsp;4.1 <spring:message code="service.line1.line4.line1"/>
                                <br>&nbsp;&nbsp;4.2 <spring:message code="service.line1.line4.line2"/>
                                <br>&nbsp;&nbsp;4.3 <spring:message code="service.line1.line4.line3"/>
                                <br>&nbsp;&nbsp;4.4 <spring:message code="service.line1.line4.line4"/>
                                <br>&nbsp;&nbsp;4.5 <spring:message code="service.line1.line4.line5"/>
                                <br>&nbsp;&nbsp;4.6 <spring:message code="service.line1.line4.line6"/>
                                <br>&nbsp;&nbsp;4.7 <spring:message code="service.line1.line4.line7"/>
                                <br>&nbsp;&nbsp;4.8 <spring:message code="service.line1.line4.line8"/>
                            </p>
                        </li>
                        <li><p href="#"> 5.<spring:message code="service.line1.line5"/></p>
                        </li>
                        <li><p href="#"> 6.<spring:message code="service.line1.line6"/></p>
                        </li>
                    </ul>
                </li>
            <li class="first-title j_collapse">
                <a href="#"><spring:message code="service.line2.name"/></a>
                <ul class="second-title ">
                    <li><p href="#"><spring:message code="service.line2.content"/></p>
                    </li>
                </ul>
            </li>
            <li class="first-title j_collapse">
                <a href="#"><spring:message code="service.line3.name"/></a>
                <ul class="second-title ">
                    <li><p href="#"><spring:message code="service.line3.content"/></p>
                    </li>
                </ul>
            </li>
            <li class="first-title j_collapse">
                <a href="#"><spring:message code="service.line4.name"/></a>
                <ul class="second-title ">
                    <li><p href="#"><spring:message code="service.line4.content"/></p>
                    </li>
                </ul>
            </li>
        </ul>


    </div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
    <script>
        $(function(){

            $(".j_collapse>a").on("click", function () {
                $(this).parent().siblings().removeClass("active");
                $(this).parent().toggleClass("active");
            });

        });
    </script>

