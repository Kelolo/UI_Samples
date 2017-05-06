import javax.swing.JButton;
import javax.swing.JTextArea;

public class MainThread extends Thread {
	private volatile boolean stop = false;
	private int counter = 0;
	JTextArea tv;
	JButton btn;
	
	public MainThread(JTextArea tv, JButton btn) {
		this.tv = tv;
		this.btn = btn;
	}
	
	public void run() {
		tv.setText("");
		while (!stop && counter < 100000000) {
			if (counter % 10000 == 0){
				tv.append(counter + "\n");
			}
			counter++;
//			System.out.println(counter++);
		}
		if (stop){
			System.out.println("Detected stop");
		}
		btn.setEnabled(false);
		
	}


	public void requestStop() {
		stop = true;
	}
}