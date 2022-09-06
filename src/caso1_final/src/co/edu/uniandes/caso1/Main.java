package co.edu.uniandes.caso1;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	
	private static int numeroMensajes;
	private static int tamanoIntermedios;
	private static int tamanoExtremos;
	
	public static void main(String[] args) {
        System.out.println("Ingrese numero de subconjuntos: ");
        Scanner sc = new Scanner(System.in);
        numeroMensajes = sc.nextInt();
        System.out.println("Ingrese tamano de buzones intermedios: ");
        sc = new Scanner(System.in);
        tamanoIntermedios = sc.nextInt();
        System.out.println("Ingrese tamano de buzones extremos: ");
        sc = new Scanner(System.in);
        tamanoExtremos = sc.nextInt();
        
        LinkedList<String> mensajes = new LinkedList<String>();
        
        for (int i = 0; i < numeroMensajes; i++) {
			String mensaje = "M"+i;
			mensajes.add(mensaje);
		}
        
        Buzon inicio = new Buzon(tamanoExtremos);
        Proceso pi = new Proceso(0, 0, null, inicio, mensajes);
        Buzon b1 = new Buzon(tamanoIntermedios);
        Buzon b2 = new Buzon(tamanoIntermedios);
        Buzon b3 = new Buzon(tamanoIntermedios);
        Proceso p11 = new Proceso(1,1,inicio,b1);
        Proceso p12 = new Proceso(1,2,inicio,b2);
        Proceso p13 = new Proceso(1,3,inicio,b3);
        Buzon b4 = new Buzon(tamanoIntermedios);
        Buzon b5 = new Buzon(tamanoIntermedios);
        Buzon b6 = new Buzon(tamanoIntermedios);
        Proceso p21 = new Proceso(2,1,b1,b4);
        Proceso p22 = new Proceso(2,2,b2,b5);
        Proceso p23 = new Proceso(2,3,b3,b6);
        Buzon fin = new Buzon(tamanoExtremos);
        Proceso p31 = new Proceso(3,1,b4,fin);
        Proceso p32 = new Proceso(3,2,b5,fin);
        Proceso p33 = new Proceso(3,3,b6,fin);
        Proceso pf = new Proceso(4, 4,fin, null);
        
        pi.start();
        p11.start();
        p12.start();
        p13.start();
        p21.start();
        p22.start();
        p23.start();
        p31.start();
        p32.start();
        p33.start();
        pf.start();

	}

}
