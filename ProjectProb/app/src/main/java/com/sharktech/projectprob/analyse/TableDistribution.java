package com.sharktech.projectprob.analyse;

import android.util.Log;
import android.widget.CheckBox;

import java.util.Locale;

class TableDistribution {

    private static float[][] NORMAL_TABLE = new float[][]{
            new float[]{0.000000f, 0.003989f, 0.007978f, 0.011967f, 0.015953f, 0.019939f, 0.023922f, 0.027903f, 0.031881f, 0.035856f},
            new float[]{0.039828f, 0.043795f, 0.047758f, 0.051717f, 0.055670f, 0.059618f, 0.063559f, 0.067495f, 0.071424f, 0.075345f},
            new float[]{0.079260f, 0.083166f, 0.087064f, 0.090954f, 0.094835f, 0.098706f, 0.102568f, 0.106420f, 0.110261f, 0.114092f},
            new float[]{0.117911f, 0.121719f, 0.125516f, 0.129300f, 0.133072f, 0.136831f, 0.140576f, 0.144309f, 0.148027f, 0.151732f},
            new float[]{0.155422f, 0.159097f, 0.162757f, 0.166402f, 0.170031f, 0.173645f, 0.177242f, 0.180822f, 0.184386f, 0.187933f},
            new float[]{0.191462f, 0.194974f, 0.198468f, 0.201944f, 0.205402f, 0.208840f, 0.212260f, 0.215661f, 0.219043f, 0.222405f},
            new float[]{0.225747f, 0.229069f, 0.232371f, 0.235653f, 0.238914f, 0.242154f, 0.245373f, 0.248571f, 0.251748f, 0.254903f},
            new float[]{0.258036f, 0.261148f, 0.264238f, 0.267305f, 0.270350f, 0.273373f, 0.276373f, 0.279350f, 0.282305f, 0.285236f},
            new float[]{0.288145f, 0.291030f, 0.293892f, 0.296731f, 0.299546f, 0.302338f, 0.305106f, 0.307850f, 0.310570f, 0.313267f},
            new float[]{0.315940f, 0.318589f, 0.321214f, 0.323814f, 0.326391f, 0.328944f, 0.331472f, 0.333977f, 0.336457f, 0.338913f},
            new float[]{0.341345f, 0.343752f, 0.346136f, 0.348495f, 0.350830f, 0.353141f, 0.355428f, 0.357690f, 0.359929f, 0.362143f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
            new float[]{0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f, 0.f},
    };

    static void normalDistribution(float value) {
        //((i * 10) + j) / 100f
        for (int i = 0; i < NORMAL_TABLE.length; i++) {
            for (int j = 0; j < NORMAL_TABLE[i].length; j++) {

                float result = Float.valueOf(String.format(Locale.getDefault(), "%.3f", ((i * 10) + j) / 100f));
                String msg = String.format(Locale.getDefault(), "i:j -> %d:%d = %f. I = %f", i, j, NORMAL_TABLE[i][j], result);
                Log.e("Table", msg);

            }
        }
    }
}
