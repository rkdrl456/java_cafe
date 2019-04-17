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
	
	private JButton btnNewButton = new JButton("전송");
	private JButton btnNewButton_1 = new JButton("DB \uC120 \uADF8\uB798\uD504");
	private JButton btnNewButton_2 = new JButton("DB \uB9C9\uB300 \uADF8\uB798\uD504");
	private JButton btnNewButton_4 = new JButton("\uC804\uCCB4 \uCC44\uD305");
	private JButton btnNewButton_3 = new JButton("전체 접속자");
	private JList list = new JList();
	
	private JScrollPane scrollPane;
	private CreateChart cc=new CreateChart();
	
	//Network Resource
	private Socket socket;
	private	InputStream is;
	private OutputStream os;
	private DataInputStream datais;
	private DataOutputStream dataos;
	
	
	//유저관련 Resource
	Vector user_list=new Vector();
	Vector room_list=new Vector();
	private String userName;
	private StringTokenizer st;
	private final JButton btnNewButton_6 = new JButton("DB \uD30C\uC774 \uCC28\uD2B8");
	private final JButton btnNewButton_7 = new JButton("\uC6F9 \uC0AC\uC774\uD2B8 \uC774\uB3D9");
	
	
	
	
	
	//서버와 스트림 연결하는 메서드
	private void Connect_to_server()
	{
		//stream 설정
		//server 소켓과 통신을 하기위한 과정
		try {
			is=socket.getInputStream();
			datais=new DataInputStream(is);
			
			os=socket.getOutputStream();
			dataos=new DataOutputStream(os);
			
			
		
		} 
		//예외 처리 루틴
		catch (IOException e) {	
			e.printStackTrace();
		}
		
		
		System.out.println("username : "+userName);
		
		//처음 접속시 username전송 
		send_message("Newuser/"+userName);
		
		
		//접속자 리스트 설정
		user_list.add(userName);
		//Jlist에 리스트 설정
		list.setListData(user_list);
		
		
		
		//thread 생성
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() 
			{
				
				while(true)
				{
					//서버로 부터 오는 메시지 받는다
					receive_message();
				}
							
			}
			
			
		});
		t.start();
	}
	
	

	//서버에게 메시지를 보내는 메서드
	private void send_message(String s_msg)
	{
		try {
			dataos.writeUTF(s_msg);
		}
		catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	//쪽지 받는 함수
	//from_user =보내는 유저 이름
	private void receive_note(String from_user,StringTokenizer st)
	{
		
		//받는 메시지
		String r_msg=st.nextToken();
		
		JOptionPane.showMessageDialog(null, from_user+"로 부터 받은 메시지 : "+r_msg);
	}
	
	//서버로부터 메시지를 받는 메서드
	private void receive_message()
	{
		try {
			String r_msg=datais.readUTF();
			st=new StringTokenizer(r_msg, "/");
			
			System.out.println("서버로 부터 받은 메시지 : "+r_msg);
			String type=st.nextToken();
			String msg=st.nextToken();
			
			System.out.println("토큰한  메시지 : "+type);
			System.out.println("토큰한  메시지 : "+msg);
			
			
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
	
	//서버에 소켓 통신하는 메서드
	private void network()
	{
		
		try {
			socket=new Socket(Server.server_ip,Server.port_num);
			textArea_2.append("IP 확인 : "+Server.server_ip+"\n");
			textArea_2.append("Port 확인 : "+Server.port_num+"\n");
			
			
			//정상적으로 소켓이 연결되었을 경우
			if(socket != null) {
				textArea_2.append("본사 서버 자동 접속 완료 ... \n");
				textArea_2.append("서버 자동 접속 완료 ... \n");
				this.Connect_to_server();		
			}
			else
				textArea_2.append("소켓 생성 실패");
			
		} 
		
		catch (UnknownHostException e) {
			
			textArea_2.append("서버 자동 접속 실패 !!!! \n");
			textArea_2.append("서버 상태를 확인해주세요 !! \n");
			e.printStackTrace();
		} 
		catch (IOException e) {
			textArea_2.append("서버 자동 접속 실패 !!!! \n");
			textArea_2.append("서버 상태를 확인해주세요 !! \n");
			e.printStackTrace();
		}
		
	}
	
	//main thread
	public static void main(String[] args) {
		
	}

	//생성자 함수
	public Client(String name) {
		this.userName=name;
		this.init();	//Frame을 그려주는 메서드 호출	
		this.network();	//서버에 소켓 통신하는 메서드 호출	
			
	}
	
	
	

	//화면을 생성하는 메소드
	private void init()
	{
		JFrame frame=new JFrame();
		
		frame.setResizable(false);
		frame.setTitle("카페 관리 프로그램");
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

		
		
		//선 차트 출력 
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
		
		//막대 그래프 출력 
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.drawBarChart();
				
				
			}
		});
		btnNewButton_2.setBounds(29, 103, 170, 36);
		contentPane_1.add(btnNewButton_2);
		
		//전송 버튼 핸들러
		btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//getText() 메서드는 해당 jTextArea 객체에 담긴 문자열을 가져오는 메서드이다.
					//trim()메서드는 스페이스나 빈칸이 있으면 앞으로 정리해주는 메서드이다
					String str=textArea_1.getText().trim();
					textArea.append(str);	
					textArea.append("\n");
					textArea_1.setText(null);
							
					//서버에게 채팅 내용과 해당 유저 이름을 보낸다
					send_message(userName+"/"+str);
					
				}
			});
				
		//엔터키 핸들러 
		textArea_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					//getText() 메서드는 해당 jTextArea 객체에 담긴 문자열을 가져오는 메서드이다.
					//trim()메서드는 스페이스나 빈칸이 있으면 앞으로 정리해주는 메서드이다
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
			
			
			//웹 페이지 띄우기
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
		
		//쪽지 보내기 핸들러
		JButton btnNewButton_5 = new JButton("\uCABD\uC9C0 \uBCF4\uB0B4\uAE30");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//받는 유저 이름
				String user=(String)list.getSelectedValue();
				
				//보낼 메시지
				String note=JOptionPane.showInputDialog("보낼 매세지");
				
				if(note != null)
				{
					//Note+보내는 유저 이름+받는 유저 이름 +메시지
					send_message("Note/"+userName+"/"+user+"/"+note);
				}
				
				
				
				System.out.println("받는 사람 : "+user+"보낼 내용 : "+note);
			}
		});
		btnNewButton_5.setForeground(Color.BLUE);
		btnNewButton_5.setBounds(228, 272, 160, 29);
		contentPane_1.add(btnNewButton_5);
		btnNewButton_3.setForeground(new Color(0, 128, 0));
		btnNewButton_3.setBounds(228, 37, 160, 23);
		
		contentPane_1.add(btnNewButton_3);
		
		//파이 차트 그리기
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
					Desktop.getDesktop().browse(new URI("http://192.168.1.58:18080/chart/chartMainPage.do?member_id=석재현"));
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
