package br.com.sistemasecia.contato;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.ContatoArrayAdapter;
import dao.ContatoDAO;
import model.Contato;
import util.Mensagem;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener{//}, DialogInterface.OnClickListener {

    private ListView listView;
    private ContatoDAO contatoDAO;
    private List<Contato> contatoList;
    private ContatoArrayAdapter contatoAdapter;
    private int idPosicao = -1, oldPosicao, codigoContato, selected;
    private EditText etContato;
    private Button btEditar, btExcluir, btLigar, btAdicionar, btLimpar;
    //private AlertDialog alertDialog, alertConfirmacao;
    private Context contexto;
    private AdapterView<?> adapterView;
    private Contato contatoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contatoDAO = new ContatoDAO(this);
        contatoList = contatoDAO.listarContatos();
        contatoAdapter = new ContatoArrayAdapter<Contato>(this, contatoList);
        contexto = this;
        //alertConfirmacao = Mensagem.criarDialogConfirmacao(this);

        etContato = (EditText) findViewById(R.id.etContato);

        btAdicionar = (Button) findViewById(R.id.btNovo);
        btEditar = (Button) findViewById(R.id.btEditar);
        //btExcluir = (Button) findViewById(R.id.btExcluir);
        btLigar = (Button) findViewById(R.id.btLigar);
        btLimpar = (Button) findViewById(R.id.btLimpar);

        listView = (ListView) findViewById(R.id.lvContatos);
        listView.setAdapter(contatoAdapter);

        //campos apagados quando iniciado
        //btExcluir.setVisibility(View.INVISIBLE);
        btEditar.setVisibility(View.INVISIBLE);
        btLigar.setVisibility(View.INVISIBLE);

        listView.setOnItemClickListener(this);

        selected = -1;
        contatoAdapter.select(selected);

        etContato.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Apaga botoes de limpar e adicionar
                btAdicionar.setVisibility(View.VISIBLE);
                btLimpar.setVisibility(View.VISIBLE);

                //Liga botões de editar
                //btExcluir.setVisibility(View.INVISIBLE);
                btEditar.setVisibility(View.INVISIBLE);
                btLigar.setVisibility(View.INVISIBLE);
                //adapterView.getChildAt(idPosicao).setBackgroundColor(Color.parseColor("#EEE9E9")); //apaga posição antiga
                selected = -1;
                contatoAdapter.select(selected);
                idPosicao = -1;
            }
        });

        //Adicionar
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(contexto, CadContatoActivity.class));
            }
        });

        //limpar
        btLimpar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                etContato.setText("");
            }
        });

        //fazer ligação
        btLigar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                contatoList = contatoAdapter.getLista();
                String telefone = contatoList.get(idPosicao).getTelefone();
                contatoAdapter.getItem(selected);
                if(telefone != null && telefone.equals("") == false) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + telefone));
                    startActivity(callIntent);
                }
            }
        });

        //Editar
        btEditar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                contatoList = contatoAdapter.getLista();
                codigoContato = contatoList.get(selected).getCodigo();
                Intent intent = new Intent(contexto, CadContatoActivity.class);
                intent.putExtra("CONTATO_CODIGO", codigoContato);
                startActivity(intent);
            }
        });

        //excluir
        //btExcluir.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
        //        alertConfirmacao.show();
        //    }
        //});


        etContato.addTextChangedListener(new

                 TextWatcher() {
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
             }
        );
        }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        adapterView = parent;
        //idPosicao = position;

        if(idPosicao == -1){
            //Apaga botoes de limpar e adicionar
            btAdicionar.setVisibility(View.INVISIBLE);
            btLimpar.setVisibility(View.INVISIBLE);

            //Liga botões de editar
           //btExcluir.setVisibility(View.VISIBLE);
            btEditar.setVisibility(View.VISIBLE);
            btLigar.setVisibility(View.VISIBLE);
        }
        if(idPosicao == -1 || position != idPosicao){
            //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();

            selected = position;
            contatoAdapter.select(position);
            //listView.setItemChecked(position, true);
            //parent.getChildAt(idPosicao).setBackgroundColor(Color.parseColor("#EEE9E9")); //apaga posição antiga
            //parent.getChildAt(position).setBackgroundColor(Color.parseColor("#3366FF")); //acende nova posição
            idPosicao = position;
        }
    }
/*
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                codigoContato = contatoList.get(idPosicao).getCodigo();

                contatoList.remove(idPosicao);
                contatoDAO.removeContato(codigoContato);
                listView.invalidateViews();
                btAdicionar.setVisibility(View.VISIBLE);
                btLimpar.setVisibility(View.VISIBLE);

                //Liga botões de editar
                btExcluir.setVisibility(View.INVISIBLE);
                btEditar.setVisibility(View.INVISIBLE);
                btLigar.setVisibility(View.INVISIBLE);
                //adapterView.getChildAt(idPosicao).setBackgroundColor(Color.parseColor("#EEE9E9")); //apaga posição antiga
                idPosicao = -1;
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacao.dismiss();
                break;
        }
    }*/
}
