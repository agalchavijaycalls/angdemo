package org.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Email
  @Column(name = "email", unique = true)
  private String email;
  
  @Column(name = "password")
  private String password;
  
  @Column(name = "display_name")
  private String displayName;
  
  public long getId() {
    return id;
  }
  
  public String getEmail() {
    return email;
  }
  
  public String getPassword() {
    return password;
  }
  
  public String getDisplayName() {
    return displayName;
  }
  
  public void setEmail(final String email) {
    this.email = email;
  }
  
  public void setPassword(final String password) {
    this.password = password;
  }
  
  public void setDisplayName(final String name) {
    this.displayName = name;
  }
  
  @JsonIgnore
  public int getSignInMethodCount()
      throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    int count = 0;
    
    if (this.getPassword() != null) {
      count++;
    }
    return count;
  }
  
  @Override
  public String toString() {
    return "User [id=" + id + ", email=" + email + ", displayName=" + displayName + "]";
  }
  
}
