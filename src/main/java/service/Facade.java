package service;

import lombok.extern.java.Log;

import javax.persistence.EntityManager;

@Log
public abstract class Facade<T> {

    private Class<T> entityClass;

    protected Facade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        System.out.println("This works");
        log.info("THIS ALSO WORKS");
        EntityManager em = getEntityManager();
        if (em == null) {
            log.info("Why da fuck is this null?");
        }
        getEntityManager().persist(entity);

    }

    public T findAll() {
        return null;
    }


}
