package cm.zy.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
/**
 * Configuración para la carga de archivos.
 */
@Configuration
public class FileUploadConfig {
    /**
     * Configura los límites para la carga de archivos.
     *
     * @return un elemento de configuración multipart
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // Establecer el tamaño máximo permitido de un solo archivo
        factory.setMaxFileSize(DataSize.parse("10MB"));
        // Establecer el tamaño máximo permitido total de la solicitud
        factory.setMaxRequestSize(DataSize.parse("10MB"));
        return factory.createMultipartConfig();
    }
}
