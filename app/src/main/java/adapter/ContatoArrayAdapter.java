package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.sistemasecia.contato.R;
import model.Contato;

/**
 * Created by maq8 on 15/05/2015.
 */
public class ContatoArrayAdapter<T> extends ArrayAdapter{

    private Context context;
    private List<Contato> lista;
    private List<Contato> itens;
    private List<Contato> itens_exibicao;
    private String prefix1, prefix2, prefix3 = "", prefix4 = "", prefix5 = "";
    private String prefixAux;
    private int tamanho, selected;
    private String valor, valorSemUltimaVirgula, primeiroCaracter;


    public ContatoArrayAdapter(Context context, List<Contato> contatos) {
        super(context, R.layout.activity_contatos, contatos);
        this.context = context;
        this.lista = contatos;
        this.itens = contatos;
        this.itens_exibicao = contatos;
    }

    public ContatoArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ContatoArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ContatoArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public ContatoArrayAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ContatoArrayAdapter(Context context, int resource, List<Contato> contatos) {
        super(context, resource, contatos);
        this.context = context;
        this.lista = contatos;
    }

    public ContatoArrayAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public int getCount() {
        return itens_exibicao.size();
    }

    @Override
    public Object getItem(int arg0) {
        return itens_exibicao.get(arg0);
    }

    public List<Contato> getLista(){
        return itens_exibicao;
    }

    public void select(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    public View getView(int position, View view, ViewGroup parent) {
        Contato contato = itens_exibicao.get(position);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_contatos, null);
        }

        TextView tvContatoNome = (TextView) view.findViewById(R.id.tvContatoNome);

        if(selected != -1 && position == selected) {
            tvContatoNome.setBackgroundColor(Color.parseColor("#3366FF"));
        }else{
            tvContatoNome.setBackgroundColor(Color.parseColor("#EEE9E9"));
        }

        tvContatoNome.setText(contato.getRazao() + " / " + contato.getNome());
        return view;
    }

    public Filter getFilter(){
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence filtro) {
                FilterResults results = new FilterResults();

                if (filtro == null || filtro.length() == 0) {
                    results.count = itens.size();
                    results.values = itens;
                }else{
                    //Toast.makeText(context, "3 "+filtro.toString(), Toast.LENGTH_SHORT).show();
                    prefixAux = filtro.toString();
                    tamanho = prefixAux.length();
                    valor = prefixAux.substring(tamanho - 1, tamanho);
                    primeiroCaracter = prefixAux.substring(0, 1);
                    valorSemUltimaVirgula = prefixAux.substring(0, tamanho - 1);
                    int numeroDeVirgulas = 0;
                    boolean segundaVirgula = false;
                    filtro = filtro.toString().toLowerCase();

                    if (filtro.toString().contains(",") && primeiroCaracter.equals(",") == false && (valor.equals(",") == false || valorSemUltimaVirgula.contains(","))) {
                        //cria um array para armazenar os objetos filtrados.
                        List<Contato> itens_filtrados = new ArrayList<Contato>();

                        for(int x = 0, y = 1; y < tamanho; x++, y++){

                            if(prefixAux.substring(x, y).equals(",")){
                                numeroDeVirgulas = numeroDeVirgulas + 1;
                            }
                        }

                        if(numeroDeVirgulas < 4) {

                            Scanner scan = new Scanner(filtro.toString()).useDelimiter(",");
                            while (scan.hasNext()) {
                                prefix1 = scan.next();
                                prefix2 = scan.next();

                                if (numeroDeVirgulas > 1) {
                                    segundaVirgula = true;
                                    prefix3 = scan.next();
                                }
                                if (numeroDeVirgulas > 2) {
                                    prefix4 = scan.next();
                                }
                                if (numeroDeVirgulas > 3) {
                                    prefix5 = scan.next();
                                }
                            }
                            scan.close();
                        }else{
                            Toast.makeText(getContext(), "Não é possível utilizar mais de quatro vírgulas para o filtro!", Toast.LENGTH_SHORT).show();
                        }

                        //percorre toda lista verificando se contem a palavra do filtro na descricao do objeto.
                        for (int i = 0; i < itens.size(); i++) {
                            Contato data = itens.get(i);

                            filtro = filtro.toString().toLowerCase();
                            String condicao = data.getRazao().toLowerCase();
                            condicao += data.getNome().toLowerCase();

                            if (condicao.contains(prefix1) && condicao.contains(prefix2) && (condicao.contains(prefix3) || numeroDeVirgulas <= 1) && (condicao.contains(prefix4) || numeroDeVirgulas <= 2) && (condicao.contains(prefix5) || numeroDeVirgulas <= 3)) {
                                //se conter adiciona na lista de itens filtrados.
                                itens_filtrados.add(data);
                            }
                        }
                        // Define o resultado do filtro na variavel FilterResults
                        results.count = itens_filtrados.size();
                        results.values = itens_filtrados;
                    }else if (segundaVirgula == false) {
                        //cria um array para armazenar os objetos filtrados.
                        List<Contato> itens_filtrados = new ArrayList<Contato>();
                        String filtroAux = filtro.toString();
                        if (valor.equals(",")) {
                            filtro = filtroAux.substring(0, tamanho - 1);
                        }
                        //percorre toda lista verificando se contem a palavra do filtro na descricao do objeto.
                        for (int i = 0; i < itens.size(); i++) {
                            Contato data = itens.get(i);

                            filtro = filtro.toString().toLowerCase();
                            String condicao = data.getRazao().toLowerCase();
                            condicao += data.getNome().toLowerCase();

                            if (condicao.contains(filtro)) {
                                //se conter adiciona na lista de itens filtrados.
                                itens_filtrados.add(data);
                            }
                        }
                        // Define o resultado do filtro na variavel FilterResults
                        results.count = itens_filtrados.size();
                        results.values = itens_filtrados;
                    }
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                    itens_exibicao = (List<Contato>) results.values; // Valores filtrados.
                    notifyDataSetChanged();  // Notifica a lista de alteração
            }
        };
        return filter;
    }
}
