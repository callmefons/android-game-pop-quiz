package com.example.myapp.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    Button btn_play,btn_exit,btn_continue;
    SharedPreferences save;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*continue last ended game*/
        save = getSharedPreferences("SAVE_GAME", 0);
        editor = save.edit();
        btn_continue = (Button) findViewById(R.id.btn_continue);
        if (save.contains("gameSaved") && save.getBoolean("gameSaved", false)) {
            btn_continue.setVisibility(View.VISIBLE);
        } else {
            btn_continue.setVisibility(View.GONE);
        }
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("continue", true);
                editor.commit();
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /*start the new game*/
        btn_play = (Button)findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("continue", false);
                editor.commit();
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });

        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }




}
