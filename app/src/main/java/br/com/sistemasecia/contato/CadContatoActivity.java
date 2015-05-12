package br.com.sistemasecia.contato;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import dao.ContatoDAO;
import model.Contato;


public class CadContatoActivity extends ActionBarActivity {

    private ContatoDAO contatoDAO;
    private EditText etRazaoSocial, etTelefone, etPessoa;
    private Contato contato;
    private int codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_contato);

        etRazaoSocial = (EditText) findViewById(R.id.etRazaoSocial);
        etTelefone = (EditText) findViewById(R.id.etTelefone);
        etPessoa = (EditText) findViewById(R.id.etPessoa);

        contatoDAO = new ContatoDAO(this);

        codigo = getIntent().getIntExtra("CONTATO_CODIGO", 0);
        if(codigo > 0){
            contato = new Contato();
            contato = contatoDAO.buscarContatoPorCodigo(codigo);
            etRazaoSocial.setText(contato.getRazao());
            etTelefone.setText(contato.getTelefone());
            etPessoa.setText(contato.getNome());
            setTitle("Atualizar");
        }
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
            case R.id.action_cadastro_excluir:
                if(codigo > 0) {
                    contatoDAO.removeContato(codigo);
                    Toast.makeText(this, "Contato removido com sucesso!", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(this, ListaContatoActivity.class));
                break;
            case R.id.action_cadastro_salvar:
                this.cadastrar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cadastrar(){
        boolean valida = true;

        String razaoSocial = etRazaoSocial.getText().toString();
        String telefone = etTelefone.getText().toString();
        String pessoa = etPessoa.getText().toString();

        if(razaoSocial == null || razaoSocial.equals("")){
            valida = false;
            etRazaoSocial.setError("Campo Razão social é obrigatório!", null);
        }

        if(telefone == null || telefone.equals("")){
            valida = false;
            etTelefone.setError("Campo Telefone é obrigatório!");
        }

        if(valida){
            contato = new Contato();
            contato.setRazao(razaoSocial);
            contato.setTelefone(telefone);
            contato.setNome(pessoa);

            //Para atualizar
            if(codigo > 0){
                contato.setCodigo(codigo);
            }

            long resultado = contatoDAO.salvarContatos(contato);

            if(resultado != -1){
                Toast.makeText(this, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this, ListaContatoActivity.class));
            }else{
                Toast.makeText(this, "Erro ao cadastrar contato!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
