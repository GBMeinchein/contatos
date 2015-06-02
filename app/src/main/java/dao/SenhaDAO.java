package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Contato;
import model.Senha;

/**
 * Created by maq8 on 28/05/2015.
 */
public class SenhaDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public SenhaDAO(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase(){
        if(database == null){
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    private Senha criarSenha(Cursor cursor){
        Senha model = new Senha(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Senhas.SenCodigo)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Senhas.SenValor))
        );
        return model;
    }

    public long salvarSenhas(Senha senha){
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Senhas.SenCodigo, senha.getCodigo());
        valores.put(DatabaseHelper.Senhas.SenValor, senha.getValor());

        return getDatabase().update(DatabaseHelper.Senhas.TABELA, valores, "SenCodigo = ?", new String[]{Integer.toString(senha.getCodigo())});
    }


    public Senha buscarSenhaPorCodigo(int codigo){
        Cursor cursor = getDatabase().query(DatabaseHelper.Senhas.TABELA, DatabaseHelper.Senhas.COLUNAS, "SenCodigo = ?", new String[]{Integer.toString(codigo)}, null, null, null);

        if(cursor.moveToNext()){
            Senha model = criarSenha(cursor);
            cursor.close();
            return model;
        }
        return null;
    }

}
