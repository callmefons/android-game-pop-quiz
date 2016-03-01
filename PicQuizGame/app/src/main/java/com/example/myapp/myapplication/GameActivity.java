package com.example.myapp.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameActivity extends ActionBarActivity {

    TextView guess_text;
    LinearLayout main_layout;
    LinearLayout[] letter_line, answer_line;
    ImageView image;
    String[] images;
    int cur_image = 0;
    int score_image = 0;
    String correct_answer = "";
    ArrayList<Point> link_list;
    static final int letters_count = 21;
    int letters_in_answer = 0;

    /*Edit 25/4/2558  */
    /*prefs to save the game*/
    SharedPreferences save;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initialization
        initViews();

        loadImage(images[cur_image]);
        correct_answer = getUpperNameWithoutExtensionAndSpaces(images[cur_image]);
        fillLetterButtons(correct_answer);
        generateAnswerButtons(getNameWithoutExtension(images[cur_image]));

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


    void generateNewLevel(){
        score_image++;
        initLinking();
        cur_image++;
        loadImage(images[cur_image]);
        letters_in_answer = 0;
        correct_answer = getUpperNameWithoutExtensionAndSpaces(images[cur_image]);
        fillLetterButtons(correct_answer);
        generateAnswerButtons(getNameWithoutExtension(images[cur_image]));
        guess_text.setText("Correct" + ": " + score_image + "/" + images.length);
    }

    void skipNewLevel(){
        initLinking();
        cur_image++;
        loadImage(images[cur_image]);
        letters_in_answer = 0;
        correct_answer = getUpperNameWithoutExtensionAndSpaces(images[cur_image]);
        fillLetterButtons(correct_answer);
        generateAnswerButtons(getNameWithoutExtension(images[cur_image]));
    }


    void initLinking() {
        link_list = new ArrayList<Point>();
        for (int i = 0; i < 3 * letters_count; i++) {
            link_list.add(i, new Point(0, 0));
        }
    }

    void initViews() {
        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        answer_line = new LinearLayout[3];
        answer_line[0] = (LinearLayout) findViewById(R.id.answer_line_1);
        answer_line[1] = (LinearLayout) findViewById(R.id.answer_line_2);
        answer_line[2] = (LinearLayout) findViewById(R.id.answer_line_3);
        letter_line = new LinearLayout[3];
        letter_line[0] = (LinearLayout) findViewById(R.id.letter_line_1);
        letter_line[1] = (LinearLayout) findViewById(R.id.letter_line_2);
        letter_line[2] = (LinearLayout) findViewById(R.id.letter_line_3);
        image = (ImageView) findViewById(R.id.question_image);

        initLinking();

        Button button = (Button) findViewById(R.id.btn_skip);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                skipNewLevel();
            }
        });
        /*Get SharedPreferences*/
        /*Context.MODE_PRIVATE = 0*/
        save = getSharedPreferences("SAVE_GAME", 0);
        editor = save.edit();
        if (save.contains("continue") && save.getBoolean("continue", false)) {
            images = save.getString("images", null).replaceAll("\'", "").split(",");
            cur_image = save.getInt("currentImage", 0);
            score_image = save.getInt("currentScore", 0);
            for (int i = 0; i < images.length; i++) {
                System.out.println(images[i]);
            }
        } else {
            images = ShuffleImages(getImagesFromAssets());
            cur_image = 0;
            score_image = 0;
        }

        guess_text = (TextView) findViewById(R.id.guess_text);
        guess_text.setText("Correct" + ": " + score_image + "/" + images.length);

    }

    String[] getImagesFromAssets() {
        String[] img_files = null;
        try {
            img_files = getAssets().list("pictures");
        } catch (IOException ex) {
            Logger.getLogger(GameActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img_files;
    }

    void loadImage(String name) {
        try {
            InputStream ims = getAssets().open("pictures/" + name);
            Drawable d = Drawable.createFromStream(ims, null);
            image.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }
    }

    void generateAnswerButtons(String answer) {
        //Remove all new images[cur_image]
        for (int i = 0; i < answer_line.length; i++) {
            answer_line[i].removeAllViews();
        }

        int cur_word = 0;
        String[] result_split = answer.split("_");
        for (int i = 0; i < result_split.length; i++) {
            if (cur_word == 0) {
                for (int j = 0; j < result_split[cur_word].length(); j++) {
                    //System.out.println(j);
                    addAnswerButton(answer_line[0]);
                }
            } else {
                for (int j = 0; j < result_split[cur_word].length(); j++) {
                    addAnswerButton(answer_line[i]);
                }
            }
            cur_word++; //System.out.println(cur_word);
        }
        setOnAnswerButtonsClickListeners();
    }

    void addAnswerButton(LinearLayout l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = (int) getResources().getDimension(R.dimen.btn_size_width);
        params.height = (int) getResources().getDimension(R.dimen.btn_size_height);
        params.setMargins(5,5,5,5);
        Button b = new Button(this);
        b.setLayoutParams(params);
        b.setText("");
        b.setTextColor(Color.BLACK);
        b.setTextSize(getResources().getDimension(R.dimen.text_answer_line));
        b.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        l.addView(b);
    }

    void fillLetterButtons(String name) {
        //clear letter lines
        for (int i = 0; i < letter_line.length; i++) {
            letter_line[i].removeAllViews();
        }
        String nameWithoutSpaces = name.toUpperCase().replaceAll("_", "");
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String letters = ShuffleLetters(nameWithoutSpaces
                + ShuffleLetters(alphabet).substring(0, letters_count - nameWithoutSpaces.length()));
        for (int i = 0; i < letters_count; i++) {
            Button b = new Button(this);
            b.setText(Character.toString(letters.charAt(i)));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            b.setLayoutParams(params);
            params.weight = 1;
            params.setMargins(5,5,5,5);
            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
            b.setTypeface(tf);
            b.setTextColor(Color.WHITE);
            b.setTextSize(getResources().getDimension(R.dimen.text_letter_line));
            b.setBackgroundColor(getResources().getColor(R.color.colorPrimary));



            if (i < letters_count / 3) {
                letter_line[0].addView(b);
            } else if ((letters_count / 3 <= i) && (i < 2 * letters_count / 3)) {
                letter_line[1].addView(b);
            } else if ((2 * letters_count / 3 <= i) && (i < letters_count)) {
                letter_line[2].addView(b);
            }
        }
        setOnLettersClickListeners();
    }

    void setOnLettersClickListeners() {
        for (int i = 0; i < letter_line.length; i++) {
            for (int j = 0; j < letter_line[i].getChildCount(); j++) {
                final Button b = (Button) letter_line[i].getChildAt(j);
                final Point letter_pos = new Point(i, j);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!b.getText().toString().equals("") && letters_in_answer < correct_answer.length()) {
                            letters_in_answer++;
                            System.out.println("letters: " + letters_in_answer); //Debug letter_in_answer
                            int free_pos = getFirstFreeAnswerButtonPosition();
                            Button a = findButtonByPos(answer_line, free_pos);
                            a.setText(b.getText());
                            hideButton(b);
                            link_list.set(free_pos, letter_pos);
                            checkPlayersAnswer(letters_in_answer);
                        }
                    }
                });
            }
        }
    }

    void checkPlayersAnswer(int current_pos){
        if(current_pos == correct_answer.length()){
            if(isCorrectAnswer()){
                Toast.makeText(getApplicationContext(),"Correct!! "+getNameWithoutExtension(images[cur_image]), Toast.LENGTH_LONG).show();
                if(score_image<images.length-1){
                    generateNewLevel();
                }else{
                    Intent intent = new Intent(GameActivity.this,EndActivity.class);
                    startActivity(intent);
                }
            }else{
                Toast.makeText(getApplicationContext(),"Wrong!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    boolean isCorrectAnswer() {
        String player_answer="";
        for(int i =0; i< answer_line.length;i++){
           for(int j = 0; j<answer_line[i].getChildCount();j++){
               Button b = (Button)answer_line[i].getChildAt(j);
               player_answer = player_answer+b.getText().toString();
           }
        }
        if(player_answer.equals(correct_answer)){
            return true;
            //System.out.println(true);
        }else{
            //System.out.println(false);
            return false;
        }
    }

    int getFirstFreeAnswerButtonPosition() {
        int pos = 0;
        boolean isFound = false;
        for (int i = 0; i < answer_line.length; i++) {
            for (int j = 0; j < answer_line[i].getChildCount(); j++) {
                Button b = (Button) answer_line[i].getChildAt(j);
                if (b.getText().toString().equals("") && !isFound) {
                    pos = getAnswerButtonPos(i, j);
                    isFound = true;
                }
            }
        }
        return pos;
    }

    void setOnAnswerButtonsClickListeners(){
        for (int i = 0; i < answer_line.length; i++) {
            for (int j = 0; j < answer_line[i].getChildCount(); j++) {
                final Button b = (Button) answer_line[i].getChildAt(j);
                final int al_pos = getAnswerButtonPos(i, j);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!b.getText().toString().equals("")) {
                            Button a = (Button) letter_line[link_list.get(al_pos).x].getChildAt(link_list.get(al_pos).y);
                            showButton(a, b.getText().toString());
                            b.setText("");
                            letters_in_answer--;
                        }
                    }
                });
            }
        }
    }

    int getAnswerButtonPos(int row, int col) {
        int pos = 1;
        for (int i = 0; i <= row; i++) {
            if (i < row) {
                pos += answer_line[i].getChildCount();
            } else {
                pos += col;
            }
        }
        return pos;
    }

    Button findButtonByPos(LinearLayout[] line, int pos) {
        if (pos <= line[0].getChildCount()) {
            return (Button) line[0].getChildAt(pos - 1);
        } else if (pos <= line[0].getChildCount() + line[1].getChildCount()) {
            return (Button) line[1].getChildAt(pos - line[0].getChildCount() - 1);
        } else {
            return (Button) line[2].getChildAt(pos - line[0].getChildCount()- line[1].getChildCount() - 1);
        }
    }
    //delete .jpg or .png
    String getNameWithoutExtension(String name) {
        return name.substring(0, name.indexOf('.'));
    }
    //All uppercase text and _ replace ""
    String getUpperNameWithoutExtensionAndSpaces(String name) {
        return getNameWithoutExtension(name.toUpperCase().replaceAll("_", ""));
    }

    String ShuffleLetters(String s){
        ArrayList<Character> char_list = new ArrayList<Character>();
        for(int i=0; i< s.length();i++){
            char_list.add(s.charAt(i));
        }
        Collections.shuffle(char_list);
        s = TextUtils.join("",char_list);
        return s;
    }

    String[] ShuffleImages(String[] imgs) {
        ArrayList<String> string_list = new ArrayList<String>();
        for (int i = 0; i < imgs.length; i++) {
            string_list.add(imgs[i]);
        }
        Collections.shuffle(string_list);
        return string_list.toArray(imgs);
    }

    void hideButton(Button b) {
        b.setText("");
        b.setVisibility(View.INVISIBLE);
    }

    void showButton(Button b, String text) {
        b.setVisibility(View.VISIBLE);
        b.setText(text);
    }

    @Override
    public void onBackPressed() {
        saveCurrentGame();
        Intent menu_intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(menu_intent);
        finish();
    }
    @Override
    public void onPause() {
        saveCurrentGame();
        super.onPause();
    }


    void saveCurrentGame() {
        editor.putBoolean("gameSaved", true);
        editor.putBoolean("gameSaved", true);
        editor.putString("images", arrayToString(images));
        editor.putInt("currentImage", cur_image);
        editor.putInt("currentScore", score_image);
        editor.commit();
    }
    String arrayToString(String[] name) {
        StringBuilder sb = new StringBuilder();
        for (String s : name) {
            sb.append(s).append(',');
        }
        if (name.length != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }



}

