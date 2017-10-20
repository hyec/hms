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

  $('#stats-bar').on('appear', function () {

    $('#stats-bar').off('appear');

    var items = [];
    $(this).find('.counter-item h2').each(function () {
      items.push({
        'elem': $(this),
        'count': $(this).data('count')
      });
    });

    $(this).prop('Counter', 0).animate({'Counter': 1}, {
      'duration': 1000,
      'step': function (i) {
        items.forEach(function (e) {
          e.elem.text(Math.ceil(e.count * i));
        });
      }
    });
  }).appear();

});


// $(window).scroll(function () {
//   if ($(document).scrollTop() > 300) {
//     $('.navbar').addClass('navbar-shrink');
//   }
//   else {
//     $('.navbar').removeClass('navbar-shrink');
//   }
// });

// $(function () {
//   $('a[href*=#]:not([href=#])').click(function () {
//     if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
//       var target = $(this.hash);
//       target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
//       if (target.length) {
//         $('html,body').animate({
//           scrollTop: target.offset().top
//         }, 1000);
//         return false;
//       }
//     }
//   });
// });


// hide #back-top first


// fade in #back-top


// scroll body to 0px on click


// // Closes the Responsive Menu on Menu Item Click
// $('.navbar-collapse ul li a').click(function() {
//     $('.navbar-toggle:visible').click();
// });


// $(function () {
//   //$('.stats-bar').appear();
//   $('.stats-bar').on('appear', function () {
//     var fx = function fx() {
//       $(".stat-number").each(function (i, el) {
//         var data = parseInt(this.dataset.n, 10);
//         var props = {
//           "from": {
//             "count": 0
//           },
//           "to": {
//             "count": data
//           }
//         };
//         $(props.from).animate(props.to, {
//           duration: 1000 * 1,
//           step: function (now, fx) {
//             $(el).text(Math.ceil(now));
//           },
//           complete: function () {
//             if (el.dataset.sym !== undefined) {
//               el.textContent = el.textContent.concat(el.dataset.sym)
//             }
//           }
//         });
//       });
//     };
//     var reset = function reset() {
//       console.log($(this).scrollTop())
//       if ($(this).scrollTop() > 90) {
//         $(this).off("scroll");
//         fx()
//       }
//     };
//     $(window).on("scroll", reset);
//   });
// });