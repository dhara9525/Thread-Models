package Team_Model;
/*
 * This class is used to create 4 small frames. The dimensions are provided by the calling class
 */
import javax.swing.JInternalFrame;

public class InternalFrames extends JInternalFrame {
    static int openFrameCount = 0;

    public InternalFrames(int a,int b,int c,int d) {
        super("Thread " + (++openFrameCount), 
              true, //re-sizable
              true, //closable
              true, //maximizable
              true);//iconifiable

        setBounds(a,b,c,d);
    }
}