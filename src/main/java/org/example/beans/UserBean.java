package org.example.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import org.example.model.User;

@SessionScoped
public class UserBean implements Serializable {
  private static final long serialVersionUID = 1L;
  private User user;
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  @Override
  public String toString() {
    return "UserBean [user=" + user + "]";
  }
  
}
