 package com.example.gautamnarayanan.myapp9;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

 /**
 * Created by Gautam Narayanan on 6/21/2017.
 */

public class CustomAdapter extends ArrayAdapter<Object> {
    ArrayList<Object> objects;
     ArrayList<String> captions=new ArrayList<String>();
    Context context;


    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Object> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.context=context;


    }




     @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.custom_layout,parent,false);
        final Object o = getItem(position);

         ImageView Gimage2= (ImageView)customView.findViewById(R.id.Gimage2);
         EditText Gtext2 = (EditText)customView.findViewById(R.id.Gtext2);
        Gimage2.setImageBitmap(o.getImg());
        Gtext2.setText(o.getCaption());
        Gtext2.setEnabled(false);
        ImageButton Gbutton4 = (ImageButton)customView.findViewById(R.id.Gbutton4);
        Gbutton4.setOnClickListener(
                new ImageButton.OnClickListener(){
                    public void onClick(View v)
                    {
                        remove(o);

                    }
                }
        );
        return customView;
    }


    @Override
    public void add(@Nullable Object object) {

        super.add(object);
    }

}
