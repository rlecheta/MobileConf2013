package br.mobileconf.gcm.demo2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

/**
 * @author Ricardo Lecheta
 *
 */
public class MainActivity extends Activity {

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String msg = intent.getStringExtra("msg");
			if(msg != null) {
				// Recebeu uma mensagem por broadcast
				showMessage(msg);
			}
			boolean refresh = intent.getBooleanExtra("refresh", false);
			if(refresh) {
				// Se fez ativação, atualiza o estado dos botões
				init();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.btnRegistrar).setOnClickListener(onClickRegistrar());
		findViewById(R.id.btnCancelar).setOnClickListener(onClickCancelar());
		
		// Configura o estado da view
		init();

		// Registra o receiver
		registerReceiver(receiver , new IntentFilter("br.mobileconf.gcm.demo2.MENSAGEM"));

		// Recebeu uma mensagem pela Notification
		String msg = getIntent().getStringExtra("msg");
		if(msg != null) {
			showMessage(msg);
		}
	}

	private void init() {
		boolean registered = GCMRegistrar.isRegistered(this);
		if(registered) {
			findViewById(R.id.btnRegistrar).setVisibility(View.GONE);
			findViewById(R.id.btnCancelar).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.btnRegistrar).setVisibility(View.VISIBLE);
			findViewById(R.id.btnCancelar).setVisibility(View.GONE);
		}
	}

	private OnClickListener onClickCancelar() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				GCMRegistrar.unregister(getApplicationContext());
			}
		};
	}

	private OnClickListener onClickRegistrar() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				GCMRegistrar.checkDevice(getApplicationContext());
				GCMRegistrar.checkManifest(getApplicationContext());
				GCMRegistrar.register(getApplicationContext(), Constants.PROJECT_NUMBER);
			}
		};
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// GCM end
		GCMRegistrar.onDestroy(getApplicationContext());
		
		// Cancela o receiver
		unregisterReceiver(receiver);
	}

	private void showMessage(String msg) {
		TextView t = (TextView) findViewById(R.id.tMsgRecebida);
		t.setText(msg);
	}
}
