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
package gov.kktcmeb.security.kktcmebsecurityacl.core.named;

import org.springframework.beans.factory.BeanNameAware;

import java.util.Objects;

public class NamedBean implements BeanNameAware, org.springframework.beans.factory.NamedBean {

  private String name;

  @Override
  public void setBeanName(String name) {
    this.name = name;
  }

  @Override
  public String getBeanName() {
    return name;
  }

  public String name() {
    return Objects.toString(name, getClass().getSimpleName());
  }

  @Override
  public String toString() {
    return name();
  }
}