package caso1;

import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Communication {
	private static int nMessages;
	private static CyclicBarrier barrier;
	
	public static void executionEnded()
	{
		try 
		{
			barrier.await();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (BrokenBarrierException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) 
	{
		Runnable ended = () -> System.out.println("Todos los procesos han terminado su ejecucion");
		barrier = new CyclicBarrier(4, ended);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el numero de mensajes a enviar: ");
		nMessages = Integer.parseInt(scanner.nextLine()); 
		scanner.close();
		int n = nMessages;
		Buffer bufferA = new Buffer("A", 8);
		Buffer bufferB = new Buffer("B", 8);
		Buffer bufferC = new Buffer("C", 8);
		Buffer bufferD = new Buffer("D", 8);
		int capacidades = bufferA.getCapacidad()+bufferB.getCapacidad()+bufferC.getCapacidad()+bufferD.getCapacidad();
		if (nMessages > capacidades)
		{
			System.out.println("El numero de mensajes a enviar excede la suma de los tamaños de los buffers");
		}
		else
		{
			try 
			{
				Process process1 = new Process(1, 1, nMessages, bufferD, bufferA);
				Process process2 = new Process(2, 1, bufferA, bufferB);
				Process process3 = new Process(3, 1, bufferB, bufferC);
				Process process4 = new Process(4, 1, bufferC, bufferD);
				process1.start();
				process2.start();
				process3.start();
				process4.start();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			while(n > 0)
			{
				bufferD.add(new Message());
				n--;
			}
		}
	}
}
