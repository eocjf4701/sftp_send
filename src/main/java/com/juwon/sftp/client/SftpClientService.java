package com.juwon.sftp.client;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Service;

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

            channelSftp.put(localFilePath, remoteFilePath);
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