package ec.edu.espe.springlab.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLoggingInterceptorTest {

    @Test
    void shouldAddElapsedHeader() throws Exception {
        // Prueba 5 — Interceptor agrega X-Elapsed-Time
        RequestLoggingInterceptor interceptor = new RequestLoggingInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        interceptor.preHandle(request, response, new Object());
        
        // Simular un pequeño retraso
        Thread.sleep(10);
        
        interceptor.postHandle(request, response, new Object(), null);

        assertThat(response.getHeader("X-Elapsed-Time")).isNotNull();
        long elapsed = Long.parseLong(response.getHeader("X-Elapsed-Time"));
        assertThat(elapsed).isGreaterThanOrEqualTo(10);
    }
}
