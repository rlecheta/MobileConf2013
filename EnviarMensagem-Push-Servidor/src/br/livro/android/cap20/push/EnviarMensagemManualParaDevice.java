package br.livro.android.cap20.push;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * @author Ricardo Lecheta
 * 
 */
public class EnviarMensagemManualParaDevice {

	// Chave de registro do device. Resultado da chamada
	// GCMRegistrar.register(...);
	private static final String DEVICE_REGISTRATION_ID = "APA91bFDiaV7MQYXtt-2ECFEEpHbi-VT3m_AW9xF-Lvj74S4Xel1_6sPU776sMS6cxAh6Gm1RX9BLBG-Sg2m006b3b2MdUimOn2d6yW8beihMKNuUYL9iUr_8_rNMHFvZrAnT6COiqDqp3YoEDgZSA62VUw8AVTrH92STySzw-tQ5DiyBFnG8QI";

	// Chave criada no Console. Menu > API Access > (create new server key)
	private static final String API_KEY = "AIzaSyAlH0cYibMH97yKKrdsSZZ2LSJiX0PmnU0";

	public static void main(String[] args) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("msg", "Olá Msg Manual!");
		String result = post(API_KEY, DEVICE_REGISTRATION_ID, params );
		System.out.println(result);
	}

	/**
	 * Faz POST no servidor do Google
	 */
	public static String post(String apiKey, String deviceRegistrationId, Map<String, String> params) throws IOException {

		// Parâmetros necessários para o POST
		StringBuilder postBody = new StringBuilder();
		postBody.append("registration_id").append("=").append(deviceRegistrationId);
		
		// Cria os parâmetros chave=valor
		Set<String> keys = params.keySet();
		for (String key : keys) {
			String value = params.get(key);
			postBody.append("&").append("data.").append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
		}

		// Cria a mensagem
		byte[] postData = postBody.toString().getBytes("UTF-8");

		// Faz POST
		URL url = new URL("https://android.googleapis.com/gcm/send");
		HttpsURLConnection.setDefaultHostnameVerifier(new CustomizedHostnameVerifier());
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		conn.setRequestProperty("Authorization", "key=" + apiKey);

		// Lê a resposta
		OutputStream out = conn.getOutputStream();
		out.write(postData);
		out.close();

		int responseCode = conn.getResponseCode();
		if(responseCode == 200) {
			// OK
			String response = conn.getResponseMessage();
			return response;
		} else {
			System.err.println(responseCode + ": " + conn.getResponseMessage());
		}
		
		return null;
	}

	private static class CustomizedHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
