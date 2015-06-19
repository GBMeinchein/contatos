package br.com.sistemasecia.contato;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import dao.ContatoDAO;
import model.Contato;
import util.Mensagem;


public class CadContatoActivity extends Activity implements DialogInterface.OnClickListener {

    private ContatoDAO contatoDAO;
    private EditText etRazaoSocial, etTelefone, etPessoa, etOrigem, etCelular, etEmail;
    private CheckBox cbOrigem;
    private Button btSalvar, btExcluir, btVoltar;
    private Contato contato;
    private int codigo;
    private AlertDialog mensagemConfirmacao;
    private Context contexto;
    private static final String[] origens = new String[]{"Manual", "Importado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_contato);

        mensagemConfirmacao = Mensagem.criarDialogConfirmacao(this);

        contexto = this;

        etRazaoSocial = (EditText) findViewById(R.id.etRazaoSocial);
        etTelefone = (EditText) findViewById(R.id.etTelefone);
        etPessoa = (EditText) findViewById(R.id.etPessoa);
        etCelular = (EditText) findViewById(R.id.etCelular);
        etEmail = (EditText) findViewById(R.id.etEmail);
        cbOrigem = (CheckBox) findViewById(R.id.cbOrigem);
        btVoltar = (Button) findViewById(R.id.btVoltar);
        btSalvar = (Button) findViewById(R.id.btConfirma);
        btExcluir = (Button) findViewById(R.id.btExclui);

        cbOrigem.setChecked(true);

        contatoDAO = new ContatoDAO(this);

        codigo = getIntent().getIntExtra("CONTATO_CODIGO", 0);
        if(codigo > 0){
            contato = new Contato();
            contato = contatoDAO.buscarContatoPorCodigo(codigo);
            etRazaoSocial.setText(contato.getRazao());
            etTelefone.setText(contato.getTelefone());
            etCelular.setText(contato.getCelular());
            etEmail.setText(contato.getEmail());
            etPessoa.setText(contato.getNome());
            String origem = contato.getOrigem();
            if(origem.equals("I")){
                cbOrigem.setChecked(false);
            }else if (origem.equals("M")){
                cbOrigem.setChecked(true);
            }
            setTitle("Atualizar");
        }

        btVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(contexto, MainActivity.class));
            }
        });

        btSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //this.cadastrar();

                //private void cadastrar() {
                boolean valida = true;

                String razaoSocial = etRazaoSocial.getText().toString();
                String telefone = etTelefone.getText().toString();
                String celular = etCelular.getText().toString();
                String pessoa = etPessoa.getText().toString();
                String email = etEmail.getText().toString();

                if(razaoSocial == null || razaoSocial.equals("")){
                    valida = false;
                    etRazaoSocial.setError("Campo Razão social é obrigatório!", null);
                }

                /*if(telefone == null || telefone.equals("")){
                    valida = false;
                    etTelefone.setError("Campo Telefone é obrigatório!");
                }*/

                if(valida){
                    contato = new Contato();
                    contato.setRazao(razaoSocial);
                    contato.setTelefone(telefone);
                    contato.setNome(pessoa);
                    contato.setCelular(celular);
                    contato.setEmail(email);
                    if(cbOrigem.isChecked()){
                        contato.setOrigem("M"); //Origem de cadastro Manual(M)
                    }else{
                        contato.setOrigem("I");
                    }

                    //Para atualizar
                    if(codigo > 0){
                        contato.setCodigo(codigo);
                    }

                    long resultado = contatoDAO.salvarContatos(contato);

                    if(resultado != -1){
                        Toast.makeText(contexto, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(contexto, MainActivity.class));
                    }else{
                        Toast.makeText(contexto, "Erro ao cadastrar contato!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btExcluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(codigo > 0) {
                    mensagemConfirmacao.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cad_contato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_cadastro_salvar:
                //this.cadastrar();
                break;
            case R.id.action_cadastro_ligar:
                String telefone = etTelefone.getText().toString();
                if(telefone != null && telefone.equals("") == false) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + telefone));
                    startActivity(callIntent);
                }
                break;
            case R.id.action_cadastro_excluir:
                if(codigo > 0) {
                    mensagemConfirmacao.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                contatoDAO.removeContato(codigo);
                Toast.makeText(this, "Contato removido com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this, MainActivity.class));
        }
    }
}
