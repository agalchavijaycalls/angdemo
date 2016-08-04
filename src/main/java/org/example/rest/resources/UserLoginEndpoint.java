package org.example.rest.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.example.beans.UserBean;
import org.example.dao.UserDAO;
import org.example.model.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserLoginEndpoint {
  
  @Inject
  private UserBean userBean;
  @Inject
  private UserDAO dao;
  
  @POST
  @Path("/login")
  public Response login(User user) {
    userBean.setUser(null);
    if (user != null) {
      User findByEmail = dao.findByEmail(user.getEmail());
      if (findByEmail != null) {
        if (findByEmail.getPassword().equals(user.getPassword())) {
          userBean.setUser(findByEmail);
          return Response.ok().entity(user).build();
        } else {
          return Response.status(Status.BAD_REQUEST).build();
        }
      } else {
        return Response.status(Status.NOT_FOUND).build();
      }
    } else {
      return Response.status(Status.BAD_REQUEST).build();
    }
  }
  
  @GET
  @Path("/logout")
  public Response logout() {
    userBean.setUser(null);
    return Response.status(Status.OK).build();
  }
  
  @GET
  public Response getAllUsers() {
    return Response.ok().entity(dao.findAll()).build();
  }
  
}
