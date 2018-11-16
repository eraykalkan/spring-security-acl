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
package com.eray.security.securityacl.grant.config;


import com.eray.security.securityacl.core.AclStrategyProvider;
import com.eray.security.securityacl.core.SimpleAclStrategy;
import com.eray.security.securityacl.core.compound.AclComposersRegistry;
import com.eray.security.securityacl.core.config.AclConfiguration;
import com.eray.security.securityacl.grant.AclPermissionEvaluator;
import com.eray.security.securityacl.grant.GrantEvaluator;
import com.eray.security.securityacl.grant.GrantEvaluatorFeature;
import com.eray.security.securityacl.grant.compound.GrantEvaluatorComposer;
import com.eray.security.securityacl.grant.evaluators.AllowAllGrantEvaluator;
import com.eray.security.securityacl.grant.evaluators.DenyAllGrantEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AclConfiguration.class)
public class GrantEvaluatorAclConfiguration {

  private GrantEvaluatorFeature grantEvaluatorFeature = new GrantEvaluatorFeature();
  private Logger logger = LoggerFactory.getLogger(GrantEvaluatorAclConfiguration.class);

  public GrantEvaluatorAclConfiguration() {
    logger.info("Configured feature : {}", grantEvaluatorFeature);
  }

  @Bean
  public GrantEvaluatorFeature grantEvaluatorFeature() {
    return grantEvaluatorFeature;
  }

  @Bean
  @ConditionalOnMissingBean(GrantEvaluatorComposer.class)
  public GrantEvaluatorComposer grantEvaluatorComposer(AclComposersRegistry registry) {
    GrantEvaluatorComposer composer = new GrantEvaluatorComposer();
    registry.register(grantEvaluatorFeature, composer);
    return composer;
  }

  @Bean
  public AclPermissionEvaluator aclPermissionEvaluator(AclStrategyProvider strategyProvider,
                                                       GrantEvaluator defaultGrantEvaluator) {
    return new AclPermissionEvaluator(strategyProvider, grantEvaluatorFeature,
        defaultGrantEvaluator);
  }

  @Bean(name = {"allowAllGrantEvaluator", "defaultGrantEvaluator"})
  public GrantEvaluator allowAllGrantEvaluator(SimpleAclStrategy allowAllStrategy) {
    GrantEvaluator allowAllGrantEvaluator = new AllowAllGrantEvaluator();
    allowAllStrategy.install(grantEvaluatorFeature, allowAllGrantEvaluator);
    return allowAllGrantEvaluator;
  }

  @Bean
  public GrantEvaluator denyAllGrantEvaluator(SimpleAclStrategy denyAllStrategy) {
    GrantEvaluator denyAllGrantEvaluator = new DenyAllGrantEvaluator();
    denyAllStrategy.install(grantEvaluatorFeature, denyAllGrantEvaluator);
    return denyAllGrantEvaluator;
  }

}
