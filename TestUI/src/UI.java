import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;

public class UI {

	private JFrame frame;
	private JFrame frameRule;
	Image image;
	MainThread mainTaskThread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		String url = this.getClass().getResource("").getPath() + "icon.png";
		
		try {
			image = ImageIO.read(new File(url));
			frame.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.setTitle("Testing Demo");
		frame.setBounds(100, 100, 1062, 622);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	if (mainTaskThread != null) {
		    		mainTaskThread.requestStop();
		    	}
		    	System.exit(0);
		    }
		});
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JScrollPane dropListSP = new JScrollPane();
		JScrollPane consoleSP = new JScrollPane();
		
		JTextArea dropTA = new JTextArea(20, 20);
		dropTA.setBackground(Color.LIGHT_GRAY);
		dropTA.setText("DropListTA\n\n");
		dropTA.setEditable(false);
		dropListSP.getViewport().setView(dropTA);
		panel.add(dropListSP);

		JTextArea consoleTA = new JTextArea(40, 40);
		
		// move textview to the line
		DefaultCaret caret = (DefaultCaret) consoleTA.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
//		TextAreaFIFO consoleTA = new TextAreaFIFO(1000);
		//redirect sysout
//		redirectOutput(consoleTA);
		consoleTA.setForeground(Color.ORANGE);
		consoleTA.setBackground(Color.DARK_GRAY);
		consoleTA.setText("ConsoleTA\n\n");
		consoleTA.setEditable(false); 
		consoleSP.getViewport().setView(consoleTA);
		
		panel.add(consoleSP);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton mainBtn = new JButton("Run");
		panel_1.add(mainBtn);
		
		JButton stopBtn = new JButton("Stops");
		stopBtn.setEnabled(false);
		panel_1.add(stopBtn);
		
		JButton ruleBtn = new JButton("Helps");
		panel_1.add(ruleBtn);
		
			
		mainBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				stopBtn.setEnabled(true);
				mainTaskThread = new MainThread(consoleTA, stopBtn);
				mainTaskThread.start();	
			}
		});
		
		
		stopBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				mainTaskThread.requestStop();
				// stop mainThread
				stopBtn.setEnabled(false);
				
			}
		});
		
		dropTA.setDropTarget(new DropTarget() {
		    public synchronized void drop(DropTargetDropEvent evt) {
		        try {
		            evt.acceptDrop(DnDConstants.ACTION_COPY);
		            @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>)
		                evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		            
		            //do thing to each file
		            for (File file : droppedFiles) {
		            	
		                dropTA.append(file.getAbsolutePath() + "\n");
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
		
		
		ruleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frameRule == null) {
					frameRule = new JFrame();
					frameRule.setIconImage(image);
					frameRule.setTitle("Helps");
					frameRule.setBounds(1162, 100, 500, 622);
					frameRule.setVisible(true);
					frameRule.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frameRule.getContentPane().setLayout(new BorderLayout(0, 0));
					JScrollPane rulePanel = new JScrollPane();
					frameRule.getContentPane().add(rulePanel, BorderLayout.CENTER);
					JTextArea ruleTA = new JTextArea();
					ruleTA.setForeground(Color.ORANGE);
					ruleTA.setBackground(Color.DARK_GRAY);
					ruleTA.setToolTipText("Imporntant Rules");
					ruleTA.setEditable(false); 
					ruleTA.append("Rule : test test test test test test\n" );
					ruleTA.append("Rule : test test test test test test\n" );
					ruleTA.append("Rule : test test test test test test\n" );
					ruleTA.append("Rule : test test test test test test\n" );
					ruleTA.append("Rule : test test test test test test\n" );
					ruleTA.append("Rule : test test test test test test\n" );
					rulePanel.getViewport().setView(ruleTA);
				} else {
					frameRule.dispose();
					frameRule = null;
				}
			}
		});
		
	}
	
	
	// redirect
	public void redirectOutput(JTextArea textArea) {
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
	}
	
	public class CustomOutputStream extends OutputStream {
	    private JTextArea textArea;

	    public CustomOutputStream(JTextArea textArea) {
	        this.textArea = textArea;
	    }
	    
	    @Override
	    public void write(byte[] buffer, int offset, int length) throws IOException
	    {
	        final String text = new String (buffer, offset, length);
	        SwingUtilities.invokeLater(new Runnable ()
	            {
	                @Override
	                public void run() 
	                {
	                	textArea.append(text);
	                }
	            });
	    }

		@Override
		public void write(int b) throws IOException {

			 write (new byte [] {(byte)b}, 0, 1);
			
		}

	}
}
