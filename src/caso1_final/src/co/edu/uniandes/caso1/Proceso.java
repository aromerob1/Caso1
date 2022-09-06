package co.edu.uniandes.caso1;

import java.util.LinkedList;

public class Proceso extends Thread{
	
	private Buzon buzonEntrada;
	
	private Buzon buzonSalida;
	
	private LinkedList<String> mensajes;
	
	private int nivel;
	
	private int id;
	
	private int contadorFin = 0;
	
	
	/**
	 * Constructor
	 * @param nivel
	 * @param id
	 * @param mensaje
	 * @param entrada
	 * @param salida
	 */
	public Proceso(int nivel, int id, Buzon entrada, Buzon salida) {
		this.id = id;
		this.nivel = nivel;
		this.buzonEntrada = entrada;
		this.buzonSalida = salida;
	}
	
	public Proceso(int nivel, int id, Buzon entrada, Buzon salida, LinkedList<String> mensajes) {
		this.id = id;
		this.nivel = nivel;
		this.buzonEntrada = entrada;
		this.buzonSalida = salida;
		this.mensajes = mensajes;
	}
	
	public void crearSubConjuntos(LinkedList<String> mensajes) {
		for (String mensaje : mensajes) {
			buzonSalida.agregarMensajeInic(mensaje);
		}
	}
	
	public void fin() {
		for (int i = 0; i < 3; i++) {
			buzonSalida.agregarMensaje("FIN");
		}
	}
	
	public void enviar(String msg)
	{
		buzonSalida.agregarMensaje(msg);
	}
	
	public String recibir()
	{
		return buzonEntrada.retirarMensaje();
	}
	
	public void integrarSubConjuntos(){
		String msgFinal = "";
		for(String msg : mensajes)
		{
			msgFinal += msg;
		}
		System.out.println(msgFinal);
	}
	
	public String transformar(String msg) {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return msg+"T"+nivel+id;
	}
	
	public void run() {
		if(id==0) {
			crearSubConjuntos(mensajes);
			fin();
		} else if (id==4) {
			this.mensajes = new LinkedList<String>();
			boolean seguir = true;
			while (seguir)
			{
				if (contadorFin < 3)
				{
					String msg = recibir();
					if (msg.equals("FIN"))
					{
						contadorFin++;
						mensajes.add(msg);
					}
					else
					{
						mensajes.add(msg);
					}
				}
				else 
				{
					integrarSubConjuntos();
					seguir = false;
				}
			
			}
		} else {
			boolean seguir = true;
			while(seguir)
			{
				String msg = recibir();
				if (msg.equals("FIN"))
				{
					seguir = false;
					enviar(msg);
					break;
				}
				msg = transformar(msg);
				enviar(msg);
			}
			
		}
	}

}
