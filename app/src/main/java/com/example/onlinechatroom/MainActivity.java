
package com.example.onlinechatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

import model.Messages;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static ArrayList<Messages> messages = new ArrayList<>();
    Timestamp timestamp;
    String messName;
    String messMessage;
    Date messDate;




    String userName;
    EditText userNameInsert;
    TextView userNameShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameInsert = findViewById(R.id.userNameInsert);


        chatRoomClick();
        makeList();
        //getAndUpdateMessages();
    }

    private void chatRoomClick(){
        Button joinButton = findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = userNameInsert.getText().toString();

                if(userName == null){
                    System.out.println("No name given");
                } else{
                    System.out.println("User has a name which is: "+userName);
                    Intent intent = (new Intent(MainActivity.this, chatRoomActivity.class));
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                }
            }
        });
    }
/*
    private void getAndUpdateMessages(){
        db.collection("messages");
        QuerySnapshot queryDocumentSnapshots;

        for (QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
            messName = documentSnapshots.getString("name");
            messMessage = documentSnapshots.getString("text");
            messDate = documentSnapshots.getDate("timestamp");
        }


    }

 */

    public void makeList(){
        System.out.println("start called");
        db.collection("messages").addSnapshotListener((snap,error) ->{
            messages.clear();
            for(DocumentSnapshot doc: snap.getDocuments()){

                messName = doc.getString("name");
                messMessage = doc.getString("text");
                timestamp = doc.getTimestamp("timestamp");
                messDate = timestamp.toDate();
                Messages mess = new Messages(messName,messMessage,messDate);
                messages.add(mess);
            }
            System.out.println(messages);


            //mainActivity.adapter.notifyDataSetChanged(); // will make the adapter reload
        });
    }
}