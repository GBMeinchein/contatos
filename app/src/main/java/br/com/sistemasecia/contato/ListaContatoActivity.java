package br.com.sistemasecia.contato;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.ContatoAdapter;
import dao.ContatoDAO;
import model.Contato;


public class ListaContatoActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private ContatoDAO contatoDAO;
    private List<Contato> contatoList;
    private ContatoAdapter contatoAdapter;
    private int idPosicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contato);

        contatoDAO = new ContatoDAO(this);
        contatoList = contatoDAO.listarContatos();
        contatoAdapter = new ContatoAdapter(this, contatoList);

        listView = (ListView) findViewById(R.id.lvContatos);
        listView.setAdapter(contatoAdapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_contato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_lista_cadastrar) {
            startActivity(new Intent(this, CadContatoActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idPosicao = position;
        //Toast.makeText(this, "entrando metodo", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CadContatoActivity.class);
        int codigo = contatoList.get(idPosicao).getCodigo();
        intent.putExtra("CONTATO_CODIGO", codigo);
        startActivity(intent);
    }
}
