package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PermissionCenterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        TextView title = new TextView(this);
        title.setText("Permission Center");
        title.setTextSize(28);
        root.addView(title);
        setContentView(root);
    }
}
