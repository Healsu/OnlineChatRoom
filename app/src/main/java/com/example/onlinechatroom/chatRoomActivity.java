package com.example.onlinechatroom;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chatRoomActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();






    String userName;
    EditText textMessage;
    TextView userNameShow;
    private ListView chatBox;
    private DatabaseReference mDatabase;

    private ArrayList<String> mDataList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //Show username
        userNameShow = findViewById(R.id.userNameShow);
        userName = getIntent().getStringExtra("userName");
        userNameShow.setText("Username: "+userName);


        //Update listView automatically
        /*
        mDatabase = FirebaseDatabase.getInstance().getReference();
        chatBox = findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDataList);
        chatBox.setAdapter(mAdapter);
         */

        textMessage = findViewById(R.id.chatInput);
        messageSend();


    }

    //Sends message from chat site
    private void messageSend(){
        //We wanna provide with the username so that each user has their own cloud firestore
        CollectionReference userCollection = db.collection(userName);
        CollectionReference allMessages = db.collection("messages");

        Button sendMessage = findViewById(R.id.sendButton);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            //Add message to cloud firestore
            @Override
            public void onClick(View view) {
                Map<String, Object> message = new HashMap<>();
                message.put("name",userName);
                message.put("text",textMessage.getText().toString());
                userCollection.add(message);
                //Extra so we can order them by time for the actual message list
                message.put("timestamp", FieldValue.serverTimestamp());
                allMessages.add(message);
            }
        });
    }


    //Update automatic the chatbox
    private void chatBoxUpdate(){
        
    }
}