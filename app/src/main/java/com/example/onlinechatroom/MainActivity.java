
package com.example.onlinechatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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


    String userName;
    EditText userNameInsert;
    TextView userNameShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameInsert = findViewById(R.id.userNameInsert);


        chatRoomClick();


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

}