package sudoku.dialog;
import java.io.*;
import javax.sound.sampled.*;

public class SoundEffect {

	private String fileName;

	public SoundEffect(String wavfile){
		fileName = wavfile;
	}

	@Override
	public void run(){
		File soundFile = new File(fileName);
		if(!soundFile.exists()){
			System.err.println("Wave file not found");
		}

		AudioInputStream audioInputStream = null;
		try{
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch(UnsupportedAudioFileException e){
			e.printStackTrace();
			return;
		}
		catch(IOException e){
			e.printStackTrace();
			return;
		}

		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try{
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		}
		catch(LineUnavailableException e){
			e.printStackTrace();
			return;
		}
		catch(Exception e){
			e.printStackTrace();
			return;
		}

		if(auline.isControlSupported(FloatControl.Type.PAN)){
			FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
		}

		auline.start();
	}
}