package jac.cookhelper;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayRecipeActivity extends AppCompatActivity {
    private String[] stepsDescriptions;
    List<RecipeData> RecipeData = new ArrayList<RecipeData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        //TabHost
        TabHost tabHost = (TabHost) findViewById(R.id.tabHostRecipeContent);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("description");
        tabSpec.setContent(R.id.tabDescription);
        tabSpec.setIndicator("Description");
        tabHost.addTab(tabSpec);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("steps");
        tabSpec2.setContent(R.id.tabSteps);
        tabSpec2.setIndicator("Steps");
        tabHost.addTab(tabSpec2);


        //RecipeData currentRecipeData = RecipeData.get(position);
        //String[] stepsDescriptions = currentRecipeData.getStepsDescriptions();
        String[] stepsDescriptions = {"Put this", "Add that", "Cook this", "Thats it"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisplayRecipeActivity.this, android.R.layout.simple_list_item_1, stepsDescriptions);
        ListView lvSteps = (ListView) findViewById(R.id.lvSteps);
        lvSteps.setAdapter(adapter);


    }

    private class RecipeDataListAdapter extends ArrayAdapter<RecipeData> {
        public RecipeDataListAdapter(){
            super (DisplayRecipeActivity.this, R.layout.steps_display_layout, RecipeData);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.steps_display_layout, parent, false);

            RecipeData currentRecipeData = RecipeData.get(position);
            String[] stepsDescriptions = currentRecipeData.getStepsDescriptions();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisplayRecipeActivity.this, android.R.layout.simple_list_item_1, stepsDescriptions);
            ListView lvSteps = (ListView) findViewById(R.id.lvSteps);
            lvSteps.setAdapter(adapter);


            /*for(int i=0; i<stepsDescriptions.length; i++){
                TextView currentStep = (TextView) view.findViewById(R.id.stepEditTxtTempDisplay);
                currentStep.setText(stepsDescriptions[i]);
            }*/
            return view;

        }
    }

    public void onListItenClick(ListView l , View v, int position, long id){
        RecipeData currentRecipeData = RecipeData.get(position);
        String[] stepsDescriptions = currentRecipeData.getStepsDescriptions();

        Toast.makeText(getBaseContext(), "You click "+stepsDescriptions[position], Toast.LENGTH_LONG).show();


    }
}
