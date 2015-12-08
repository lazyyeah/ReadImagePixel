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
        int[] block = new int[17];


        for(int i = 0;i<bm.getWidth();i++)
        {
            for(int j =0;j<bm.getHeight();j++)
            {
                int col = bm.getPixel(i, j);
                int red = (col&0x00FF0000)>>16;
                int green = (col&0x0000FF00)>>8;
                int blue = (col&0x000000FF);
                int gray = (int) ((((float)red*0.3+(float)green*0.59+(float)blue*0.11)));


                if (j<height/2&i<width/4)
                    block[0]=block[0]+gray;

                if (j>=height/2&i<width/4)
                    block[1]=block[1]+gray;

                if (j<height/2&i>=width/4&i<width/2)
                    block[2]=block[2]+gray;

                if (j>=height/2&i>=width/4&i<width/2)
                    block[3]=block[3]+gray;

                if (j<height/2&i>=width/2&i<(width*3/4))
                    block[4]=block[4]+gray;

                if (j>=height/2&i>=width/2&i<(width*3/4))
                    block[5]=block[5]+gray;

                if (j<height/2&i>=(width*3/4))
                    block[6]=block[6]+gray;

                if (j>=height/2&i>=(width*3/4))
                    block[7]=block[7]+gray;

            }
        }

        block[8] = block[0]+block[1];
        block[9] = block[2]+block[3];
        block[10] = block[4]+block[5];
        block[11] = block[6]+block[7];
        block[12] = block[0]+block[4];
        block[13] = block[1]+block[5];
        block[14] = block[2]+block[6];
        block[15] = block[3]+block[7];
        block[16] = block[8]+block[9]+block[10]+block[11];
        for (int k=0;k<=16;k++)
        System.out.println(block[k]);

    }




}
