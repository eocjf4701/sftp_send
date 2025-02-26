package com.juwon.sftp.client.service;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class SftpClientService {

    public void uploadFile(String host, int port, String username, String password, String localFilePath, String remoteFilePath) {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;

            // stream으로 읽어서 업로드합니다.
            InputStream ins = new FileInputStream(remoteFilePath);
            channelSftp.put(ins, localFilePath, new SftpProgressMonitor() {
                private long max = 0;
                private long count = 0;
                private long percent = 0;

                @Override
                public void init(int op, String src, String dest, long max) {

                }

                @Override
                public boolean count(long bytes) {
                    this.count += bytes;
                    long percentNow = this.count*100/max;
                    if(percentNow>this.percent){
                        this.percent = percentNow;
                        System.out.println("progress : " + this.percent);
                    }
                    return true;
                }

                @Override
                public void end() {

                }
            });
            System.out.println("File uploaded successfully to " + remoteFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}