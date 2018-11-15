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
package gov.kktcmeb.security.kktcmebsecurityacl.jpa.config;


import gov.kktcmeb.security.kktcmebsecurityacl.core.AclStrategyProvider;
import gov.kktcmeb.security.kktcmebsecurityacl.core.SimpleAclStrategy;
import gov.kktcmeb.security.kktcmebsecurityacl.core.compound.AclComposersRegistry;
import gov.kktcmeb.security.kktcmebsecurityacl.core.config.AclConfiguration;
import gov.kktcmeb.security.kktcmebsecurityacl.jpa.JpaSpecFeature;
import gov.kktcmeb.security.kktcmebsecurityacl.jpa.JpaSpecProvider;
import gov.kktcmeb.security.kktcmebsecurityacl.jpa.compound.JpaSpecComposer;
import gov.kktcmeb.security.kktcmebsecurityacl.jpa.spec.AllowAllSpecification;
import gov.kktcmeb.security.kktcmebsecurityacl.jpa.spec.DenyAllSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;

@Configuration
@Import(AclConfiguration.class)
public class JpaSpecAclConfiguration<T> {

  private JpaSpecFeature<T> jpaSpecFeature = new JpaSpecFeature<>();
  private Logger logger = LoggerFactory.getLogger(JpaSpecAclConfiguration.class);

  public JpaSpecAclConfiguration() {
    logger.info("Configured feature : {}", jpaSpecFeature);
  }

  @Bean
  public JpaSpecFeature<T> jpaSpecFeature() {
    return jpaSpecFeature;
  }

  @Bean
  @ConditionalOnMissingBean(JpaSpecComposer.class)
  public JpaSpecComposer<T> jpaSpecComposer(AclComposersRegistry registry) {
    JpaSpecComposer<T> composer = new JpaSpecComposer<>();
    registry.register(jpaSpecFeature, composer);
    return composer;
  }

  @Bean
  public JpaSpecProvider<T> jpaSpecProvider(AclStrategyProvider strategyProvider,
                                            Specification<T> defaultAclSpecification) {
    return new JpaSpecProvider<>(strategyProvider, jpaSpecFeature, defaultAclSpecification);
  }

  @Bean(name = {"allowAllSpecification", "defaultAclSpecification"})
  public AllowAllSpecification<T> allowAllSpecification(SimpleAclStrategy allowAllStrategy) {
    AllowAllSpecification<T> allowAllSpecification = new AllowAllSpecification<>();
    allowAllStrategy.install(jpaSpecFeature, allowAllSpecification);
    return allowAllSpecification;
  }

  @Bean
  public DenyAllSpecification<T> denyAllSpecification(SimpleAclStrategy denyAllStrategy) {
    DenyAllSpecification<T> denyAllSpecification = new DenyAllSpecification<>();
    denyAllStrategy.install(jpaSpecFeature, denyAllSpecification);
    return denyAllSpecification;
  }
}
