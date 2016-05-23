package com.nvisia.examples.camel.customer;

import org.springframework.stereotype.*;

/**
 * Default implementation for customer services.
 * 
 * @author Michael Hoffman, NVISIA
 *
 */
@Service("customerService")
public class DefaultCustomerService implements CustomerService {

   @Override
   public Customer getCustomer(int id) {
      if (id == 1) {
         return new Customer(1, "Han", "Solo", "888-888-8888");
      } else if (id == 2) {
         return new Customer(2, "Ben", "Solo", "444-333-8228");
      } else {
         return null;
      }
   }

}
