package org.example.rest.resources;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.example.beans.UserBean;
import org.example.model.Example;

/**
 * 
 */
@Stateless
@Path("/examples")
public class ExampleEndpoint {
  @PersistenceContext(unitName = "example-persistence-unit")
  private EntityManager em;
  
  @Inject
  private UserBean userBean;
  
  @POST
  @Consumes("application/json")
  public Response create(Example entity) {
    if (userBean.getUser() == null) {
      return Response.status(Status.UNAUTHORIZED).build();
    }
    em.persist(entity);
    return Response.created(UriBuilder.fromResource(ExampleEndpoint.class).path(String.valueOf(entity.getId())).build())
        .build();
  }
  
  @DELETE
  @Path("/{id:[0-9][0-9]*}")
  public Response deleteById(@PathParam("id") Long id) {
    if (userBean.getUser() == null) {
      return Response.status(Status.UNAUTHORIZED).entity("Authorization required").build();
    }
    Example entity = em.find(Example.class, id);
    if (entity == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    em.remove(entity);
    return Response.noContent().build();
  }
  
  @GET
  @Path("/{id:[0-9][0-9]*}")
  @Produces("application/json")
  public Response findById(@PathParam("id") Long id) {
    TypedQuery<Example> findByIdQuery
        = em.createQuery("SELECT DISTINCT e FROM Example e WHERE e.id = :entityId ORDER BY e.id", Example.class);
    findByIdQuery.setParameter("entityId", id);
    Example entity;
    try {
      entity = findByIdQuery.getSingleResult();
    } catch (NoResultException nre) {
      entity = null;
    }
    if (entity == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    return Response.ok(entity).build();
  }
  
  @GET
  @Produces("application/json")
  public List<Example> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
    TypedQuery<Example> findAllQuery = em.createQuery("SELECT DISTINCT e FROM Example e ORDER BY e.id", Example.class);
    if (startPosition != null) {
      findAllQuery.setFirstResult(startPosition);
    }
    if (maxResult != null) {
      findAllQuery.setMaxResults(maxResult);
    }
    final List<Example> results = findAllQuery.getResultList();
    return results;
  }
  
  @PUT
  @Path("/{id:[0-9][0-9]*}")
  @Consumes("application/json")
  public Response update(@PathParam("id") Long id, Example entity) {
    if (userBean.getUser() == null) {
      return Response.status(Status.UNAUTHORIZED).entity("Authorization required").build();
    }
    if (entity == null) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    if (id == null) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    if (!id.equals(entity.getId())) {
      return Response.status(Status.CONFLICT).entity(entity).build();
    }
    if (em.find(Example.class, id) == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    try {
      entity = em.merge(entity);
    } catch (OptimisticLockException e) {
      return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
    }
    
    return Response.noContent().build();
  }
}
