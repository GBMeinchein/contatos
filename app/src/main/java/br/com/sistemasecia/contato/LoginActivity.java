package br.com.sistemasecia.contato;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        Button btEntrar = (Button) findViewById(R.id.btEntrar);
        EditText etSenha = (EditText) findViewById(R.id.etSenha);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ListaContatoActivity.class);
                startActivity(intent);
            }
        });
    }
}
