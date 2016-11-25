package jac.cookhelper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    //Spinners
    Spinner spinner, spinner2;
    ArrayAdapter<CharSequence> adapter, adapter2;

    Button ingredientsAddRow, stepsAddRow;
    TableLayout ingredientsTable, stepsTable;

    ImageView stepsPhotoView;


    //private static final int IMAGE_PICK     = 20;
    //private static final int IMAGE_CAPTURE  = 21;

    //Data Storage
    EditText etRecipeName;
    EditText etRecipeDescription;
    int cookingTime;
    int numServings;
    String[] etIngredients;
    String[] etStepsDescriptions;
    Uri[] uriStepsImages;


    FileOutputStream fos;
    RecipeData fileName;
    TextView dataResults;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //////////////////////////////setTitle(R.string.app_addRecipePage);
        setContentView(R.layout.activity_add_recipe);




        // Ingredients Table Part
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.minutes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();

                // Save to Data
                //////////cookingTime = (int) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ingredientsAddRow = (Button) findViewById(R.id.addIngredientBtn);
        ingredientsTable = (TableLayout) findViewById(R.id.ingredientsTable);
        ingredientsTable.setColumnStretchable(0, true);
        ingredientsTable.setColumnStretchable(1, true);



        // Steps Table Part
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.portions, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();

                // Save to Data
                /////////////numServings = (int) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stepsAddRow = (Button) findViewById(R.id.addStepsBtn);
        stepsTable = (TableLayout) findViewById(R.id.stepsTable);
        stepsTable.setColumnStretchable(0, true);



        setUpVariables();

    }

    private void setUpVariables() {
        Button save = (Button) findViewById(R.id.saveBtn);
        etRecipeName = (EditText) findViewById(R.id.etRecipeName);
        etRecipeDescription = (EditText) findViewById(R.id.etRecipeDescription);

        View ingRow = (View) ingredientsTable.getChildAt(0);
        for (int i=0; i<ingredientsTable.getChildCount(); i++){
            EditText targetIng = (EditText) ingRow.findViewWithTag("ing");
            EditText targetPor = (EditText) ingRow.findViewWithTag("por");

            ////////////////etIngredients[i] = targetPor.getText().toString() + targetIng.getText().toString();

            // Move the pointer to next row
            ingRow = (View) ingredientsTable.getChildAt(i+1);
        }

        View stepsRow = (View) stepsTable.getChildAt(0);
        for (int i=0; i<stepsTable.getChildCount(); i++){
            EditText stepsDescription = (EditText) stepsRow.findViewWithTag("stepsDescription");
            ImageView stepsImage = (ImageView) stepsRow.findViewWithTag("stepsPhotoTag");

            ////////////////////////////etStepsDescriptions[i] = stepsDescription.getText().toString();
            ////////////////////////////////////////stepsImage[i] =


        }


        String recipeName = etRecipeName.getText().toString();
        /////////////////////////////////////String recipeDescription =
        /*fileName = new RecipeData (recipeName, String recipeDescription,
        int cookingTime, int numServings,
        String[] ingredients, String[] stepsDescriptions,
                Uri[] stepsImages)*/

        /*try {
            fos = openFileOutput(fileName.getRecipeName(), Context.MODE_PRIVATE);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void onIngredientsDeleteClicked(View v) {
        // get the row by calling the getParent on button
        View row = (View) v.getParent();

        // container contais all the rows
        ViewGroup mContainer = ((ViewGroup)row.getParent());

        // delete the row
        mContainer.removeView(row);

        // invalidate the view
        mContainer.invalidate();



    }

    public void onStepsDeleteClicked(View v) {
        // get the row by calling the getParent on button
        View row = (View) v.getParent().getParent().getParent();

        // container contais all the rows
        ViewGroup mContainer = ((ViewGroup)row.getParent());

        // index of the row
        int index = mContainer.indexOfChild(row);


        try {
            // get next row
            View nextRow = (View) mContainer.getChildAt(index + 1);

            // delete the row
            mContainer.removeView(row);

            // re-assign the steps number after the removal of the row at the index
            for (int i = index; i < mContainer.getChildCount(); i++) {

                // Find the target TextView object
                TextView target = (TextView) nextRow.findViewWithTag("stepNumberTag");
                // Assign the step number to the added row
                target.setText(Integer.toString(i+1));
                // Move the pointer to next row
                nextRow = (View) mContainer.getChildAt(i+1);
            }
        } catch (NullPointerException e){
            // delete the row
            mContainer.removeView(row);
        }

        // invalidate the view
        mContainer.invalidate();

    }

    public void onClickPhoto(View v){
        // get the row by calling the getParent on button
        View row = (View) v.getParent().getParent();

        // container contais all the rows
        ViewGroup mContainer = ((ViewGroup)row.getParent());

        try {
            // Find the target TextView object
            stepsPhotoView = (ImageView) row.findViewWithTag("stepsPhotoTag");

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), 20);


        } catch (NullPointerException e){
            // invalidate the view
            mContainer.invalidate();

        }
    }

    /**
     * Click listener for taking a new picture
     */
    /*public void onClickTakePhoto(View v) {

        // get the row by calling the getParent on button
        View row = (View) v.getParent().getParent();

        // container contais all the rows
        ViewGroup mContainer = ((ViewGroup)row.getParent());

        try {
            // Find the target TextView object
            stepsPhotoView = (ImageView) row.findViewWithTag("stepsPhotoTag");

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 21);


        } catch (NullPointerException e){
            // invalidate the view
            mContainer.invalidate();

        }

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addIngredientBtn:
                TableLayout ingredientsTblAddLayout = (TableLayout) findViewById(R.id.ingredientsTable);

                LayoutInflater ingInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                TableRow ingredientsInflatedRow = (TableRow) ingInflater.inflate(R.layout.ingredients_table_layout, null);

                ingredientsTblAddLayout.addView(ingredientsInflatedRow);
                break;


            case R.id.addStepsBtn:
                TableLayout stepsTblAddLayout = (TableLayout) findViewById(R.id.stepsTable);

                LayoutInflater stepsInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                TableRow stepsInflatedRow = (TableRow) stepsInflater.inflate(R.layout.steps_table_layout, null);
                //TableRow stepsInflatedRow = (TableRow) View.inflate(AddRecipeActivity.this, R.layout.steps_table_layout, null);

                stepsTblAddLayout.addView(stepsInflatedRow);

                // number of total rows in the table
                int lastRowIndex = stepsTblAddLayout.getChildCount();
                // Find the target TextView object
                TextView target = (TextView) stepsInflatedRow.findViewWithTag("stepNumberTag");
                // Assign the step number to the added row
                target.setText(Integer.toString(lastRowIndex));


                break;


            case R.id.saveBtn:
                String dataRecipeName = etRecipeName.getText().toString();
                try{
                    fos = openFileOutput(fileName.getRecipeName(), Context.MODE_PRIVATE);
                    fos.write(dataRecipeName.getBytes());
                    fos.close();

                } catch (IOException e) {

                    e.printStackTrace();
                }
                break;

             /*case R.id.loadBtn:
                 *//*String collected = null;
                 FileInputStream fis = null;

                 try {
                     fis = openFileInput(fileName.getRecipeName());
                     byte[] dataArray = new byte[fis.available()];

                     // stop when we've read everything
                     while (fis.read(dataArray) != -1) {
                         collected = new String(dataArray);
                     }
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 } finally {
                     try {
                         fis.close();
                         dataResults.setText(collected);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }*//*

                 //new loadAsync().execute(fileName.getRecipeName());
                 break;*/


            case R.id.cancelBtn:
                finish();
                break;



            default:
                break;
        }
    }






    public void onActivityResult (int reqCode, int resCode, Intent data){
        if (resCode == RESULT_OK) {
            if(reqCode == 20)
                this.imageFromGallery(resCode, data);

            if(reqCode == 21)
                this.imageFromCamera(resCode, data);

        }
    }



    private void imageFromGallery(int resultCode, Intent data) {
        Uri selectedImage = data.getData();
        String [] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        try {
            //this.stepsPhotoTemp.setImageBitmap(BitmapFactory.decodeFile(filePath));
            Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            //Bitmap bitmap = BitmapFactory.decodeFile(selectedImage.getPath());

            stepsPhotoView.setImageBitmap(bitmap);
            stepsPhotoView.setScaleType(CENTER_CROP);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void imageFromCamera(int resultCode, Intent data) {
        stepsPhotoView.setImageBitmap((Bitmap) data.getExtras().get("data"));
    }

    /*// load data at background
    public class loadAsync extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            String collected = null;
            FileInputStream fis = null;

            try {
                fis = openFileInput(fileName.getRecipeName());
                byte[] dataArray = new byte[fis.available()];

                // stop when we've read everything
                while (fis.read(dataArray) != -1) {
                    collected = new String(dataArray);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    return collected;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        protected void onPostExecute(String result) {
            dataResults.setText(result);
        }
    }*/




}
