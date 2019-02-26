package utils;

import entity.DummyBook;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DummyBookService {

    public List<DummyBook> createCars(int size) {
        List<DummyBook> list = new ArrayList<DummyBook>();
        for (int i = 0; i < size; i++) {
            list.add(new DummyBook(getRandomId(), getRandomName(), getRandomPrice()));
        }

        return list;
    }

    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


    private String getRandomName() {
        return "Book " + (int) (Math.random() * 100);
    }

    private int getRandomPrice() {
        return (int) (Math.random() * 100);
    }
}
