package service;

import entity.Ad;
import org.primefaces.model.SortOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;

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

    public List<Ad> getAds(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Ad> cq = cb.createQuery(Ad.class);
        return em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }
}
