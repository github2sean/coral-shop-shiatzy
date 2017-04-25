package com.dookay.coral.common.utils;

import org.junit.Test;

import java.io.File;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/24
 */
public class FileUtilsTest {
    @Test
    public void downloadFile() throws Exception {
        File image = FileUtils.downloadFile("http://photocdn.sohu.com/20170104/Img477698790.jpeg", "test.jpg");
        System.out.println(image.getAbsolutePath());
    }

    @Test
    public void getExtension() {
        String file = "test.jpg";
        System.out.print(file.substring(file
                .lastIndexOf('.')));
    }
}
