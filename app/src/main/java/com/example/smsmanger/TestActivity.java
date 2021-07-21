package com.example.smsmanger;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.scottyab.aescrypt.AESCrypt;
import java.security.GeneralSecurityException;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String password = "password";
        String encryptedMsg = "2B22cS3UC5s35WBihLBo8w==";

        TextView textView=findViewById(R.id.textView3);
        Button button=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String messageAfterDecrypt = AESCrypt.decrypt(password, encryptedMsg);
                    textView.setText(messageAfterDecrypt);
                    Toast.makeText(getApplicationContext(), messageAfterDecrypt, Toast.LENGTH_LONG).show();
                }catch (GeneralSecurityException e){
                    //handle error - could be due to incorrect password or tampered encryptedMsg
                }


            }
        });


    }
}