package com.uncub;

import com.sun.org.apache.bcel.internal.generic.FieldOrMethod;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    //扫描目录
    static String BASE_PATH;
    //文件单位
    static String FILE_SIZE_UTIL;
    //目录前缀
    static String PRE_PATH;
    static List<String> PRE_PATHS;
    static Properties config = new Properties();

    static {
        try {
            config.load(Main.class.getClassLoader().getResource("config.properites").openStream());
            BASE_PATH = config.getProperty("BASE_PATH");
            FILE_SIZE_UTIL = config.getProperty("FILE_SIZE_UTIL");
            PRE_PATH = config.getProperty("PRE_PATH");
            PRE_PATHS = StringUtils.isNotBlank(PRE_PATH) ? Arrays.asList(PRE_PATH.split(";")) : new ArrayList<String>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {

        File baseDir = new File(BASE_PATH);
        if (StringUtils.isBlank(BASE_PATH)) throw new Exception("扫描目录不能为空");
        if (!baseDir.exists()) throw new Exception("扫描目录不存在");
        if (baseDir.isFile()) throw new Exception("扫描目录不能为文件");
        List<FileEntity> fileEntities = new ArrayList<FileEntity>();
        //未扫描目录集合
        LinkedList<File> unscanDirList = new LinkedList<File>();
        unscanDirList.addFirst(baseDir);
        File file = null;
        while(unscanDirList.size() > 0 && (file =unscanDirList.removeFirst()) != null){
            if (file.isFile()){//为文件时读取文件信息
                FileEntity fileEntity = translateFile(file);
                fileEntities.add(fileEntity);
            }else {//为目录时，读取子文件
                File[] childFiles = file.listFiles();
                for (File childFile : childFiles){
                    unscanDirList.addLast(childFile);
                }
            }
        }
        printResult(fileEntities);
    }

    private static void printResult(List<FileEntity> fileEntities){
        System.out.println();
        for (FileEntity fileEntity: fileEntities){
            System.out.print(fileEntity.getFileName() + "\t" + fileEntity.getFilePath() + "\t" + fileEntity.getFileSize() + fileEntity.getSizeUtil() + "\n");
        }
        System.out.flush();
    }

    public void test(){
        File [] strs = new File[]{};
        for (File str : strs){

        }
    }

    private static FileEntity translateFile(File file){
        FileEntity fileEntity = new FileEntity();
        fileEntity.setAbsolutePath(file.getAbsolutePath());
        fileEntity.setFileName(file.getName());
        fileEntity.setSizeUtil(FILE_SIZE_UTIL);
        fileEntity.setFilePath(file.getAbsolutePath().replace(BASE_PATH,"").replaceAll("\\\\", "/"));
        for (String prePath : PRE_PATHS){
            fileEntity.setFilePath(fileEntity.getFilePath().replace(prePath, ""));
        }
        if ("KB".equalsIgnoreCase(FILE_SIZE_UTIL)){
            fileEntity.setFileSize(String.valueOf(file.length()/1024));
        }
        else if ("B".equalsIgnoreCase(FILE_SIZE_UTIL)){
            fileEntity.setFileSize(String.valueOf(file.length()));
        }
        return fileEntity;
    }


}
