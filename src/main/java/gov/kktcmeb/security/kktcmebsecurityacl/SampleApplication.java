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
package gov.kktcmeb.security.kktcmebsecurityacl;


import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import gov.kktcmeb.security.kktcmebsecurityacl.core.SimpleAclStrategy;
import gov.kktcmeb.security.kktcmebsecurityacl.elasticsearch.ElasticSearchFeature;
import gov.kktcmeb.security.kktcmebsecurityacl.elasticsearch.repository.AclElasticsearchRepositoryFactoryBean;
import gov.kktcmeb.security.kktcmebsecurityacl.grant.GrantEvaluator;
import gov.kktcmeb.security.kktcmebsecurityacl.grant.GrantEvaluatorFeature;
import gov.kktcmeb.security.kktcmebsecurityacl.jpa.JpaSpecFeature;
import gov.kktcmeb.security.kktcmebsecurityacl.jpa.repository.AclJpaRepositoryFactoryBean;
import gov.kktcmeb.security.kktcmebsecurityacl.sample.domain.Customer;
import gov.kktcmeb.security.kktcmebsecurityacl.sample.grant.CustomerGrantEvaluator;
import gov.kktcmeb.security.kktcmebsecurityacl.sample.jpa.CustomerRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@EnableJpaRepositories(value = "gov.kktcmeb.security.kktcmebsecurityacl.sample.jpa",
    repositoryFactoryBeanClass = AclJpaRepositoryFactoryBean.class)
@EnableElasticsearchRepositories(value = "gov.kktcmeb.security.kktcmebsecurityacl.sample.elasticsearch",
    repositoryFactoryBeanClass = AclElasticsearchRepositoryFactoryBean.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SampleApplication {

  private SimpleAclStrategy customerStrategy = new SimpleAclStrategy();

  public static void main(String[] args) {
    SpringApplication.run(SampleApplication.class, args);
  }

  @Bean
  public SimpleAclStrategy customerStrategy() {
    return customerStrategy;
  }

  /*@Bean
  public QueryBuilder smithFamilyFilter(ElasticSearchFeature elasticSearchFeature) {
    QueryBuilder smithFamilyFilter = matchQuery("lastName", "Smith");
    customerStrategy.install(elasticSearchFeature, smithFamilyFilter);
    return smithFamilyFilter;
  }*/

  @Bean
  public Specification<Customer> smithFamilySpec(JpaSpecFeature<Customer> jpaSpecFeature) {
    Specification<Customer> smithFamilySpec = new Specification<Customer>() {
      @Override
      public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        return cb.equal(root.get("lastName"), "Smith");
      }
    };
    customerStrategy.install(jpaSpecFeature, smithFamilySpec);
    return smithFamilySpec;
  }

  @Bean
  public GrantEvaluator smithFamilyGrantEvaluator(CustomerRepository customerRepository,
                                                  GrantEvaluatorFeature grantEvaluatorFeature) {
    GrantEvaluator smithFamilyGrantEvaluator = new CustomerGrantEvaluator(customerRepository);
    customerStrategy.install(grantEvaluatorFeature, smithFamilyGrantEvaluator);
    return smithFamilyGrantEvaluator;
  }


}
