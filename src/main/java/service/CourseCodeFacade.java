package service;

import entity.CourseCode;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

}
