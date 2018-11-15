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
package gov.kktcmeb.security.kktcmebsecurityacl.elasticsearch.config;


import gov.kktcmeb.security.kktcmebsecurityacl.core.AclStrategyProvider;
import gov.kktcmeb.security.kktcmebsecurityacl.core.SimpleAclStrategy;
import gov.kktcmeb.security.kktcmebsecurityacl.core.compound.AclComposersRegistry;
import gov.kktcmeb.security.kktcmebsecurityacl.core.config.AclConfiguration;
import gov.kktcmeb.security.kktcmebsecurityacl.elasticsearch.AclFilterProvider;
import gov.kktcmeb.security.kktcmebsecurityacl.elasticsearch.ElasticSearchFeature;
import gov.kktcmeb.security.kktcmebsecurityacl.elasticsearch.compound.FilterBuilderComposer;
import org.elasticsearch.index.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Configuration
@Import(AclConfiguration.class)
public class ElasticSearchAclConfiguration {

  private ElasticSearchFeature elasticSearchFeature = new ElasticSearchFeature();
  private Logger logger = LoggerFactory.getLogger(ElasticSearchAclConfiguration.class);

  public ElasticSearchAclConfiguration() {
    logger.info("Configured feature : {}", elasticSearchFeature);
  }

  @Bean
  public ElasticSearchFeature elasticSearchFeature() {
    return elasticSearchFeature;
  }

  @Bean
  @ConditionalOnMissingBean(FilterBuilderComposer.class)
  public FilterBuilderComposer filterBuilderComposer(AclComposersRegistry registry) {
    FilterBuilderComposer composer = new FilterBuilderComposer();
    registry.register(elasticSearchFeature, composer);
    return composer;
  }

  @Bean
  public AclFilterProvider aclFilterProvider(AclStrategyProvider strategyProvider,
                                             QueryBuilder defaultFilter) {
    return new AclFilterProvider(strategyProvider, elasticSearchFeature, defaultFilter);
  }

  @Bean(name = {"allowAllFilter", "defaultFilter"})
  public QueryBuilder allowAllFilter(SimpleAclStrategy allowAllStrategy) {
    QueryBuilder allowAllFilter = matchAllQuery();
    allowAllStrategy.install(elasticSearchFeature, allowAllFilter);
    return allowAllFilter;
  }

  @Bean
  public QueryBuilder denyAllFilter(SimpleAclStrategy denyAllStrategy) {
    QueryBuilder denyAllFilter = boolQuery().mustNot(matchAllQuery());
    denyAllStrategy.install(elasticSearchFeature, denyAllFilter);
    return denyAllFilter;
  }
}
