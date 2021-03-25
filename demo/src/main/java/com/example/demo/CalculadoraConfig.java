package com.example.demo;

import java.applet.AppletContext;

import javax.servlet.ServletRegistration;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableWs
@Configuration
public class CalculadoraConfig extends WsConfigurerAdapter {
    //Trata de recuperar el esquema que vamos a usar
    @Bean
    public XsdSchema calculadoraSchema(){
        return new SimpleXsdSchema(new ClassPathResource("calculadora.xsd"));
    }
    //tener un punto de entrada para donde se va a contactar
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext){
        
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }
    //exponer la ruta donde vamos a contactar el sitio web, donde esta el endpoint, define el puerto de servicio web
    //nos da la exposicion del wsdl
    @Bean (name = "calculadora")
    public DefaultWsdl11Definition defaultWsdl11Definition (XsdSchema calculadorSchema){

        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("CalculadoraPort");
        wsdl.setLocationUri("/ws/calculadora");
        wsdl.setTargetNamespace("http://www.example.org/calculadora");
        wsdl.setSchema(calculadorSchema);
        return wsdl;
        
    }

    @Bean
    public FilterRegistrationBean corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    
}
