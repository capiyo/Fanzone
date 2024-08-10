package ke.co.capiyo.fanzone.Models;

import java.util.Objects;

public class Item {
    private String Name;
    private String Value;

    public Item(String Name, String Value) {
        this.Name = Name;
        this.Value = Value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(Name, item.Name) &&
                Objects.equals(Value, item.Value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Value);
    }

    @Override
    public String toString() {
        return "Item{" +
                "Name='" + Name + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}


