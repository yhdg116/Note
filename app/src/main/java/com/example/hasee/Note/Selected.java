package com.example.hasee.Note;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Selected extends Activity implements View.OnClickListener {

    private Button s_delete;
    private Button s_update;
    private Button s_back;
    private EditText s_editv;

    private SQLite jsbdb;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        s_back = (Button) findViewById(R.id.s_back);
        s_delete = (Button) findViewById(R.id.s_delete);
        s_update = (Button) findViewById(R.id.s_update);
        s_editv = (EditText) findViewById(R.id.s_editv);

        jsbdb = new SQLite(this);
        dbWriter = jsbdb.getWritableDatabase();


        s_back.setOnClickListener(this);
        s_delete.setOnClickListener(this);
        s_update.setOnClickListener(this);
        s_editv.setOnClickListener(this);

        s_editv.setText(getIntent().getStringExtra(SQLite.CONTENT));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_delete:
                deleteData();
                finish();
                break;
            case R.id.s_update:
                updateData();
                finish();
                break;

            case R.id.s_back:
                finish();
                break;
        }
    }

    private void deleteData() {
        dbWriter.delete(SQLite.TABLE_NAME, "_id=" + getIntent().getIntExtra(SQLite.ID, 0), null);

    }
    private void updateData(){
        int id;
        ContentValues cv = new ContentValues();
        cv.put(SQLite.ID,getIntent().getIntExtra(SQLite.ID, 0));
        cv.put(SQLite.CONTENT, s_editv.getText().toString());
        cv.put(SQLite.TIME, Add.getTime());
        id=getIntent().getIntExtra(SQLite.ID, 0);
        String w[]={id + ""};
   //     dbWriter.update(SQLite.TABLE_NAME, cv,"_id=?"+ getIntent().getIntExtra(SQLite.ID, 0),null);
        dbWriter.update(SQLite.TABLE_NAME,cv,"_id=?",w);
    }
}