package com.newpaper.myproject1.Controllers;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//comment test1
public class FileUploadUtil {
    public static void saveFile(Path uploadPath, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPathTarget = Paths.get("c:/Users/Volodymyr/IdeaProjects/my-project-1/target/classes/static/images/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }


        if (!Files.exists(uploadPathTarget)) {
            Files.createDirectories(uploadPathTarget);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPathTarget.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }
}
