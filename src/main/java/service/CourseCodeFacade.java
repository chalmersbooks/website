package service;

import entity.CourseCode;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CourseCodeFacade extends Facade<CourseCodeFacade> implements Serializable {

    @PersistenceContext(unitName = "database")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseCodeFacade() {
        super(CourseCodeFacade.class);
    }

    // TODO: Broken for now... see CourseCode class
    public List<CourseCode> findByIsbn(long isbn) {
        //TypedQuery<CourseCode> query = em.createNamedQuery("CourseCode.findByIsbn", CourseCode.class);
        //return query.getResultList();
        return new ArrayList<>();
    }

}
