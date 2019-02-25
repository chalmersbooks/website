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

    // TODO: Broken for now... see CourseCode class
    public List<CourseCode> findByIsbn(long isbn) {
        //TypedQuery<CourseCode> query = em.createNamedQuery("CourseCode.findByIsbn", CourseCode.class);
        //return query.getResultList();
        return new ArrayList<>();
    }

    // TODO: Can this look better?
    public CourseCode findById(String courseCode) {
        try {
            return em.createNamedQuery("CourseCode.findById", CourseCode.class)
                    .setParameter("courseCode", courseCode)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

    }

    public List<CourseCode> findByBook(String isbn) {

        // TODO: Make this from a query instead? Future work

        List<CourseCode> searched = new ArrayList<>();
        List<CourseCode> courseCodes = findAll();
        for (CourseCode cc : courseCodes) {
            if (containsBook(cc, isbn)) {
                searched.add(cc);
            }
        }

        return searched;

        /*return em.createNamedQuery("CourseCode.findByBook", CourseCode.class)
                .setParameter("isbn", isbn)
                .getResultList();*/

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
