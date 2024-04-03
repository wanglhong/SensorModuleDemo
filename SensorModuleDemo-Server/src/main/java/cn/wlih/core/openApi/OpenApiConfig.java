package cn.wlih.core.openApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(SwaggerProperties.class)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(SwaggerProperties swaggerProperties) {
        Info info = new Info().title(swaggerProperties.getTitle()).version(swaggerProperties.getVersion()).description(swaggerProperties.getDescription());
        return new OpenAPI().info(info);
    }

    @Bean
    public GroupedOpenApi upmsApi(SwaggerProperties swaggerProperties) {
        String[] paths = {"/api/upms/**"};
        String[] packagedToMatch = {swaggerProperties.getServiceBasePackage() + ".upms.controller"};
        return GroupedOpenApi.builder().group("用户权限分组接口")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public GroupedOpenApi appApi(SwaggerProperties swaggerProperties) {
        String[] paths = {"/api/app/**"};
        String[] packagedToMatch = {swaggerProperties.getServiceBasePackage() + ".app.controller"};
        return GroupedOpenApi.builder().group("系统业务分组接口")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public GroupedOpenApi sensormoduleApi(SwaggerProperties swaggerProperties) {
        String[] paths = {"/api/sensormodule/**"};
        String[] packagedToMatch = {swaggerProperties.getServiceBasePackage() + ".sensormodule.controller"};
        return GroupedOpenApi.builder().group("物联网信息分组接口")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

}
