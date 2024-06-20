package cm.zy.controller;

import cm.zy.pojo.Result;
import com.uploadcare.api.Client;
import com.uploadcare.upload.FileUploader;
import com.uploadcare.upload.UploadFailureException;
import com.uploadcare.upload.Uploader;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;


@RestController
public class FileUploadController {

    @Value("${web.upload-path}")
    private String folder;
    @Value("${web.url-path}")
    private String urlPath;


    @Value("${uploadcare.public_key}")
    private String publicKey;

    @Value("${uploadcare.secret_key}")
    private String secretKey;


    /**
     * Sube un archivo al servidor.
     *
     * @param file el archivo a subir
     * @return un Result con la ruta del archivo subido si tiene éxito, o un mensaje de error si falla
     * @throws IOException si ocurre un error de entrada/salida durante la operación de subida
     */
//    @PostMapping("/upload")
    public Result<String>   upload(@RequestParam("file") MultipartFile file) throws IOException {
        // if el archivo es vacío devolver error
        if (file.isEmpty()) {
            return Result.error("El archivo es vacío.");
        }
        // if el archivo es demasiado grande devolver error
        if (file.getSize() > 1024 * 1024 * 5) {
            return Result.error("El archivo es demasiado grande.");
        }
        // if el archivo no tiene extension png, jpg, jpeg devolver error
        if (!file.getOriginalFilename().endsWith(".png") && !file.getOriginalFilename().endsWith(".jpg") && !file.getOriginalFilename().endsWith(".jpeg")) {
            return Result.error("El archivo no tiene extension png, jpg, jpeg.");
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString()
                + oldName.substring(oldName.lastIndexOf("."), oldName.length());

        try {
            // almacenar el archivo
            file.transferTo(new File(folder, newName));

            // devolver la ruta
            String filePath = urlPath + newName;
            return Result.success(filePath);
        } catch (IOException e) {
            return Result.error("Error al subir el archivo.");

        }


    }
   @PostMapping("/upload")
    public Result<String> uploadcare(@RequestParam("file") MultipartFile file) throws IOException {
        // Validate file emptiness, size, and extension
        if (file.isEmpty()) {
            return Result.error("El archivo es vacío.");
        }
        if (file.getSize() > 1024 * 1024 * 5) {
            return Result.error("El archivo es demasiado grande. Máximo 5MB.");
        }
        String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        if (!Arrays.asList("png", "jpg", "jpeg").contains(extension)) {
            return Result.error("El archivo no tiene extension png, jpg, jpeg.");
        }

        // Connect to Uploadcare using provided credentials
        Client client = new Client(publicKey, secretKey);
        // asigna una nombre a file
        String fileName = file.getOriginalFilename();
         File  uploadFile = convert(file);
        try {
            // Use FileUploader with InputStream for efficiency
            Uploader uploader = new FileUploader(client, uploadFile);
            com.uploadcare.api.File uploadedFile = uploader.upload().save();

            return Result.success(uploadedFile.getOriginalFileUrl().toString());

        } catch (UploadFailureException e) {
            // Handle Uploadcare-specific exceptions if needed
            return Result.error("Error al subir el archivo a Uploadcare: " + e.getMessage());
        } catch (Exception e) {
            // Catch general exceptions for unexpected issues
            Result.error("Error inesperado al subir el archivo.");
        }
        return Result.error("Error desconocido al subir el archivo.");
    }

    public File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close(); //IOUtils.closeQuietly(fos);
        } catch (IOException e) {
            convFile = null;
        }

        return convFile;
    }

}
