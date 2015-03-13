/* 
 * ========================================================================
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ========================================================================
 */
package com.manning.junitbook.ch17.dbunit;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.el.BeanELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

import org.apache.el.ValueExpressionLiteral;

public final class ELContextImpl extends ELContext {
  
  private final BeanELResolver resolver = new BeanELResolver();
  private final FunctionMapper functionMapper = new NoopFunctionMapper();
  private final VariableMapper variableMapper = new VariableMapperImpl();
  private final Map<String, ValueExpression> variables =  new HashMap<String, ValueExpression>();
  private final ExpressionFactory factory;

  ELContextImpl() {
    final String factoryClass = "org.apache.el.ExpressionFactoryImpl";
    System.setProperty("javax.el.ExpressionFactory", factoryClass);
    factory = ExpressionFactory.newInstance();
    if ( factory == null ) {
      throw new RuntimeException( "could not get instance of factory class " + factoryClass );
    }
  }
  
  public ExpressionFactory getFactory() {
    return factory;
  }

  @Override
  public ELResolver getELResolver() {
    return resolver;
  }

  @Override
  public FunctionMapper getFunctionMapper() {
    return functionMapper;
  }

  @Override
  public VariableMapper getVariableMapper() {
    return variableMapper;
  }
  
  public void bind( String variable, Object obj ) {
    variables.put( variable, new ValueExpressionLiteral(obj,Object.class) );
  }
  
  private class VariableMapperImpl extends VariableMapper {
    public ValueExpression resolveVariable(String s) {
      return variables.get(s);
    }
    public ValueExpression setVariable(String s, ValueExpression valueExpression) {
      return (variables.put(s, valueExpression));
    }
  }

  private class NoopFunctionMapper extends FunctionMapper {
    public Method resolveFunction(String s, String s1) {
      return null;
    }

  }

}
