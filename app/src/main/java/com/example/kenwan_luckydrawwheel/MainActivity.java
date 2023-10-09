package com.example.kenwan_luckydrawwheel;

import static com.example.kenwan_luckydrawwheel.LuckDrawWheelView.*;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.kenwan_luckydrawwheel.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LuckDrawWheelView luckWheel;
    private ActivityMainBinding binding;
    private int currentSector = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF0000"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("KenWan's Lucky Draw");

        luckWheel = binding.luckWheel;
        Button spinButton = binding.spinButton;

        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomSector = 0;
                while(randomSector == 0) {
                    randomSector = new Random().nextInt(NUM_SECTORS);
                }
                spinWheel(randomSector);

                currentSector = randomSector;
                binding.resultTextView.setText("You won " + currentSector);
            }
        });


    }

    private void spinWheel(final int selectedSector) {
        //luckWheel.spinWheelToSector(selectedSector);
        luckWheel.spinWheelToSector(selectedSector, currentSector);

        // Display the result after a delay (simulating the spinning animation)
        luckWheel.postDelayed(new Runnable() {
            @Override
            public void run() {
                showResult(selectedSector);
            }
        }, 1000); // 3 seconds delay, adjust as needed
    }

    private void showResult(int selectedSector) {
        // Display or handle the result as needed
        //Toast.makeText(this, "You won: " + SECTOR_LABELS[selectedSector], Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Congratulation");
        builder.setMessage("You won Item " + selectedSector + "\n" +
                "Your prize is ???");
        builder.show();
    }
}