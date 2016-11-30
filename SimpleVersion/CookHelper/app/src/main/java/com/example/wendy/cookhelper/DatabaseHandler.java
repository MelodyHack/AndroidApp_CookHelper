package com.example.wendy.cookhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "recipes";
    public static final String RECIPES_ID = "id";
    public static final String RECIPES_NAME = "recipeName";
    public static final String RECIPES_DESCRIPTION = "description";
    public static final String RECIPES_CATEGORY = "category";
    public static final String RECIPES_TYPE = "type";
    public static final String RECIPES_COOKINGTIME = "cookingTime";
    public static final String RECIPES_NUMSERVINGS = "numServings";
    public static final String RECIPES_INGREDIENTS = "ingredients";
    public static final String RECIPES_STEPS = "steps";
    private HashMap hp;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table recipes " +
                        "(id integer primary key, recipeName text,ingredients text,description text, category text, type text, cookingTime text,numServings text, steps text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }

    public boolean insertRecipe (String recipeName, String description, String category, String type, String cookingTime,String numServings, String ingredients, String steps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recipeName", recipeName);
        contentValues.put("ingredients", ingredients);
        contentValues.put("description", description);
        contentValues.put("category", category);
        contentValues.put("type", type);
        contentValues.put("cookingTime", cookingTime);
        contentValues.put("numServings", numServings);
        contentValues.put("steps", steps);
        db.insert("recipes", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from recipes where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateRecipe (Integer id, String recipeName, String description, String category, String type, String cookingTime,String numServings, String ingredients, String steps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recipeName", recipeName);
        contentValues.put("ingredients", ingredients);
        contentValues.put("description", description);
        contentValues.put("category", category);
        contentValues.put("type", type);
        contentValues.put("cookingTime", cookingTime);
        contentValues.put("numServings", numServings);
        contentValues.put("steps", steps);
        db.update("recipes", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteRecipe (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("recipes",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllRecipes() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from recipes", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(RECIPES_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
