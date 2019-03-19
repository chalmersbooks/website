package service;

import entity.Book;
import entity.CourseCode;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CourseCodeFacade extends Facade<CourseCode> {

    @PersistenceContext(unitName = "database")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseCodeFacade() {
        super(CourseCode.class);
    }

    public CourseCode findById(String courseCode) {
        try {
            return em.createNamedQuery("CourseCode.findById", CourseCode.class)
                    .setParameter("courseCode", courseCode)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<CourseCode> findByBookId(String isbn) {
        return em.createNamedQuery("CourseCode.findByBook", CourseCode.class)
                .setParameter("isbn", isbn)
                .getResultList();

    }

    public List<CourseCode> findByIds(List<String> codes) {
        return em.createNamedQuery("CourseCode.findByIds", CourseCode.class)
                .setParameter("listOfCodes", codes)
                .getResultList();
    }

    public List<String> findBySearchTerm(String searchTerm){
        return em.createNamedQuery("CourseCode.findBySearchTerm", String.class)
                .setParameter("searchTerm",searchTerm).getResultList();
    }

    private boolean containsBook(CourseCode cc, String isbn) {
        for (Book book : cc.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

}
