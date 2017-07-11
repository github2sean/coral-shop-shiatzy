<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html style="height: 100%;width: 100%">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <title>
        <%=request.getParameter("pageTitle")%>-
        <spring:message code="shiatzhChen"/>
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
        p {
            width: 100%;
            line-height: 2rem;
        }
        body {
            width: 100%;
            min-height: 100%;
            margin: 0;
            overflow-x: hidden !important;
            position: relative;
            /* background: #000; */
            font-size: 100%;
            line-height: 1;
            font-weight: 400;
            font-family: 'TrajanProRegular', 'SpectrumMT', 'Myriad Pro', 'PingFang TC', 'MHei', 'STHeitiTC-Light','Helvetica', '微軟正黑體' , 'Microsoft JhengHei' , Arial, sans-serif;
            text-rendering: optimizeLegibility;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
            -moz-font-feature-settings: 'liga', 'kern';
            -webkit-overflow-scrolling: touch;
        }
        .dx-container{
            position: fixed;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            z-index: 1000;
            overflow: auto;
        }
        .table {

            margin-bottom: 0;
            margin: auto;
            font-size: .75rem;
            width: 99.8%;

            text-align: center;
        }

        .table td, .table th {
            position: relative;
            white-space: nowrap;
            overflow: hidden;
        }
        .dx-container .content{
            width: 90%;
            margin-right: auto;
            margin-left: auto;}
        .scrollable {
            position: relative;
            margin-bottom: 20px;
            overflow: scroll;
            overflow-y: hidden;
        }
    </style>
    <!--设置浏览器根元素的值-->

