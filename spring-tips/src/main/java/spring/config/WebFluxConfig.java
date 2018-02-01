package spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.*;
import org.springframework.web.reactive.resource.VersionResourceResolver;

/**
 * webFlux配置
 */
@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    /**
     * 跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")
                .allowedOrigins("http://domain2.com")
                .allowedMethods("PUT", "POST")
                .allowedHeaders("header1", "hearder2")
                .exposedHeaders("header1", "header2")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 自定义输出格式
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    /**
     * bean验证
     *
     * @return
     */
    @Override
    public Validator getValidator() {
        return null;
    }

    /**
     * 自定义头部请求
     *
     * @param builder
     */
    @Override
    public void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {

    }


    /**
     * 自定义消息格式【json/xml】
     *
     * @param configurer
     */
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {

    }

    /**
     * 配置视图模板转换器
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {


    }

    /**
     * 自定义静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public/")
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    /**
     * 自定义路径匹配
     *
     * @param configurer
     */
    @Override
    public void configurePathMatching(PathMatchConfigurer configurer) {

    }


}
