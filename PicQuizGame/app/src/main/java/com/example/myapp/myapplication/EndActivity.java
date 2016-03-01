package com.example.myapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;


public class EndActivity extends FragmentActivity {

    Button btn_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_end_activity);

        btn_play = (Button)findViewById(R.id.btn_back_main);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
