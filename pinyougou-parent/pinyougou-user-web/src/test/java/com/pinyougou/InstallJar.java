package com.pinyougou;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.16-23:31
 */
public class InstallJar {
    public static void main(String[] args) throws IOException {
//        generateJarsLinuxInstallSh("F:\\品优购\\jar\\","linux","F:\\品优购\\jar\\jar-install.sh");
//    moveJarsAndWars("F:\\pingyougou_project\\pinyougou-parent\\","F:\\品优购\\");
   rename("F:\\品优购\\项目文件\\");
    }
    private static void rename(String path){
        File file=new File(path);
        if (file.exists()){
            File[] files = file.listFiles();
            for(File file1 : files) {
                if (file1.isFile()){
                    if (file1.getName().endsWith("war")){
                        String newName = file1.getName().split("-1")[0];
                        boolean b = file1.renameTo(new File(newName + ".war"));
                        System.out.println(newName+"改名："+b);
                    }

                }
            }
        }
    }
    private static void moveJarsAndWars(String path,String destPath){
        File file=new File(path);
        if (file.exists()){
            File[] files=file.listFiles();
            if (files!=null){
                for(File file1 : files) {
                    if (file1.isDirectory()){
                        File target = new File(file1.getName() + "\\target\\");
                        if (!target.exists()){
                            continue;
                        }
                        File[] files1 = target.listFiles();
                        for(File file2 : files1) {
                            if (file2.getName().endsWith("jar")||file2.getName().endsWith("war")){
                                boolean b = file2.renameTo(new File(destPath + file2.getName()));
                                System.out.println(file2.getName()+"移动："+b);
                            }
                        }
                    }
                }
            }
        }
    }
    private static void generateJarsLinuxInstallSh(String path,String OS,String generatePath) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(generatePath), true));
        File dirFile = new File(path);
        if (dirFile.exists()) {
            File[] files = dirFile.listFiles();
            if (files != null) {
                String installStr1;
                if (!isWindows(OS)){
                    bufferedWriter.write("#!/bin/bash\n");
                    installStr1="mvn install:install-file -Dfile=[filename] -DgroupId=[gid] -DartifactId=[artid] -Dversion=[version] -Dpackaging=jar -DgeneratePom=true";
                }else{
                    installStr1="call mvn install:install-file -Dfile=[filename] -DgroupId=[gid] -DartifactId=[artid] -Dversion=[version] -Dpackaging=jar -DgeneratePom=true";
                }
                for(File fileChildDir : files) {
                   String installStr =installStr1 ;
                    //输出文件名
                    String fileChildDirName = fileChildDir.getName();
                    if (fileChildDirName.endsWith("jar")) {
                        installStr = installStr.replace("[filename]", fileChildDirName);
                        if (fileChildDirName.contains("dysmsapi")) {
                            setInfo(bufferedWriter, installStr, fileChildDirName, "com.aliyun", "-1", "1.0.0-SNAPSHOT");
                        }
                        if (fileChildDirName.contains("core")) {
                            setInfo(bufferedWriter, installStr, fileChildDirName, "com.aliyun", "-3", "3.2.5");
                        }
                        if (fileChildDirName.startsWith("wxpay")) {
                            setInfo(bufferedWriter, installStr, fileChildDirName, "com.github.wxpay", "-0", "0.0.3");
                        }
                        if (fileChildDirName.startsWith("pinyougou")) {
                            setInfo(bufferedWriter,
                                    installStr,
                                    fileChildDirName,
                                    "com.pinyougou", "-1", "1.0-SNAPSHOT");
                        }
                    }
                }
            }
        } else {
            System.out.println("你想查找的文件不存在");
        }
        if (isWindows(OS)){
            bufferedWriter.write("pause");
        }
        bufferedWriter.close();
    }

    private static void setInfo(BufferedWriter bufferedWriter,
                                String installStr,
                                String fileChildDirName,
                                String groupId,
                                String artifactIdRgx,
                                String version) throws IOException {
        installStr = installStr.replace("[gid]", groupId);
        installStr = installStr.replace("[version]", version);
        String artid = fileChildDirName.split(artifactIdRgx)[0];
        installStr = installStr.replace("[artid]", artid);
        bufferedWriter.write(installStr + "\n");
    }
    private static boolean isWindows(String OS){
        return "windows".equals(OS);
    }
}
