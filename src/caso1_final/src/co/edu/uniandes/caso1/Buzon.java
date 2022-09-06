package co.edu.uniandes.caso1;

import java.util.LinkedList;

/**
 * 
 * Clase Buzon
 * 
 * Es la clase encargada de recibir los mensajes que dejan los procesos.
 * 
 * @author Grupo 9
 *
 */
public class Buzon {

	/**
	 * Capacidad total de mensajes que puede tener un buzon
	 */
	private int capacidad;

	/**
	 * Mensajes que hay en el buzon. mensaje.size()<capacidad
	 */
	private  LinkedList<String> mensajes;

	/**
	 * Constructor del buzon
	 */
	public Buzon(int capacidad) {
		this.capacidad = capacidad;
		mensajes = new LinkedList<String>();
	}
	
	/**
	 * Agrega el mensaje a la bolsa
	 */
	public synchronized void agregarMensaje(String msg) {
		while(mensajes.size() == capacidad) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mensajes.add(msg);
		notify();

	}
	
	public void agregarMensajeInic(String msg) {
		while(mensajes.size() == capacidad) {
			Thread.yield();
		}
		synchronized (this) {
			mensajes.add(msg);
			this.notifyAll();
		}
	}

	/**
	 * Retira un mensaje de la bolsa
	 * @return
	 */
	public synchronized String retirarMensaje() {
		while(mensajes.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String mensaje = mensajes.remove(0);
		notify();
		return mensaje;
	}
	
	public synchronized String retirarMensajeFin() {
		while(mensajes.size() == 0) {
			Thread.yield();
		}
		String mensaje = mensajes.remove(0);
		notifyAll();
		return mensaje;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public LinkedList<String> getMensajes() {
		return mensajes;
	}

	public void setMensajes(LinkedList<String> mensajes) {
		this.mensajes = mensajes;
	}
}
