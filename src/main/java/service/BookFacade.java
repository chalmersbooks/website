package service;

import entity.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class BookFacade extends Facade<Book> {

    @PersistenceContext(unitName = "database")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

    // TODO: Can this look better?
    public Book findById(String isbn) {
        try {
            return em.createNamedQuery("Book.findById", Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
