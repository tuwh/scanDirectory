package com.uncub;

public class FileEntity {
    //文件名
    private String fileName;
    //文件绝对路径
    private String absolutePath;
    //文件相对路径
    private String filePath;
    //文件大小
    private String fileSize;
    //单位
    private String sizeUtil;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getSizeUtil() {
        return sizeUtil;
    }

    public void setSizeUtil(String sizeUtil) {
        this.sizeUtil = sizeUtil;
    }
}
