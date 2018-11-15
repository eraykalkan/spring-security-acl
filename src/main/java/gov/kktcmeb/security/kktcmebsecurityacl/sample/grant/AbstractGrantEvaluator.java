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

import gov.kktcmeb.security.kktcmebsecurityacl.grant.TypedGrantEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

import static org.springframework.util.Assert.notNull;

public abstract class AbstractGrantEvaluator<T, ID extends Serializable>
    extends TypedGrantEvaluator<T, ID, Authentication, Permission> {

  @Override
  protected Permission mapPermission(Object permission) {
    notNull(permission, "Permission must be not null");
    return Permission.valueOf(String.valueOf(permission));
  }

}
