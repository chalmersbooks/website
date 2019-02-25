package service;

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

}
