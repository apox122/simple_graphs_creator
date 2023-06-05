package data;
/*
JAKUB FRYDRYCH 06.12.2002
263991
Prosty edytor gragów, LAB4
 */

public class PersonName {

    private static final long serialVersionUID = 1L;
    String name;

    public PersonName(String name) {
        this.name = "-";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
