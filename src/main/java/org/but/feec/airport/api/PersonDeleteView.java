package org.but.feec.airport.api;

public class PersonDeleteView {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PersonDeleteView{" +
                "id_customer='" + id + '\'' +
                '}';
    }
}
