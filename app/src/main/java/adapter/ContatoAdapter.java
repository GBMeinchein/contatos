package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.sistemasecia.contato.R;
import model.Contato;

/**
 * Created by maq8 on 07/05/2015.
 */
public class ContatoAdapter extends BaseAdapter {

    private Context context;
    private List<Contato> lista;

    public ContatoAdapter(Context ctx, List<Contato> contatos){
        this.context = ctx;
        this.lista = contatos;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Contato contato = lista.get(position);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_contatos, null);
        }

        TextView tvContatoNome = (TextView) view.findViewById(R.id.tvContatoNome);
        tvContatoNome.setText(contato.getRazao() + " / " + contato.getNome());

        //TextView tvContatoTelefone = (TextView) view.findViewById(R.id.tvUsuarioTelefone);
        //tvContatoTelefone.setText(contato.getTelefone());

        return view;
    }


}
