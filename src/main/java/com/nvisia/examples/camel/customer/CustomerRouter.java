package com.nvisia.examples.camel.customer;

import java.util.*;

import org.apache.camel.component.servlet.*;
import org.apache.camel.model.rest.*;
import org.apache.camel.spring.boot.*;
import org.apache.camel.swagger.servlet.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.embedded.*;
import org.springframework.context.annotation.*;

/**
 * Spring boot application that defines the routes available for customer
 * services
 * 
 * @author Michael Hoffman, NVISIA
 *
 */
@SpringBootApplication
public class CustomerRouter extends FatJarRouter {

   @Override
   public void configure() {
      initializeRestConfiguration();
      initializeCustomerRoute();
   }

   protected void initializeRestConfiguration() {
      // Start by building an instance of RestConfigurationDefinition. Need to
      // specify the component we are going to use for enabling REST endpoints,
      // specifically CamelServlet in this case. Set the binding mode to JSON.
      restConfiguration()
            // Leverage the CamelServlet component for the REST DSL
            .component("servlet")
            // Bind using JSON
            .bindingMode(RestBindingMode.json)
            // I like pretty things...
            .dataFormatProperty("prettyPrint", "true")
            // This is the context path to be used for Swagger API documentation
            .apiContextPath("api-doc")
            // Properties for Swagger
            // Title of the API
            .apiProperty("api.title", "NVISIA Customer Service API")
            // Version of the API
            .apiProperty("api.version", "1.0.0")
            // CORS (resource sharing) enablement
            .apiProperty("cors", "true")
            // Use localhost for calls
            .apiProperty("host", "localhost:8081")
            // Set base path
            .apiProperty("base.path", "camel-customer-service/api");
   }

   protected void initializeCustomerRoute() {
      // Definition of the get customer endpoint
      rest("/customer")
            // This is a GET method call for getting a customer by ID.
            .get("{id}")
            // Description of what this method does
            .description("Retrieve a customer by ID")
            // Define the output type that will be returned from this method
            .outType(Customer.class)
            // Define where the message is routed to as a URI. Here we use a
            // Spring Bean and define the bean method to invoke. Note that Camel
            // has converted the ID placeholder from the URL into a header
            // entry.
            .to("bean:customerService?method=getCustomer(${header.id})");
   }

   @Bean
   public ServletRegistrationBean camelServletRegistrationBean() {
      ServletRegistrationBean registration = new ServletRegistrationBean(
            new CamelHttpTransportServlet(), "/api/*");
      registration.setName("CamelServlet");
      return registration;
   }

   @Bean
   public ServletRegistrationBean swaggerServletRegistrationBean() {
      ServletRegistrationBean registration = new ServletRegistrationBean(
            new RestSwaggerServlet(), "/api-docs/*");
      registration.setName("SwaggerServlet");
      return registration;
   }

   @Bean
   public FilterRegistrationBean filterRegistrationBean() {
      FilterRegistrationBean filterBean = new FilterRegistrationBean();
      filterBean.setFilter(new RestSwaggerCorsFilter());
      List<String> urlPatterns = new ArrayList<String>();
      urlPatterns.add("/camel-customer-service/api-docs/*");
      urlPatterns.add("/camel-customer-service/api/*");
      filterBean.setUrlPatterns(urlPatterns);
      return filterBean;
   }

}
