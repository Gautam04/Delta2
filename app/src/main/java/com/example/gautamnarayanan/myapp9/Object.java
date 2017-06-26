package com.example.gautamnarayanan.myapp9;

import android.graphics.Bitmap;

/**
 * Created by Gautam Narayanan on 6/21/2017.
 */

public class Object{
        private Bitmap img;
        private String caption;
        public Object(String caption,Bitmap img)
        {
            this.caption=caption;
            this.img=img;
        }
        public String getCaption()
        {return caption;}
        public Bitmap getImg()
        {return img;}
        public void setImg(Bitmap imguri)
        {img= imguri;}
        public void setCaption(String caption)
        {this.caption=caption;}
}
