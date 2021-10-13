package com.example.musica_listentillthehorizon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText user1,pwd1;
    Button login;
    DBHelper DB;
    String u1,p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user1 = (EditText)findViewById(R.id.user1);
        pwd1 = (EditText) findViewById(R.id.pwd1);
        login = (Button) findViewById(R.id.login);
        DB = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u1 = user1.getText().toString();
                p1 = pwd1.getText().toString();

                Boolean checkuserpass = DB.checkusernamepassword(u1,p1);
                if (checkuserpass ==true)
                {
                    Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Musiclist.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Login.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
