package com.example.hasee.Note;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button addbtn;
    private Button searchbtn;
    private EditText text;
    private ListView listview;
//    private Intent intent;
    private SQLiteDatabase dbReader;
    private Cursor cursor;
    private SQLite jsbdb;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        addbtn = (Button) findViewById(R.id.main_add);
        searchbtn=(Button) findViewById(R.id.main_search);
        text=(EditText) findViewById(R.id.main_text);
        listview = (ListView) findViewById(R.id.main_list);

        addbtn.setOnClickListener(this);
        searchbtn.setOnClickListener(this);
        text.setOnClickListener(this);

        jsbdb = new SQLite(this);
        dbReader = jsbdb.getReadableDatabase();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent intent = new Intent(MainActivity.this, Selected.class);
                intent.putExtra(SQLite.ID, cursor.getInt(cursor.getColumnIndex(SQLite.ID)));
                intent.putExtra(SQLite.CONTENT, cursor.getString(cursor.getColumnIndex(SQLite.CONTENT)));
                intent.putExtra(SQLite.TIME, cursor.getString(cursor.getColumnIndex(SQLite.TIME)));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_add:
                add_btn();
                break;
            case R.id.main_search:
                search_btn();
                break;
        }
    }

    private void search_btn() {
        // TODO Auto-generated method stub
        searchDb(text.getText().toString().trim());
     //   finish();
    }

    private void add_btn() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MainActivity.this, Add.class);
        startActivity(intent);
      //  finish();
    }

    private void selectAllDb() {
        cursor = dbReader.query(SQLite.TABLE_NAME, null, null, null, null, null, null);
        adapter = new Adapter(this, cursor);
        listview.setAdapter(adapter);
    }
    private void searchDb(String word){
     //   String sql="Selected * from notes where content like '" + "%"+ word + "%' order by time desc";
        String sql="select * from notes where content like '" + "%"+ word + "%' ";
        cursor = dbReader.rawQuery(sql,null);
        adapter = new Adapter(this, cursor);
        listview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectAllDb();
    }
}
