package caso1;

public class Process extends Thread {
	private int id;
	private int nMessages;
	private int waitTime;
	private Buffer rBuffer;
	private Buffer lBuffer;
	private boolean execute;
	private boolean tipoEnvio;
	private boolean tipoRecep;

	public Process(int id, int waitTime, Buffer lbuffer, Buffer rBuffer, boolean tipoEnvio, boolean tipoRecep) {
		this.id = id;
		this.waitTime = waitTime;
		this.lBuffer = lbuffer;
		this.rBuffer = rBuffer;
		this.tipoEnvio = tipoEnvio;
		this.tipoRecep = tipoRecep;
	}

	public Process(int id, int waitTime, int nMessages, Buffer lbuffer, Buffer rBuffer, boolean tipoEnvio,
			boolean tipoRecep) throws Exception {
		if (id == 1) {
			this.id = id;
			this.waitTime = waitTime;
			this.nMessages = nMessages;
			this.lBuffer = lbuffer;
			this.rBuffer = rBuffer;
			this.tipoEnvio = tipoEnvio;
			this.tipoRecep = tipoRecep;
		} else {
			Exception e = new Exception("Solo el proceso 1 puede conocer el numero de mensajes");
			throw e;
		}
	}

	public void run() {
		if (id == 1) {
			int n = nMessages;
			boolean acaboDePasarPrimerosMensajes = true;
			while (n >= 0 || acaboDePasarPrimerosMensajes) {
				Message m = get(this.tipoRecep);
				if (m.toString().equals("Mensaje:")) {
					transform(m);
					send(m, this.tipoEnvio);
				} else if (m.toString().contains("Mensaje: ")) {
					System.out.println(m.toString());
					n--;
				} else if (m.toString().equals("Mensaje:FIN")) {
					acaboDePasarPrimerosMensajes = false;
				}

				if (n == 0) {
					send(new Message(true), this.tipoEnvio);
					n--;
				}
			}

		} else {
			execute = true;
			while (execute) {
				Message m = get(this.tipoRecep);
				if (m.toString().equals("Mensaje:FIN")) {
					send(m, this.tipoEnvio);
					execute = false;
				} else {
					transform(m);
					send(m, this.tipoEnvio);
				}
			}
		}

		Communication.executionEnded();
	}

	public Message get(Boolean tipoRecep) {
		Message m = new Message();
		if(tipoRecep){
			m = lBuffer.removeActive();
		}
		else{
			m = lBuffer.removePassive();
		}
		return m;
	}

	public void transform(Message m) {
		m.stamp(Integer.toString(id));
		if (tipoEnvio) {
			m.stamp("A");
		} else {
			m.stamp("P");
		}
		if (tipoRecep) {
			m.stamp("A");
		} else {
			m.stamp("P");
		}
	}

	public void send(Message m, Boolean esActivo) {
		try {
			sleep(waitTime);
			if(esActivo){
				rBuffer.addActive(m);
			}
			else{
				rBuffer.addPassive(m);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
