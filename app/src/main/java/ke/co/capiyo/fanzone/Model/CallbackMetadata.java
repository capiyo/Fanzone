package ke.co.capiyo.fanzone.Model;

import ke.co.capiyo.fanzone.Models.Item;

import java.util.List;

public class CallbackMetadata {
    private List<ke.co.capiyo.fanzone.Models.Item> Item;

    public CallbackMetadata(List<Item> Item) {
        this.Item = Item;
    }

    public List<Item> getItem() {
        return Item;
    }

    public void setItem(List<Item> Item) {
        this.Item = Item;
    }
}

