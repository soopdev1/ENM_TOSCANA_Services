///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package rc.soop.rest;
//
//import io.swagger.jaxrs.config.BeanConfig;
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//
///**
// *
// * @author Administrator
// */
//public class SwaggerConfigurationServlet extends HttpServlet{
//        
//    public void init(ServletConfig config) throws ServletException{
//        super.init(config);
//        
//        BeanConfig cbc = new BeanConfig();
//        
//        cbc.setHost("localhost:8081");
//        cbc.setSchemes(new String[]{"http"});
//        cbc.setBasePath("/restv2/enm");
//        cbc.setTitle("ENM SERVICES - YISU TOSCANA");
//        cbc.setVersion("1.0.0");
//        cbc.setResourcePackage("rc.soop.rest");
//        cbc.setScan(true);
//        cbc.setPrettyPrint(true);
//    }
//}
