package engine.core;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;

public class AudioManager {
    private static AudioManager ourInstance = new AudioManager();

    private HashMap<String, AudioClip> loadedAudios = new HashMap<>();

    public static AudioManager getInstance() {
        return ourInstance;
    }

    private AudioManager() {

    }

    public AudioClip loadAudio(String path) {
        if (loadedAudios.get(path) != null) {
            return loadedAudios.get(path);
        }
        URL audioUrl = getClass().getResource(path);
        return Applet.newAudioClip(audioUrl);
    }
}
