package br.com.sistemasecia.contato;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import util.Mensagem;

//@SuppressLint("NewApi")
public class ListaContatoActivity extends Activity{//ActionBarActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

    private ListView listView;
    private ContatoDAO contatoDAO;
    private List<Contato> contatoList;
    private ContatoArrayAdapter contatoAdapter;
    private int idPosicao, oldPosicao;
    private EditText etContato;
    private AlertDialog alertDialog, alertConfirmacao;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contato);

        contatoDAO = new ContatoDAO(this);
        contatoList = contatoDAO.listarContatos();
        contatoAdapter = new ContatoArrayAdapter<Contato>(this, contatoList);

        //alertDialog      = Mensagem.criarAlertDialog(this);
        alertConfirmacao = Mensagem.criarDialogConfirmacao(this);

        etContato = (EditText) findViewById(R.id.etContato);

        listView = (ListView) findViewById(R.id.lvContatos);
        listView.setAdapter(contatoAdapter);

        //listView.setTextFilterEnabled(true);
        //listView.setSelected(true);
        //listView.setOnItemClickListener(this);
/*
        ActionBar actionBar = this.getActionBar();
        EditText search = (EditText) actionBar.getCustomView().findViewById(R.id.txt_search);
        search.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Toast.makeText(ListaContatoActivity.this, "Search triggered", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
*/
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

/*
    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_contato, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int codigoContato = contatoList.get(idPosicao).getCodigo();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_lista_cadastrar:
                startActivity(new Intent(this, CadContatoActivity.class));
                break;
            case R.id.action_lista_editar:
                Intent intent = new Intent(this, CadContatoActivity.class);
                intent.putExtra("CONTATO_CODIGO", codigoContato);
                startActivity(intent);
                break;
            case R.id.action_lista_excluir:
                alertConfirmacao.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                contatoList.remove(idPosicao);
                contatoDAO.removeContato(codigoContato);
                listView.invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacao.dismiss();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listView.setItemChecked(position, true);
        if(idPosicao == 0 || position != idPosicao){
            parent.getChildAt(idPosicao).setBackgroundColor(Color.parseColor("#EEE9E9"));
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#3366FF"));
            idPosicao = position;
        }*/
        //listView.getChildAt(position).setBackgroundColor(0x3366FF);
        //view.setBackgroundColor(0x3366FF);
        //Toast.makeText(this, "etrando ha", Toast.LENGTH_SHORT).show();
        //alertDialog.show();
        //Toast.makeText(this, "entrando metodo", Toast.LENGTH_SHORT).show();
        /*
        Intent intent = new Intent(this, CadContatoActivity.class);
        int codigo = contatoList.get(idPosicao).getCodigo();
        intent.putExtra("CONTATO_CODIGO", codigo);
        startActivity(intent);*/
   /* }


    //@Override
    public void onClick(DialogInterface dialog, int which) {

        int id = contatoList.get(idPosicao).getCodigo();

        switch (which){
            case 0:
                Intent intent = new Intent(this, CadContatoActivity.class);
                intent.putExtra("USUARIO_CODIGO", id);
                startActivity(intent);
                break;
            case 1:
                //liga
                break;
            case 2:
                alertConfirmacao.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                contatoList.remove(idPosicao);
                contatoDAO.removeContato(id);
                listView.invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacao.dismiss();
                break;
            case 3:

                break;
        }
    }*/

}
