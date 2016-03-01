package com.example.myapp.myapplication; /**
 * Created by Fon on 4/26/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Fon on 2/28/2015.
 */
public class CustomArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final String[] values_description;
    private final String[] values_description_2;
    private final int[] images;
    private final int poseID;

    public CustomArrayAdapter(Context context, int resource, String[] values, String[] values_description,String[] values_description_2,int[] images, int poseID) {
        super(context, resource, values);
        this.context = context;
        this.values = values;
        this.values_description = values_description;
        this.values_description_2 = values_description_2;
        this.images = images;
        this.poseID = poseID;
    }

    TextView firstLine;
    TextView secondLine;
    TextView thirdLine;
    ImageView imageView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);

        firstLine = (TextView) rowView.findViewById(R.id.firstLine);
        secondLine = (TextView) rowView.findViewById(R.id.secondLine);
        thirdLine = (TextView) rowView.findViewById(R.id.thirdLine);
        imageView = (ImageView) rowView.findViewById(R.id.icon);

        firstLine.setText(values[position]);
        secondLine.setText(values_description[position]);
        thirdLine.setText(values_description_2[position]);
        imageView.setImageResource(images[position]);

        String s = values[position];
        String[] sText = {"employees", "Standing", "Sitting", "Beginner", "Intermediate", "Advanced"};

        for (int i = 0; i < sText.length; i++) {
            if (s.startsWith(sText[i])) {
                rowView.setBackgroundColor(Color.parseColor("#cccccc"));
                rowView.setOnClickListener(null);
            }
        }

        return rowView;
    }

}