package br.com.capsistema.shoppinglist.data.partialentities;

import androidx.room.ColumnInfo;

import java.util.List;
import java.util.Random;

import br.com.capsistema.shoppinglist.data.entities.Item;

public class ShoppingListInsert {
    @ColumnInfo(name = "shopping_list_id")
    public String id;
    public String name;
    public String category = generateCategory();

    public ShoppingListInsert(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String generateCategory() {
        String[] categories = new String[]{"Fitness", "Eventos", "Rápidas"};
        return categories[new Random().nextInt(3)];
    }
}
