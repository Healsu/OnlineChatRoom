package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.onlinechatroom.R;

import java.util.ArrayList;

import model.Messages;

public class CustomAdapter extends ArrayAdapter<Messages> {


    private LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Messages> messages) {
        super(context, 0, messages);
        inflater = LayoutInflater.from(context);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.myrow, parent, false);
        }

        TextView itemTextView = convertView.findViewById(R.id.rowTextView);
        TextView itemTextView2 = convertView.findViewById(R.id.rowTextView2);

        Messages customObject = getItem(position);

            itemTextView.setText(customObject.getMessName());
            itemTextView2.setText(customObject.getMessMessage());// Replace getName() with the appropriate method to retrieve the desired attribute from your custom object
        System.out.println(itemTextView);


        return convertView;
    }
}

