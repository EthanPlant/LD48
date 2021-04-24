package com.exedo.ld;

import java.util.Random;

public class Utils {
    // Determine if a collection of world pixels are located within the current camera view
    public static boolean isOnScreen(float centreX, float centreY, float x, float y, float spriteSize) {
        float startX = centreX - (LudumDare.V_WIDTH / 2);
        float startY = centreY - (LudumDare.V_HEIGHT / 2);

        return (x >= startX - (spriteSize * 2) &&
                y >= startY - (spriteSize * 2) &&
                x <= startX + LudumDare.V_WIDTH + spriteSize &&
                y <= startY + LudumDare.V_HEIGHT + spriteSize);
    }

    public static int getRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
