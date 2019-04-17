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
	private JButton btnNewButton=new JButton("서버 실행");
	private JButton btnNewButton_1 = new JButton("서버 중지");
	private JTextArea textArea = new JTextArea();
	
	
	//Network Resource
	private ServerSocket server_socket;
	private Socket socket;
	
	//other..
	public static Vector user_vc=new Vector();
	
	

	public static final int port_num=50000;		//포트 번호 상수화
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
	
	//생성자 함수
	public Server() {
		//this.init(); //Frame 생성하는 메서드 호출
		
		Server_Start();	//서버 실행 메서드 호출
	}

	
	//서버를 시작하는 메서드 
	private void Server_Start() {
			
			try {		
				server_socket=new ServerSocket(port_num);	//서버 소켓 생성
				
				if(server_socket !=null)	//정상적으로 포트가 열렸으면
				{
					Connection();
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		
		
			
		}
		
		
		
		
		//소켓 생성 및 연결하는 메서드
		private void Connection() 
		{
			
			//하나의 스레드는 한가지 일만 처리가능
			Thread t=new Thread(new Runnable()
			{

				@Override
				//Runnable 인터페이스 클래스의 추상 메서드 재정의
				//Thread 관련 함수 정리
				//run() -> 쓰레드 스케줄러가 실행되면 실제 실행될 작업코드,start()메서드를 호출당한 쓰레드는 ready상태로 있다가 스케줄러에 의해서 실행상태로 변경됨
				//start() ->이 메서드에 의해서,생성된 쓰레드는 준비 상태로 들어가서 Ready-Que에서 스케줄러 기다림 즉,run() 함수를 실행한다.
				//stop() -> 쓰레드 스케줄러에서 제거하여 실행 중단
				//wait() -> 이 함수를 호출한 쓰레드는 wait set에 저장 후 동작 정지
				//...등 스레드 관련 메서드는 많으니 구글에서 찾아보도록
				public void run() 
				{
					
					while(true)
					{
						try 
						{
							textArea.append("사용자 접속 대기중 ..\n");
							socket=server_socket.accept();		//소켓 리스닝 상태 
																//클라이언트 접속시 accept후 소켓 반환
							textArea.append("사용자 접속 !!!\n");
							
							
							//객체 생성 
							ServerMultiThread smt=new ServerMultiThread(socket);
							//객체 스레드 실행
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
	
	//Frame 생성 메서드
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
		
		//서버 실행 버튼 핸들러
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
	
	


