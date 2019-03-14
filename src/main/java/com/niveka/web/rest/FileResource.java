package com.niveka.web.rest;

import com.niveka.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *  Created by Nivek@lara on 05/03/2019.
 */
@RestController
@RequestMapping("/files")
public class FileResource {
    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    final private FileStorageService fileStorageService;

    public FileResource(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }
    @GetMapping("/image/{path}")
    public ResponseEntity<byte[]> getImage(@PathVariable String path) throws IOException {
        File img = fileStorageService.loadFileAsResource(path).getFile();
        //File img = new File("src/main/resources/static/test.jpg");
        return ResponseEntity
            .ok()
            .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
            .body(Files.readAllBytes(img.toPath()));
    }
    @GetMapping("/stream/{path}")
    public ResponseEntity<byte[]> what(@PathVariable String path) throws IOException{
        File file = new File(path);
        //File file = new File("src/main/resources/static/thing.pdf");
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=" +file.getName())
            .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
            .body(Files.readAllBytes(file.toPath()));
    }

    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        //resource.getFile().getPath();
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
            //kkk
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
