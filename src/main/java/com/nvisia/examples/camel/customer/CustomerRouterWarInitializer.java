package com.nvisia.examples.camel.customer;

import org.apache.camel.spring.boot.*;

/**
 * Extends Spring Boot's SpringBootServletInitializer for initializing the web
 * application, providing the ability to initialize the Camel router for customer services.
 * 
 * @author Michael Hoffman, NVISIA
 *
 */
public class CustomerRouterWarInitializer extends FatWarInitializer {

   @Override
   protected Class<? extends FatJarRouter> routerClass() {
      return CustomerRouter.class;
   }

}
