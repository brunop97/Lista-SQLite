package br.com.capsistema.shoppinglist.additemslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.capsistema.shoppinglist.R;
import br.com.capsistema.shoppinglist.shoppinglists.MainActivity;

public class AddItemsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items_list);
    }

    public  void botaoSaveItemOnClick (View v){
        // Obter valores dos campos de texto
        EditText nameField = findViewById(R.id.editTextNameItem);
        String name = nameField.getText().toString();
        EditText valueField = findViewById(R.id.editTextPreco);
        String value = valueField.getText().toString();

        //******* Falta add no banco de dados ************/

        Intent telaEditItem = new Intent(this, MainActivity.class);
        startActivity(telaEditItem);
    }
}