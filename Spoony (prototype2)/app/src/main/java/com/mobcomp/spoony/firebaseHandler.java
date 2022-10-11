package com.mobcomp.spoony;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class firebaseHandler {

    private static final int UPDATEINTERVAL = 300;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference qdb = db.collection("questions");
    private final CollectionReference qcdb = db.collection("questionCount");
    private Map<String, Object> qCountDoc;
    private Map<String, Object> multiQDoc;
    private int numQuestion;
    private java.util.Date lastUpdatedTime;
    private boolean firstBoot;

    public firebaseHandler() {
        qCountDoc = null;
        multiQDoc = null;
        firstBoot = true;
        getQuestionCount();
    }

    public interface DocumentCallback {
        void getDocumentSucess(boolean success);
    }

    // get number of questions in firestore
    private void getQuestionCount() {
        qcdb.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    qCountDoc = document.getData();
                    Log.d("FSQC", String.valueOf(qCountDoc));
                    if (qCountDoc != null) {
                        numQuestion = ((Long) qCountDoc.get("amount")).intValue();
                    }
                }
            } else {
                Log.e("FSQCERR", "Error getting documents.", task.getException());
            }
        });
        lastUpdatedTime = new Date();
    }

    // increment question count in firestore
    private void incrementQuestionCount() {
        Map<String, Object> data = new HashMap<>();
        getQuestionCount();
        data.put("amount", ++numQuestion);
        qcdb.document("0").set(data).addOnSuccessListener(aVoid -> Log.d("FSQCADD", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("FSQCADDERR", "Error writing document", e));
    }

    public Map<String, Object> getQuestions() {
        return multiQDoc;
    }

    // return a mapping of multiple questions
    // key: id, val: question string
    @SuppressWarnings (value="unchecked")
    public void checkQuestions(String questionJSON, DocumentCallback documentCallback) {
        AtomicReference<Boolean> successFlag = new AtomicReference<>(false);
        if (checkUpdateTime() || questionJSON == null || firstBoot) {
            firstBoot = false;
            getQuestionCount();

            multiQDoc = new HashMap<>();

            qdb.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    successFlag.set(true);
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        multiQDoc.put(document.getId(), document.getData().get("question"));
                    }
                    Log.d("FSQ", String.valueOf(multiQDoc));
                    documentCallback.getDocumentSucess(true);
                } else {
                    Log.e("FSQERR", "Error getting documents.", task.getException());
                    documentCallback.getDocumentSucess(false);
                }
            });
        } else if (questionJSON != null) {
            multiQDoc = new Gson().fromJson(questionJSON, Map.class);
            documentCallback.getDocumentSucess(true);
        }
    }

    // return true or false if successfully added question
    public void addNewQuestion(String question, DocumentCallback documentCallback) {
        Map<String, Object> data = new HashMap<>();
        getQuestionCount();

        // no question cleaning implemented yet (nice-to-haves)
        if (question == null || question.equals("")) {
            documentCallback.getDocumentSucess(false);
        } else {
            data.put("question", question);
            qdb.document(String.valueOf(numQuestion)).set(data).addOnSuccessListener(aVoid -> {
                Log.d("FSQADD", "DocumentSnapshot successfully written!");
                incrementQuestionCount();
                documentCallback.getDocumentSucess(true);
            }).addOnFailureListener(e -> {
                Log.e("FSQADDERR", "Error adding document", e);
                documentCallback.getDocumentSucess(false);
            });
        }
    }

    private boolean checkUpdateTime() {
        int timeDiff = (int) ((new Date().getTime() - lastUpdatedTime.getTime()) / 1000);
        return timeDiff >= UPDATEINTERVAL;
    }

    protected String loadQuestionFromJSONFile(Context c, boolean firstBoot) {
        String json;
        InputStream is;
        try {
            if (firstBoot) {
                is = c.getAssets().open("questions.json");
                Log.d("QFILEIN1", c.getAssets().toString() + "/questions.json");
            } else {
                is = new FileInputStream(c.getFilesDir() + "/questions.json");
                Log.d("QFILEIN2", c.getFilesDir() + "/questions.json");
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

    protected void saveQuestionToJSONFile(Context c, Map<String, Object> q) {
        try {
            FileWriter file = new FileWriter(c.getFilesDir() + "/questions.json");
            file.write(q.toString());
            file.flush();
            file.close();
            Log.d("QFILEOUT", c.getFilesDir() + "/questions.json");
        } catch (IOException e) {
            Log.e("QFILEOUTERR", "Error in Writing: " + e.getLocalizedMessage());
        }
    }
}