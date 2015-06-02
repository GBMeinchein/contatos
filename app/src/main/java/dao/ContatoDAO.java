package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import model.Contato;

public class ContatoDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public ContatoDAO(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase(){
        if(database == null){
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    private Contato criarContato(Cursor cursor){
        Contato model = new Contato(
            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Contatos.CntCodigo)),
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contatos.CntRazSocial)),
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contatos.CntNomePessoa)),
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contatos.CntTelefone)),
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contatos.CntCelular)),
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contatos.CntEmail)),
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contatos.CntOrigem))
        );
        return model;
    }

    public List<Contato> listarContatos(){
        Cursor cursor = getDatabase().query(DatabaseHelper.Contatos.TABELA, DatabaseHelper.Contatos.COLUNAS, null, null, null, null, DatabaseHelper.Contatos.CntRazSocial);

        List<Contato> contatos = new ArrayList<Contato>();
        while (cursor.moveToNext()){
            Contato model = criarContato(cursor);
            contatos.add(model);
        }
        cursor.close();
        return contatos;
    }

    public long salvarContatos(Contato contato){
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Contatos.CntRazSocial, contato.getRazao());
        valores.put(DatabaseHelper.Contatos.CntTelefone, contato.getTelefone());
        valores.put(DatabaseHelper.Contatos.CntCelular, contato.getCelular());
        valores.put(DatabaseHelper.Contatos.CntNomePessoa, contato.getNome());
        valores.put(DatabaseHelper.Contatos.CntEmail, contato.getEmail());
        valores.put(DatabaseHelper.Contatos.CntOrigem, contato.getOrigem());

        if(contato.getCodigo() != 0) {
            return getDatabase().update(DatabaseHelper.Contatos.TABELA, valores, "CntCodigo = ?", new String[]{Integer.toString(contato.getCodigo())});
        }
        return getDatabase().insert(DatabaseHelper.Contatos.TABELA, null, valores);
    }

    public boolean removeContato(int codigo){
        return getDatabase().delete(DatabaseHelper.Contatos.TABELA, "CntCodigo = ?", new String[]{Integer.toString(codigo)}) > 0;
    }

    public boolean removeContatoPorRazao(String razao){
        return getDatabase().delete(DatabaseHelper.Contatos.TABELA, "CntRazSocial = ? and CntOrigem = 'I'", new String[]{razao}) > 0;
    }

    public Contato buscarContatoPorCodigo(int codigo){
        Cursor cursor = getDatabase().query(DatabaseHelper.Contatos.TABELA, DatabaseHelper.Contatos.COLUNAS, "CntCodigo = ?", new String[]{Integer.toString(codigo)}, null, null, null);

        if(cursor.moveToNext()){
            Contato model = criarContato(cursor);
            cursor.close();
            return model;
        }

        return null;
    }

    public void fechar(){
        databaseHelper.close();
        database = null;
    }
}
