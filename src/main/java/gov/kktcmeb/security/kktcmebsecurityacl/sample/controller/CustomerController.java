package gov.kktcmeb.security.kktcmebsecurityacl.sample.controller;

import gov.kktcmeb.security.kktcmebsecurityacl.sample.domain.Customer;
import gov.kktcmeb.security.kktcmebsecurityacl.sample.grant.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @PostMapping(path="/save")
    //@PreAuthorize("#oauth2.hasScope('server') and hasAuthority('ROLE_OPERATOR')")
    public void kisiBilgi(@RequestBody Customer customer) {
        customerService.save(customer);
    }

    @GetMapping(path="/get")
    //@PreAuthorize("#oauth2.hasScope('server') and hasAuthority('ROLE_OPERATOR')")
    public void get() {
        customerService.findByLastName("armut");
    }

    @GetMapping(path="/getall")
    //@PreAuthorize("#oauth2.hasScope('server') and hasAuthority('ROLE_OPERATOR')")
    public List<Customer> getAll() {
        return customerService.findAll();
    }


}
