var gulp = require('gulp');
var plugins = require('gulp-load-plugins')();

gulp.task('copyPlugins', function () {
    gulp.src([
        "../../lib/html5shiv/dist/html5shiv.min.js",
        "../../lib/respond/dest/respond.min.js"
    ])
        .pipe(gulp.dest('src/js/plugins'));
});