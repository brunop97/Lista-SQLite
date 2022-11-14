package br.com.capsistema.shoppinglist.shoppinglists;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import br.com.capsistema.shoppinglist.Utils;
import br.com.capsistema.shoppinglist.data.ShoppingListRepository;
import br.com.capsistema.shoppinglist.data.entities.Colaborador;
import br.com.capsistema.shoppinglist.data.entities.Info;
import br.com.capsistema.shoppinglist.data.entities.Item;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListId;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListInsert;
import br.com.capsistema.shoppinglist.data.relationentities.ShoppingListWithCollaborators;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShoppingListViewModel extends AndroidViewModel {

    // Repositorio de listas de compras
    private final ShoppingListRepository mRepository;

    // Filtros observados
    private final MutableLiveData<List<String>> mCategories
            = new MutableLiveData<>(new ArrayList<>());

    // Listas de compras observadas
    private final LiveData<List<ShoppingListWithCollaborators>> mShoppingLists;

    // Filtros
    private final List<String> mFilters = new ArrayList<>();

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ShoppingListRepository(application);

        // Obter listas de compras por categorias
        mShoppingLists = Transformations.switchMap(
                mCategories,
                categories -> {
                    if (categories.isEmpty()) {
                        return mRepository.getShoppingLists();
                    } else {
                        return mRepository.getShoppingListsWithCategories(categories);
                    }
                }
        );
    }

    public void insert(ShoppingListInsert shoppingList, String arroz1, String leite1, String carne1, String feijao1, String refri1) {
        String infoCreatedDate = Utils.getCurrentDate();
        String colaboratorId = UUID.randomUUID().toString();

        // Preparar info
        Info info = new Info(shoppingList.id,
                infoCreatedDate, infoCreatedDate);

        // Preparar colaboradores
        Colaborador colaborador = new Colaborador(colaboratorId,
                "C-" + colaboratorId, shoppingList.id);
        List<Colaborador> collaborators = new ArrayList<>();
        collaborators.add(colaborador);

        // Preparar items
        List<Item> items = new ArrayList<>();
        if(!arroz1.isEmpty()) {
            Item item = new Item(UUID.randomUUID().toString(), arroz1);
            items.add(item);
        }
        if(!leite1.isEmpty()) {
            Item item = new Item(UUID.randomUUID().toString(), leite1);
            items.add(item);
        }
        if(!carne1.isEmpty()) {
            Item item = new Item(UUID.randomUUID().toString(), carne1);
            items.add(item);
        }
        if(!feijao1.isEmpty()) {
            Item item = new Item(UUID.randomUUID().toString(), feijao1);
            items.add(item);
        }
        if(!refri1.isEmpty()) {
            Item item = new Item(UUID.randomUUID().toString(), refri1);
            items.add(item);
        }

        // Inserir no repositorio
        mRepository.insert(shoppingList, info, collaborators, items);
    }

    public void addFilter(String category) {
        mFilters.add(category);
        mCategories.setValue(mFilters);
    }

    public void removeFilter(String category) {
        mFilters.remove(category);
        mCategories.setValue(mFilters);
    }

    public LiveData<List<ShoppingListWithCollaborators>> getShoppingLists() {
        return mShoppingLists;
    }

    public void markFavorite(String shoppingListId) {
        mRepository.markFavorite(shoppingListId);
    }

    public void deleteShoppingList(ShoppingListWithCollaborators shoppingList) {
        ShoppingListId id = new ShoppingListId(shoppingList.shoppingList.id);
        mRepository.deleteShoppingList(id);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
