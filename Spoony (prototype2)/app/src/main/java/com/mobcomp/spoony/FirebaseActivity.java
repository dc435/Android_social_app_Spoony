package com.mobcomp.spoony;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@SuppressWarnings("ALL")
public class FirebaseActivity extends AppCompatActivity {

    private Map<String, Object> qs;

    private TextView qTextView;
    private Button dButton;
    private Button uButton;
    private EditText qTextInput;
    private TextView outputTextView;

    private String questionJSON;
    private boolean firstBootFlag;

    private CoordinatorLayout _layout;

    firebaseHandler fb = new firebaseHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firstBootFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        _layout = findViewById(R.id.firebaseLayout);
        dButton = findViewById(R.id.downloadButton);
        uButton = findViewById(R.id.uploadButton);
        qTextView = findViewById(R.id.questionTextView);
        qTextInput = findViewById(R.id.questionTextInput);
        outputTextView = findViewById(R.id.outputTextView);

        fb.checkQuestions(loadQuestionFromJSONFile(firstBootFlag), success -> {
            qs = fb.getQuestions();
            qTextView.setText((CharSequence) String.valueOf(qs));
            Log.d("QFILEIN", String.valueOf(qs));
            firstBootFlag = false;
        });

        dButton.setOnClickListener(view -> {
            loadQuestionFromJSONFile(firstBootFlag);
            fb.checkQuestions(questionJSON, success -> {
                if (success) {
                    qs = fb.getQuestions();
                    qTextView.setText((CharSequence) String.valueOf(qs));
                    Log.d("QOUT", String.valueOf(qs));
                    saveQuestionToJSONFile(qs);
                }
            });
        });

        uButton.setOnClickListener(view -> {
            String input = qTextInput.getText().toString();
            Log.d("QIN", input);
            outputTextView.setText("SUCCESS");
            outputTextView.setBackgroundColor(Color.BLACK);
            outputTextView.setTextColor(Color.GREEN);

            // disabled for now to prevent accidentally adding stuff to firestore
//            fb.addNewQuestion(input, success -> {
//                if (success) {
//                    outputTextView.setText("SUCCESS");
//                    outputTextView.setBackgroundColor(Color.BLACK);
//                } else {
//                    outputTextView.setText("ERROR");
//                    outputTextView.setTextColor(Color.RED);
//                    outputTextView.setBackgroundColor(Color.BLACK);
//                }
//            });
        });
    }

    private String loadQuestionFromJSONFile(boolean firstBoot) {
        String json;
        InputStream is;
        try {
            if (firstBoot) {
                is = getAssets().open("questions.json");
                Log.d("QFILEIN1", getAssets().toString() + "/questions.json");
            } else {
                is = new FileInputStream(new File(getFilesDir() + "/questions.json"));
                Log.d("QFILEIN2", getFilesDir() + "/questions.json");
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void saveQuestionToJSONFile(Map<String, Object> q) {
        try {
            FileWriter file = new FileWriter(getFilesDir() + "/questions.json");
            file.write(q.toString());
            file.flush();
            file.close();
            Log.d("QFILEOUT", getFilesDir() + "/questions.json");
        } catch (IOException e) {
            Log.e("QFILEOUTERR", "Error in Writing: " + e.getLocalizedMessage());
        }
    }
}