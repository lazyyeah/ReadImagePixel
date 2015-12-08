package com.example.lazyyeah.readimagepixel;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res=getResources();
        Bitmap bm = BitmapFactory.decodeResource(res, R.drawable.test00);

        setImgInfo(bm);
    }

    private void setImgInfo(Bitmap bm)
    {

        int width = bm.getWidth();
        int height = bm.getHeight();
        //int[] pixels = new int[width*height];//保存所有的像素的数组，图片宽×高
        //bm.getPixels(pixels, 0, width, 0, 0, bm.getWidth(), height);


        for(int i = 0;i<bm.getWidth();i++)
        {
            for(int j =0;j<bm.getHeight();j++)
            {
                int col = bm.getPixel(i, j);
                int red = (col&0x00FF0000)>>16;
                int green = (col&0x0000FF00)>>8;
                int blue = (col&0x000000FF);
                int gray = (int)((float)red*0.3+(float)green*0.59+(float)blue*0.11);
                System.out.println(gray);

            }
        }

    }




}
