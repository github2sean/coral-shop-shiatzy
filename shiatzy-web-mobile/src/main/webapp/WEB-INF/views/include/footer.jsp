<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a href="javascript:;" id="j_back_top" class="back-top">返回顶部</a>
<footer class="footer font-12">
    <ul class="do-list-btm j_drop_down">
        <li><a href="/u/account/index">我的账户</a></li>
        <li><a href="/content/faq">常见问题</a></li>
        <li><a href="">特别服务</a></li>
        <li>
            <a href="javascript:;">关于我们</a>
            <ul class="do-sub-list-btm">
                <li><a href="">官方网站</a></li>
                <li><a href="">联系我们</a></li>
                <li><a href="">隐私声明</a></li>
            </ul>
        </li>
    </ul>
    <ul class="do-list-lang">
        <li><a href="">选择其他国家或地区</a></li>
        <li><a href="">选择語言</a></li>
    </ul>
    <div class="do-subscribe-w">
        <p>订阅最新动态资讯</p>
        <div class="do-subscribe-from">
            <form action="">
                <input type="text" placeholder="请输入电子邮件地址" class="do-fill-email">
                <button class="do-btn-subscribe">订阅</button>
            </form>
        </div>
    </div>
    <div class="do-online-service">
        <p class="do-online-t">在线客户服务</p>
        <p>中文电话 4008 213 760</p>
        <p>(仅限中国大陆地区)</p>
        <p>周一至周五 北京时间上午9点至晚间6点</p>
        <p class="mt-1">中/英文电子邮件 customercare@shiatzychen.com</p>
        <p>(支持所有区域问询)</p>
    </div>
    <div class="text-center do-copy">沪ICP备16022295号-1 创姿服饰（上海）有限公司</div>
</footer>
</div>
</div>
<!-- 核心js插件开始 -->
<script src="${ctx}/static/js/dookayui.min.js"></script>
<script src="${ctx}/static/js/plugins/layer/layer.js"></script>
<!-- 核心js插件 结束 -->
<!-- 页面插件开始 -->
<script src="${ctx}/static/js/common.js"></script>
<script src="${ctx}/static/js/plugins/validator/jquery.validator.min.js"></script>
<script src="${ctx}/static/js/plugins/validator/local/zh-CN.js"></script>
<script src="${ctx}/static/js/backend.js"></script>
<!-- 页面插件结束 -->
<!-- js页面应用 开始 -->
<script>
    $(function () {
        commonApp.init();
        backendApp.init();
    });
</script>
<script>
    $(function () {
        $("#searchBtn").click(function () {
            $("#contentForm").submit();
        });
    });

</script>
<!-- js页面应用 结束 -->
</body>
</html>