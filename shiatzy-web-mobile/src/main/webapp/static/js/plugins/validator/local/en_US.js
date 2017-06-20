/*********************************
 * Themes, rules, and i18n support
 * Locale: English; 英文
 *********************************/

(function(factory) {
    typeof module === "object" && module.exports ? module.exports = factory( require( "jquery" ) ) :
    typeof define === 'function' && define.amd ? define(['jquery'], factory) :
    factory(jQuery);
}(function($) {

    /* Global configuration
     */

        $.validator.config({
            //stopOnError: true,
            //focusCleanup: true,
            //theme: 'yellow_right',
            //timely: 2,

            // Custom rules
            rules: {
                digits: [/^\d+$/, "Please fill in the numbers."]
                ,letters: [/^[a-z]+$/i, "Please fill in the letter."]
                ,date: [/^\d{4}-\d{2}-\d{2}$/, "Please fill in the valid date form: yyyy-mm-dd."]
            ,time: [/^([01]\d|2[0-3])(:[0-5]\d){1,2}$/, "Please fill in the valid time between 00:00 and 23:59."]
            ,email: [/^[\w\+\-]+(\.[\w\+\-]+)*@[a-z\d\-]+(\.[a-z\d\-]+)*\.([a-z]{2,4})$/i, "Please fill in the correct email address."]
            ,url: [/^(https?|s?ftp):\/\/\S+$/i, "Please fill in the valid URL."]
            ,qq: [/^[1-9]\d{4,}$/, "Please fill in the valid QQ number."]
            ,IDcard: [/^\d{6}(19|2\d)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)?$/, "Please fill in the correct identification number."]
            ,tel: [/^(?:(?:0\d{2,3}[\- ]?[1-9]\d{6,7})|(?:[48]00[\- ]?[1-9]\d{6}))$/, "Please fill in the correct tel number."]
            ,mobile: [/^1[3-9]\d{9}$/, "Please fill in the correct phone number."]
            ,zipcode: [/^\d{6}$/, "Please check the zip code format."]
            ,chinese: [/^[\u0391-\uFFE5]+$/, "Please fill in the Chinese characters."]
            ,username: [/^\w{3,12}$/, "Please fill in 3-12 digits, letters and underline."]
            ,password: [/^[\S]{6,16}$/, "Please fill out the 6-16 characters and cannot contain spaces"]
            ,accept: function (element, params){
                if (!params) return true;
                var ext = params[0],
                    value = $(element).val();
                return (ext === '*') ||
                       (new RegExp(".(?:" + ext + ")$", "i")).test(value) ||
                       this.renderMsg("Files that accept only the{1}suffix", ext.replace(/\|/g, ','));
            }
            
        },

        // Default error messages
        messages: {
            0: "in here",
            fallback: "{0} the format is incorrect",
            loading: "Verifying...",
            error: "Network anomaly",
            timeout: "Request Timeout",
            required: "{0} is Required",
            remote: "{0}Already in use",
            equalTo: "The password entered for the two time must be consistent.",
            integer: {
                '*': "Please fill in the integer.",
                '+': "Fill in the positive integer, please.",
                '+0': "Please fill in the positive integer or 0.",
                '-': "Please fill in the negative integer.",
                '-0': "Please fill in the negative integer or 0."
            },
            match: {
                eq: "{0} and {1} is not same",
                neq: "{0} and {1} can not be the same",
                lt: "{0} must be less than {1}",
                gt: "{0} must be greater than {1}",
                lte: "{0} not greater than {1}",
                gte: "{0} not less than {1}"
            },
            range: {
                rg: "请填写{1}到{2}的数",
                gte: "请填写不小于{1}的数",
                lte: "请填写最大{1}的数",
                gtlt: "请填写{1}到{2}之间的数",
                gt: "请填写大于{1}的数",
                lt: "请填写小于{1}的数"
            },
            checked: {
                eq: "请选择{1}项",
                rg: "请选择{1}到{2}项",
                gte: "请至少选择{1}项",
                lte: "请最多选择{1}项"
            },
            length: {
                eq: "请填写{1}个字符",
                rg: "请填写{1}到{2}个字符",
                gte: "请至少填写{1}个字符",
                lte: "请最多填写{1}个字符",
                eq_2: "",
                rg_2: "",
                gte_2: "",
                lte_2: ""
            }
        }
    });

    /* Themes
     */
    var TPL_ARROW = '<span class="n-arrow"><b>◆</b><i>◆</i></span>';
    $.validator.setTheme({
        'simple_right': {
            formClass: 'n-simple',
            msgClass: 'n-right'
        },
        'simple_bottom': {
            formClass: 'n-simple',
            msgClass: 'n-bottom'
        },
        'yellow_top': {
            formClass: 'n-yellow',
            msgClass: 'n-top',
            msgArrow: TPL_ARROW
        },
        'yellow_right': {
            formClass: 'n-yellow',
            msgClass: 'n-right',
            msgArrow: TPL_ARROW
        },
        'yellow_right_effect': {
            formClass: 'n-yellow',
            msgClass: 'n-right',
            msgArrow: TPL_ARROW,
            msgShow: function($msgbox, type){
                var $el = $msgbox.children();
                if ($el.is(':animated')) return;
                if (type === 'error') {
                    $el.css({left: '20px', opacity: 0})
                        .delay(100).show().stop()
                        .animate({left: '-4px', opacity: 1}, 150)
                        .animate({left: '3px'}, 80)
                        .animate({left: 0}, 80);
                } else {
                    $el.css({left: 0, opacity: 1}).fadeIn(200);
                }
            },
            msgHide: function($msgbox, type){
                var $el = $msgbox.children();
                $el.stop().delay(100).show()
                    .animate({left: '20px', opacity: 0}, 300, function(){
                        $msgbox.hide();
                    });
            }
        }
    });
}));
