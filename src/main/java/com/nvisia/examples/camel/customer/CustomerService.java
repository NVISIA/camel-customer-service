package com.nvisia.examples.camel.customer;

/**
 * Interface for services to process requests for customer data. 
 * 
 * @author Michael Hoffman, NVISIA
 *
 */
public interface CustomerService {

   /**
    * Retrieves a customer by its unique identifier. 
    * 
    * @param id
    * @return Customer
    */
   Customer getCustomer(int id); 
}
