package service;

import entity.Ad;
import org.primefaces.model.SortOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

    public List<Ad> findByUserId(String userId) {
        return em.createNamedQuery("Ad.findByUserId", Ad.class).setParameter("userId", userId).getResultList();
    }

    public Ad findById(Long id){
        return em.createNamedQuery("Ad.findById", Ad.class).setParameter("id", id).getSingleResult();
    }

    public List<Ad> getAds(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Ad> cq = cb.createQuery(Ad.class);
        return em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public List<Ad> findBySearchTerm(String searchTerm){
        List<Ad> byCourseCode = em.createNamedQuery("Ad.findCourseCodeBySearchTerm", Ad.class)
                .setParameter("searchTerm", searchTerm).getResultList();
        List<Ad> byBook = em.createNamedQuery("Ad.findBookBySearchTerm",Ad.class)
                .setParameter("searchTerm", searchTerm).getResultList();
        byCourseCode.addAll(byBook);
        return byCourseCode;
    }

    public void delete(Ad ad){
        Ad tmp = findById(ad.getId());
        em.remove(tmp);
    }
}
