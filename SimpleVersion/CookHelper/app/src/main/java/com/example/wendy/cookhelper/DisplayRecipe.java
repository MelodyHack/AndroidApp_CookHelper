package com.example.wendy.cookhelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayRecipe extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DatabaseHandler mydb ;

    TextView tvRecipeName ;
    TextView tvDescription;
    TextView tvCategory;
    TextView tvType;
    TextView tvCookingTime;
    TextView tvNumServings;
    TextView tvIngredients;
    TextView tvSteps;

    //Bundle extras;
    //int Value;

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
        tvRecipeName = (TextView) findViewById(R.id.etRecipeName);
        tvDescription = (TextView) findViewById(R.id.etDescription);
        tvCategory = (TextView) findViewById(R.id.etCategory);
        tvType = (TextView) findViewById(R.id.etType);
        tvCookingTime = (TextView) findViewById(R.id.etCookingTime);
        tvNumServings = (TextView) findViewById(R.id.etNumServings);
        tvIngredients = (TextView) findViewById(R.id.etIngredients);
        tvSteps = (TextView) findViewById(R.id.etSteps);

        mydb = new DatabaseHandler(this);

        Bundle extras = getIntent().getExtras();
        //extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            //Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add recipe part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String recipeName = rs.getString(rs.getColumnIndex(DatabaseHandler.RECIPES_NAME));
                String description = rs.getString(rs.getColumnIndex(DatabaseHandler.RECIPES_DESCRIPTION));
                String category = rs.getString(rs.getColumnIndex(DatabaseHandler.RECIPES_CATEGORY));
                String type = rs.getString(rs.getColumnIndex(DatabaseHandler.RECIPES_TYPE));
                String cookingTime = rs.getString(rs.getColumnIndex(DatabaseHandler.RECIPES_COOKINGTIME));
                String numServings = rs.getString(rs.getColumnIndex(DatabaseHandler.RECIPES_NUMSERVINGS));
                String ingredients = rs.getString(rs.getColumnIndex(DatabaseHandler.RECIPES_INGREDIENTS));
                String steps = rs.getString(rs.getColumnIndex(DatabaseHandler.RECIPES_STEPS));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.btnSaveRecipe);
                b.setVisibility(View.INVISIBLE);

                tvRecipeName.setText((CharSequence)recipeName);
                tvRecipeName.setFocusable(false);
                tvRecipeName.setClickable(false);

                tvDescription.setText((CharSequence)description);
                tvDescription.setFocusable(false);
                tvDescription.setClickable(false);

                tvCategory.setText((CharSequence)category);
                tvCategory.setFocusable(false);
                tvCategory.setClickable(false);

                tvType.setText((CharSequence)type);
                tvType.setFocusable(false);
                tvType.setClickable(false);

                tvCookingTime.setText((CharSequence)cookingTime);
                tvCookingTime.setFocusable(false);
                tvCookingTime.setClickable(false);

                tvNumServings.setText((CharSequence)numServings);
                tvNumServings.setFocusable(false);
                tvNumServings.setClickable(false);

                tvIngredients.setText((CharSequence)ingredients);
                tvIngredients.setFocusable(false);
                tvIngredients.setClickable(false);

                tvSteps.setText((CharSequence)steps);
                tvSteps.setFocusable(false);
                tvSteps.setClickable(false);
            }
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_menu, menu);
            } else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Recipe:
                Button b = (Button)findViewById(R.id.btnSaveRecipe);
                b.setVisibility(View.VISIBLE);
                tvRecipeName.setEnabled(true);
                tvRecipeName.setFocusableInTouchMode(true);
                tvRecipeName.setClickable(true);

                tvDescription.setEnabled(true);
                tvDescription.setFocusableInTouchMode(true);
                tvDescription.setClickable(true);

                tvCategory.setEnabled(true);
                tvCategory.setFocusableInTouchMode(true);
                tvCategory.setClickable(true);

                tvType.setEnabled(true);
                tvType.setFocusableInTouchMode(true);
                tvType.setClickable(true);

                tvCookingTime.setEnabled(true);
                tvCookingTime.setFocusableInTouchMode(true);
                tvCookingTime.setClickable(true);

                tvNumServings.setEnabled(true);
                tvNumServings.setFocusableInTouchMode(true);
                tvNumServings.setClickable(true);

                tvIngredients.setEnabled(true);
                tvIngredients.setFocusableInTouchMode(true);
                tvIngredients.setClickable(true);

                tvSteps.setEnabled(true);
                tvSteps.setFocusableInTouchMode(true);
                tvSteps.setClickable(true);

                return true;


            case R.id.Delete_Recipe:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteRecipe)
                        .setPositiveButton(R.string.deleteConfirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteRecipe(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Delete recipe");
                d.show();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onSave(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateRecipe(id_To_Update,tvRecipeName.getText().toString(),
                        tvDescription.getText().toString(),
                        tvCategory.getText().toString(),
                        tvType.getText().toString(),
                        tvCookingTime.getText().toString(),
                        tvNumServings.getText().toString(),
                        tvIngredients.getText().toString(),
                        tvSteps.getText().toString())){
                    Toast.makeText(getApplicationContext(), "The recipe has been updated.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "The recipe has not been updated.", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertRecipe(tvRecipeName.getText().toString(),
                        tvDescription.getText().toString(),
                        tvCategory.getText().toString(),
                        tvType.getText().toString(),
                        tvCookingTime.getText().toString(),
                        tvNumServings.getText().toString(),
                        tvIngredients.getText().toString(),
                        tvSteps.getText().toString())){
                    Toast.makeText(getApplicationContext(), "The recipe has been added.",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "The recipe has not been added.",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
