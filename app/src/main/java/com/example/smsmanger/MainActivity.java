package com.example.smsmanger;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.scottyab.aescrypt.AESCrypt;
import java.security.GeneralSecurityException;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;
    String password = "HG;_3UGM}34U}H(";
    Button textView;
    TextView displayen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayen=findViewById(R.id.displayview);
        textView=findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DecryptActivity.class);
                startActivity(intent);

            }
        });

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
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

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.with(this).setPermissionListener(permissionlistener).setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]").setPermissions(Manifest.permission.SEND_SMS).check();

    }


    protected void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        String usermessage = txtMessage.getText().toString();
        try {
             message = AESCrypt.encrypt(password, usermessage);
             displayen.setText(message);
             setClipboard(MainActivity.this,message);
        }catch (GeneralSecurityException e){
        }

    }



    private void setClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+91"+phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(), "Sms Sent Text Copied To CLip Board", Toast.LENGTH_LONG).show();

    }

}