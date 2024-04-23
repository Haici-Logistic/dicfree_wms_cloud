package com.dicfree;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author wiiyaya
 * @date 2021/7/28.
 */
public class LicenseForJavaFileUtils {
    private static final int MAX_FILE_SIZE = 2 * 1024 * 1024;
    private static final String javaFilesDir = "E:\\海辞\\github_source\\dicfree_cloud\\src\\main\\java\\com\\dicfree";
    private static final String licenseStr = "/**\n" +
            " * Copyright 2024 Wuhan Haici Technology Co., Ltd \n" +
            " * <p>\n" +
            " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
            " * you may not use this file except in compliance with the License.\n" +
            " * You may obtain a copy of the License at\n" +
            " * <p>\n" +
            " * http://www.apache.org/licenses/LICENSE-2.0\n" +
            " * <p>\n" +
            " * Unless required by applicable law or agreed to in writing, software\n" +
            " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
            " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
            " * See the License for the specific language governing permissions and\n" +
            " * limitations under the License.\n" +
            " */";

    public static void main(String[] args) {
        iterativeHandleFiles();
    }

    private static void iterativeHandleFiles() {
        try {
            File file = new File(javaFilesDir);
            if (!file.exists()) {
                return;
            }
            iterativeHandleFiles(file);
        } catch (Exception e) {
            System.out.println("iterativeHandleFiles: " + e.getMessage());
        }
    }

    private static void iterativeHandleFiles(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File file1 : files) {
                iterativeHandleFiles(file1);
            }
        } else {
            String name = file.getName();
            // 不是目标文件则直接返回，不处理
            if (!name.endsWith("java")) {
                return;
            }
            handleFile(file);
        }
    }

    private static void handleFile(File file) {
        RandomAccessFile targetRandomAccessFile = null;
        try {
            targetRandomAccessFile = new RandomAccessFile(file, "rw");

            if (targetRandomAccessFile.length() > MAX_FILE_SIZE) {
                System.out.println("file size is too long!" + file.getName());
                return;
            }

            // 读取license文本内容
            byte[] contentBytes = new byte[(int) targetRandomAccessFile.length()];
            targetRandomAccessFile.readFully(contentBytes);
            String contentStr = new String(contentBytes);

            int indexOfPackage = contentStr.indexOf("package");
            // 拼接最终的文件内容
            contentStr = licenseStr + "\n" + contentStr.substring(indexOfPackage);
            targetRandomAccessFile.seek(0);
            targetRandomAccessFile.setLength(contentStr.length());
            targetRandomAccessFile.write(contentStr.getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (targetRandomAccessFile != null) {
                    targetRandomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
