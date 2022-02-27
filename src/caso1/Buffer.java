package caso1;

import java.util.ArrayList;

import javax.sql.rowset.spi.SyncResolver;

public class Buffer {

	private String id;
	private ArrayList<Message> buff;
	private int capacidad;

	public Buffer(String id, int capacidad) {
		this.id = id;
		this.capacidad = capacidad;
		buff = new ArrayList<Message>();
	}

	public void addActive(Message m) {
		while (buff.size() == capacidad) {
			Thread.yield();
		}
		buff.add(m);
		synchronized (this) {
			notify();
		}
	}

	public synchronized void addPassive(Message m) {
		while (buff.size() == capacidad) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buff.add(m);
		notify();

	}

	public synchronized Message removePassive() {
		while (buff.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Message m = buff.remove(0);
		notify();

		return m;
	}

	public Message removeActive() {
		while (buff.size() == 0) {
			Thread.yield();
		}
		Message m = buff.remove(0);
		synchronized (this) {
			notify();
		}
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

	public synchronized int getCapacidad() {
		return this.capacidad;
	}

	public int getBuffSize() {
		return this.buff.size();
	}

}
