package caso1;

import java.util.ArrayList;

public class Buffer {

	private String id;
	private ArrayList<Message> buff;
	private int capacidad;

	public Buffer(String id, int capacidad) {
		this.id = id;
		this.capacidad = capacidad;
		buff = new ArrayList<Message>();
	}

	public synchronized void add(Message m, Boolean esActivo) {
		if (esActivo) {
			while (buff.size() == capacidad) {
				// Probable yield: Preguntar
			}
		} else {
			while (buff.size() == capacidad) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		buff.add(m);
		notify();
	}

	// sos
	public synchronized Message remove(Boolean esActivo) {

		while (buff.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Message m = buff.remove(0);
		return m;
	}

	public void cleanBuffer() {
		buff.clear();
	}

	public boolean empty() {
		if (this.buff.size() == 0) {
			return true;
		}
		return false;
	}

	public int getCapacidad() {
		return this.capacidad;
	}

	public int getBuffSize() {
		return this.buff.size();
	}

}
