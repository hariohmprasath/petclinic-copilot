package org.springframework.samples.petclinic.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class ApiGatewayApplication {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Value("classpath:/static/index.html")
    private Resource indexHtml;

    /**
     * workaround solution for forwarding to index.html
     * @see <a href="https://github.com/spring-projects/spring-boot/issues/9785">#9785</a>
     */
    @Bean
    RouterFunction<?> routerFunction() {
        return RouterFunctions.resources("/ui*", new ClassPathResource("static/"))
            .andRoute(RequestPredicates.GET("/"),
                request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml));
    }

    private void startUp(){
        try {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator
                    (false, false, "UTF-8", new ClassPathResource("data.sql"));
            resourceDatabasePopulator.execute(dataSource);
            log.info("Database successfully initialized for petclinic");
        } catch (Exception e) {
            String error = "Error while initializing data " + e.getMessage();
            log.info(error, e);
        }
    }
}
