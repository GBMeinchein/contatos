package br.com.sistemasecia.contato;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import java.util.List;

import adapter.ContatoAdapter;
import adapter.ContatoArrayAdapter;
import dao.ContatoDAO;
import model.Contato;

public class ListaContatoActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private ContatoDAO contatoDAO;
    private List<Contato> contatoList;
    private ContatoArrayAdapter contatoAdapter;
    private int idPosicao;
    private EditText etContato;
    private AlertDialog mensagemConfirmacao;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contato);

        contatoDAO = new ContatoDAO(this);
        contatoList = contatoDAO.listarContatos();
        contatoAdapter = new ContatoArrayAdapter<Contato>(this, contatoList);

        etContato = (EditText) findViewById(R.id.etContato);

        listView = (ListView) findViewById(R.id.lvContatos);
        listView.setAdapter(contatoAdapter);

        listView.setOnItemClickListener(this);

        etContato.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Toast.makeText(getApplicationContext(), "entrando metodo", Toast.LENGTH_SHORT).show();
                contatoAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_contato, menu);

        //SearchView searchView=new SearchView(this);

        //MenuItem searchItem = menu.findItem(R.id.etContato);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setQueryHint("Pesquisar");
/*
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                              public boolean onQueryTextChange(String newText) {

                                                  contatoAdapter.getFilter().filter(newText);

                                                  return true;


                                              }

                                              public boolean onQueryTextSubmit(String query) {
                                                  contatoAdapter.getFilter().filter(query);

                                                  return true;
                                              }
                                          }
        );*/
        /*MenuItem item=menu.findItem(R.id.item_busca);
        searchView=(SearchView)item.getActionView();
        */
        //m.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //m.setActionView(searchView);
/*
        View v = (View) menu.findItem(R.id.search).getActionView();

        EditText txtSearch = ( EditText ) v.findViewById(R.id.txt_search);

        txtSearch.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(getBaseContext(), "Search : " + v.getText(), Toast.LENGTH_SHORT).show();
                return false;
           }
        });
*/
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onPrepareOptionsMenu(Menu menu){

        menu.add(0, R.id.etContato, 1, "busca");
        menu.add(0, 1, 1, "busca2");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_lista_cadastrar:
                startActivity(new Intent(this, CadContatoActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idPosicao = position;
        //Toast.makeText(this, "entrando metodo", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CadContatoActivity.class);
        int codigo = contatoList.get(idPosicao).getCodigo();
        intent.putExtra("CONTATO_CODIGO", codigo);
        startActivity(intent);
    }


}
