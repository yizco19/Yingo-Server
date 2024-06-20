package cm.zy.interceptors;
import cm.zy.utils.JwtUtil;
import cm.zy.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Map;
/**
 * Interceptor para la autenticación de usuario.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * Método que se ejecuta antes de manejar la solicitud.
     *
     * @param request  la solicitud HTTP
     * @param response la respuesta HTTP
     * @param handler  el objeto manejador
     * @return true si la solicitud debe continuar, false si no
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof ResourceHttpRequestHandler) {
            return true; // Permitir acceso sin autorización a recursos estáticos
        }
        String token = request.getHeader("Authorization");
        try {
            // obtener token de redis
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String redisToken = ops.get(token);
            if(redisToken==null){
                // token no existe
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);

            // hilo local de usuario
            ThreadLocalUtil.set(claims);
            return true;//  correcto
        }catch (Exception e){
            // token invalido y http status 401
            response.setStatus(401);
            return false; // no correcto
        }
    }
    /**
     * Método que se ejecuta después de completar la solicitud.
     *
     * @param request  la solicitud HTTP
     * @param response la respuesta HTTP
     * @param handler  el objeto manejador
     * @param ex       la excepción (si se produce)
     * @throws Exception si ocurre una excepción
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // vaciar hilo local
        ThreadLocalUtil.remove();
    }
}
