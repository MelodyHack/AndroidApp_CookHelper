package jac.cookhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyRecipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        ListView listViewMyRecipes = (ListView) findViewById(R.id.listViewMyRecipes);
        // LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String[] allRecipes1 = {"Tabouleh", "bhhh","hghj"};
        ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(MyRecipes.this, android.R.layout.simple_list_item_1, allRecipes1);
        listViewMyRecipes.setAdapter(adapter1);
    }
}

