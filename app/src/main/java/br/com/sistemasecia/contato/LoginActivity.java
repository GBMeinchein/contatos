package br.com.sistemasecia.contato;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import dao.ContatoDAO;
import dao.DatabaseHelper;
import dao.SenhaDAO;
import model.Contato;
import model.Senha;


public class LoginActivity extends Activity {

    private ContatoDAO contatoDAO;
    private Contato contato;
    private SenhaDAO senhaDAO;
    private Senha senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        Button btEntrar = (Button) findViewById(R.id.btEntrar);
        Button btSenha = (Button) findViewById(R.id.btSenha);
        final CheckBox cbAtualizaContatos = (CheckBox) findViewById(R.id.cbAtualizaContatos);
        final EditText etSenha = (EditText) findViewById(R.id.etSenha);

        contatoDAO = new ContatoDAO(this);
        senhaDAO = new SenhaDAO(this);
        senha = senhaDAO.buscarSenhaPorCodigo(1);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSenha.getText().toString().equals(senha.getValor())) {
                    if(cbAtualizaContatos.isChecked()) {
                        File file = android.os.Environment.getExternalStorageDirectory();
                        final String nomeArquivo = file + "/listaContatos.txt";

                        Scanner scanner = null;
                        String razao, telefone, celular, pessoa;

                        try {
                            contato = new Contato();
                            scanner = new Scanner(new FileReader(nomeArquivo)).useDelimiter("\\||\\n");
                            while (scanner.hasNext()) {
                                razao = scanner.next();
                                telefone = scanner.next();
                                celular = scanner.next();
                                pessoa = scanner.next();

                                contato.setRazao(razao);
                                contato.setTelefone(telefone);
                                contato.setCelular(celular);
                                contato.setNome(pessoa);
                                contato.setOrigem("I");//Origem de cadastro Importado(I)

                                contatoDAO.removeContatoPorRazao(contato.getRazao());
                                contatoDAO.salvarContatos(contato);
                            }
                            scanner.close();

                        } catch (FileNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "O arquivo para atualização não foi encontrado!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadSenhaActivity.class);
                startActivity(intent);
            }
            });
        }
}
