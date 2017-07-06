<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <style>
        img {
            max-width: 100%;
        }
        p,body,html {
            margin: 0;
            padding: 0;
        }
        .email-submit {
            width: 85%;
            margin: 0 auto;
            border: 1px solid #000;
            padding-left: 5%;
            padding-right: 5%;
        }
        .email-submit .title {
            text-align: center;
            margin-bottom: 20px;
            margin-top: 20px;
        }
        .email-submit .email-con {
            border-top: 1px solid #000;
            border-bottom: 1px solid #000;
            padding-top: 30px;
            padding-bottom: 30px;
        }
        .email-con .email-title {
            font-family: "lucida Grande",Verdana,"Microsoft YaHei";
            font-size: 20px;
            font-weight: 300;
            margin: 0;
        }
        .email-con .email-txt {
            margin-top: 10px;
            margin-bottom: 10px;
        }
        .email-con .email-txt .txt {
            font-size: 14px;
            line-height: 22px;
        }
        .email-con .email-txt .txt .link-web {
            text-decoration: underline;
            color: #2a586f;
        }
        .email-submit .email-footer {
            padding: 14px 0;
        }

    </style>
</head>
<body>
    <div class="email-submit">
        <p class="title"><img src="${picUrl}" alt=""></p>
        <div class="email-con">
            <h1 class="email-title">${title}</h1>
            <div class="email-txt">
                <p class="name">Dear ${name} ,</p>
                <br>
                <p class="txt">${contentPrefix}<a href="#" class="link-web">SHOP.SHIATZYCHEN.COM</a>. ${contentSuffix}</p>
            <a href="http://shop.shiatzychen.com/" target="_blank" style="text-decoration-line: none; padding: 10px 0px; display: block; width: 240px; margin: 30px auto; background-color: rgb(0, 0, 0); color: rgb(255, 255, 255); letter-spacing: 2px; cursor: pointer; text-align: center;">SHOP NOW</a>
            </div>
        </div>
        <p class="email-footer">
            <span style="">© shiatzy chen. all rights reserved</span>
        </p>
    </div>
</body>
</html>