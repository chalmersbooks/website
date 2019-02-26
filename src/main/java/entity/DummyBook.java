package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"id"})
public class DummyBook implements Serializable {

    public String id;
    public String name;
    public int price;

    public DummyBook() {}

    public DummyBook(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
