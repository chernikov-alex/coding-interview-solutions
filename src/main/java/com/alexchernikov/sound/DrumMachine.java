package com.alexchernikov.sound;

import javax.sound.sampled.*;
import java.io.*;

public class DrumMachine {
    static final int SAMPLE_RATE = 44100;
    static final int BPM = 140;
    static final int STEPS = 64;
    static double[] melody = {
            261.63, 0, 329.63, 0,
            392.00, 0, 329.63, 0
    };

    public static void main(String[] args) throws Exception {

        double stepDuration = 60.0 / BPM / 2; // 8th notes
        int totalSamples = (int)(SAMPLE_RATE * stepDuration * STEPS);

        byte[] buffer = new byte[totalSamples * 2]; // 16-bit mono

        for (int i = 0; i < totalSamples; i++) {
            double time = i / (double) SAMPLE_RATE;

            int step = (int)(time / stepDuration);
            double tInStep = time % stepDuration;

            double sample = 0;
            int patternStep = step % 8;

            double freq = melody[patternStep];
            if (freq > 0) {
                sample += synth(tInStep, freq);
                sample += 0.5 * synth(tInStep, freq * 1.25); // harmony
            }

            // Kick
            if (patternStep == 0 || patternStep == 4) {
                sample += kick(tInStep);
            }

            // Snare
            if (patternStep == 2 || patternStep == 6) {
                sample += snare(tInStep);
            }

            // Hi-hat
            sample += hihat(tInStep);

            // Normalize
            sample *= 0.3;

            sample *= 0.2;

            short pcm = (short)(sample * Short.MAX_VALUE);

            buffer[i * 2] = (byte)(pcm & 0xff);
            buffer[i * 2 + 1] = (byte)((pcm >> 8) & 0xff);
        }

        saveWav(buffer, "beat4.wav");
    }

    static double kick(double t) {
        double freq = 150 * Math.exp(-t * 8);
        return Math.sin(2 * Math.PI * freq * t) * Math.exp(-t * 6);
    }

    static double snare(double t) {
        double noise = (Math.random() * 2 - 1);
        return noise * Math.exp(-t * 12);
    }

    static double hihat(double t) {
        double noise = (Math.random() * 2 - 1);
        return noise * Math.exp(-t * 40);
    }

    static double synth(double t, double freq) {
        double envelope = Math.exp(-t * 3); // fade out
        return Math.sin(2 * Math.PI * freq * t) * envelope;
    }

    static void saveWav(byte[] audioData, String filename) throws Exception {
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
        AudioInputStream ais = new AudioInputStream(bais, format, audioData.length / 2);

        File file = new File(filename);
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
    }
}
