package caso1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Process extends Thread {
	private int id;
	private int waitTime;
	private int nMessages;
	private Buffer rBuffer;
	private Buffer lBuffer;
	private boolean execute;
	
	public Process(int id, int waitTime, Buffer lbuffer, Buffer rBuffer)
	{
		this.id = id;
		this.waitTime = waitTime;
		this.lBuffer = lbuffer;
		this.rBuffer = rBuffer;
	}
	
	public Process(int id, int waitTime, int nMessages, Buffer lbuffer, Buffer rBuffer) throws Exception
	{
		if (id == 1)
		{
			this.id = id;
			this.waitTime = waitTime;
			this.nMessages = nMessages;
			this.lBuffer = lbuffer;
			this.rBuffer = rBuffer;
		}
		else
		{
			Exception e = new Exception("Solo el proceso 1 puede conocer el numero de mensajes");
			throw e;
		}
	}
	
	public void run()
	{
		execute = true;
		if (id == 1)
		{
			int n = nMessages;
			boolean existingMessage = true;
			while(existingMessage)
			{
				Message m = get();
				if(m.toString().equals("Mensaje:"))
				{
					transform(m);
					send(m);
				}
				else
				{
					while(n > 0)
					{
						System.out.println(m.toString());
						n--;
					}
					existingMessage = false;
				}
				
			}
			lBuffer.cleanBuffer();
			send(new Message(true));
			Communication.executionEnded();
		}
		else
		{
			while(execute)
			{
				Message m = get();
				if(m.toString().equals("Mensaje:FIN"))
				{
					send(m);
					execute = false;	
				}
				else
				{
					transform(m);
					send(m);
				}
				Communication.executionEnded();

			}
		}		
	}
	
	public Message get()
	{
		Message m = lBuffer.remove();
		return m;
	}
	
	public void transform(Message m)
	{
		m.stamp(Integer.toString(id));
	}
	
	public void send(Message m)
	{
		try 
		{
			sleep(waitTime);
			rBuffer.add(m);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
	}
}
