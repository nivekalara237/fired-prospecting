package com.niveka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  Created by Nivek@lara on 13/02/2019.
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
