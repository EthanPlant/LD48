package com.exedo.ld.world.chunk.gen;

import java.util.Random;

public class SimplexNoise {
    // Gradient lookup table
    private int[][] grad3 = {
            {1,1,0},{-1,1,0},{1,-1,0},{-1,-1,0},
            {1,0,1},{-1,0,1},{1,0,-1},{-1,0,-1},
            {0,1,1},{0,-1,1},{0,1,-1},{0,-1,-1}};

    // Permutation table - Just a random jumble of all numbers 0-255
    // Borrowed from https://github.com/SRombauts/SimplexNoise/blob/master/src/SimplexNoise.cpp
    private int[] p = {151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,
            23,190,6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,88,237,149,56,87,174,20,125,
            136,171,168, 68,175,74,165,71,134,139,48,27,166,77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,
            55,46,245,40,244,102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196, 135,130,116,
            188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,5,202,38,147,118,126,255,82,85,212,207,206,
            59,227,47,16,58,17,182,189,28,42,223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,
            9,129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,251,34,242,193,238,210,144,12,
            191,179,162,241, 81,51,145,235,249,14,239,107,49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,
            127, 4,150,254,138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180};

    // To remove the need for index wrapping, float the permutation table length
    private int[] perm = new int[512];

    public SimplexNoise() {
        for (int i = 0; i < 512; i++) {
            perm[i] = p[i & 255];
        }
    }

    // This is significantly faster than using the built in floor function
    private static int floor(float x) {
        return x > 0 ? (int) x : (int) x - 1;
    }

    // Calculates dot product of a point and a gradient
    private static float dot(int g[], float x, float y) {
        return g[0] * x + g[1] * y;
    }

    // Generate noise at a given position
    public float generate(float x, float y) {
        float n0, n1, n2; // Noise contributions from the corners

        // Skewing factor
        final float F2 = 0.5f * ((float)Math.sqrt(3.0) - 1.0f);
        final float G2 = (3.0f - (float)Math.sqrt(3.0)) / 6.0f;

        // Skew the input space
        final float s = (x + y) * F2;
        final int i = floor(x + s);
        final int j = floor(y + s);

        // Unskew the cell origin
        final float t = (i + j) * G2;
        final float X0 = i - t;
        final float Y0 = j-t;
        final float x0 = x - X0;
        final float y0 = x - Y0;

        // Determine which simplex we're in
        int i1, j1;
        if (x0 > y0) {
            i1 = 1;
            j1 = 0;
        } else {
            i1 = 0;
            j1 = 1;
        }

        // Yeah I dunno from here it just works
        // Perlin was too big brain for me
        float x1 = x0 - i1 + G2;
        float y1 = y0 - j1 + G2;
        float x2 = x0 - 1.0f + 2.0f * G2;
        float y2 = y0 - 1.0F + 2.0f * G2;

        int ii = i & 255;
        int jj = j & 255;
        int gi0 = perm[ii + perm[jj]] % 12;
        int gi1 = perm[ii + i1 + perm[jj + j1]] % 12;
        int gi2 = perm[ii + 1 + perm[jj + 1]] % 12;

        float t0 = 0.5f - x0 * x0 - y0 * y0;
        if (t0 < 0)
            n0 = 0.0f;
        else {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0);
        }

        float t1 = 0.5f - x1 * x1 - y1 * y1;
        if (t1 < 0)
            n1 = 0.0f;
        else {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
        }

        float t2 = 0.5f - x2 * x2 - y2 * y2;
        if (t2 < 0)
            n2 = 0.0f;
        else {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
        }

        return 70.0f * (n0 + n1 + n2);
    }

    // Generate simplex noise with octaves
    public float generate(float x, float y, int octaves, float roughness, float scale) {
        float total = 0;
        float frequency = scale;
        float amplitude = 1;

        // Have to keep track of largest amplitude so we can normalize later
        float maxAmplitude = 0;

        for (int i = 0; i < octaves; i++) {
            total += generate(x * frequency, y * frequency) * amplitude;

            frequency *= 2;
            maxAmplitude += amplitude;
            amplitude *= roughness;
        }

        return total / maxAmplitude;
    }

    // Randomize the p array based off a seed
    public void genGrad(long seed) {
        Random r = new Random(seed);
        for (int i = 0; i < 255; i++)
            p[i] = i;
        for (int i = 0; i < 255; i++) {
            int j = r.nextInt(255);
            int swap = p[i];
            p[i] = p[j];
            p[j] = swap;
        }
        for (int i = 0; i < 512; i++) {
            perm[i] = p[i & 255];
        }
    }
}
