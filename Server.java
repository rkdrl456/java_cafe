import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame {

	
	//GUI Resource 
	private JPanel contentPane;
	private Container cPane;
	private JButton btnNewButton=new JButton("���� ����");
	private JButton btnNewButton_1 = new JButton("���� ����");
	private JTextArea textArea = new JTextArea();
	
	
	//Network Resource
	private ServerSocket server_socket;
	private Socket socket;
	
	//other..
	public static Vector user_vc=new Vector();
	
	

	public static final int port_num=50000;		//��Ʈ ��ȣ ���ȭ
	public static String server_ip="127.0.0.1";
	private int user_num=1;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//������ �Լ�
	public Server() {
		//this.init(); //Frame �����ϴ� �޼��� ȣ��
		
		Server_Start();	//���� ���� �޼��� ȣ��
	}

	
	//������ �����ϴ� �޼��� 
	private void Server_Start() {
			
			try {		
				server_socket=new ServerSocket(port_num);	//���� ���� ����
				
				if(server_socket !=null)	//���������� ��Ʈ�� ��������
				{
					Connection();
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		
		
			
		}
		
		
		
		
		//���� ���� �� �����ϴ� �޼���
		private void Connection() 
		{
			
			//�ϳ��� ������� �Ѱ��� �ϸ� ó������
			Thread t=new Thread(new Runnable()
			{

				@Override
				//Runnable �������̽� Ŭ������ �߻� �޼��� ������
				//Thread ���� �Լ� ����
				//run() -> ������ �����ٷ��� ����Ǹ� ���� ����� �۾��ڵ�,start()�޼��带 ȣ����� ������� ready���·� �ִٰ� �����ٷ��� ���ؼ� ������·� �����
				//start() ->�� �޼��忡 ���ؼ�,������ ������� �غ� ���·� ���� Ready-Que���� �����ٷ� ��ٸ� ��,run() �Լ��� �����Ѵ�.
				//stop() -> ������ �����ٷ����� �����Ͽ� ���� �ߴ�
				//wait() -> �� �Լ��� ȣ���� ������� wait set�� ���� �� ���� ����
				//...�� ������ ���� �޼���� ������ ���ۿ��� ã�ƺ�����
				public void run() 
				{
					
					while(true)
					{
						try 
						{
							textArea.append("����� ���� ����� ..\n");
							socket=server_socket.accept();		//���� ������ ���� 
																//Ŭ���̾�Ʈ ���ӽ� accept�� ���� ��ȯ
							textArea.append("����� ���� !!!\n");
							
							
							//��ü ���� 
							ServerMultiThread smt=new ServerMultiThread(socket);
							//��ü ������ ����
							smt.start();
						
						} 
						catch (IOException e)
						{
							e.printStackTrace();
						}	
					
					}//end of while..
				}
			
				
			});
			
			t.start();			
		}
	
	//Frame ���� �޼���
	private void init() {
		
		
		cPane=new Container();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 567);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		//���� ���� ��ư �ڵ鷯
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBounds(81, 429, 134, 38);
		contentPane.add(btnNewButton);
		
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setForeground(Color.RED);
		btnNewButton_1.setBounds(295, 429, 123, 38);
		contentPane.add(btnNewButton_1);
		textArea.setEditable(false);
		textArea.setBounds(81, 31, 334, 326);
		
		contentPane.add(textArea);
		
		
	}

		
}
	
	


