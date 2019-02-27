package service;

import entity.User;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class UserFacade extends Facade<User> {

    @PersistenceContext(unitName = "database")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    public User getUserById(String id) {
        try {
            return em.createNamedQuery("User.findById", User.class)
                    .setParameter("email", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
