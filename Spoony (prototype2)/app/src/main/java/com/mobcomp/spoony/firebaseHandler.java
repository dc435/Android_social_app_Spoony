package com.mobcomp.spoony;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class firebaseHandler {

    private static final int UPDATEINTERVAL = 600;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference qdb = db.collection("questions");
    private final CollectionReference qcdb = db.collection("questionCount");
    private Map<String, Object> questionCountDocument;
    private Map<String, Object> questionDocument;
    private String question;
    public int numQuestion;
    private java.util.Date lastUpdatedTime;

    public firebaseHandler() {
        getQuestionCount();
    }

    private void getQuestionCount() {
        qcdb.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            questionCountDocument = document.getData();
                            Log.d("QCInt", String.valueOf(questionCountDocument));
                            if (questionCountDocument != null) {
                                numQuestion = ((Long) questionCountDocument.get("amount")).intValue();
                            }
                        }
                    } else {
                        Log.w("QCIntErr", "Error getting documents.", task.getException());
                    }
                });
        lastUpdatedTime = new Date();
    }

    private void incrementQuestionCount() {
        getQuestionCount();
        Map<String, Object> data = new HashMap<>();
        data.put("amount", ++numQuestion);
        qcdb.document("0").set(data);
    }

    public String getNewQuestion() {
        if (checkUpdateTime()) {
            getQuestionCount();
        }
        int r = ThreadLocalRandom.current().nextInt(0, numQuestion);
        qdb.document(String.valueOf(r)).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("QStr", "DocumentSnapshot data: " + document.getData());
                            questionDocument = document.getData();
                            if (questionDocument != null) {
                                question = (String) questionDocument.get("question");
                            }
                        } else {
                            Log.d("QStrErr", "No such document");
                        }
                    } else {
                        Log.w("QStrErr", "Error getting documents.", task.getException());
                    }
                });
        return question;
    }

    public Boolean addNewQuestion(String question) {
        AtomicReference<Boolean> successFlag = new AtomicReference<>(false);
        Map<String, Object> data = new HashMap<>();
        data.put("question", question);
        getQuestionCount();
        qdb.document(String.valueOf(numQuestion))
                .set(data)
                .addOnSuccessListener(aVoid -> {
                    Log.d("QAdd", "DocumentSnapshot successfully written!");
                    incrementQuestionCount();
                    successFlag.set(true);
                })
                .addOnFailureListener(e -> Log.w("QAddErr", "Error adding document", e));
        return successFlag.get();
    }

    private boolean checkUpdateTime() {
        int timeDiff = (int) ((new Date().getTime() - lastUpdatedTime.getTime()) / 1000);
        return timeDiff >= UPDATEINTERVAL;
    }
}