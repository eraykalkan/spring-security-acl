/*******************************************************************************
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package gov.kktcmeb.security.kktcmebsecurityacl.sample.grant;

import gov.kktcmeb.security.kktcmebsecurityacl.sample.domain.Customer;
import gov.kktcmeb.security.kktcmebsecurityacl.sample.jpa.CustomerRepository;
import org.springframework.security.core.Authentication;

import static gov.kktcmeb.security.kktcmebsecurityacl.jpa.spec.AclJpaSpecifications.idEqualTo;


public class CustomerGrantEvaluator extends AbstractGrantEvaluator<Customer, String> {

  private CustomerRepository repository;

  public CustomerGrantEvaluator(CustomerRepository repository) {
    super();
    this.repository = repository;
  }

  //obje yetkileri icin
  @Override
  public boolean isGranted(Permission permission, Authentication authentication,Customer domainObject) {
    //return "Smith".equals(domainObject.getLastName());
    System.out.println(authentication.getName());
    boolean flag=false;
    if(authentication.getName().equals("user"))
      flag=true;
    return flag;
  }

  //field yetkileri icin
  @Override
  public boolean isGranted(Permission permission, Authentication authentication, String targetId,
      Class<? extends Customer> targetType) {
    // thanks to JpaSpecFeature, repository will count only authorized customers !
    return repository.count(idEqualTo(targetId)) == 1;
    // if Jpa feature was not enabled, we would use
    // return repository.countByLastName("Smith")
  }

}
