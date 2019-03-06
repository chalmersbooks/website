package service;

import entity.Ad;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AdFacade extends Facade<Ad> {

    @PersistenceContext(unitName = "database")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public AdFacade() {
        super(Ad.class);
    }

    public List<Ad> findByName(String name){
        return em.createNamedQuery("Ad.findByName", Ad.class).setParameter("name", name).getResultList();
    }

}
