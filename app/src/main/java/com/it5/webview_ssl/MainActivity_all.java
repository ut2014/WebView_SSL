package com.it5.webview_ssl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.it5.webview_ssl.acitivity_1.MainActivity_1;
import com.it5.webview_ssl.activity.MainActivity;
import com.it5.webview_ssl.activity_2.MainActivity_2;

public class MainActivity_all extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_all);
    }

    public void btn_click(View view){
        Toast.makeText(this,"s",Toast.LENGTH_SHORT).show();
        goActivity(MainActivity.class);
    }
    public void btn1_click(View view){
        goActivity(MainActivity_1.class);
    }
    public void btn2_click(View view){
        goActivity(MainActivity_2.class);
    }
    public void btn3_click(View view){
        goActivity(MainActivity_3.class);
    }
    public void btnac_click(View view){
        goActivity(MainActivity_confim_alert.class);
    }

    private void goActivity(Class clazz) {
        if (clazz==null)return;
        startActivity(new Intent(this,clazz));
    }
}
