package com.example.eventapp.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("upload")
    public ResponseEntity fileUpload(){
        Map<String, Object> data = new HashMap<>();
        data.put("id", "jeong");
        data.put("type", "web");
        data.put("fileSize", 10);

        fileService.fileUpload(data);

        return ResponseEntity.ok("success");

    }
}
