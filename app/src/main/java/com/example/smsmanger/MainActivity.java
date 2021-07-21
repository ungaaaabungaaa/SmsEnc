package com.example.smsmanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.scottyab.aescrypt.AESCrypt;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;
    String password = "HG;_3UGM}34U}H(";
    TextView textView;
    TextView displayen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView2);
        displayen=findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DecryptActivity.class);
                startActivity(intent);

            }
        });
        sendBtn =findViewById(R.id.button);
        txtphoneNo =findViewById(R.id.editTextTextPersonName2);
        txtMessage = findViewById(R.id.message);
        sendBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sendSMSMessage();
           }
       });

    }


    protected void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        String usermessage = txtMessage.getText().toString();
        try {
             message = AESCrypt.encrypt(password, usermessage);
             displayen.setText("Decrypt Sms"+"\n"+"Encrypted"+"\n"+message);
        }catch (GeneralSecurityException e){
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (message!=null&& message.equals(""))
                    {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("+91"+phoneNo, null, message, null, null);
                        Toast.makeText(getApplicationContext(), "Sms Sent", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getApplicationContext(), "Encryption Failed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

}