package caso1;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Communication {
	private static int nMessages;
	private static CyclicBarrier barrier;

	public static void executionEnded() {
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Runnable ended = () -> System.out.println("Todos los procesos han terminado su ejecucion");
		barrier = new CyclicBarrier(4, ended);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el numero de mensajes a enviar: ");
		nMessages = Integer.parseInt(scanner.nextLine());
		Config data = new Config();
		try {
			data.loadData();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		scanner.close();
		int n = nMessages;
		Buffer bufferA = new Buffer("A", data.getAmessages());
		Buffer bufferB = new Buffer("B", data.getBmessages());
		Buffer bufferC = new Buffer("C", data.getCmessages());
		Buffer bufferD = new Buffer("D", data.getDmessages());
		int capacidades = bufferA.getCapacidad() + bufferB.getCapacidad() + bufferC.getCapacidad()
				+ bufferD.getCapacidad();
		if (nMessages > capacidades) {
			System.out.println("El numero de mensajes a enviar excede la suma de los tama�os de los buffers");
		} else {
			try {
				Process process1 = new Process(1, data.getP1Tiempo(), nMessages, bufferD, bufferA, data.getP1TipoEnvio(), data.getP1TipoRecep());
				Process process2 = new Process(2, data.getP2Tiempo(), bufferA, bufferB, data.getP2TipoEnvio(), data.getP2TipoRecep());
				Process process3 = new Process(3, data.getP3Tiempo(), bufferB, bufferC, data.getP3TipoEnvio(), data.getP3TipoRecep());
				Process process4 = new Process(4, data.getP4Tiempo(), bufferC, bufferD, data.getP4TipoEnvio(), data.getP4TipoRecep());
				process1.start();
				process2.start();
				process3.start();
				process4.start();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			while (n > 0) {
				bufferD.add(new Message(), data.getP4TipoEnvio());
				n--;
			}
		}
	}
}
