<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的订阅 </p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< 回上页</a>
</div>
<div class="my-account">
    <div class="contact">
        <h3>我同意Shop.shiatzychen.com 通过以下方式与我联系</h3>
        <div>
            <span class="mr-2"><label class="radiobox"><input type="radio" name="subscribe" data-value="1"><i class="i-radiobox"></i>快递及邮件</label></span>
        </div>
        <div>
            <span class="mr-2"><label class="radiobox"><input type="radio" name="subscribe" data-value="2"><i class="i-radiobox"></i>短信</label></span>
        </div>
        <div>
            <span class="mr-2"><label class="radiobox"><input type="radio" name="subscribe" data-value="3"><i class="i-radiobox"></i>彩信</label></span>
        </div>
        <div>
            <span class="mr-2"><label class="radiobox"><input type="radio" name="subscribe" data-value="4"><i class="i-radiobox"></i>我希望收到夏姿电商產品信息</label></span>
        </div>
    </div>
    <div id="showInfo" style='text-align: center;color: red;display: none'>请先选择类型</div>
    <div class="finish">
        <a href="#" id="saveBtn">保存</a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">>　</span>
            <span style="float: left;">隐私政策</span>
        </a>
    </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {

        $('input[name="subscribe"]').click(function () {
            $("#showInfo").hide();
        });

        $("#saveBtn").click(function () {
            var info = $('input[name="subscribe"]:checked ').attr("data-value");
            if(typeof (info)=="undefined"){
                $("#showInfo").show();
                return false;
            }
            $.post("/u/account/setSubscribe",{"subscribeType":info},function (data) {
                if(data.code==200){
                    location.href = "/u/account/details";
                }
            });
        });

    });

</script>