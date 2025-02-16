package com.juwon.sftp.client.controller;

import com.juwon.sftp.client.service.SftpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SftpClientController {

    @Autowired
    private SftpClientService sftpClientService;

    @GetMapping("/upload")
    public String uploadGetFile(@RequestParam String localFilePath, @RequestParam String remoteFilePath) {
        sftpClientService.uploadFile("localhost", 22, "username", "password", localFilePath, remoteFilePath);
        return "File upload get 호출성공";
    }

    @PostMapping("/upload")
    public String uploadPostFile(@RequestParam String localFilePath, @RequestParam String remoteFilePath) {
        sftpClientService.uploadFile("localhost", 22, "username", "password", localFilePath, remoteFilePath);
        return "File upload post 호출성공";
    }
}