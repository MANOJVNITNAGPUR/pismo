package io.pismo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder().title("Pismo Banking API")
                .description("Pismo banking service to handle customer accounts & transactions")
                .version(version)
                .build();

    }
    public Docket swaggerApiConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Pismo Banking API")
                .apiInfo(apiInfo("v1"))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.ant("v1/*"))
                .build();
    }

}
