package com.igrallery.jun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Arrays;

@Slf4j
@Component
public class Init implements CommandLineRunner {

    @Value("${file.img}") private String imgPath;
    @Value("${file.thumbnail}") private String thumbnailPath;
    @Value("${spring.jpa.hibernate.ddl-auto}") private String ddlMode;

    private Resource imgDir;
    private Resource thumbnailDir;

    @PostConstruct
    public void init () {
        imgDir = new FileSystemResource(imgPath);
        thumbnailDir = new FileSystemResource(thumbnailPath);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (!imgDir.exists()) { imgDir.getFile().mkdir(); }
            if (!thumbnailDir.exists()) { thumbnailDir.getFile().mkdir(); }
        }
        catch (Exception e) {
            log.info("can't create folder (*require to run) due to permission");
            throw new ExportException("PERMISSION");
        }

        fileSystemInit();
        log.info("[INIT] LET'S PLAY HACK :D");
    }

    public void fileSystemInit () throws IOException {
        if (!ddlMode.equals("update")) {
            Arrays.stream(thumbnailDir.getFile().listFiles()).forEach(File::delete);
            Arrays.stream(imgDir.getFile().listFiles()).forEach(File::delete);
        }
    }
}
