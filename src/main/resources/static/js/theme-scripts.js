$(function () {

  var scrollTop = 300;

  if (!$('#nav').hasClass('navbar-shrink')) {
    $(window).scroll(function () {
      if ($(document).scrollTop() > scrollTop) {
        $('#nav').addClass('navbar-shrink');
      } else {
        $('#nav').removeClass('navbar-shrink');
      }
    });
  }

  $('.page-scroll').click(function () {
    var target = $(this.hash);
    if (target) {
      $('html,body').animate({
        scrollTop: target.offset().top
      }, 1000);
    }
  });

  $('#back-top').on("click", function () {
    $('body,html').animate({
      scrollTop: 0
    }, 800);
    return false;
  }).hide();

  $(window).scroll(function () {
    if ($(this).scrollTop() > scrollTop) {
      $('#back-top').fadeIn();
    } else {
      $('#back-top').fadeOut();
    }
  });

});
