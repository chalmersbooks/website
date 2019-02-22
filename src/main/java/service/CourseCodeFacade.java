package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CourseCodeFacade extends Facade<CourseCodeFacade> {

    @PersistenceContext(unitName = "database")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseCodeFacade() {
        super(CourseCodeFacade.class);
    }

}
