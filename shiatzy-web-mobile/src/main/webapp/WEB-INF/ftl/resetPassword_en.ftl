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
            width: 95%;
            margin: 0 auto;
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
            margin-bottom: 15px;
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
                <p class="name">Dear ,${name}</p>
                <br>
                <p class="txt">We have just created a new password for you. For security reasons, we suggest to change this password with your next log-in of your choice. Should you have any questions, please contact us via: <a href="#" class="link-web">customercare@shiatzychen.com</a>.</p>
                <p class="txt">
                    Should you have any other questions, please contact us via<br>
                    <a href="#" class="link-web">customercare@shiatzychen.com</a>
                </p>
                <p class="txt" style="margin-bottom: 0">
                    点击下面连接重新设置密码
                </p>
                <a href="${setUrl}" target="_blank" style="padding: 10px 0; display:block; width: 100%; margin: 0px auto 30px; background: #000; color: #fff; letter-spacing: 2px; text-decoration: none; cursor:pointer ">设置新密码</a>
            </div>
        </div>
        <p class="email-footer">
            <span style="">© shiatzy chen. all rights reserved</span>
        </p>
    </div>
</body>
</html>