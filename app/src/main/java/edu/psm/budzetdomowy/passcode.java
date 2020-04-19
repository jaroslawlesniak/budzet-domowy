package edu.psm.budzetdomowy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class passcode extends AppCompatActivity implements View.OnClickListener {

    StringBuilder sb = new StringBuilder();
    final private String password = "1234";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);


        findViewById(R.id.pin1).setOnClickListener(this);
        findViewById(R.id.pin2).setOnClickListener(this);
        findViewById(R.id.pin3).setOnClickListener(this);
        findViewById(R.id.pin4).setOnClickListener(this);
        findViewById(R.id.pin5).setOnClickListener(this);
        findViewById(R.id.pin6).setOnClickListener(this);
        findViewById(R.id.pin7).setOnClickListener(this);
        findViewById(R.id.pin8).setOnClickListener(this);
        findViewById(R.id.pin9).setOnClickListener(this);
        findViewById(R.id.pinBackspace).setOnClickListener(this);
        findViewById(R.id.pin0).setOnClickListener(this);
        findViewById(R.id.pinCheck).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final TextView welcomeText = findViewById(R.id.welcomeText);
        final TextView passwordText = findViewById(R.id.passwordText);

        Intent intent;

        switch (v.getId()){

            case R.id.pin1:
                sb.append("1");
                passwordText.setText(sb);
                break;
            case R.id.pin2:
                sb.append("2");
                passwordText.setText(sb);
                break;
            case R.id.pin3:
                sb.append("3");
                passwordText.setText(sb);
                break;
            case R.id.pin4:
                sb.append("4");
                passwordText.setText(sb);
                break;
            case R.id.pin5:
                sb.append("5");
                passwordText.setText(sb);
                break;
            case R.id.pin6:
                sb.append("6");
                passwordText.setText(sb);
                break;
            case R.id.pin7:
                sb.append("7");
                passwordText.setText(sb);
                break;
            case R.id.pin8:
                sb.append("8");
                passwordText.setText(sb);
                break;
            case R.id.pin9:
                sb.append("9");
                passwordText.setText(sb);
                break;
            case R.id.pinBackspace:
                sb.deleteCharAt(sb.length()-1);
                passwordText.setText(sb);
                break;
            case R.id.pin0:
                sb.append("0");
                passwordText.setText(sb);
                break;
            case R.id.pinCheck:
                if(password.contains(sb)){
                    welcomeText.setText("hasło poprawne!");
                    intent = new Intent(this, homepage.class);
                    startActivity(intent);
                    break;
                }
                else
                    welcomeText.setText("hasło niepoprawne");
                break;
        }
    }
}