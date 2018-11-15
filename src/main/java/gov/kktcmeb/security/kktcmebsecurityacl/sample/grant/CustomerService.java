/*******************************************************************************
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package gov.kktcmeb.security.kktcmebsecurityacl.sample.grant;

import gov.kktcmeb.security.kktcmebsecurityacl.sample.domain.Customer;
import gov.kktcmeb.security.kktcmebsecurityacl.sample.elasticsearch.CustomerSearchRepository;
import gov.kktcmeb.security.kktcmebsecurityacl.sample.jpa.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {

  @Resource
  private CustomerRepository repository;

  /*@Autowired
  private CustomerSearchRepository repository;*/

  @Transactional
  @PreAuthorize("hasPermission(#customer, 'SAVE')")
  public Customer save(Customer customer) {
    return repository.save(customer);
  }

  @Transactional
  @PreAuthorize("hasPermission(#customerId, 'gov.kktcmeb.security.kktcmebsecurityacl.sample.domain.Customer', 'READ')")
  public List<Customer> findByLastName(String lastName) {
    return repository.findByLastName(lastName);
  }


  public List<Customer> findAll() {
      return repository.findAll();
  }
}