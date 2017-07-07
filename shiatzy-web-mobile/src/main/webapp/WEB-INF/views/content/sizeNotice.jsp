<%@ page import="com.dookay.coral.common.model.ImageModel" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="/WEB-INF/views/include/taglib.jsp" %>
    <html style="width: 100%">
      <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <meta name="renderer" content="webkit">
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <title>
          <%=request.getParameter("pageTitle")%>-
            <spring:message code="shiatzhChen" />
        </title>
        <!-- 核心样式 开始 -->
        <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
        <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css">
        <link rel="stylesheet" href="${ctx}/static/css/dookayui.css">
        <!-- 核心样式 结束 -->
        <!-- 页面样式 开始 -->
        <link rel="stylesheet" href="${ctx}/static/css/common.css">
        <link rel="stylesheet" href="${ctx}/static/css/dx.css">
        <link rel="stylesheet" href="${ctx}/static/css/dy.css">
        <link rel="stylesheet" href="${ctx}/static/js/plugins/validator/css/jquery.validator.css">
        <!-- 页面样式 结束 -->
        <style>
          p{width: 100%}

        </style>
        <!--设置浏览器根元素的值-->
        <script>
          (function (doc, win) {
            var docEl = doc.documentElement,
              resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
              recalc = function () {
                var clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                docEl.style.fontSize = 10 * (clientWidth / 320) + 'px';
              };
            if (!doc.addEventListener) return;
            win.addEventListener(resizeEvt, recalc, false);
            doc.addEventListener('DOMContentLoaded', recalc, false);
          })(document, window);
        </script>
      </head>
      <body  style="width: 88%;padding-left: 10px;padding-right: 10px;">
      <div class="dx-CommonProblems" style="width: 90%;font-size: 1.2rem;">
        <div class="content" style="width: 100%;padding-bottom: 50px">
          <h3 style="text-align: center;font-size: 1.6rem">尺码建议91</h3>
          <p class=""><a href="#">如无特别说明，我们统一采用法码标准，以下表格可以帮助您根据实际尺寸所对应的各地区不同尺码进行参照。</a></p>
          <table class="table table-bordered ">
            <thead style="font-size:12px ">
              <tr>
                <th COLSPAN="6" STYLE="text-align: center">女士成衣尺码对照表
                </th>
              </tr>
            </thead>
            <tbody style="font-size:10px ">
              <tr>
                <td>意大利(IT)</td>
                <td>38</td>
                <td>40</td>
                <td>42</td>
                <td>44</td>
                <td>46</td>
              </tr>
              <tr>
                <td>法国 (FR)</td>
                <td>36</td>
                <td>38</td>
                <td>40</td>
                <td>42</td>
                <td>44</td>
              </tr>
              <tr>
                <td>德国 (DE)</td>
                <td>32</td>
                <td>34</td>
                <td>36</td>
                <td>38</td>
                <td>40</td>
              </tr>
              <tr>
                <td>台湾 (TW)</td>
                <td>6</td>
                <td>8</td>
                <td>10</td>
                <td>12</td>
                <td>14</td>
              </tr>
              <tr>
                <td>日本 (JP)</td>
                <td>7</td>
                <td>9</td>
                <td>11</td>
                <td>13</td>
                <td>15</td>
              </tr>
              <tr>
                <td>美国 (US)</td>
                <td>0</td>
                <td>2-4</td>
                <td>6</td>
                <td>8</td>
                <td>10-12</td>
              </tr>
              <tr>
                <td>澳大利亚 (AUS)</td>
                <td>4</td>
                <td>6-8</td>
                <td>10</td>
                <td>12</td>
                <td>14-16</td>
              </tr>
              <tr>
                <td>英国 (UK)</td>
                <td>4</td>
                <td>6-8</td>
                <td>10</td>
                <td>12</td>
                <td>14-16</td>
              </tr>
              <tr>
                <td>国际 (Intl)</td>
                <td>XS</td>
                <td>X</td>
                <td>S-M</td>
                <td>M-L</td>
                <td>L</td>
              </tr>
              <tr>
                <td>中国 (CN)</td>
                <td>155</td>
                <td>160</td>
                <td>165</td>
                <td>170</td>
                <td>175</td>
              </tr>
              <tr>
                <td>胸围 (厘米)</td>
                <td>78-82</td>
                <td>83-87 </td>
                <td>88-92</td>
                <td>93-97</td>
                <td>98-102</td>
              </tr>
              <tr>
                <td>腰围 (厘米)</td>
                <td>59-63</td>
                <td>64-68 </td>
                <td>69-73</td>
                <td>74-78</td>
                <td>79-83</td>
              </tr>
              <tr>
                <td>臀围 (厘米)</td>
                <td>85-89</td>
                <td>90-94 </td>
                <td>95-99</td>
                <td>100-104</td>
                <td>105-109</td>
              </tr>
            </tbody>
          </table>
          <table class="table table-bordered ">
            <thead style="font-size:12px ">
              <tr>
                <th COLSPAN="6" STYLE="text-align: center">男士成衣尺码对照表
                </th>
              </tr>
            </thead>
            <tbody style="font-size:10px ">

              <tr>
                <td>法国 (FR)</td>
                <td>46</td>
                <td>48</td>
                <td>50</td>
                <td>52</td>
                <td>54</td>
              </tr>
              <tr>
                <td>国际 (Intl)</td>
                <td>S</td>
                <td>M</td>
                <td>L</td>
                <td>XL</td>
                <td>XXL</td>
              </tr>
              <tr>
                <td>中国 (CN)</td>
                <td>170</td>
                <td>175</td>
                <td>180</td>
                <td>185</td>
                <td>190</td>
              </tr>
              <tr>
                <td>胸围 (厘米)</td>
                <td>83-87</td>
                <td>88-92 </td>
                <td>93-97</td>
                <td>98-102</td>
                <td>103-107</td>
              </tr>
              <tr>
                <td>腰围 (厘米)</td>
                <td>74-78</td>
                <td>79-83 </td>
                <td>84-88</td>
                <td>89-93</td>
                <td>94-98</td>
              </tr>
              <tr>
                <td>臀围 (厘米)</td>
                <td>93-97</td>
                <td>98-102</td>
                <td>103-107</td>
                <td>108-112</td>
                <td>113-117</td>
              </tr>


            </tbody>
          </table>
          <div class="table-responsive">
            <table class="table table-bordered ">
              <thead style="font-size:12px ">
                <tr>
                  <th COLSPAN="13" STYLE="text-align: center">女士鞋履尺码对照表
                  </th>
                </tr>
              </thead>
              <tbody style="font-size:10px ">

                <tr>
                  <td>法国 (FR)</td>
                  <td>35</td>
                  <td>36</td>
                  <td>36.5</td>
                  <td>37</td>
                  <td>37.5</td>
                  <td>38.5</td>
                  <td>38.5</td>
                  <td>39</td>
                  <td>40</td>
                  <td>41</td>
                  <td>42</td>
                  <td>43</td>

                </tr>
                <tr>
                  <td>中国 (CN)</td>
                  <td>35</td>
                  <td>36</td>
                  <td>36.5</td>
                  <td>37</td>
                  <td>37.5</td>
                  <td>38.5</td>
                  <td>38.5</td>
                  <td>39</td>
                  <td>40</td>
                  <td>41</td>
                  <td>42</td>
                  <td>43</td>
                </tr>
                <tr>
                  <td>意大利(IT)</td>
                  <td>34</td>
                  <td>35</td>
                  <td>35.5</td>
                  <td>36</td>
                  <td>36.5</td>
                  <td>37.5</td>
                  <td>37.5</td>
                  <td>38</td>
                  <td>39</td>
                  <td>40</td>
                  <td>41</td>
                  <td>42</td>
                </tr>
                <tr>
                  <td>美国 (US)</td>
                  <td>4</td>
                  <td>5</td>
                  <td>5.5</td>
                  <td>6</td>
                  <td>6.5</td>
                  <td>7.5</td>
                  <td>7.5</td>
                  <td>8</td>
                  <td>9</td>
                  <td>10</td>
                  <td>11</td>
                  <td>12</td>
                </tr>
                <tr>
                  <td>英国 (UK)</td>
                  <td>1</td>
                  <td>2</td>
                  <td>2.5</td>
                  <td>3</td>
                  <td>3.5</td>
                  <td>4.5</td>
                  <td>4.5</td>
                  <td>5</td>
                  <td>6</td>
                  <td>7</td>
                  <td>8</td>
                  <td>9</td>
                </tr>
              </tbody>
            </table>
          </div>
         <div class="table-responsive">
            <table class="table table-bordered ">
             <thead style="font-size:12px ">
               <tr>
                 <th COLSPAN="13" STYLE="text-align: center">男士鞋履尺码对照表
                 </th>
               </tr>
             </thead>
             <tbody style="font-size:10px ">

               <tr>
                 <td>欧洲码 (EU)</td>
                 <td>40</td>
                 <td>41</td>
                 <td>42</td>
                 <td>42.5</td>
                 <td>43</td>
                 <td>43.5</td>
                 <td>44</td>
                 <td>44.5</td>
                 <td>45</td>
                 <td>46</td>
               </tr>
               <tr>
                 <td>美国 (US)</td>
                 <td>7</td>
                 <td>8</td>
                 <td>9</td>
                 <td>9.5</td>
                 <td>10</td>
                 <td>10.5</td>
                 <td>11</td>
                 <td>11.5</td>
                 <td>12</td>
                 <td>13</td>
               </tr>
               <tr>
                 <td>英国 (UK)</td>
                 <td>6</td>
                 <td>7</td>
                 <td>8</td>
                 <td>8.5</td>
                 <td>9</td>
                 <td>9.5</td>
                 <td>10</td>
                 <td>10.5</td>
                 <td>11</td>
                 <td>12</td>
               </tr>
             </tbody>
           </table>
         </div>
          <table class="table table-bordered " style="table-layout: fixed">
            <thead style="font-size:12px ">
              <tr>
                <th COLSPAN="8" STYLE="text-align: center">其它配饰尺码对照表（腰带）
                </th>
              </tr>
            </thead>
            <tbody style="font-size:10px">
              <tr>
                <td >法国 </td>
                <td>70</td>
                <td>75</td>
                <td>80</td>
                <td>85</td>
                <td>90</td>
                <td>95</td>
                <td>100</td>
              </tr>
              <tr>
                <td>意大利 (ITALY)</td>
                <td>38</td>
                <td>40</td>
                <td>42</td>
                <td>44</td>
                <td>46</td>
                <td>16</td>
                <td>50</td>
              </tr>
              <tr>
                <td>英国 (UK)</td>
                <td>6</td>
                <td>8</td>
                <td>10</td>
                <td>12</td>
                <td>14</td>
                <td>16</td>
                <td>18</td>
              </tr>
              <tr>
                <td>美国 (US)</td>
                <td>0</td>
                <td>2-4</td>
                <td>4-6</td>
                <td>8</td>
                <td>10</td>
                <td>12</td>
                <td>14</td>
              </tr>
            </tbody>
          </table>
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
        <script>
          $(function () {
            //常见问题页面JS
            //下拉菜单展开收起
            $(".j_toggle").on("click", function () {
              $(this).next().toggleClass("hide");
            });

            $(".j_toggle:first").on("click", function () {
              $(this).siblings(".tfoot").toggleClass("hide");
            });

            $(".j_toggle:not(:first)").on("click", function () {
              $(this).siblings(".tfoot").addClass("hide");
              $(this).siblings("table").addClass("hide");
            });

            $(".j_toggle2>li").on("click", function () {
              $(this).find(".answer").toggleClass("hide");
            });
          });
        </script>
      </div>
      </body>
    </html>