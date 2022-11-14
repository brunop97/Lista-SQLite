package br.com.capsistema.shoppinglist.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.capsistema.shoppinglist.Utils;
import br.com.capsistema.shoppinglist.data.dados.ItemDao;
import br.com.capsistema.shoppinglist.data.dados.ShoppingListDao;
import br.com.capsistema.shoppinglist.data.dados.ShoppingListItemDao;
import br.com.capsistema.shoppinglist.data.entities.Colaborador;
import br.com.capsistema.shoppinglist.data.entities.Info;
import br.com.capsistema.shoppinglist.data.entities.Item;
import br.com.capsistema.shoppinglist.data.entities.ShoppingList;
import br.com.capsistema.shoppinglist.data.entities.ShoppingListItem;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListInsert;
import br.com.capsistema.shoppinglist.data.views.ShoppingListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        ShoppingList.class,
        Info.class,
        Colaborador.class,
        Item.class,
        ShoppingListItem.class},
        views = ShoppingListView.class,
        version = 7, exportSchema = false)
public abstract class ShoppingListDatabase extends RoomDatabase {

    // Exposição DAO
    public abstract ShoppingListDao shoppingListDao();

    public abstract ItemDao itemDao();

    public abstract ShoppingListItemDao shoppingListItemDao();

    private static final String DATABASE_NAME = "shopping-list-db";

    private static ShoppingListDatabase INSTANCE;

    private static final int THREADS = 4;

    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(THREADS);

    public static ShoppingListDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoppingListDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), ShoppingListDatabase.class,
                            DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
