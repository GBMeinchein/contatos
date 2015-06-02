package br.com.sistemasecia.contato;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dao.SenhaDAO;
import model.Senha;


public class CadSenhaActivity extends Activity {

    private EditText etSenhaAntiga, etSenhaNova;
    private Button btSalvar, btVoltar;
    private Senha senha, senhaAtual;
    private SenhaDAO senhaDAO;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_senha);

        contexto = this;
        etSenhaAntiga = (EditText) findViewById(R.id.etSenhaAntiga);
        etSenhaNova = (EditText) findViewById(R.id.etSenhaNova);
        btSalvar = (Button) findViewById(R.id.btSalvar);
        btVoltar = (Button) findViewById(R.id.btVoltar);

        senhaDAO = new SenhaDAO(this);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senhaAtual = senhaDAO.buscarSenhaPorCodigo(1);

                if(etSenhaAntiga.getText().toString().equals(senhaAtual.getValor())){
                    senha = new Senha();
                    senha.setCodigo(1);
                    senha.setValor(etSenhaNova.getText().toString());
                    senhaDAO.salvarSenhas(senha);
                    Toast.makeText(contexto, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(contexto, LoginActivity.class));
                }else{
                    Toast.makeText(contexto, "Senha antiga incorreta!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(contexto, LoginActivity.class));
            }
        });
    }
}
