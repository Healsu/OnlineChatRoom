package adapter;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import model.Messages;

public class CustomAdapter extends ArrayAdapter<Messages> {

    /*
    private LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Messages> dataList) {
        super(context, 0, dataList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView itemTextView = convertView.findViewById(R.id.itemTextView);

        CustomObject customObject = getItem(position);
        if (customObject != null) {
            itemTextView.setText(customObject.getName()); // Replace getName() with the appropriate method to retrieve the desired attribute from your custom object
        }

        return convertView;
    }

     */
}

