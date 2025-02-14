package com.juwon.sftp.client.controller;

import com.juwon.sftp.client.service.SftpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SftpClientController {

    @Autowired
    private SftpClientService sftpClientService;

    @GetMapping("/upload")
    public String uploadFile(@RequestParam String localFilePath, @RequestParam String remoteFilePath) {
        sftpClientService.uploadFile("127.0.1", 22, "username", "password", localFilePath, remoteFilePath);
        return "File upload 호출성공";
    }
}