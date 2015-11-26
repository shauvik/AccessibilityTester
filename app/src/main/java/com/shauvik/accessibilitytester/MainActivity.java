package com.shauvik.accessibilitytester;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static String TAG = "AccessibiltyTester";
    static Activity thisActivity;

    static TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisActivity = this;
        textView = (TextView) findViewById(R.id.myTextView);

        if(isAccessibilityServiceEnabled()) {
            log(">> AccessibilityService Enabled");
        } else {
            log(">> AccessibilityService Not Enabled");
        }

        // Check Permissions
        findViewById(R.id.myButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(thisActivity,
                        Manifest.permission.BIND_ACCESSIBILITY_SERVICE);
                //log("Activity has permission="+permissionCheck);
            }
        });


    }

    private void showAccessibilityServiceEnableWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Please enable accessibility service")
                .setTitle("Accessibility service needed")
                .setPositiveButton("Take me to Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 0);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    protected boolean isAccessibilityServiceEnabled() {
        AccessibilityManager am = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> svcs = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo svc : svcs) {
            Log.i(TAG, svc.getId());
            if (TextUtils.equals(svc.getId(), "com.shauvik.accessibilitytester/.MyAccessibilityService")) {
                return true;
            }
        }
        return false;
    }


    public static void log(String message) {
        Log.d(TAG, message);
        if(textView != null)
            textView.setText(textView.getText() + "\n"+message);
    }
}
