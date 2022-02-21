package caso1;

public class Message {
	private String text = "";

	public Message() {

	}

	public Message(boolean fin) {
		if (fin) {
			this.text = "FIN";
		}
	}

	public void stamp(String text) {
		this.text += " " + text;
	}

	@Override
	public String toString() {
		return "Mensaje:" + this.text;
	}

}
