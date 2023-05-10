package com.example.onlinechatroom;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import adapter.CustomAdapter;
import model.Messages;

public class chatRoomActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    String userName;
    EditText textMessage;
    TextView userNameShow;
    ListView chatBox;
    CustomAdapter customAdapter;


    ArrayList<Messages> messages = new ArrayList<>();
    Timestamp timestamp;
    String messName;
    String messMessage;
    Date messDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //Show username
        userNameShow = findViewById(R.id.userNameShow);
        userName = getIntent().getStringExtra("userName");
        userNameShow.setText("Username: "+userName);
        chatBox = findViewById(R.id.listView);
        textMessage = findViewById(R.id.chatInput);

        customAdapter = new CustomAdapter(chatRoomActivity.this,messages);
        chatBox.setAdapter(customAdapter);
        chatBoxUpdate();

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


    private void makeList(){
        System.out.println("start called");
        db.collection("messages").addSnapshotListener((snap,error) ->{
            messages.clear();
            for(DocumentSnapshot doc: snap.getDocuments()){

                messName = doc.getString("name");
                messMessage = doc.getString("text");
                timestamp = doc.getTimestamp("timestamp");


                Messages mess = new Messages(messName,messMessage,messDate);

                messages.add(mess);
            }
            System.out.println(messages);


        });
    }


    //Update automatic the chatbox
    private void chatBoxUpdate(){
       makeList();
        customAdapter.notifyDataSetChanged();
        chatBox.setAdapter(customAdapter);
    }
}