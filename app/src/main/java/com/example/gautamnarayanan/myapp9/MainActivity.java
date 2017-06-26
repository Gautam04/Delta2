package com.example.gautamnarayanan.myapp9;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Uri imguri=null;
    Bitmap img;
    ArrayList<Object> objects=new ArrayList<Object>();;
     ArrayAdapter<Object> gdapter;
    private Context context=MainActivity.this;
    int counter=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton Gbutton = (ImageButton)findViewById(R.id.Gbutton);
        ImageButton Gbutton2 = (ImageButton)findViewById(R.id.Gbutton2);
        ImageButton Gbutton3 = (ImageButton)findViewById(R.id.Gbutton3);
        ListView Glist = (ListView)findViewById(R.id.Glist);

        gdapter = new CustomAdapter(MainActivity.this,R.layout.custom_layout,objects);
        final EditText Gtext=(EditText)findViewById(R.id.Gtext);
        ImageView Gimage = (ImageView)findViewById(R.id.Gimage);
        Glist.setAdapter(gdapter);

        Gbutton2.setOnClickListener(
                new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        if(img==null)
                            Toast.makeText(MainActivity.this,"No image imported",Toast.LENGTH_SHORT).show();
                        else
                        {String caption = Gtext.getText().toString();
                            if(TextUtils.isEmpty(caption))
                            {
                                Toast.makeText(MainActivity.this,"No caption",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Object o = new Object(caption,img);
                                gdapter.add(o);
                            }
                        }

                        Gtext.setText(null);
                    }
                }
        );

        Gbutton.setOnClickListener(
                new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i,1);

                    }
                }
        );



        Gbutton3.setOnClickListener(
                new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 2);
                        }
                    }

        );


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {   Bundle extras = data.getExtras();
            imguri = data.getData();
            ImageView Gimage=(ImageView)findViewById(R.id.Gimage);
            try {
                img= getThumbnail(imguri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Bitmap img = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imguri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gimage.setImageBitmap(img);



        }
        else
            if(requestCode==2&&resultCode==RESULT_OK&&data!=null)
            {   ImageView Gimage=(ImageView)findViewById(R.id.Gimage);
                img =  (Bitmap)data.getExtras().get("data");
                Gimage.setImageBitmap(img);
            }
    }


    private Bitmap getThumbnail(Uri uri)  throws FileNotFoundException, IOException{
        InputStream input = this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 300) ? (originalSize / 300) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        input = this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }


}
