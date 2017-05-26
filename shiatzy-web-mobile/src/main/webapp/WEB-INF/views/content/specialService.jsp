<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<div class="dx-CommonProblems">
    <div class="dx-title">特别服务 <a href="javascript:history.go(-1)">返回上页</a></div>
    <div class="content">
        <p class="first-title j_toggle"><a href="#">会员政策</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#"> 1.线下精品店会员线上消费可享有所有原会员政策。</p>
            </li>
            <li><p href="#"> 2.单一注册账户对应单一线下会员卡号，不可重复关联账户。</p>
            </li>
            <li><p href="#"> 3.线上消费时会员信息可于“我的账户”内录入保存，任何线上消费时会员所享相应优惠政策、优惠以及积分将自动累计。</p>
            </li>
            <li><p href="#"> 4.此会员政策目前仅适用于中国(不含港澳台)所有店铺持有兰花卡/墨竹卡/牡丹卡会员。<span style="float: right"> ></span></p>
                <p class="answer hide">&nbsp;&nbsp;4.1 会员卡会员级别有效期周期为12个月，（以该会员成功申请会员卡后第二个月起算，如：2015年1月成功申请会员卡，2015年2月起算）。有效期内，兰花卡会员线上线下累计消费金额RMB 80,000后，可成为墨竹卡会员；有效期内，墨竹卡会员线上线下累计消费金额RMB 150,000后，可成为牡丹卡会员，满额次日升级。
                    <br>&nbsp;&nbsp;4.2 有效期到期，系统将根据会员12个月内消费累计总额自动续级、降级。
                    <br>&nbsp;&nbsp;4.3 有效期内，若会员消费累计总额符合升卡标准，会籍将自动提前升级至对应卡别，并享受对应折扣及优惠（自符合升卡标准之日第二天起生效）。
                    <br>&nbsp;&nbsp;4.4 有效期到期日24点前，若会员消费累计总额未达续卡标准，会籍将自动降级至对应卡别，并享受对应折扣及优惠（自原卡别到期之日第二天起生效）。
                    <br>&nbsp;&nbsp;4.5 有效期到期日24点前，若会员消费累计总额符合续卡标准，会籍自动延续，并继续享受对应折扣及优惠（自原卡别到期之日第二天起生效）。
                    <br>&nbsp;&nbsp;4.6 有效期到期日24点前，若会员消费累计总额未达兰花卡标准则转为临时卡。
                    <br>&nbsp;&nbsp;4.7 升、降、续、停卡累计，均从会员符合各会籍标准后之第二天归零后开始计算。
                    <br>&nbsp;&nbsp;4.8 会员专享折扣: 兰花卡9.2折，墨竹卡9折，牡丹卡8.8折（折扣仅限正价商品）。
                </p>
            </li>
            <li><p href="#"> 5.会员优惠政策与线上活动优惠政策见每次活动条款。</p>
            </li>
            <li><p href="#"> 6.SHIATZY CHEN（夏姿·陈）品牌所属创姿服饰（上海）有限公司保留对会员政策线上精品店使用的最终解释权。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="#">礼物包装</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">SHIATZY CHEN（夏姿·陈）为您呈现专属奢宠礼遇，在线购买任意商品，即可享有奢美礼盒包装。礼物包装部分指运输包装盒内的商品包装。</p>
            </li>

        </ul>
        <p class="first-title j_toggle"><a href="#">免费尺寸更改</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">我们为在线顾客提供服饰类商品（男装、女装）的免费尺寸修改服务。目前此政策适用于中国(不含港澳台)所有店铺。有任何疑问也可联系我们的在线客服人员。
                收到商品后，如有服饰类商品需要尺寸调整，您可选择前往就近线下精品店联系门店人员，我们的门店人员将会为您提供相应的量体服务，为您进行免费尺寸更改。点击查询就近门店信息。</p>
            </li>
        </ul>
        <p class="first-title j_toggle"><a href="#">精品店预约</a></p>
        <ul class="second-title j_toggle2 hide">
            <li><p href="#">为了给您提供更为便捷的购物，在线商品部分提供精品店预约功能，您可在商品页面将喜爱的商品添加至“精品店预约”，于“精品店预约清单”内选择相关精品店进行线下体验。目前该服务仅限于中国大陆地区。
                我们会将您预约清单内的商品配送至指定门店，便于您线下舒适快捷的体验，支付将于线下门店完成。</p>
            </li>
        </ul>
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

