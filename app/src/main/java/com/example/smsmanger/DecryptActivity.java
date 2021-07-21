package com.example.smsmanger;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.scottyab.aescrypt.AESCrypt;
import java.security.GeneralSecurityException;

public class DecryptActivity extends AppCompatActivity {

    String password = "HG;_3UGM}34U}H(";
    EditText editText;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        editText=findViewById(R.id.message);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encryptedMsg = editText.getText().toString();;
                try {
                    String messageAfterDecrypt = AESCrypt.decrypt(password, encryptedMsg);
                    textView.setText(messageAfterDecrypt);
                }catch (GeneralSecurityException e){
                }

            }
        });

    }
}