</head>
<body>
<div class="dx-container" style="font-size: 1.2rem;">
    <div class="content" style="padding-bottom: 50px">
        <h3 style="text-align: center;font-size: 1.6rem">${web:selectLanguage()=='en_US'?"SIZE GUIDE":"尺码建议"}</h3>
        <p class="">
            <c:if test="${web:selectLanguage()!='en_US'}">
                如无特别说明，我们统一采用法码标准，以下表格可以帮助您根据实际尺寸所对应的各地区不同尺码进行参照。
            </c:if>
            <c:if test="${web:selectLanguage()=='en_US'}">
                The following charts will help you match the size scheme for different product (indicated in the dropdown) to your usual size. All sizes on SHOP.SHIATZYCHEN.COM are French unless otherwise stated.
            </c:if>
        </p>
        <div class="scrollable">
            <table class="table table-bordered ">
                <thead>
                <tr>
                    <th COLSPAN="6" STYLE="text-align: center">
                        <c:if test="${web:selectLanguage()!='en_US'}">
                            女士成衣尺码对照表
                        </c:if>
                        <c:if test="${web:selectLanguage()=='en_US'}">
                            WOMEN'S READY TO WEAR SIZE CHART
                        </c:if>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"ITALY":"意大利(IT)"}</td>
                    <td>38</td>
                    <td>40</td>
                    <td>42</td>
                    <td>44</td>
                    <td>46</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"FRANCE":"法国 (FR)"}</td>
                    <td>36</td>
                    <td>38</td>
                    <td>40</td>
                    <td>42</td>
                    <td>44</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"GERMANY":"德国 (DE)"}</td>
                    <td>32</td>
                    <td>34</td>
                    <td>36</td>
                    <td>38</td>
                    <td>40</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"TAIWAN":"台湾 (TW)"}</td>
                    <td>6</td>
                    <td>8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"JAPAN":"日本 (JP)"}</td>
                    <td>7</td>
                    <td>9</td>
                    <td>11</td>
                    <td>13</td>
                    <td>15</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"US":"美国 (US)"}</td>
                    <td>0</td>
                    <td>2-4</td>
                    <td>6</td>
                    <td>8</td>
                    <td>10-12</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"AUSTRALIA":"澳大利亚 (AUS)"}</td>
                    <td>4</td>
                    <td>6-8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14-16</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"UK":"英国 (UK)"}</td>
                    <td>4</td>
                    <td>6-8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14-16</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"INTERNATIONAL":"国际 (Intl)"}</td>
                    <td>XS</td>
                    <td>X</td>
                    <td>S-M</td>
                    <td>M-L</td>
                    <td>L</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"CHINA":"中国 (CN)"}</td>
                    <td>155</td>
                    <td>160</td>
                    <td>165</td>
                    <td>170</td>
                    <td>175</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"BUST (cm)":"胸围 (厘米)"}</td>
                    <td>78-82</td>
                    <td>83-87</td>
                    <td>88-92</td>
                    <td>93-97</td>
                    <td>98-102</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"WAIST (cm)":"腰围 (厘米)"}</td>
                    <td>59-63</td>
                    <td>64-68</td>
                    <td>69-73</td>
                    <td>74-78</td>
                    <td>79-83</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"HIPS (cm)":"臀围 (厘米)"}</td>
                    <td>85-89</td>
                    <td>90-94</td>
                    <td>95-99</td>
                    <td>100-104</td>
                    <td>105-109</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="scrollable">
            <table class="table table-bordered ">
                <thead>
                <tr>
                    <th COLSPAN="6" STYLE="text-align: center">
                        <c:if test="${web:selectLanguage()!='en_US'}">
                            男士成衣尺码对照表
                        </c:if>
                        <c:if test="${web:selectLanguage()=='en_US'}">
                            MEN'S READY TO WEAR SIZE CHART
                        </c:if>
                    </th>
                </tr>
                </thead>
                <tbody>

                <tr>
                    <td>${web:selectLanguage()=='en_US'?"FRANCE":"法国 (FR)"}</td>
                    <td>46</td>
                    <td>48</td>
                    <td>50</td>
                    <td>52</td>
                    <td>54</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"INTERNATIONAL":"国际 (Intl)"}</td>
                    <td>S</td>
                    <td>M</td>
                    <td>L</td>
                    <td>XL</td>
                    <td>XXL</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"CHINA":"中国 (CN)"}</td>
                    <td>170</td>
                    <td>175</td>
                    <td>180</td>
                    <td>185</td>
                    <td>190</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"BUST (cm)":"胸围 (厘米)"}</td>
                    <td>83-87</td>
                    <td>88-92</td>
                    <td>93-97</td>
                    <td>98-102</td>
                    <td>103-107</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"WAIST (cm)":"腰围 (厘米)"}</td>
                    <td>74-78</td>
                    <td>79-83</td>
                    <td>84-88</td>
                    <td>89-93</td>
                    <td>94-98</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"HIPS (cm)":"臀围 (厘米)"}</td>
                    <td>93-97</td>
                    <td>98-102</td>
                    <td>103-107</td>
                    <td>108-112</td>
                    <td>113-117</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="scrollable">
            <table class="table table-bordered ">
                <thead>
                <tr>
                    <th COLSPAN="13" STYLE="text-align: center">
                        <c:if test="${web:selectLanguage()!='en_US'}">
                            女士鞋履尺码对照表
                        </c:if>
                        <c:if test="${web:selectLanguage()=='en_US'}">
                            WOMEN'S SHOE SIZE CHART
                        </c:if>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"FRANCE":"法国 (FR)"}</td>
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
                    <td>${web:selectLanguage()=='en_US'?"CHINA":"中国 (CN)"}</td>
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
                    <td>${web:selectLanguage()=='en_US'?"ITALY":"意大利(IT)"}</td>
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
                    <td>${web:selectLanguage()=='en_US'?"US":"美国 (US)"}</td>
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
                    <td>${web:selectLanguage()=='en_US'?"UK":"英国 (UK)"}</td>
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
        <div class="scrollable">
        <table class="table table-bordered ">
            <thead>
            <tr>
                <th COLSPAN="11" STYLE="text-align: center">
                    <c:if test="${web:selectLanguage()!='en_US'}">
                        男士鞋履尺码对照表
                    </c:if>
                    <c:if test="${web:selectLanguage()=='en_US'}">
                        MEN'S SHOE SIZE CHART
                    </c:if>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${web:selectLanguage()=='en_US'?"EUROPEAN (EU)":"欧洲码 (EU)"}</td>
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
                <td>${web:selectLanguage()=='en_US'?"US":"美国 (US)"}</td>
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
                <td>${web:selectLanguage()=='en_US'?"UK":"英国 (UK)"}</td>
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
        <div class="scrollable">

            <table class="table table-bordered " >
                <thead>
                <tr>
                    <th COLSPAN="8" STYLE="text-align: center">
                        <c:if test="${web:selectLanguage()!='en_US'}">
                            其它配饰尺码对照表（腰带）
                        </c:if>
                        <c:if test="${web:selectLanguage()=='en_US'}">
                            BELT SIZE CHART
                        </c:if>
                    </th>
                </tr>
                </thead>
                <tbody style="font-size:10px">
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"FRANCE":"法国 (FR)"}</td>
                    <td>70</td>
                    <td>75</td>
                    <td>80</td>
                    <td>85</td>
                    <td>90</td>
                    <td>95</td>
                    <td>100</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"ITALY":"意大利(IT)"}</td>
                    <td>38</td>
                    <td>40</td>
                    <td>42</td>
                    <td>44</td>
                    <td>46</td>
                    <td>16</td>
                    <td>50</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"UK":"英国 (UK)"}</td>
                    <td>6</td>
                    <td>8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14</td>
                    <td>16</td>
                    <td>18</td>
                </tr>
                <tr>
                    <td>${web:selectLanguage()=='en_US'?"US":"美国 (US)"}</td>
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
    </div>

</div>


</body>
</html>