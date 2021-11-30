package com.xl.base.utils;

import android.os.Environment;
import android.util.Log;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class FileUtils {
    /**
     * 递归创建目录
     *
     * @param path
     * @return
     */
    public static boolean mkDir(String path) {
        boolean isFinished = false;
        try {
            File f = new File(path);
            if (f.isDirectory()) {
                return true;
            }
            File parent = f.getParentFile();
            if (!parent.exists()) {
                mkDir(parent.getAbsolutePath());
            }
            isFinished = f.mkdirs();
        } catch (Exception e) {

        }
        return isFinished;
    }

    // 返回相对外部存储（SDK）根目录的文件目录全路径
    public static String GetAbsoluteFileNameByRelateFileNameForRoot(
            String sFileRelateName) {
        return Environment.getExternalStorageDirectory().getPath()
                + File.separatorChar + sFileRelateName;
    }


    // 获得一个完整文件路径名的目录部分
    public static String GetFileDirecotry(String sFileFullName) {
        if (StringUtils.isEmpty(sFileFullName))
            return "";
        return sFileFullName.substring(0, sFileFullName.lastIndexOf("/") + 1);
    }

    // 是否存在指定的文件
    public static Boolean ExistsFile(String sInput) {
        if (StringUtils.isEmpty(sInput))
            return false;
        if (sInput.indexOf(".") < 0)
            return false;
        if (sInput.indexOf("/") < 0)
            return false;
        File file = new File(sInput);
        return file.exists() && (file.length() > 0);
    }

    /**
     * 存储字符串到文件
     *
     * @param file      文件
     * @param inputText 存储内容
     * @return
     */
    public static boolean saveString(File file, String inputText) {
        boolean result = true;
        FileOutputStream out;
        BufferedWriter writer = null;
        try {
            out = new FileOutputStream(file, false);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean SaveStringByUTF8(File file, String inputText) {
        boolean result = true;
        FileOutputStream out;
        BufferedWriter writer = null;
        try {
            out = new FileOutputStream(file, false);
            writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(inputText);
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean SaveStringByGBK(File file, String inputText) {
        boolean result = true;
        FileOutputStream out;
        BufferedWriter writer = null;
        try {
            out = new FileOutputStream(file, false);
            writer = new BufferedWriter(new OutputStreamWriter(out, "GBK"));
            writer.write(inputText);
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 读取文件String内容
     *
     * @param file 文件
     * @return
     */
    public static String readString(File file) {
        if (!file.exists()) {
            return null;
        }
        FileInputStream in;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * 文件是否存在
     *
     * @param path 文件路径
     * @return true if file exist.
     */
    public static boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 获取指定路径下的所有文件列表
     *
     * @param dir 要查找的目录
     * @return
     */
    public static List<String> getFileList(String dir) {
        List<String> listFile = new ArrayList<>();
        File dirFile = new File(dir);
        // 如果不是目录文件，则直接返回
        if (dirFile.isDirectory()) {
            // 获得文件夹下的文件列表，然后根据文件类型分别处理
            File[] files = dirFile.listFiles();
            if (null != files && files.length > 0) {
                // 根据时间排序
                Arrays.sort(files, new Comparator<File>() {
                    public int compare(File f1, File f2) {
                        return (int) (f1.lastModified() - f2.lastModified());
                    }

                    public boolean equals(Object obj) {
                        return true;
                    }
                });
                for (File file : files) {
                    // 如果不是目录，直接添加
                    if (!file.isDirectory()) {
                        listFile.add(file.getAbsolutePath());
                    } else {
                        // 对于目录文件，递归调用
                        listFile.addAll(getFileList(file.getAbsolutePath()));
                    }
                }
            }
        }
        return listFile;
    }

    /**
     * 删除目录下的所有文件
     * tcookieid
     *
     * @param dir 目录
     */
    public static void deleteFiles(String dir) {
        File dirFile = new File(dir);
        if (dirFile.isDirectory()) {
            // 获得文件夹下的文件列表，然后根据文件类型分别处理
            File[] files = dirFile.listFiles();
            for (File file : files) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
        }
    }

    public static boolean delFile(String filePath) {
        boolean delOk = false;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + filePath);
            if (file.exists()) {
                delOk = file.delete();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return delOk;
    }

    public static final String Phone_BasePaht = Environment.getExternalStorageDirectory().getPath() + File.separator;

    /**
     * 创建文件并添加内容
     */
    public static boolean createFiles(String fileNames, String content) {
        boolean createOk = false;
        try {
            String filesPath = Phone_BasePaht + fileNames;
            File file = new File(filesPath);
            createOk = saveString(file, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createOk;
    }

    /**
     * 创建文件并添加内容
     */
    public static boolean createFiles2(String filePath, String content) {
        boolean createOk = false;
        try {
            File file = new File(filePath);
            createOk = saveString(file, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createOk;
    }

    /**
     * 文件是否存在
     */
    public static boolean fileIsExists(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    /**
     * 追加内容
     */
    public static void AddContent(String filePath, String Content) {
        try {
            Content = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())) + "  " + Content + "\r\n";
            if (fileIsExists(filePath) == false) {
                createFiles2(filePath, Content);
            } else {
                // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                FileWriter writer = new FileWriter(filePath, true);
                writer.write(Content);
                writer.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean IsLog = false;
    static Object Lock = new Object();
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "/Zity/log/" + File.separator;

    public static void XposedFileLog(String Content) {
        synchronized (Lock) {
            if (IsLog) {
                AddContent(BASE_PATH + "XposedFileLog.txt",
                        Content);
            }
        }

    }


    /**
     * 将log日志打印到手机上
     * <p>
     * 文件在手机上的输出目录：com.rwx/wkjglog.txt
     * 即在 文件中直接搜索wkjglog.txt即可
     *
     * @param
     * @return
     */

//将调试信息记录到文件中
    public static void LogDebugToFile(String sContent) {

        String fileName = "Zity/log//" + new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())) + ".txt";
        FileUtils.SaveString(
                fileName,
                String.format("%s:%s\r\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())), sContent),
                "",
                true);
    }

    //保存指定内容到指定的文件中
    public static boolean SaveString(String sFileName, String inputText, String sEncodeName, boolean bIsAppend) {
        boolean bSuccess = false;
        if (StringUtils.isEmpty(sFileName)) {
            return false;
        }
        String sNewFileName = sFileName;
        if (sFileName.startsWith("/") == false) {
            sNewFileName = String.format("/%s", sFileName);
        }
        if (sNewFileName.startsWith("/sdcard") == false) {
            sNewFileName = String.format("/sdcard%s", sNewFileName);
        }
        String sFilePath = GetFileDirecotry(sNewFileName);
        mkDir(sFilePath);
        File file = new File(sNewFileName);
        SaveString(file, inputText, sEncodeName, bIsAppend);
        return bSuccess;
    }


    //往指定的文件中存储指定的内容
    private static boolean SaveString(File file, String inputText, String sEncodeName, boolean bIsAppend) {
        boolean result = true;
        FileOutputStream out;
        BufferedWriter writer = null;
        String sCurrentEncodeName = "UTF-8";
        if (StringUtils.isEmpty(sEncodeName) == false) {
            sCurrentEncodeName = sEncodeName;
        }
        try {
            out = new FileOutputStream(file, bIsAppend);
            writer = new BufferedWriter(new OutputStreamWriter(out, sCurrentEncodeName));
            writer.write(inputText);
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
            Log.d("TagJson参数===", e.toString());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TagJson参数===", e.toString());
            }
        }
        return result;
    }


    public static boolean DeleteOverdueLogFile() {
        String fileDir = "Zity/log/";
        String sNewFileName = fileDir;
        if (fileDir.startsWith("/") == false) {
            sNewFileName = String.format("/%s", fileDir);
        }
        if (sNewFileName.startsWith("/sdcard") == false) {
            sNewFileName = String.format("/sdcard%s", sNewFileName);
        }
        String sFilePath = GetFileDirecotry(sNewFileName);
        File mfolder = new File(sFilePath);
        if (mfolder.isDirectory()) {
            File[] AllFiles = mfolder.listFiles(); //列出目录下的所有文件
            if (AllFiles == null)
                return false;
            ArrayList<String> mFilesList = new ArrayList<String>();  //存放/myLog 下的所有文件
            for (int i = 0; i < AllFiles.length; i++) {
                File mFile = AllFiles[i]; //得到文件
                String Name = mFile.getName(); //得到文件的名字
                if (Name == null || Name.length() < 1)
                    return false;
                if (Name.endsWith(".txt")) {  //筛选出log
                    mFilesList.add(Name); //把文件名添加到链表里
                }
            }
            Collections.sort(mFilesList);   // 将文件按自然排序升序排列
            //判断日志文件如果大于5，就要处理
            for (int i = 0; i < mFilesList.size() - 4; i++) {
                String Name = mFilesList.get(i); //得到链表最早的文件名
                File mFile = new File(mfolder, Name);  //得到最早的文件
                mFile.delete(); //删除
            }
        }
        return true;
    }


}