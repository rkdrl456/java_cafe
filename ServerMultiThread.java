import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;


//������ ��Ƽ������ ����� ���� ��ü
//���� ���� �� ��Ʈ�� ���� ���ҽ� 

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
	
	//������ �Լ�
	public ServerMultiThread(Socket sck)
	{
		//���� ���� ����
		multithread_socket=sck;
		//��Ʈ��ũ ���� �Լ� ȣ��
		this.init_NetworkRsc();
	}
	
	//��Ʈ��ũ ���� ��ü ����
	private void init_NetworkRsc()
	{
		//stream ����
		//Ŭ���̾�Ʈ�� ����ϱ� ���� ����
		try {
			is=multithread_socket.getInputStream();	
			datais=new DataInputStream(is);
			
			os=multithread_socket.getOutputStream();
			dataos=new DataOutputStream(os);
			
			
			//user_Name=datais.readUTF();
			//System.out.println(user_Name + "����� ���� \n");
		
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	//Ŭ���̾�Ʈ���� �޼����� ������ �Լ�
	private void send_Msg(String s_msg)
	{
		try {
			System.out.println("���� �޽��� : "+ s_msg);
			dataos.writeUTF(s_msg);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//���� ������ �Լ� 
	//from_user = ������ �����̸�
	private void Send_Note(String from_user,StringTokenizer st)
	{
		//�޴� ���� �̸�
		String to_user=st.nextToken();
		//�޴� �޽���
		String r_msg=st.nextToken();
		
		
		for(int i=0;i<Server.user_vc.size();i++) 
		{
			ServerMultiThread s=(ServerMultiThread)Server.user_vc.elementAt(i);
			
			if(s.user_Name.equals(to_user)) {
				//���� �޽���
				//Note+���� ���� �̸� +�޽���
				s.send_Msg("Note/"+from_user+"/"+r_msg);
				break;
			}
		}
	
	}
	
	
	//Ŭ���̾�Ʈ�κ��� �޽����� �޴� �Լ�
	private void receive_Msg()
	{
		try {
			String r_msg=datais.readUTF();
			st=new StringTokenizer(r_msg,"/");
			
			String type=st.nextToken();
			String msg=st.nextToken();
			
			
			System.out.println("type : " + type);
			System.out.println("msg : " + msg);
			
			//���� ����̶��
			if(type.equals("Newuser"))
			{
				setUserList(msg);
			}
			
			else if(type.equals("Note"))
			{
				Send_Note(msg,st);
			}
			//�Ϲ� ä���̶��
			else
				broadCast(type,msg);
			
			
			
			System.out.println("Ŭ���̾�Ʈ�� ���� ���� �޽��� : "+r_msg);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	//Ŭ���̾�Ʈ �����ϴ� �Լ�
	private void setUserList(String username)
	{

		//���� ���ӵ� Ŭ���̾�Ʈ���� ���ο� ����� �˸�
		for(int i=0;i<Server.user_vc.size();i++) {
			//���Ϳ� �ִ� ��ü�� ��δ� ������.
			//Vector.elementAt(index)�� �ش� �ε����� ��ġ�� ��ü�� ��ȯ�Ѵ�.
			//��ȯ�� ��ü�� ServerMultiThread��ü�� type casting(�� ��ȯ)�� �Ѵ�.
			ServerMultiThread s=(ServerMultiThread)Server.user_vc.elementAt(i);
			
			
			System.out.println("setUserList �� �޽��� : " +username);
			s.send_Msg("Newuser"+"/"+username);
			this.send_Msg("Newuser"+"/"+s.user_Name);
			
		}
		
		
		
		//�ش� client ��ü�� vector�� ����
		this.user_Name=username;
		Server.user_vc.add(this);
		
		
		
		System.out.println("���� Ŭ���̾�Ʈ ��  : "+Server.user_vc.size());
	}

	//���� Ŭ���̾�Ʈ�� ����ϴ� �ٽ� �Լ�
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
	//������ ���� ��ƾ
	public void run()
	{
		while(true)
		{
			receive_Msg();
			
			
		}
	}
}

