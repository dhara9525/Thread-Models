package Team_Model;
/*
 * This class takes in an input file of type .wav and plays music from that file.Sound library is used in this class.
 */
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class PlayMusic implements LineListener {
     
    boolean playCompleted;
    public String play(String sfile) {
        File file = new File(sfile);
 
        try {
        	//Takes in the audio file name as input of .wav format
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioStream.getFormat();
            
            //To add the media related functionality
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            
            //Audio data is loaded prior to playback instead of streaming
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            
            //OPen the stream and start playing music
            audioClip.open(audioStream);
            audioClip.start();
             
            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            audioClip.close();
            return("Audio played");
             
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
            return("The specified audio file is not supported.");
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            return("Audio line for playing back is unavailable.");
        } catch (IOException ex) {
            ex.printStackTrace();
            return("Error playing the audio file.");
        }
         
    }
     
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
          if (type == LineEvent.Type.STOP) {
        	//Playback completed
            playCompleted = true;
        }
 
    }
 
}
