$(function() {
  $('#java-signed,#java-unsigned').click(function() {
      /* $('body').append('<applet ' +
                       'archive="net-browserify-java-1.0.jar" ' +
                       'code="com.github.jryans.netbrowserify.Server" ' +
                       'width="400" ' +
                       'height="300" ' +
                       'id="ts">' +
                       '</applet>'); */

    var app = document.createElement('applet');

    if (this.id === "java-signed") {
      app.archive = '/net-browserify-java-1.0-signed.jar';
    } else {
      app.archive = '/net-browserify-java-1.0.jar';
    }
    app.code = 'com.github.jryans.netbrowserify.Server';
    app.width = 1;
    app.height = 1;
    app.id = 'ts';

    $('body').append(app);

  });

  $('#actions').on('click', 'button', function() {
    ts[this.id]();

    $('#status span').each(function() {
      $(this).text(ts[this.id]());
    });
  });
});

