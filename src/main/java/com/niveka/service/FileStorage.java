package com.niveka.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 *  Created by Nivek@lara on 13/02/2019.
 */
public interface FileStorage {
    public void store(MultipartFile file);
    public Resource loadFile(String filename);
    public void deleteAll();
    public void init();
    public Stream<Path> loadFiles();
}
