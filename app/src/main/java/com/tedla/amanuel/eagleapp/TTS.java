package com.tedla.amanuel.eagleapp;

/**
 * Created by dVentus-hq on 6/23/2017.
 */
public class TTS {
    public static android.speech.tts.TextToSpeech myTTS;
    public static void speakWords(String speech) {
        myTTS.speak(speech, android.speech.tts.TextToSpeech.QUEUE_ADD, null);
    }
}


