package com.lblabs.maths;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Coddy created on 2010-6-11
 * 
 */
public class Server
{
	boolean started = false;// 判断服务器端是不是启动状态的标识符
	ServerSocket ss = null;// 服务器端socket
	List<Client> clients = new ArrayList<Client>();// 记录所有的连接客户端

	public static void main(String[] args)
	{
		new Server().start();
	}

	// main方式是静态，如果在main方法里new一个非静态类，是错误的
	// 因此将实现细节包装到一个单独的方法里
	public void start()
	{
		try
		{
			ss = new ServerSocket(8888);// 服务器端启动
			started = true;// 将服务器状态标识符设为启动状态
			System.out.println("服务器启动成功.目前无客户端连入.");// 连接没问题，启动成功
		} catch (BindException e)
		{
			System.out.println("端口使用中，服务器启动失败.");
			System.out.println("请勿同时运行多个服务器端程序.");
			System.exit(0);
		} catch (IOException e)
		{
			System.out.println("服务器启动失败.");
			e.printStackTrace();
		}

		try
		{
			while (started)
			{
				Socket s = ss.accept();// 阻塞型方法，接受连接
				Client c = new Client(s);// main方式是静态，如果在main方法里new一个非静态类，是错误的
				System.out.println("A client connected.");// 测试
				new Thread(c).start(); // 启动该线程
				clients.add(c);// 将该客户端加入连接客户端列表

				CheckPoint checkPoint = new CheckPoint();
				Primes primes = new Primes(checkPoint);

				while (!clients.isEmpty())
				{
					primes.calculatePrimes();
					checkPoint.increaseStopBy(10);
				}
			}
		} catch (IOException e1)
		{
			e1.printStackTrace();
		} finally
		{
			try
			{
				ss.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private class Client implements Runnable
	{
		private Socket s = null; // 此线程的Socket
		private DataInputStream dis = null;// 此线程的输入流管道
		private DataOutputStream dos = null;// 此线程的输出流管道
		private boolean bConnected = false;// 此线程是否处于连接状态

		// 构造方法，将连接时的Socket传进来
		public Client(Socket s)
		{
			this.s = s;
			try
			{
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		// 发送当前的字符串str
		public void send(String str)
		{
			try
			{
				dos.writeUTF(str);
			} catch (IOException e)
			{
				clients.remove(this);
			}
		}

		// 运行方法
		public void run()
		{
			Client c = null;
			try
			{
				while (bConnected)
				{
					String str = null;
					str = dis.readUTF();// 注意！阻塞型方法，会一直等待输入流，有输入就读取
					System.out.println(str);// 测试

					// 为所有客户端发送该语句
					for (int i = 0; i < clients.size(); i++)
					{
						c = clients.get(i);
						c.send(str);
					}

					// 注意：用Iterator会有内部锁定，效率低下。
					/*
					 * 用Iterator的写法1 for(Iterator<Client> it =
					 * clients.iterator();it.hasNext();) { Client c = it.next();
					 * c.send(str); }
					 */

					/*
					 * 用Iterator的写法2 Iterator<Client> it = clients.iterator();
					 * while(it.hasNext()) { Client c = it.next(); c.send(str);
					 * }
					 */
				}
			} catch (SocketException e)
			{
				clients.remove(this);
				System.out.println("A client quit.");
			} catch (EOFException e)
			{
				System.out.println("client closed.");
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
					if (s != null)
						s.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				if (this != null)
					clients.remove(this);
			}
		}
	}

}