package org.example.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.example.model.User;

@ApplicationScoped
public class UserDAO {
  @PersistenceContext(unitName = "example-persistence-unit")
  private EntityManager em;
  
  public User findById(Long id) {
    return em.find(User.class, id);
  }
  
  public User findByEmail(String email) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<User> criteria = cb.createQuery(User.class);
    Root<User> member = criteria.from(User.class);
    criteria.select(member).where(cb.equal(member.get("email"), email));
    return em.createQuery(criteria).getSingleResult();
  }
  
  public User save(User user) {
    return em.merge(user);
  }
  
  public List<User> findAll() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<User> criteria = cb.createQuery(User.class);
    Root<User> member = criteria.from(User.class);
    criteria.select(member);
    return em.createQuery(criteria).getResultList();
  }
  
}
