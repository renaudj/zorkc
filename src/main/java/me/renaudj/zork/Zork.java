package me.renaudj.zork;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Zork {
  public Zork(){
    connect();
  }
  private volatile AtomicBoolean isFinished = new AtomicBoolean();
  public static void main(String[] args){
    new Zork();
  }
  
  public void connect(){
    try {
        final Socket socket = new Socket("127.0.0.1", 3000);
      final Thread outThread = new Thread() {
			@Override
			public void run() {
				System.out.println("Started...");
				PrintWriter out = null;
				Scanner sysIn = new Scanner(System.in);
				try {
					out = new PrintWriter(socket.getOutputStream());
					out.println("Test");
					out.flush();

					while (sysIn.hasNext() && !isFinished.get()) {
						String line = sysIn.nextLine();
						if ("exit".equals(line)) {
							synchronized (isFinished) {
								isFinished.set(true);
							}
						}
						out.println(line);
						out.flush();
						socket.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			};
		};
		outThread.start();

		final Thread inThread = new Thread() {
			@Override
			public void run() {
				// Use a Scanner to read from the remote server

				Scanner in = null;
				try {
					in = new Scanner(socket.getInputStream());
					String line = in.nextLine();
					while (!isFinished.get()) {
						System.out.println(line);
						line = in.nextLine();
					}
				} catch (Exception e) {
//					e.printStackTrace();
				} finally {
					if (in != null) {
						in.close();
					}
				}
			};
		};
		inThread.start();
    } catch (UnknownHostException e) {
      System.out.println("Unknown Host!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
