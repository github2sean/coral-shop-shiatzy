//__dirname：获取当前运行目录
/**
 * 获取模块信息
 */
var fs = require("fs");
function getModuleInfo(name,modulesPath) {
    var moduleInfo = [],
        path = 'src/modules/';
    fs.readdirSync(path).forEach(function (itm, index) {
        var stat = fs.statSync(path + itm);
        if (stat.isDirectory()) {
            if(fs.existsSync(path + itm + '/'+ itm +'.json')){
                var conf = require('./'+path + itm + '/'+ itm +'.json');
                moduleInfo.push(conf);
            }
        }
    });
    if(typeof name !="undefined"){
        var nameValue = [];
        for (var i in moduleInfo){
            if(typeof moduleInfo[i][name] == "undefined") continue;
            if (typeof moduleInfo[i][name] === 'object' && !isNaN(moduleInfo[i][name].length)) {
                if(typeof modulesPath != "undefined") {
                    for (var t = 0; t < moduleInfo[i][name].length; t++) {
                        moduleInfo[i][name][t] = path + moduleInfo[i]['name'] + '/'+ moduleInfo[i][name][t];
                    }
                }
                nameValue = nameValue.concat(moduleInfo[i][name]);
            }else{
                nameValue.push(typeof modulesPath != "undefined"?path + moduleInfo[i]['name'] + '/'+ moduleInfo[i][name]:moduleInfo[i][name]);
            }
        }
        return nameValue;
    }else {
        return moduleInfo;
    }
}

var gulp = require('gulp');
var plugins = require('gulp-load-plugins')();
var browserSync = require('browser-sync');
var reload = browserSync.reload;

var lodash = require('lodash');

// 加载配置项
var config = lodash.extend(
    // require('../../config.json'),
    require('./config.json')
);

// 引入其他任务
var requireDir = require('require-dir');
requireDir('./tasks');

/**
 * 合并并压缩核心css资源
 */
gulp.task('compileBootstrapLess', function () {
    return gulp.src('src/less/bootstrap.less')
        .pipe(plugins.less())
        .pipe(gulp.dest('build/css'));
});

gulp.task('concatCoreCss', function () {
    return gulp.src(config.concat.css)
        .pipe(plugins.concat('dookayui.css'))
        .pipe(gulp.dest('build/css'))

        .pipe(plugins.cssnano())
        .pipe(plugins.rename({ extname: '.min.css'}))
        .pipe(gulp.dest('src/css'));
});

gulp.task('compositeTask:coreCss',['compileBootstrapLess','concatCoreCss']);

/**
 * 合并并压缩核心js资源
 */
gulp.task('concatCoreJs', function () {
    return gulp.src(config.concat.js)
        .pipe(plugins.concat('dookayui.js'))
        .pipe(gulp.dest('build/js'))

        .pipe(plugins.uglify())
        .pipe(plugins.rename({ extname: '.min.js'}))
        .pipe(gulp.dest('src/js'))

        .pipe(plugins.sourcemaps.init())
        .pipe(plugins.sourcemaps.write('./'))
        .pipe(gulp.dest('src/js'));
});

gulp.task('copyJsResource',function () {
    for (var i in config.copy){
        gulp.src(config.copy[i].value)
            .pipe(gulp.dest(config.copy[i].name));
    }
});

gulp.task('compositeTask:coreJs',['copyJsResource','concatCoreJs']);

/**
 * 合并模块资源
 */
gulp.task('concatModuleCss',function () {
    var moduleInfo = getModuleInfo('css',true);
    return gulp.src(moduleInfo)
        .pipe(plugins.concat('module.css'))
        .pipe(gulp.dest('src/css'));
});

gulp.task('concatModuleJs',function () {
    var moduleInfo = getModuleInfo('js',true);
    return gulp.src(moduleInfo)
        .pipe(plugins.concat('module.js'))
        .pipe(gulp.dest('src/js'));
});

gulp.task('compositeTask:concatModule',['concatModuleCss','concatModuleJs']);
/**
 * 包含
 */
gulp.task('include', function() {
    return gulp.src(['src/**/*.html','!src/include/**.html'])
        .pipe(plugins.changed('dist'))
        .pipe(plugins.fileInclude({
            prefix: '@',
            basepath: '@file'
        }))
        .pipe(gulp.dest('dist'))
        .pipe(reload({ stream:true }));
});

/**
 * 复制
 */
gulp.task('copy', function() {
    return gulp.src(['src/js/**/*','src/css/**/*.css','src/images/**/*'],{ base: 'src' })
        .pipe(plugins.changed('dist'))
        .pipe(gulp.dest('dist'))
        .pipe(reload({ stream:true }));
});

// 自动刷新
gulp.task('serve',['include','copy'], function() {
    browserSync({
        server: {
            baseDir: 'dist'
        }
    });
    gulp.watch(['src/**/*.html'],['include'], reload);
    gulp.watch(['src/css/*.css','src/js/*.js','src/images/**/*'],['copy'], reload);
});