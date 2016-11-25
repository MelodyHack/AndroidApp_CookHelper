package jac.cookhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    //Search bar
    ArrayAdapter<String> adapter;
    String[] recipes;

    EditText editText;
    Spinner spinner;
    Spinner spinner2;
    ArrayAdapter<CharSequence> adapter2, adapter3;

    // Tmp variables
    ArrayList<String> listRecipes;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ListView listView=(ListView)findViewById(R.id.listView);
        ArrayList<String> listRecipes = new ArrayList<>();
        listRecipes.addAll(Arrays.asList(getResources().getStringArray(R.array.category)));
        adapter = new ArrayAdapter<String>(SearchActivity.this,
                android.R.layout.simple_list_item_1,listRecipes);
        listView.setAdapter(adapter);

        editText=(EditText)findViewById(R.id.txtsearch);
        initList();

        spinner = (Spinner)findViewById(R.id.spinner);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id ){
                Toast.makeText(getBaseContext(),parent.getItemIdAtPosition(position )+
                        " selected", Toast.LENGTH_LONG) .show();

            }

            public void onNothingSelected(AdapterView<?> parent){

            }
        });


        editText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    //reset listview
                    initList();
                }
                else{
                    //perform search
                    searchRecipe(charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void searchRecipe(String textToSearch){
        for(String recipe:recipes){
            if(recipe.contains(textToSearch)){
                listRecipes.remove(recipe);
            }
        }
        adapter.notifyDataSetChanged();

    }

    public void initList(){
        recipes = new String[]{"Tomato","Avocado","Oignons","Pepper"};
        listRecipes = new ArrayList<>(Arrays.asList(recipes));
        adapter = new ArrayAdapter<String>(this, R.layout.list_recipe, R.id.txtrecipe,listRecipes );
        //Tmp comment
        //listView.setAdapter(adapter);
    }
}


