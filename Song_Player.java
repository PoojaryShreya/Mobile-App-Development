package com.example.musica_listentillthehorizon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button signup,login1;
    EditText mail,pwd,user;
    String m,u,p;
   DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = (EditText)findViewById(R.id.mail);
        pwd = (EditText)findViewById(R.id.pwd);
        user = (EditText)findViewById(R.id.user);
        signup = (Button)findViewById(R.id.signup);
        login1 = (Button)findViewById(R.id.login1);
        DB = new DBHelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m =mail.getText().toString();
                u =user.getText().toString();
                p =pwd.getText().toString();

                Boolean checkuser = DB.checkusername(u);
                if (checkuser==false)
                {
                    Boolean insert = DB.insertData(u,p);
                    if (insert == true)
                    {
                        Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"User Already Exists. Please Login",Toast.LENGTH_SHORT).show();
                }



            }
        });
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1= new Intent(getApplicationContext(),Login.class);
                startActivity(i1);
            }
        });
    }
}
