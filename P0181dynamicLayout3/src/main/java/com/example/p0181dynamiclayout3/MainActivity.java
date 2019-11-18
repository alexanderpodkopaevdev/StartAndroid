package com.example.p0181dynamiclayout3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    Button btn2;
    SeekBar sbWeight;
    LinearLayout.LayoutParams layoutParams1;
    LinearLayout.LayoutParams layoutParams2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        sbWeight = findViewById(R.id.sbWeight);

        layoutParams1 = (LinearLayout.LayoutParams) btn1.getLayoutParams();
        layoutParams2 = (LinearLayout.LayoutParams) btn2.getLayoutParams();



        sbWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.v("tag","IN");
                int leftValue = progress;
                int rightValue = seekBar.getMax() - progress;

                layoutParams1.weight = leftValue;
                layoutParams2.weight = rightValue;
                btn1.requestLayout();
                btn2.setText(Integer.toString(rightValue));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
