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
package gov.kktcmeb.security.kktcmebsecurityacl.core.activation.web;


import gov.kktcmeb.security.kktcmebsecurityacl.core.activation.AclSecurityActivator;
import gov.kktcmeb.security.kktcmebsecurityacl.core.activation.AclStatus;

import javax.servlet.*;
import java.io.IOException;

import static gov.kktcmeb.security.kktcmebsecurityacl.core.activation.AclStatus.ENABLED;


public class AclActivatorFilter implements Filter {

  private AclStatus statusDuringTest;
  private AclSecurityActivator aclSecurityActivator;

  public AclActivatorFilter(AclSecurityActivator aclSecurityActivator) {
    this(ENABLED, aclSecurityActivator);
  }

  public AclActivatorFilter(AclStatus statusDuringTest, AclSecurityActivator aclSecurityActivator) {
    super();
    this.statusDuringTest = statusDuringTest;
    this.aclSecurityActivator = aclSecurityActivator;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    AclStatus status = aclSecurityActivator.getStatus();
    aclSecurityActivator.setStatus(statusDuringTest);
    try {
      chain.doFilter(request, response);
    } finally {
      aclSecurityActivator.setStatus(status);
    }
  }

  @Override
  public void destroy() {}
}
