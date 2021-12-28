package org.but.feec.airport.api;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SQLInjectionView {
    private StringProperty column1 = new SimpleStringProperty();
    private StringProperty column2 = new SimpleStringProperty();
    private StringProperty column3 = new SimpleStringProperty();

    public void setColumn1(String col1) {
        this.column1.set(col1);
    }

    public String getColumn1() {
        return column1.get();
    }


    public void setColumn2(String col2) {
        this.column2.set(col2);
    }

    public String getColumn2() {
        return column2.get();
    }

    public void setColumn3(String col3) {
        this.column3.set(col3);
    }

    public String getColumn3() {
        return column3.get();
    }
}
