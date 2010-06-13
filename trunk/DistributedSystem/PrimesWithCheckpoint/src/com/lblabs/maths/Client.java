package com.lblabs.maths;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends Frame
{
	private Socket s = null;// 客户端Socket
	private DataOutputStream dos = null;// 输出流
	private DataInputStream dis = null; // 输入流
	private boolean bConnected = false; // 是否连接上服务器
	// private String UserName = null;//客户端用户名
	private TextField tfText = new TextField();// 公共聊天信息框
	private TextArea taContent = new TextArea(); // 个人语句输出栏
	private Thread recvThread = new Thread(new RecieveThread());// 接收线程

	public static void main(String[] args)
	{
		new Client("Client").launchFrame();
	}

	public Client(String s)
	{
		super(s);
	}

	public void launchFrame()
	{
		// 窗口设置
		this.setBackground(Color.gray);
		this.setVisible(true);
		this.setSize(400, 400);
		this.setLocation(200, 200);

		// 界面布局
		BorderLayout BL = new BorderLayout();
		this.setLayout(BL);
		this.add(taContent, BorderLayout.NORTH);
		this.add(tfText, BorderLayout.SOUTH);
		this.pack();

		// 添加各种监听器
		tfText.addActionListener(new TFListener());
		this.addWindowListener(new WindowListener());

		// 连接服务器端
		connect();

		// 启动接收线程
		recvThread.start();
	}

	// 连接服务器端
	public void connect()
	{
		try
		{
			this.s = new Socket("127.0.0.1", 8888);
			this.dos = new DataOutputStream(s.getOutputStream());// 初始化输出
			this.dis = new DataInputStream(s.getInputStream());// 初始化输入
			System.out.println("Server Connected.");
			this.bConnected = true;
		} catch (UnknownHostException e)
		{
			System.out.println("未找到服务器,程序自动退出。请检查服务器是否已经正常启动或其他问题。");
			System.exit(0);
		} catch (ConnectException e)
		{
			System.out.println("连接出错。程序自动退出。请重启客户端程序。");
			System.exit(0);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	// 断开连接
	public void disconnect()
	{
		try
		{
			this.bConnected = false;// 关闭线程
			this.dis.close();// 输入流关闭
			this.dos.close();// 输出流关闭
			this.s.close();// Socket关闭
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				this.dis.close();// 输入流关闭
				this.dos.close();// 输出流关闭
				this.s.close();// Socket关闭
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// 输入框事件监听
	private class TFListener implements ActionListener
	{

		// 发送字符串的方法
		public void send(String str)
		{
			try
			{
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());

				dos.writeUTF(str);
				dos.flush();
				// dos.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		// 对输入字符串并敲回车后进行处理
		public void actionPerformed(ActionEvent e)
		{
			// 注意：不在这里获取字符串可能有错
			String str = tfText.getText().trim();// 获取输入的字符串并去掉两边的空格
			// taContent.append(str);//将该字符串添加到上面的聊天内容里
			tfText.setText(null);// 将自己的输入框设为空
			send(str);// 发送字符串
		}
	}

	// 窗口事件监听
	private class WindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			disconnect();// 断开连接
			System.exit(0);// 关闭窗口
		}
	}

	// 接收线程
	private class RecieveThread implements Runnable
	{

		public void run()
		{
			try
			{
				while (bConnected)
				{
					String str;
					str = dis.readUTF();
					System.out.println(str);
					taContent.append(str + '\n');
				}
			} catch (EOFException e)
			{
				System.out.println("退出了，bye");
			} catch (SocketException e)
			{
				System.out.println("退出了，bye");
			} catch (IOException e)
			{
				System.out.println("退出了，bye");
			}
		}
	}

}
