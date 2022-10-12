package com.mobcomp.spoony;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.Map;

@SuppressWarnings("ALL")
public class FirebaseActivity extends AppCompatActivity {

    private Map<String, Object> qs;

//    private Button dButton;
    private Button uButton;
//    private TextView qTextView;
    private EditText qTextInput;
    private TextView outputTextView;
    private String questionJSON;

    private CoordinatorLayout _layout;

    firebaseHandler fb = new firebaseHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        _layout = findViewById(R.id.firebaseLayout);
//        dButton = findViewById(R.id.downloadButton);
        uButton = findViewById(R.id.uploadButton);
//        qTextView = findViewById(R.id.questionTextView);
        qTextInput = findViewById(R.id.questionTextInput);
        outputTextView = findViewById(R.id.outputTextView);

//        fb.updateQuestions(fb.loadQuestionFromJSONFile(this, fb.isFirstBoot()), success -> {
//            qs = fb.getQuestions();
//            qTextView.setText((CharSequence) String.valueOf(qs));
//            Log.d("QFILEIN", String.valueOf(qs));
//        });
//
//        dButton.setOnClickListener(view -> {
//            fb.loadQuestionFromJSONFile(this, fb.isFirstBoot());
//            fb.updateQuestions(questionJSON, success -> {
//                if (success) {
//                    qs = fb.getQuestions();
//                    qTextView.setText((CharSequence) String.valueOf(qs));
//                    Log.d("QOUT", String.valueOf(qs));
//                    fb.saveQuestionToJSONFile(this, qs);
//                }
//            });
//        });

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
}