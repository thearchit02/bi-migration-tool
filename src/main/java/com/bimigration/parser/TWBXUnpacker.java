package com.bimigration.parser;

import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

@Component
public class TWBXUnpacker {

    public File unpack(File twbxFile) throws Exception {
        Path tempDir = Files.createTempDirectory("bimigration_");
        File extractedTwb = null;

        try (ZipInputStream zipIn = new ZipInputStream(
                new FileInputStream(twbxFile))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                if (entry.getName().endsWith(".twb")) {
                    extractedTwb = tempDir.resolve(
                            entry.getName()).toFile();
                    extractFile(zipIn, extractedTwb);
                    break;
                }
                zipIn.closeEntry();
            }
        }

        if (extractedTwb == null) {
            throw new Exception(
                    "No TWB file found inside TWBX: " +
                            twbxFile.getName());
        }

        return extractedTwb;
    }

    private void extractFile(ZipInputStream zipIn,
            File destFile) throws IOException {
        destFile.getParentFile().mkdirs();
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(destFile))) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = zipIn.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
        }
    }

    public void cleanup(File extractedTwb) {
        if (extractedTwb != null && extractedTwb.exists()) {
            extractedTwb.getParentFile().deleteOnExit();
            extractedTwb.deleteOnExit();
        }
    }
}