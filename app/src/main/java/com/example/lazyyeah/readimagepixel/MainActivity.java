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

        double[][] net1 = new double[9][17];
        net1=initnet(net1);
        double[][] net2 = new double[4][9];
        net2=initnet(net2);
        double[] out = new double[4];
        double[] in = new double[9];
        double[] block;


        Resources res = getResources();
        Bitmap bm0 = BitmapFactory.decodeResource(res, R.drawable.test00);
        int[] Dout = {0, 0, 0, 0};
        block = setImgInfo(bm0);
        out = fout(block, net1, in, out, net2);
        //for (int i=0;i<=3;i++)
        //    System.out.println((int)out[i]);
        in = fin(block, net1, in, out, net2);
        net2 = fnet2(Dout, out, net2, in);
        net1 = fnet1(Dout, in, net1, block, out, net2);
        out = fout(block, net1, in, out, net2);
        for (int i = 0; i <= 3; i++)
            System.out.println((int) out[i]);

        Bitmap bm1 = BitmapFactory.decodeResource(res, R.drawable.test1);
        Dout[3] = 1;
        block = setImgInfo(bm1);
        out = fout(block, net1, in, out, net2);
        //for (int i=0;i<=3;i++)
        //    System.out.println((int)out[i]);
        in = fin(block, net1, in, out, net2);
        net2 = fnet2(Dout, out, net2, in);
        net1 = fnet1(Dout, in, net1, block, out, net2);
        out = fout(block, net1, in, out, net2);
        for (int i = 0; i <= 3; i++)
            System.out.println("aa" + (int) out[i]);

    }


    private double[] setImgInfo(Bitmap bm) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        //int[] pixels = new int[width*height];//保存所有的像素的数组，图片宽×高
        //bm.getPixels(pixels, 0, width, 0, 0, bm.getWidth(), height);
        double[] block = new double[17];


        for (int i = 0; i < bm.getWidth(); i++) {
            for (int j = 0; j < bm.getHeight(); j++) {
                int col = bm.getPixel(i, j);
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                double gray = ((((double) red * 0.3 + (double) green * 0.59 + (double) blue * 0.11)));


                if (j < height / 2 & i < width / 4)
                    block[0] = block[0] + gray;

                if (j >= height / 2 & i < width / 4)
                    block[1] = block[1] + gray;

                if (j < height / 2 & i >= width / 4 & i < width / 2)
                    block[2] = block[2] + gray;

                if (j >= height / 2 & i >= width / 4 & i < width / 2)
                    block[3] = block[3] + gray;

                if (j < height / 2 & i >= width / 2 & i < (width * 3 / 4))
                    block[4] = block[4] + gray;

                if (j >= height / 2 & i >= width / 2 & i < (width * 3 / 4))
                    block[5] = block[5] + gray;

                if (j < height / 2 & i >= (width * 3 / 4))
                    block[6] = block[6] + gray;

                if (j >= height / 2 & i >= (width * 3 / 4))
                    block[7] = block[7] + gray;


            }
        }

        block[8] = block[0] + block[1];
        block[9] = block[2] + block[3];
        block[10] = block[4] + block[5];
        block[11] = block[6] + block[7];
        block[12] = block[0] + block[4];
        block[13] = block[1] + block[5];
        block[14] = block[2] + block[6];
        block[15] = block[3] + block[7];
        block[16] = block[8] + block[9] + block[10] + block[11];
        for (int k = 0; k <= 16; k++) {
            block[k] = block[k] / width / height / 256;//像素密度
            //    System.out.println(block[k]);
        }
        return block;

    }

    private double Sigmoid(double a) {
        double b = 1 / (1 + Math.pow(Math.E, -a));
        return b;
    }


    private double[] fout(double[] block, double[][] net1, double[] in, double[] out, double[][] net2) {//a是block[17]，net是参数，c是隐藏层9个输出,out是输出的4个
        for (int i = 0; i <= 8; i++)
            for (int k = 0; k <= 16; k++) {
                in[i] = Sigmoid(block[k] * net1[i][k]) + in[i];
                System.out.println("fdsa" + in[i]);
            }

        for (int i = 0; i <= 3; i++)
            for (int k = 0; k <= 8; k++)
                out[i] = Sigmoid(in[k] * net2[i][k]) + out[i];
        return out;
    }

    private double[] fin(double[] a, double[][] net1, double[] in, double[] out, double[][] net2) {//a是block[17]，net是参数，c是隐藏层9个输出,d是输出的4个
        for (int i = 0; i <= 8; i++)
            for (int k = 0; k <= 16; k++)
                in[i] = Sigmoid(a[k] * net1[i][k]) + in[i];
        return in;

    }

    private double[][] fnet2(int[] Dout, double[] out, double[][] net2, double[] in) {
        //Dout是期望输出，out输出，从fout算出来,net2是隐层到输出的权值,in是隐层计算值，从fin算出来。
        for (int i = 0; i <= 3; i++)
            for (int k = 0; k <= 8; k++) {
                net2[i][k] = net2[i][k] + 0.01 * out[i] * (1 - out[i]) * (Dout[i] - out[i]) * in[k];
            }
        return net2;
    }

    private double[][] fnet1(int[] Dout, double[] in, double[][] net1, double[] block, double[] out, double net2[][]) {
        //Dout是期望输出，out输出，从fin算出来,net2是隐层到输出的权值，in从fin是隐层,out输出，从fout算出来

        double sum[] = new double[9];
        for (int j = 0; j <= 8; j++)
            for (int i = 0; i <= 3; i++)
                sum[j] = (sum[j] + out[i] * (1 - out[i]) * (Dout[i] - out[i]) * net2[i][j]);


        for (int i = 0; i <= 8; i++)
            for (int k = 0; k <= 16; k++) {
                net1[i][k] = net1[i][k] + 0.01 * (in[i] * (1 - in[i]) * (sum[i])) * block[k];
            }
        return net1;
    }


    private double[][] initnet(double[][] net) {
        for (int i = 0, len = net.length; i != len; i++)
            for (int j = 0, len2 = net[i].length; j != len2; j++)
                net[i][j] = 0.3;
        return net;

    }

}