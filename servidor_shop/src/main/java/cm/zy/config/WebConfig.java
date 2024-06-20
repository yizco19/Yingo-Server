package cm.zy.config;

import cm.zy.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

/**
 * Configuración para la aplicación web.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    /**
     * Agrega interceptores para gestionar la autenticación.
     *
     * @param registry el registro de interceptores
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // login y register no interceptan
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register","/register","/login");
    }

}
