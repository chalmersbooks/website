package control;

import entity.DummyBook;
import utils.DummyBookService;


import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Named("dataScroller")
@ViewScoped
public class DataScrollerBean implements Serializable {

    private List<DummyBook> books;
    private DummyBookService service = new DummyBookService();
    private boolean SORTED = false;

    public List<DummyBook> getBooks() {
        books = service.createCars(50);

        if (SORTED) {

            books.sort(new Comparator<DummyBook>() {
                @Override
                public int compare(DummyBook o1, DummyBook o2) {
                    return Integer.compare(o1.price, o2.price);
                }
            });
        }
        return books;
    }

    public void sort() {
        SORTED = true;
    }
}
