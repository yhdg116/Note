package com.example.hasee.Note;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Add extends Activity implements View.OnClickListener {


    private Button savebtn, cancelbtn;
    private EditText ettext;
    private SQLite jsbdb;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adlist);

        savebtn = (Button) findViewById(R.id.add_save);
        cancelbtn = (Button) findViewById(R.id.add_cancel);
        ettext = (EditText) findViewById(R.id.add_etv);
        savebtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        jsbdb = new SQLite(this);
        dbWriter = jsbdb.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_save:
                addDB();
                finish();
                break;
            case R.id.add_cancel:
                finish();
                break;
        }
    }

    private void addDB() {
        ContentValues cv = new ContentValues();
        cv.put(SQLite.CONTENT, ettext.getText().toString());
        cv.put(SQLite.TIME, getTime());
        dbWriter.insert(SQLite.TABLE_NAME, null, cv);
    }

    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }
}
