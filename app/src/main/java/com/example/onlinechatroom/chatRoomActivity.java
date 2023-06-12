package com.example.onlinechatroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Comparator;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        setupListener();
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
                chatBoxUpdate();
            }
        });
    }

    //Update automatic the chatbox
    private void chatBoxUpdate(){
        makeList();


    }

    private void makeList(){
        System.out.println("start called");
        db.collection("messages").addSnapshotListener((snap,error) ->{
            messages.clear();
            for(DocumentSnapshot doc: snap.getDocuments()){

                messName = doc.getString("name");
                messMessage = doc.getString("text");
                //Timestamp is a bit of a fucky, so we have to check if it exist in the cloud storage fsr
                if (doc.contains("timestamp") && doc.get("timestamp") instanceof com.google.firebase.Timestamp) {
                    timestamp = doc.getTimestamp("timestamp");
                    messDate = timestamp.toDate();
                } else {
                    System.out.println("TIMESTAMP COULDNT BE FOUND");
                }

                Messages mess = new Messages(messName,messMessage,messDate);

                messages.add(mess);
            }
            Collections.sort(messages, new MessageComparator());
            System.out.println("The Sorted list: "+messages);

            customAdapter.notifyDataSetChanged();

        });
    }

    public class MessageComparator implements Comparator<Messages> {
        @Override
        public int compare(Messages message1, Messages message2) {
            return message1.getMessDate().compareTo(message2.getMessDate());
        }
    }

    private void setupListener(){
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");

        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

                String messageText = dataSnapshot.child("text").getValue(String.class);
                String senderName = dataSnapshot.child("name").getValue(String.class);
                Timestamp senderTimestamp = dataSnapshot.child("timestamp").getValue(Timestamp.class);
                Date senderDate = senderTimestamp.toDate();

                Messages message = new Messages(senderName, messageText,senderDate);
                messages.add(message);
                soundMaker();
                customAdapter.notifyDataSetChanged();

            }
            // Other methods of ChildEventListener (onChildChanged, onChildRemoved, onChildMoved, onCancelled)
            // They could have been made into its own class, but im too far deep into this project to do so :(
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
    private void soundMaker(){
        System.out.println("playing sound");
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.ding);

        mediaPlayer.start();
    }


}