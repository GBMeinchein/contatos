package br.com.sistemasecia.contato;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
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


public class MainActivity extends Activity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    private ListView listView;
    private ContatoDAO contatoDAO;
    private List<Contato> contatoList;
    private ContatoArrayAdapter contatoAdapter;
    private int idPosicao = -1, oldPosicao, codigoContato, selected;
    private EditText etContato;
    private Button btEditar, btExcluir, btLigar, btAdicionar, btLimpar;
    private AlertDialog alertDialogEmail,alertConfirmacao;
    private Context contexto;
    private Activity activity;
    private View view;
    private AdapterView<?> adapterView;
    private Contato contatoSelecionado;
    private String telefone, celular, emailContato, qualAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contatoDAO = new ContatoDAO(this);
        contatoList = contatoDAO.listarContatos();
        contatoAdapter = new ContatoArrayAdapter<Contato>(this, contatoList);
        contexto = this;
        activity = this;
        view = findViewById(R.id.vwLista);
        view.setBackgroundColor(Color.parseColor("#363636"));

        etContato = (EditText) findViewById(R.id.etContato);

        btAdicionar = (Button) findViewById(R.id.btNovo);
        btEditar = (Button) findViewById(R.id.btEditar);
        btLigar = (Button) findViewById(R.id.btLigar);
        btLimpar = (Button) findViewById(R.id.btLimpar);

        listView = (ListView) findViewById(R.id.lvContatos);
        listView.setAdapter(contatoAdapter);

        //campos apagados quando iniciado
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
                telefone = contatoList.get(idPosicao).getTelefone();
                celular = contatoList.get(idPosicao).getCelular();
                if(celular == null){
                    contatoAdapter.getItem(selected);
                    if(telefone != null && telefone.equals("") == false) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + telefone));
                        startActivity(callIntent);
                    }
                }else{
                    CharSequence[] opcoes = {telefone, celular};
                    qualAlert = "ligar";
                    alertConfirmacao = Mensagem.criarAlertDialog(activity, opcoes);
                    alertConfirmacao.show();
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

        if(selected == position){

            qualAlert = "mensagem";
            AlertDialog alertDialogEmail = Mensagem.opcoesEmail(this);
            alertDialogEmail.show();

         }

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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case 0:
                if (qualAlert == "ligar") {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + telefone));
                    startActivity(callIntent);
                    break;
                }else {
                    contatoList = contatoAdapter.getLista();
                    emailContato = contatoList.get(selected).getEmail();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    //intent.setType("message/rfc822");

                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailContato});
                    //intent.putExtra(Intent.EXTRA_SUBJECT, "Recado");
                    //intent.putExtra(Intent.EXTRA_TEXT, "enviando email");
                    Intent mailer = Intent.createChooser(intent, null);
                    startActivity(mailer);
                    break;
                }
            case 1:
                if (qualAlert == "ligar") {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + celular));
                    startActivity(callIntent);
                    break;
                }else {
                    contatoList = contatoAdapter.getLista();
                    celular = contatoList.get(selected).getCelular();
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", celular);
                    //smsIntent.putExtra("sms_body","Body of Message");
                    startActivity(smsIntent);
                    break;
                }
            case 2:
                contatoList = contatoAdapter.getLista();
                celular = contatoList.get(selected).getCelular();
                Uri uri = Uri.parse("smsto:" + celular);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, ""));
                break;
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
