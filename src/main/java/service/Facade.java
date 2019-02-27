package service;

import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Log
public abstract class Facade<T> {

    private Class<T> entityClass;

    protected Facade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        EntityManager em = getEntityManager();
        if (em == null) {
            log.info("EntityManager is null inside Facade");
        }
        log.info("Adding {" + entity.toString() + "} to database!");
        getEntityManager().persist(entity);
        log.info("Data added to database successful!");
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

}
