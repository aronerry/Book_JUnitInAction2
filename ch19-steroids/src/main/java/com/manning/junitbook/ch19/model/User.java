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
package com.manning.junitbook.ch19.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="users")
public class User {
  
  @Id @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  
  private String username;
  
  @Column(name="first_name")
  private String firstName;
  
  @Column(name="last_name")
  private String lastName;

  @OneToMany(cascade=CascadeType.ALL)
  @JoinColumn(name="user_id")
  @ForeignKey(name="fk_telephones_users")
  private List<Telephone> telephones = new ArrayList<Telephone>();
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String login) {
    this.username = login;
  }

  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String name) {
    this.firstName = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public List<Telephone> getTelephones() {
    return telephones;
  }
  
  public void setTelephones(List<Telephone> telephones) {
    this.telephones = telephones;
  }

}
