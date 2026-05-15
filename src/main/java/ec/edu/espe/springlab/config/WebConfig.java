package ec.edu.espe.springlab.config;

import ec.edu.espe.springlab.interceptor.RequestLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLoggingInterceptor loggingInterceptor;

    //Registrar el interceptor para todas las rutas
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/api/**");
    }
}
