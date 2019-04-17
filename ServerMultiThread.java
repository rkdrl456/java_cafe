import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;


//서버의 멀티스레드 통신을 위한 객체
//소켓 생성 및 스트림 관련 리소스 

public class ServerMultiThread extends Thread {
	
	//Resources..
	private OutputStream os;
	private InputStream is;
	private DataOutputStream dataos;
	private DataInputStream datais;
	
	//Network Resource
	private Socket multithread_socket;
	
	//User Resource

	private StringTokenizer st;
	private String user_Name;
	
	//생성자 함수
	public ServerMultiThread(Socket sck)
	{
		//서버 소켓 설정
		multithread_socket=sck;
		//네트워크 설정 함수 호출
		this.init_NetworkRsc();
	}
	
	//네트워크 관련 객체 생성
	private void init_NetworkRsc()
	{
		//stream 설정
		//클라이언트와 통신하기 위한 과정
		try {
			is=multithread_socket.getInputStream();	
			datais=new DataInputStream(is);
			
			os=multithread_socket.getOutputStream();
			dataos=new DataOutputStream(os);
			
			
			//user_Name=datais.readUTF();
			//System.out.println(user_Name + "사용자 접속 \n");
		
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	//클라이언트에게 메세지를 보내는 함수
	private void send_Msg(String s_msg)
	{
		try {
			System.out.println("보낼 메시지 : "+ s_msg);
			dataos.writeUTF(s_msg);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//쪽지 보내는 함수 
	//from_user = 보내는 유저이름
	private void Send_Note(String from_user,StringTokenizer st)
	{
		//받는 유저 이름
		String to_user=st.nextToken();
		//받는 메시지
		String r_msg=st.nextToken();
		
		
		for(int i=0;i<Server.user_vc.size();i++) 
		{
			ServerMultiThread s=(ServerMultiThread)Server.user_vc.elementAt(i);
			
			if(s.user_Name.equals(to_user)) {
				//보낼 메시지
				//Note+보낸 유저 이름 +메시지
				s.send_Msg("Note/"+from_user+"/"+r_msg);
				break;
			}
		}
	
	}
	
	
	//클라이언트로부터 메시지를 받는 함수
	private void receive_Msg()
	{
		try {
			String r_msg=datais.readUTF();
			st=new StringTokenizer(r_msg,"/");
			
			String type=st.nextToken();
			String msg=st.nextToken();
			
			
			System.out.println("type : " + type);
			System.out.println("msg : " + msg);
			
			//유저 등록이라면
			if(type.equals("Newuser"))
			{
				setUserList(msg);
			}
			
			else if(type.equals("Note"))
			{
				Send_Note(msg,st);
			}
			//일반 채팅이라면
			else
				broadCast(type,msg);
			
			
			
			System.out.println("클라이언트로 부터 받은 메시지 : "+r_msg);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	//클라이언트 관리하는 함수
	private void setUserList(String username)
	{

		//현재 접속된 클라이언트에게 새로운 사용자 알림
		for(int i=0;i<Server.user_vc.size();i++) {
			//벡터에 있는 객체를 모두다 꺼낸다.
			//Vector.elementAt(index)는 해당 인덱스에 위치한 객체를 반환한다.
			//반환한 객체를 ServerMultiThread객체로 type casting(형 변환)을 한다.
			ServerMultiThread s=(ServerMultiThread)Server.user_vc.elementAt(i);
			
			
			System.out.println("setUserList 의 메시지 : " +username);
			s.send_Msg("Newuser"+"/"+username);
			this.send_Msg("Newuser"+"/"+s.user_Name);
			
		}
		
		
		
		//해당 client 객체를 vector에 저장
		this.user_Name=username;
		Server.user_vc.add(this);
		
		
		
		System.out.println("현재 클라이언트 수  : "+Server.user_vc.size());
	}

	//다중 클라이언트와 통신하는 핵심 함수
	private void broadCast(String user_name,String msg)
	{
		for(int i=0;i<Server.user_vc.size();i++)
		{
			ServerMultiThread smt=(ServerMultiThread)Server.user_vc.elementAt(i);
			
			System.out.println("broadcast : "+user_name);
			System.out.println("broadcast : "+msg);
		
			
			if(smt.user_Name.equals(user_name))
				;
			
			else
				smt.send_Msg(user_name+"/"+msg);
		}
		
	}
	
	
	
	@Override
	//스레드 실행 루틴
	public void run()
	{
		while(true)
		{
			receive_Msg();
			
			
		}
	}
}

