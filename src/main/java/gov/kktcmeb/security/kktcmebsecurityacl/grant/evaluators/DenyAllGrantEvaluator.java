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
package gov.kktcmeb.security.kktcmebsecurityacl.grant.evaluators;


import gov.kktcmeb.security.kktcmebsecurityacl.core.named.NamedBean;
import gov.kktcmeb.security.kktcmebsecurityacl.grant.GrantEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class DenyAllGrantEvaluator extends NamedBean implements GrantEvaluator {

  @Override
  public boolean isGranted(Object permission, Authentication authentication, Object domainObject) {
    return false;
  }

  @Override
  public boolean isGranted(Object permission, Authentication authentication, Serializable targetId,
      String targetType) {
    return false;
  }

}
