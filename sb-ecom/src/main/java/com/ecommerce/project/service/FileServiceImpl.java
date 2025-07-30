package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uplodeImage(String path, MultipartFile file) throws IOException {
        //File names of current/original file
        String originalFileName = file.getOriginalFilename();
        //Generate unique file
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filepath = path+ File.separator+fileName;
        //check if path exists and create
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }


        //uplode to server
        Files.copy(file.getInputStream(), Paths.get(filepath));
        return fileName;


    }
}
