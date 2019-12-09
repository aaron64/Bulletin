package net.aaronchambers.bulletin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.aaronchambers.bulletin.BulletinAPIUtil.BulletinExistsCallback;
import net.aaronchambers.bulletin.BulletinAPIUtil.RequestUtil;
import net.aaronchambers.bulletin.util.FontUtil;

public class MainActivity extends AppCompatActivity {

    TextView mainTextIcon;
    TextView mainTextLabel;
    TextView mainTextSubLabel;
    Button mainBtnConnect;
    EditText mainEditName;

    String ssid;

    boolean boardExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        setContentView(R.layout.activity_main);

        mainTextIcon = findViewById(R.id.main_text_icon);
        mainTextLabel = findViewById(R.id.main_text_label);
        mainTextSubLabel = findViewById(R.id.main_text_sublabel);
        mainBtnConnect = findViewById(R.id.main_btn_connect);
        mainEditName = findViewById(R.id.main_edit_name);

        mainTextIcon.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FONTAWESOME));
        mainTextIcon.setText(getString(R.string.icon_thumbtack));

        ssid = NetworkUtil.getSSID(this);

        if(NetworkUtil.isConnected(this)) {
            setupAccessBoard();
        } else {
            setupNoConnection();
        }

        mainBtnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BulletinBoardActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, ssid);
                startActivity(intent);
            }
        });

        mainEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 4) {
                    mainBtnConnect.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    final void setupAccessBoard() {
        boardExists = true;

        mainTextLabel.setText("Connect to " + ssid);
        mainTextSubLabel.setText("(" + ssid.replace("\"", "") + ")");

        mainBtnConnect.setText("Enter Board");
        mainBtnConnect.setEnabled(true);
        mainEditName.setVisibility(View.INVISIBLE);
    }

    final void setupNoConnection() {
        mainTextLabel.setText("Not Connected to WiFi");

        mainBtnConnect.setText("Enter Board");
        mainBtnConnect.setEnabled(false);
        mainEditName.setVisibility(View.INVISIBLE);
    }
}
