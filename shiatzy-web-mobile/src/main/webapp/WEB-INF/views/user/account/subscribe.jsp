<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="com.dookay.coral.shop.customer.enums.SubscribeTypeEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">我的账户/我的订阅 </p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< 回上页</a>
</div>
<div class="my-account">.
            <div class="contact">
                <h3>我同意 夏资陈 通过以下方式与我联系</h3>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" class="subscribeType" name="subscribeType0" value="0" data-value="0"><i
                            class="i-radiobox iconfont icon-duigou"></i>快递及邮件</label></span>
                </div>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" class="subscribeType" name="subscribeType1" value="1" data-value="1"><i
                            class="i-radiobox iconfont icon-duigou"></i>短信</label></span>
                </div>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" class="subscribeType" name="subscribeType2" value="2" data-value="2"><i
                            class="i-radiobox iconfont icon-duigou"></i>彩信</label></span>
                </div>
            </div>
            <div id="showInfo" style='text-align: center;color: red;display: none'>请先选择类型</div>
            <div class="finish">
                <a href="#" id="saveBtn">完成</a>
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

        var haveCheck = '${customerDomain.subscribeType}';
        console.log("list:"+haveCheck);
        var array;
        if(haveCheck.indexOf(",")==0){
            haveCheck=haveCheck.substr(1);
        }

        if(haveCheck.indexOf(",")>0){
            array = haveCheck.split(",");
        }else{
            array = new Array();
            array[0] = haveCheck;
        }
        console.log("array:"+array);
        $('input[type=checkbox]').each(function () {
            for(var i=0;i<array.length;i++){
                if($(this).val()==array[i]){
                    $(this).attr("checked","checked")
                }
            }
        });


        $('input[name="subscribe"]').click(function () {
            $("#showInfo").hide();
        });

        $("#saveBtn").click(function () {
            var type0 = $('input[name="subscribeType0"]:checked ').attr("data-value");
            var type1 = $('input[name="subscribeType1"]:checked ').attr("data-value");
            var type2 = $('input[name="subscribeType2"]:checked ').attr("data-value");
            var info = {"subscribeType0": type0,"subscribeType1":type1,"subscribeType2":type2};
            if (typeof (info) == "undefined") {
                $("#showInfo").show();
                return false;
            }
            console.log(info);
            $.post("/u/account/setSubscribe",info, function (data) {
                if (data.code == 200) {
                    location.href = "/u/account/details";
                }else{
                    layer.msg("订阅失败");
                }
            });
        });

    });

</script>