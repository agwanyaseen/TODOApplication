package com.example.yaseen.tododemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UpdateActivity extends AppCompatActivity {

    private static int id;
    private static String data;
    private Button submit,cancel;
    private EditText todoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);
        todoList = findViewById(R.id.editText);

        viewtoUpdateText();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todoList.setText("");
            }
        });

    }

    public void viewtoUpdateText(){
        Intent i = new Intent();
     //   id = Integer.parseInt(i.getStringExtra("idGlobal"));
        id = getIntent().getIntExtra("idGlobal",0);
        //id = i.getIntExtra("idGlobal");
        //id = (int) getIntent().getIntExtra("idGlobal");
        //id = getIntent("idGlobal");

        data = getIntent().getStringExtra("dataGolbal");
        System.out.print("gharatk = "+id+ "  "+data);
        todoList.setText(data);
    }

    public void update(View view) {
        System.out.println("Entry Found");
        DBHelper db = new DBHelper(getApplicationContext());
        String newdata = todoList.getText().toString();

        System.out.println("main + "+id);
        System.out.println("Main2 = "+newdata);
        if(!newdata.isEmpty()) {
            if (db.updateList(id, newdata)) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                Toast t = Toast.makeText(getApplicationContext(), "cannot Update", Toast.LENGTH_SHORT);
                t.show();
            }
        }
        else
        {
            Toast t = Toast.makeText(getApplicationContext(), "cannot be empty", Toast.LENGTH_SHORT);
            t.show();
        }
        // m.display();

    }
}
