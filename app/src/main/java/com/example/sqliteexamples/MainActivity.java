package com.example.sqliteexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            String path = getFilesDir().getAbsolutePath();
            db = SQLiteDatabase.openDatabase(path + "/mydb",
                    null, SQLiteDatabase.CREATE_IF_NECESSARY);
        } catch (Exception ex) {
            ex.printStackTrace();
            db = null;
        }

        // createTable();

        findViewById(R.id.button_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = findViewById(R.id.edit_name);
                EditText editPhone = findViewById(R.id.edit_phone);

                db.beginTransaction();
                try {
//                    String sName = editName.getText().toString();
//                    String sPhone = editPhone.getText().toString();
//                    db.execSQL("insert into tblAMIGO(name, phone) " +
//                            "values('" + sName + "','" + sPhone +"')");


                    ContentValues cv = new ContentValues();

                    cv.put("name", "ABC");
                    cv.put("phone", "555-1010");
                    long ret = db.insert("tblAMIGO", null, cv);
                    Log.v("TAG", "ret = " + ret);

                    cv.put("name", "DEF");
                    cv.put("phone", "555-2020");
                    ret = db.insert("tblAMIGO", null, cv);
                    Log.v("TAG", "ret = " + ret);

                    cv.clear();

                    ret = db.insert("tblAMIGO", null, cv);
                    Log.v("TAG", "ret = " + ret);

                    ret = db.insert("tblAMIGO", "name", cv);
                    Log.v("TAG", "ret = " + ret);

                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        });

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.beginTransaction();
                try {
//                    db.execSQL("update tblAMIGO set name='ABC' where recID < 4");

                    ContentValues cv = new ContentValues();
                    cv.put("name", "Maria");
                    int ret = db.update("tblAMIGO", cv, "recID > 2 and recID < 7", null);
                    Log.v("TAG", "ret = " + ret);

                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.beginTransaction();
                try {
//                    db.execSQL("delete from tblAMIGO where recID < 4");

                    int ret = db.delete("tblAMIGO", "recID < 7", null);
                    Log.v("TAG", "ret = " + ret);

                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        });

        findViewById(R.id.button_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cursor cs = db.rawQuery("select * from tblAMIGO", null);
                String[] columns = new String[]{"recID", "name", "phone"};
                Cursor cs = db.query(
                        "tblAMIGO",
                        columns,
                        "recID > 5", null,
                        null, null,
                        "recID DESC"
                );

                Log.v("TAG", "Num of records: " + cs.getCount());
                cs.moveToPosition(-1);
                while (cs.moveToNext()) {
                    int recID = cs.getInt(0);
                    String name = cs.getString(1);
                    String phone = cs.getString(2);

                    Log.v("TAG", recID + " - " + name + " - " + phone);
                }
                cs.close();
            }
        });
    }

    private void createTable() {
        db.beginTransaction();
        try {
            db.execSQL("create table tblAMIGO(" +
                    "recID integer PRIMARY KEY autoincrement," +
                    "name text," +
                    "phone text)");
            db.execSQL("insert into tblAMIGO(name, phone) values('AAA','555-1111')");
            db.execSQL("insert into tblAMIGO(name, phone) values('BBB','555-2222')");
            db.execSQL("insert into tblAMIGO(name, phone) values('CCC','555-3333')");
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    protected void onDestroy() {
        if (db != null)
            db.close();
        super.onDestroy();
    }
}