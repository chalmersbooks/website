package service;

import entity.Ad;
import lombok.extern.java.Log;
import org.primefaces.model.SortOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Map;

@Log
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

    public Ad findById(Long id) {
        return em.createNamedQuery("Ad.findById", Ad.class).setParameter("id", id).getSingleResult();
    }

    public List<Ad> findBySearchTerm(String searchTerm) {
        List<Ad> byCourseCode = em.createNamedQuery("Ad.findCourseCodeBySearchTerm", Ad.class)
                .setParameter("searchTerm", searchTerm).getResultList();
        List<Ad> byBook = em.createNamedQuery("Ad.findBookBySearchTerm", Ad.class)
                .setParameter("searchTerm", searchTerm).getResultList();
        byCourseCode.addAll(byBook);
        return byCourseCode;
    }

    public void delete(Ad ad) {
        Ad tmp = findById(ad.getId());
        em.remove(tmp);
    }

    public List<Ad> findAndSortByPrice(Order order, String searchTerm) {
        return findAndSort("Price", order, searchTerm);
    }

    public List<Ad> findAndSortByDate(Order order, String searchTerm) {
        return findAndSort("Date", order, searchTerm);
    }

    private List<Ad> findAndSort(String sortBy, Order order, String searchTerm) {
        searchTerm = searchTerm == null ? "" : searchTerm;
        String queryName = "Ad.findAndSortBy".concat(sortBy).concat(order.value);
        return em.createNamedQuery(queryName, Ad.class)
                .setParameter("searchTerm", searchTerm)
                .getResultList();
    }

    public enum Order {
        ASC("Asc"),
        DESC("Desc");

        private String value;

        Order(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}