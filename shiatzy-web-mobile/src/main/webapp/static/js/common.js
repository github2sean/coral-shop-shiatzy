var commonApp = function () {
  //返回顶部
  var backTop = function () {
    $('#j_back_top').on('click', function (e) {
      e.preventDefault();
      $('.main-content').animate({
        scrollTop: 0
      }, 400);
    })
  };

  var guideNav = function () {
    var navPanel = $('#j_nav_panel');
    //顶部导航显示隐藏
    $('#j_show_nav').on("click", function () {
      navPanel.fadeIn();
      $('#j_close_nav').on('click', function () {
        navPanel.fadeOut();
      })
    });
    //下拉菜单展开收起
    $('.j_drop_down>li').on('click', function () {
      $(this).toggleClass('active');
    });
    //点击栏目后显示图片
    $('.guide-nav>li>a').on('click', function () {
      $('.search-rec-img').addClass('active');
    });
  };
  //产品列表筛选
  var proFilter = function () {
    $('.j_panel_trigger').on('click', function (e) {
      e.preventDefault();
      var id = '#' + $(this).data('panel');
      $(id).fadeIn();
      $('.j_close_panel').on('click', function () {
        $(this).parent().fadeOut();
      });
      alert('12313')
    })
  };

  //收藏和预约按钮
  var collect = function () {
    function clickStyle(btn, originIcon, activeIcon) {
      btn.each(function () {
        $(this).on('click', function () {
          if ($(this).hasClass('active')) {
            $(this).removeClass('active').find('use').attr('xlink:href', originIcon);
          } else {
            $(this).addClass('active').find('use').attr('xlink:href', activeIcon);
          }
        });
      })
    }

    var btn = $('.j_collect');
    clickStyle(btn, '#heart', '#heart-red');

    var apbtn = $('.j_appointment');
    clickStyle(apbtn, '#ap-small', '#ap-active');

    var bagbtn = $('.j_bag');
    clickStyle(bagbtn, '#bag', '#bag-active');
  };

  // 发票信息
  var invoice = function () {
    $("#requireInvoice").on("change", function () {
      if ($(this).is(":checked")) {
        $(".invoice-info").removeClass("hide")
      }
    });
    $("#noInvoice").on("change", function () {
      if ($(this).is(":checked")) {
        $(".invoice-info").addClass("hide")
      }
    })
  };

  // 支付方式

  var payment = function () {
    $("#j_m_payment input[type=radio]").on("change", function () {
      var $this = $(this);
      if ($this.is(":checked")) $this.parents("li").addClass("active").siblings().removeClass("active");
    })
  };

  return {
    init: function () {
      backTop();
      guideNav();
      proFilter();
      collect();
      invoice();
      payment();
    }
  }
}();