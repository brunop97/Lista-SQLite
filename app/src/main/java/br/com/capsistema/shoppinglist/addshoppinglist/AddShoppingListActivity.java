package br.com.capsistema.shoppinglist.addshoppinglist;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.com.capsistema.shoppinglist.R;
import br.com.capsistema.shoppinglist.data.entities.Item;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListInsert;
import br.com.capsistema.shoppinglist.shoppinglists.ShoppingListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddShoppingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewModelProvider.AndroidViewModelFactory factory
                = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ShoppingListViewModel vm = new ViewModelProvider(this, (ViewModelProvider.Factory) factory)
                .get(ShoppingListViewModel.class);

        setupCreateButton(vm);
    }

    private void setupCreateButton(ShoppingListViewModel vm) {
        findViewById(R.id.create_button).setOnClickListener(
                view -> {
                    // Obter os valores dos campos de texto e radio button
                    EditText nameField = findViewById(R.id.name_field);
                    String name = nameField.getText().toString();
                    RadioButton arroz = findViewById(R.id.btnArroz);
                    String arroz1 = "";
                    RadioButton leite = findViewById(R.id.btnLeite);
                    String leite1 = "";
                    RadioButton carne = findViewById(R.id.btnCarne);
                    String carne1 = "";
                    RadioButton feijao = findViewById(R.id.btnFeijao);
                    String feijao1 = "";
                    RadioButton refri = findViewById(R.id.btnRefri);
                    String refri1 = "";

                    if (arroz.isChecked()) {
                        arroz1 = "Arroz 1 Kg (R$ 2,69)";
                    }
                    if (leite.isChecked()) {
                        leite1 = "Leite longa vida (R$ 2,70)";
                    }
                    if (carne.isChecked()) {
                        carne1 = "Carne Friboi (R$ 16,70)";
                    }
                    if (feijao.isChecked()) {
                        feijao1 = "Feijão carioquinha 1 Kg (R$ 3,38)";
                    }
                    if (refri.isChecked()) {
                        refri1 = "Refrigerante coca-cola 2 litros (R$3,00)";
                    }

                    // Desconsiderar trigger se houver 0 caracteres
                    if (name.isEmpty()) {
                        return;
                    }

                    // Criar entidade e guardar no banco de dados
                    String id = UUID.randomUUID().toString();
                    ShoppingListInsert shoppingList = new ShoppingListInsert(id, name);
                    vm.insert(shoppingList, arroz1, leite1, carne1, feijao1, refri1);

                    finish();
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}