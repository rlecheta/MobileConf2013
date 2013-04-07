package br.livro.android.cap20.push;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

/**
 * @author Ricardo Lecheta
 */
public class EnviarMensagemParaDevice {
	
	// Identificador do device
	private static final String DEVICE_REGISTRATION_ID = "APA91bG0ciGMI6_t9TCFuUKK0dM5f5sdHUMZ9XvvcPVZtuJCPNpfqqWoSulAaehv8cLnAPLVmlJ2LzD4DDjD78d0uprFIkc-M4z3hINFEDfE4My7iOFYhrW06bOxH0tuKSWz9sfIT2OJ0OOgvRqoBkjvPEhAHWp6e1VvTfQ8rD2eqm78aj9NuaU";
	
	// Chave criada no Console. Menu > API Access > (create new server key)
	private static final String API_KEY = "AIzaSyAlH0cYibMH97yKKrdsSZZ2LSJiX0PmnU0";

	public static void main(String[] args) throws IOException {
		Sender sender = new Sender(API_KEY);
		Message message = new Message.Builder().collapseKey("mobileconf").addData("msg", "Olá MobileConf legal !").build();
		sender.send(message, DEVICE_REGISTRATION_ID, 5);
		System.out.println("Mensagem enviada: " + message.getData().get("msg"));
	}
}