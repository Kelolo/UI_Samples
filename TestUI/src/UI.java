import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.BoxLayout;

public class UI {

	private JFrame frame;

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
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Testing Demo");
//		frame.setSize(1000, 20000);
		frame.setBounds(100, 100, 1062, 622);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JScrollPane dropListSP = new JScrollPane();
		JScrollPane consoleSP = new JScrollPane();
		
		
		JTextArea dropTA = new JTextArea(20, 20);
		dropTA.setBackground(Color.LIGHT_GRAY);
		dropTA.setText("DropListTA");
		dropTA.setEditable(false);
//		dropListSP.add(dropTA);
		dropListSP.getViewport().setView(dropTA);
		panel.add(dropListSP);
		
//		pane.getViewport ().setView ( myComponent );
		
		JTextArea consoleTA = new JTextArea(50, 50);
		consoleTA.setForeground(Color.ORANGE);
		consoleTA.setBackground(Color.DARK_GRAY);
		consoleTA.setText("ConsoleTA");
		consoleTA.setEditable(false); 
		consoleSP.getViewport().setView(consoleTA);
//		consoleSP.add(consoleTA);
		panel.add(consoleSP);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton mainBtn = new JButton("Run");
		panel_1.add(mainBtn);
		
		JButton ruleBtn = new JButton("Helps");
		panel_1.add(ruleBtn);
		
		mainBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				consoleTA.append("Btn Clicked\n");
				
			}
		});
		
		
		dropTA.setDropTarget(new DropTarget() {
		    public synchronized void drop(DropTargetDropEvent evt) {
		        try {
		            evt.acceptDrop(DnDConstants.ACTION_COPY);
		            List<File> droppedFiles = (List<File>)
		                evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
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
				JFrame frameRule = new JFrame();
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
//				ruleTA.setText("RuleTA");
				ruleTA.setEditable(false); 
				rulePanel.getViewport().setView(ruleTA);
				
			}
		});
		
	}

}
