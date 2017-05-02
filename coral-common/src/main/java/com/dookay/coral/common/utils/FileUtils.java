package com.dookay.coral.common.utils;

import com.dookay.coral.common.model.FineUploaderParamModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 文件出料口工具类
 */
public class FileUtils {
    public static final String DOT = ".";

    @SuppressWarnings("rawtypes")
    public static String doPost(HttpServletRequest request,
                                FineUploaderParamModel paramModel) {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (!item.isFormField()) {
                    if (item.getName() != null && !item.getName().equals("")) {
                        String tempFileName = item.getName();

                        String fileName = MessageFormat.format(
                                "{0}/{1}/{2}/{3}{4}{5}", "/uploads",
                                paramModel.getModule(), new SimpleDateFormat(
                                        "yyyyMMdd").format(new Date()),
                                new SimpleDateFormat("HHmmss")
                                        .format(new Date()), Math.ceil(Math
                                        .random() * 1000), tempFileName
                                        .substring(tempFileName
                                                .lastIndexOf('.')));

                        String filePath = request.getServletContext()
                                .getRealPath(fileName);

                        System.out.println(filePath);
                        File file = new File(filePath);
                        File dir = new File(file.getParent());

                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        item.write(file);
                        return fileName;
                    } else {
                        return null;
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return rannum + str;// 当前时间
    }

    public static String getExtension(String fileName) {
        if (fileName.indexOf(DOT) != -1)
            return "";

        String ext = fileName.substring(fileName.lastIndexOf(DOT));

        return ext.trim();
    }

    public static String getDirectoryName(String filePath) {
        filePath = filePath.replace("\\", "/");
        String directoryName = filePath.substring(0, filePath.lastIndexOf("/"));
        return directoryName.trim();
    }

    public static String getFileName(String filePath) {
        filePath = filePath.replace("\\", "/");
        return filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(DOT));
    }

    public static boolean deleteDirectory(String filePath) {
        File dir = new File(filePath);
        if (dir.exists()) {
            return deleteDirectory(dir);
        } else {
            return false;
        }
    }

    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 将文件转化为二进制流
     *
     * @param file
     * @return
     */
    public static byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 下载文件
     *
     * @param remoteFilePath 远程路径
     * @param localFilePath  本地保存路径
     * @return
     */
    public static File downloadFile(String remoteFilePath, String localFilePath) {
        File file = new File(localFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            URL urlFile = new URL(remoteFilePath);
            HttpURLConnection httpUrl = (HttpURLConnection) urlFile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(file));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /*缺少包报错注释*/
//	/**
//     * 压缩文件
//     *
//     * @param srcfile
//     *            File[] 需要压缩的文件列表
//     * @param zipfile
//     *            File 压缩后的文件
//     */
//    public static void ZipFiles(File[] srcfile, File zipfile) {
//        byte[] buf = new byte[1024];
//        try {
//            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
//                    zipfile));
//            for (int i = 0; i < srcfile.length; i++) {
//                FileInputStream in = new FileInputStream(srcfile[i]);
//                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
//                String str = srcfile[i].getName();
//                int len;
//                while ((len = in.read(buf)) > 0) {
//                    out.write(buf, 0, len);
//                }
//                out.closeEntry();
//                in.close();
//            }
//            out.close();
//            System.out.println("压缩完成.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//	 /**
//     * zip解压缩
//     *
//     * @param zipfile
//     *            File 需要解压缩的文件
//     * @param descDir
//     *            String 解压后的目标目录
//     */
//    public static void unZipFiles(File zipfile, String descDir) {
//        try {
//            ZipFile zf = new ZipFile(zipfile);
//            for (Enumeration entries = zf.getEntries(); entries
//                    .hasMoreElements();) {
//                ZipEntry entry = ((ZipEntry) entries.nextElement());
//                String zipEntryName = entry.getName();
//                InputStream in = zf.getInputStream(entry);
//                OutputStream out = new FileOutputStream(descDir + zipEntryName);
//                byte[] buf1 = new byte[1024];
//                int len;
//                while ((len = in.read(buf1)) > 0) {
//                    out.write(buf1, 0, len);
//                }
//                in.close();
//                out.close();
//                //System.out.println("解压缩完成.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//	/**
//	    * 根据原始rar路径，解压到指定文件夹下.
//	    * @param srcRarPath 原始rar路径
//	    * @param dstDirectoryPath 解压到的文件夹
//	    */
//	    public static void unRarFile(String srcRarPath, String dstDirectoryPath) {
//	        if (!srcRarPath.toLowerCase().endsWith(".rar")) {
//	            System.out.println("非rar文件！");
//	            return;
//	        }
//	        File dstDiretory = new File(dstDirectoryPath);
//	        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
//	            dstDiretory.mkdirs();
//	        }
//	        Archive a = null;
//	        try {
//	            a = new Archive(new File(srcRarPath));
//	            if (a != null) {
//	                a.getMainHeader().print(); // 打印文件信息.
//	                FileHeader fh = a.nextFileHeader();
//	                while (fh != null) {
//	                    if (fh.isDirectory()) { // 文件夹
//	                        File fol = new File(dstDirectoryPath + File.separator
//	                                + fh.getFileNameString());
//	                        fol.mkdirs();
//	                    } else { // 文件
//	                        File out = new File(dstDirectoryPath + File.separator
//	                                + fh.getFileNameString().trim());
//	                        //System.out.println(out.getAbsolutePath());
//	                        try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
//	                            if (!out.exists()) {
//	                                if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
//	                                    out.getParentFile().mkdirs();
//	                                }
//	                                out.createNewFile();
//	                            }
//	                            FileOutputStream os = new FileOutputStream(out);
//	                            a.extractFile(fh, os);
//	                            os.close();
//	                        } catch (Exception ex) {
//	                            ex.printStackTrace();
//	                        }
//	                    }
//	                    fh = a.nextFileHeader();
//	                }
//	                a.close();
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }
}
