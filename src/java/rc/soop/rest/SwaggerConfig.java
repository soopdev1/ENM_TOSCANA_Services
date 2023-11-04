///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package rc.soop.rest;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// *
// * @author Administrator
// */
//@Configuration
//@EnableSwagger2
////@Tag(name = "book service", description = "the book API with description tag annotation")
//public class SwaggerConfig {
//    
//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.basePackage("rc.soop.rest.ServiceREST"))
//                .paths(PathSelectors.regex("/restv2/enm/2.0/*"))
//                .build().apiInfo(apiInfo());                
//    }
//            
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("ENM SERVICES")
//                .description("YISU TOSCANA")
//                .version("1.0.0")
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//                .contact(new Contact("SmartOOP", "https://smartoop.it/", "developers@smartoop.it"))
//                .build();
//    }}
