import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.UIManager;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	//private MainForm mf;
	public  String userid;
	private ImageIcon img;
	private JLabel imgBox;
	private JTextArea textArea_1;
	private JTextArea textArea;
	public Client c;
	private ImageIcon bonobono;
	
	private JLabel bonoimg;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new LoginFrame();
	}


	
	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		//프레임 생성 및 설정
		JFrame frame=new JFrame();
		frame.setResizable(false);
		frame.setTitle("로그인 창");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 559, 599);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		img=new ImageIcon("C:\\Eclipse-Workspace\\chat.jpg");
		imgBox=new JLabel(img);
		
		imgBox.setBounds(166, 26, 259, 201);
		contentPane.add(imgBox);
		
		bonobono=new ImageIcon("C:\\Users\\user\\Desktop\\Projects\\640ff711c2ff4f80e5dc1620b2bcdffef43ad912ad8dd55b04db6a64cddaf76d.gif");
		bonoimg=new JLabel(bonobono);
		
		bonoimg.setBounds(12, 72, 218, 282);
		
		contentPane.add(bonoimg);
		JButton btnNewButton = new JButton("ID \uC785\uB825");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(54, 364, 139, 33);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uBE44\uBC00\uBC88\uD638 \uC785\uB825");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(54, 431, 139, 33);
		contentPane.add(btnNewButton_1);
		
		
		//아이디 textarea 
		JTextArea textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//TAB키 누르면 textarea이동
				if(e.getKeyCode() == e.VK_TAB) 
				{
					textArea.transferFocus();
					

					e.consume();
				}
				
				
			}
			
		});
		
		
		textArea.setBackground(SystemColor.menu);
		textArea.setBounds(205, 369, 259, 33);
		contentPane.add(textArea);
		
		textArea_1 = new JTextArea();
		textArea_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				userid=textArea.getText().trim();			
				JOptionPane.showMessageDialog(null,"로그인 확인 완료");
				
				c=new Client(userid);

				JOptionPane.showMessageDialog(null,userid+"님 환영합니다.");
				
				frame.hide();
				}
				
			}
		});
		textArea_1.setBackground(SystemColor.menu);
		textArea_1.setBounds(205, 436, 259, 33);
		contentPane.add(textArea_1);
		
	
		
		frame.setVisible(true);
	}

	
}
