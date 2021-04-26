package com.lzl;

import org.csource.fastdfs.*;

import java.io.OutputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        ClientGlobal.init("F:\\pingyougou_project\\fastdfsDemo\\src\\main\\resources\\fdfs_client.conf");
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer connection = trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient storageClient=new StorageClient(connection,storageServer);
        String[] confs = storageClient.upload_file("D:/0.jpg", "jpg", null);
        for(String conf : confs) {
            System.out.print(conf+" ,");
        }

    }
}
