package com.example.kenwan_luckydrawwheel;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LuckDrawWheelView extends View {

    public static final String[] SECTOR_LABELS = {
            //"Item 1", "Item 2", "Item 3", "Item 4",
            //"Item 5", "Item 6", "Item 7", "Item 8"
            "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "11", "12", "13", "14", "15", "16",
            "17", "18", "19", "20", "21", "22", "23", "24",
            //"25", "26", "27", "28", "29", "30", "31", "32"
    };
    public static final int NUM_SECTORS = SECTOR_LABELS.length;

    private Paint sectorPaint;
    private Paint textPaint;
    private int selectedSector = -1;
    private float rotationAngle = 0;
    private float sectorAngle = 360 / NUM_SECTORS;
    private float rotateWheel = 90 + sectorAngle / 2;

    public LuckDrawWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        sectorPaint = new Paint();
        sectorPaint.setStyle(Paint.Style.FILL);
        sectorPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(0xFFFFFFFF); // White color for text
        textPaint.setTextSize(30); // Adjust text size as needed
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2;

        for (int i = 0; i < NUM_SECTORS; i++) {
            float startAngle = i * sectorAngle - (rotateWheel);

            // Set a different color for each sector
            sectorPaint.setColor(getSectorColor(i));

            //rotationAngle = Math.abs(rotationAngle);

            // Draw the sector
            canvas.drawArc(0, 0, width, height, startAngle + rotationAngle, sectorAngle, true, sectorPaint);

            // Draw the sector label closer to the center of the sector
            drawTextOnArc(canvas, SECTOR_LABELS[i], radius * 0.8, startAngle + rotationAngle + sectorAngle / 2);

        }
    }

    private void drawTextOnArc(Canvas canvas, String text, double radius, float angle) {
        int xPos = getWidth() / 2;
        int yPos = getHeight() / 2;

        float textHeight = textPaint.descent() - textPaint.ascent();

        // Calculate the position closer to the center
        float x = xPos + (float) Math.cos(Math.toRadians(angle)) * (float) (radius);
        float y = yPos + (float) Math.sin(Math.toRadians(angle)) * (float) (radius);

        canvas.drawText(text, x, y - textHeight / 2, textPaint);
    }


    private int getSectorColor(int index) {
        // Define your colors or use a random color generator
        //int[] colors = {0xFFE57373, 0xFF81C784, 0xFF64B5F6, 0xFFFFD54F, 0xFF9575CD, 0xFFFF8A65, 0xFF4DB6AC, 0xFFFFCC80};
        //int[] colors = {Color.rgb(219, 90, 107), Color.rgb(205, 92, 92), Color.RED};
        //int[] colors = {Color.RED, Color.BLUE};
        int[] colors = {Color.rgb(255,127,127), Color.rgb(255,201,102)};

        return colors[index % colors.length];
    }

    public void spinWheelToSector(int targetSector, int currentSector) {
        int distanceSector = ( NUM_SECTORS - targetSector + currentSector ) % NUM_SECTORS;
        float targetRotation = distanceSector * (360f / NUM_SECTORS);
        float startDrawLocation = rotationAngle;

        // Calculate the duration based on the total number of cycles
        int duration = (int) (360 * 3);

        // Ensure that the rotation always increases (clockwise)
        //float startRotation = (rotationAngle + 360) % 360;
        float startRotation = rotationAngle;
        float finalRotation = targetRotation - startRotation;

        //ValueAnimator animator = ValueAnimator.ofFloat(startRotation, startRotation + duration, targetRotation);
        ValueAnimator animator = ValueAnimator.ofFloat(startRotation, finalRotation + duration);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //rotationAngle = (startDrawLocation + targetRotation) % 360;
                float test0 = (startDrawLocation + targetRotation) % 360;
                float test1 = (float) animation.getAnimatedValue() % 360;
                float test2 = startDrawLocation;
                float test3 = targetRotation;
                float test4 = finalRotation;
                float test5 = (float) animation.getAnimatedValue();
                float test6 = ((float) animation.getAnimatedValue() + (startRotation * 2)) % 360;
                rotationAngle = ((float) animation.getAnimatedValue() + (startRotation * 2));
                invalidate(); // Trigger a redraw
            }
        });
        animator.start();
    }
}
