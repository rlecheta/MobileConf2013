package br.mobileconf.gcm.demo2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * @author Ricardo Lecheta
 *
 */
public class GCMIntentService extends GCMBaseIntentService {
	
	public GCMIntentService() {
		// Project Number lá do Google APIs Console
		super(Constants.PROJECT_NUMBER);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		// Lembre-se: O Google pode chamar este método mais de uma vez, para atualizar o registrationId.
		Log.i(TAG,"onRegistered: " + registrationId);
		
		// Fez o registro. Vc precisa enviar o registrationId para o servidor
		
		showMessage("onRegistered: " + registrationId);
		
		
		refresh();
	}

	@Override
	protected void onUnregistered(Context arg0, String registrationId) {
		Log.i(TAG,"onUnregistered: " + registrationId);
		
		// Fez o cancelamento do registro. Vc precisa remover este id do seu servidor.
		showMessage("onUnregistered: " + registrationId);
		
		refresh();
	}

	private void refresh() {
		// Para atualizar os botões da tela (veja o receiver lá)
		Intent intent = new Intent("br.mobileconf.gcm.demo2.MENSAGEM");
		intent.putExtra("refresh", true);
		sendBroadcast(intent);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// Recebeu uma mensagem
		String msg = intent.getStringExtra("msg");
		Log.i(TAG,"onMessage: " + msg);
		
		showMessage("onMessage: " + msg);
		
		// Intent para Notificação
		Intent it = new Intent(context, MainActivity.class);
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		it.putExtra("msg", msg);
		NotificationUtil.generateNotification(context, "Nova mensagem: " + msg, it);

		// TODO no livro android 3ª edição é mostrado como verificar se a app está aberta
		// Se está aberta envia a msg por broadcast, e se está fechada cria a notification.
	}

	private void showMessage(String string) {
		// Dispara o broadcast. O receiver lá na activity vai interceptar
		Intent intent = new Intent("br.mobileconf.gcm.demo2.MENSAGEM");
		intent.putExtra("msg", string);
		sendBroadcast(intent);
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		
	}
}
