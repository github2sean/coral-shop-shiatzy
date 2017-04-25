package com.dookay.coral.common.web.jcaptcha;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.*;
import java.awt.image.ImageFilter;

/**
 * from https://code.google.com/p/musicvalley/source/browse/trunk/musicvalley/doc/springSecurity/springSecurityIII/src/main/java/com/spring/security/jcaptcha/GMailEngine.java?spec=svn447&r=447
 * JCaptcha验证码图片生成引擎, 仿照JCaptcha2.0编写类似GMail验证码的样式.
 *
 * @author : kezhan
 * @version : v0.0.1
 * @since : 2016年11月17日
 */
public class GMailEngine extends ListImageCaptchaEngine {

    @Override
    protected void buildInitialFactories() {

        // 图片和字体大小设置
        int minWordLength = 4;
        int maxWordLength = 5;
        // TODO: 2016/11/25 前台验证码修改
        int fontSize = 28;
        // TODO: 2016/11/25 前台验证码修改
        int imageWidth = 127;
        // TODO: 2016/11/25 前台验证码修改
        int imageHeight = 52;
        // TODO: 2016/11/25 前台验证码修改
        Color backgroundColor = new Color(212, 194, 180);

        WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(
                new FileDictionary("toddlist"));
        // word2image components
        @SuppressWarnings("deprecation")
        TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength,
                maxWordLength, new RandomListColorGenerator(new Color[]{
                new Color(23, 170, 27), new Color(220, 34, 11),
                new Color(23, 67, 172)}), new TextDecorator[]{});
        BackgroundGenerator background = new UniColorBackgroundGenerator(
                imageWidth, imageHeight, backgroundColor);
        FontGenerator font = new RandomFontGenerator(fontSize, fontSize,
                new Font[]{new Font("nyala", Font.BOLD, fontSize),
                        new Font("Bell MT", Font.PLAIN, fontSize),
                        new Font("Credit valley", Font.BOLD, fontSize)});

        ImageDeformation postDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation backDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation textDef = new ImageDeformationByFilters(
                new ImageFilter[]{});

        WordToImage word2image = new DeformedComposedWordToImage(font,
                background, randomPaster, backDef, textDef, postDef);

        addFactory(new GimpyFactory(dictionnaryWords, word2image));
    }

}
