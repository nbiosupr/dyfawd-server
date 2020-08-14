package site.deepsleep.dyfawd.config.resttemplate;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import site.deepsleep.dyfawd.config.resttemplate.RestTemplateLoggingRequestInterceptor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                // 로깅 인터셉터에서 Stream을 소비하므로 BufferingClientHttpRequestFactory 을 꼭 써야한다.
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(30000))
                .setReadTimeout(Duration.ofMillis(30000))
                // UTF-8 인코딩으로 메시지 컨버터 추가
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                // 로깅 인터셉터 설정
                .additionalInterceptors(new RestTemplateLoggingRequestInterceptor()) .build(); }
}


