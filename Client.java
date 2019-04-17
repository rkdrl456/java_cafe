import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.dnd.DragGestureEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class Client extends JFrame {
	

	//GUI Resources
	private JPanel contentPane= new JPanel();
	private JPanel contentPane_1;
	private JTextArea textArea = new JTextArea();
	private JTextArea textArea_1 = new JTextArea();
	private JTextArea textArea_2 = new JTextArea();
	
	private JButton btnNewButton = new JButton("����");
	private JButton btnNewButton_1 = new JButton("DB \uC120 \uADF8\uB798\uD504");
	private JButton btnNewButton_2 = new JButton("DB \uB9C9\uB300 \uADF8\uB798\uD504");
	private JButton btnNewButton_4 = new JButton("\uC804\uCCB4 \uCC44\uD305");
	private JButton btnNewButton_3 = new JButton("��ü ������");
	private JList list = new JList();
	
	private JScrollPane scrollPane;
	private CreateChart cc=new CreateChart();
	
	//Network Resource
	private Socket socket;
	private	InputStream is;
	private OutputStream os;
	private DataInputStream datais;
	private DataOutputStream dataos;
	
	
	//�������� Resource
	Vector user_list=new Vector();
	Vector room_list=new Vector();
	private String userName;
	private StringTokenizer st;
	private final JButton btnNewButton_6 = new JButton("DB \uD30C\uC774 \uCC28\uD2B8");
	private final JButton btnNewButton_7 = new JButton("\uC6F9 \uC0AC\uC774\uD2B8 \uC774\uB3D9");
	
	
	
	
	
	//������ ��Ʈ�� �����ϴ� �޼���
	private void Connect_to_server()
	{
		//stream ����
		//server ���ϰ� ����� �ϱ����� ����
		try {
			is=socket.getInputStream();
			datais=new DataInputStream(is);
			
			os=socket.getOutputStream();
			dataos=new DataOutputStream(os);
			
			
		
		} 
		//���� ó�� ��ƾ
		catch (IOException e) {	
			e.printStackTrace();
		}
		
		
		System.out.println("username : "+userName);
		
		//ó�� ���ӽ� username���� 
		send_message("Newuser/"+userName);
		
		
		//������ ����Ʈ ����
		user_list.add(userName);
		//Jlist�� ����Ʈ ����
		list.setListData(user_list);
		
		
		
		//thread ����
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() 
			{
				
				while(true)
				{
					//������ ���� ���� �޽��� �޴´�
					receive_message();
				}
							
			}
			
			
		});
		t.start();
	}
	
	

	//�������� �޽����� ������ �޼���
	private void send_message(String s_msg)
	{
		try {
			dataos.writeUTF(s_msg);
		}
		catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	//���� �޴� �Լ�
	//from_user =������ ���� �̸�
	private void receive_note(String from_user,StringTokenizer st)
	{
		
		//�޴� �޽���
		String r_msg=st.nextToken();
		
		JOptionPane.showMessageDialog(null, from_user+"�� ���� ���� �޽��� : "+r_msg);
	}
	
	//�����κ��� �޽����� �޴� �޼���
	private void receive_message()
	{
		try {
			String r_msg=datais.readUTF();
			st=new StringTokenizer(r_msg, "/");
			
			System.out.println("������ ���� ���� �޽��� : "+r_msg);
			String type=st.nextToken();
			String msg=st.nextToken();
			
			System.out.println("��ū��  �޽��� : "+type);
			System.out.println("��ū��  �޽��� : "+msg);
			
			
			if(type.equals("Newuser"))
			{
				user_list.add(msg);
				list.setListData(user_list);
			}
			
			else if(type.equals("Note"))
			{
				receive_note(msg,st);
			}
		
			else{
				textArea.append(type+ " : " + msg+"\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//������ ���� ����ϴ� �޼���
	private void network()
	{
		
		try {
			socket=new Socket(Server.server_ip,Server.port_num);
			textArea_2.append("IP Ȯ�� : "+Server.server_ip+"\n");
			textArea_2.append("Port Ȯ�� : "+Server.port_num+"\n");
			
			
			//���������� ������ ����Ǿ��� ���
			if(socket != null) {
				textArea_2.append("���� ���� �ڵ� ���� �Ϸ� ... \n");
				textArea_2.append("���� �ڵ� ���� �Ϸ� ... \n");
				this.Connect_to_server();		
			}
			else
				textArea_2.append("���� ���� ����");
			
		} 
		
		catch (UnknownHostException e) {
			
			textArea_2.append("���� �ڵ� ���� ���� !!!! \n");
			textArea_2.append("���� ���¸� Ȯ�����ּ��� !! \n");
			e.printStackTrace();
		} 
		catch (IOException e) {
			textArea_2.append("���� �ڵ� ���� ���� !!!! \n");
			textArea_2.append("���� ���¸� Ȯ�����ּ��� !! \n");
			e.printStackTrace();
		}
		
	}
	
	//main thread
	public static void main(String[] args) {
		
	}

	//������ �Լ�
	public Client(String name) {
		this.userName=name;
		this.init();	//Frame�� �׷��ִ� �޼��� ȣ��	
		this.network();	//������ ���� ����ϴ� �޼��� ȣ��	
			
	}
	
	
	

	//ȭ���� �����ϴ� �޼ҵ�
	private void init()
	{
		JFrame frame=new JFrame();
		
		frame.setResizable(false);
		frame.setTitle("ī�� ���� ���α׷�");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 906, 639);
		contentPane_1 = new JPanel();
		contentPane_1.setBackground(SystemColor.activeCaption);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane_1);
		contentPane_1.setLayout(null);
		textArea_2.setBounds(29, 322, 359, 264);
		contentPane_1.add(textArea_2);
		textArea_2.setBackground(Color.WHITE);
		textArea_2.setEditable(false);
		
		
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		
		
		btnNewButton.setBounds(790, 543, 69, 43);
		contentPane_1.add(btnNewButton);

		
		
		//�� ��Ʈ ��� 
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.drawLineChart();
				
			}
		});
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		
		
		btnNewButton_1.setForeground(Color.BLUE);
		btnNewButton_1.setBounds(29, 165, 170, 36);
		contentPane_1.add(btnNewButton_1);
		
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		btnNewButton_2.setForeground(Color.MAGENTA);
		
		//���� �׷��� ��� 
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.drawBarChart();
				
				
			}
		});
		btnNewButton_2.setBounds(29, 103, 170, 36);
		contentPane_1.add(btnNewButton_2);
		
		//���� ��ư �ڵ鷯
		btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//getText() �޼���� �ش� jTextArea ��ü�� ��� ���ڿ��� �������� �޼����̴�.
					//trim()�޼���� �����̽��� ��ĭ�� ������ ������ �������ִ� �޼����̴�
					String str=textArea_1.getText().trim();
					textArea.append(str);	
					textArea.append("\n");
					textArea_1.setText(null);
							
					//�������� ä�� ����� �ش� ���� �̸��� ������
					send_message(userName+"/"+str);
					
				}
			});
				
		//����Ű �ڵ鷯 
		textArea_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					//getText() �޼���� �ش� jTextArea ��ü�� ��� ���ڿ��� �������� �޼����̴�.
					//trim()�޼���� �����̽��� ��ĭ�� ������ ������ �������ִ� �޼����̴�
					String str=textArea_1.getText().trim();
					textArea.append(userName +" : " +str);	
					textArea.append("\n");
					textArea_1.setText(null);
					
					
					send_message(userName+"/"+str);
					
					
				}
					
			}
			
		});
		textArea_1.setBounds(424, 550, 354, 36);
		contentPane_1.add(textArea_1);
		
	
		list.setBounds(228, 68, 160, 194);
		contentPane_1.add(list);
		btnNewButton_4.setForeground(new Color(128, 0, 128));
		
		
		btnNewButton_4.addActionListener(new ActionListener() {
			
			
			//�� ������ ����
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("http://192.168.1.58:8080/sb_admin_jsp_3.do"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_4.setBounds(424, 33, 435, 30);
		
		contentPane_1.add(btnNewButton_4);
		
		scrollPane=new JScrollPane();
		
		scrollPane.setBounds(424, 64, 435, 447);
		
		contentPane_1.add(scrollPane);
		
		scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(textArea);
		
		
		textArea.setEditable(false);
		
		//���� ������ �ڵ鷯
		JButton btnNewButton_5 = new JButton("\uCABD\uC9C0 \uBCF4\uB0B4\uAE30");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�޴� ���� �̸�
				String user=(String)list.getSelectedValue();
				
				//���� �޽���
				String note=JOptionPane.showInputDialog("���� �ż���");
				
				if(note != null)
				{
					//Note+������ ���� �̸�+�޴� ���� �̸� +�޽���
					send_message("Note/"+userName+"/"+user+"/"+note);
				}
				
				
				
				System.out.println("�޴� ��� : "+user+"���� ���� : "+note);
			}
		});
		btnNewButton_5.setForeground(Color.BLUE);
		btnNewButton_5.setBounds(228, 272, 160, 29);
		contentPane_1.add(btnNewButton_5);
		btnNewButton_3.setForeground(new Color(0, 128, 0));
		btnNewButton_3.setBounds(228, 37, 160, 23);
		
		contentPane_1.add(btnNewButton_3);
		
		//���� ��Ʈ �׸���
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cc.drawPieChart();
			}
		});
		btnNewButton_6.setForeground(new Color(153, 50, 204));
		btnNewButton_6.setBounds(29, 37, 170, 36);
		
		contentPane_1.add(btnNewButton_6);
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("http://192.168.1.58:18080/chart/chartMainPage.do?member_id=������"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton_7.setBounds(29, 233, 170, 36);
		
		contentPane_1.add(btnNewButton_7);
		
		frame.setVisible(true);
	}
}
