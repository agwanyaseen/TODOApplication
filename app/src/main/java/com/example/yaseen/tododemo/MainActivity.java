package com.example.yaseen.tododemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText listItem;
    Button submit;
    ListView ls;
    ArrayList todoList;
    ArrayAdapter<String> adapter;
    static String data;
    static int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItem = findViewById(R.id.listItem);
        submit = findViewById(R.id.submit);
        ls = findViewById(R.id.viewList);
        todoList = new ArrayList();

        display();
        registerForContextMenu(ls);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(getApplicationContext());
                String name = listItem.getText().toString().trim();

                if(!(listItem.getText().toString()).isEmpty()){

                    if(db.insertList(name)) {
                        display();
                        listItem.setText("");
                    }
                    else {
                        Toast t = Toast.makeText(getApplicationContext(),"ERROR INSERTING DATABASE",Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER,0,0);
                        t.show();
                    }
                }
                else {

                    Toast t = Toast.makeText(getApplicationContext(),"List cannot Be Empty",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,0);
                    t.show();
                }
            }
        });


    }

    //DISPLAY AND VIEW RECORDS FROM DATABASE TO LISTVIEW
    public  void display() {
        DBHelper db = new DBHelper(getApplicationContext());
        Cursor c;
        ArrayList<String> l= new ArrayList<>();

        c = db.viewRecords();

        while(c.moveToNext()){
            l.add(c.getString(1));

            System.out.println("det 1"+c.getInt(0));
            System.out.println("det 2"+c.getString(1));
        }

        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,l);
        ls.setAdapter(adapter);
    }

    //MENU OPERATION CODE


    private void deleteListInMainActivity() {
        DBHelper db = new DBHelper(getApplicationContext());
        String locData = (String)ls.getItemAtPosition(MainActivity.pos);
        int delId = db.getID(locData);

        if(db.deleteList(delId)) {
            display();
        }
        else{
            Toast t = Toast.makeText(getApplicationContext(),"Cennot Be Deleted",Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
        }
    }

    public void sendToUpdateActivity(int id,String datatoSend){
        Intent i = new Intent(MainActivity.this,UpdateActivity.class);
        System.out.println("main = "+id);
        System.out.println("main fa"+datatoSend);
        i.putExtra("idGlobal",id);
        i.putExtra("dataGolbal",datatoSend);
        startActivity(i);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);

        menu.setHeaderTitle("Select Operation");

        menu.add(0,v.getId(),0,"Delete");
        menu.add(0,v.getId(),0,"Edit");
    }

    @Override
    public boolean onContextItemSelected(MenuItem m) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)m.getMenuInfo();
        pos = info.position;
        String locdata = (String)ls.getItemAtPosition(pos);
        if(m.getTitle()=="Delete") {
            deleteListInMainActivity();
        }
        else {
            DBHelper dbhep = new DBHelper(getApplicationContext());
            int posititon = dbhep.getID(locdata);
            sendToUpdateActivity(posititon,locdata);
        }
        return true;
    }
}
