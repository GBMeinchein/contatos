package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maq8 on 07/05/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String BANCO_DADOS = "SecContato";
    private static final int VERSAO = 1;

    public DatabaseHelper(Context context){
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tabela de contato
        db.execSQL("CREATE TABLE Contatos("
                 + "CntCodigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + "CntRazSocial VARCHAR(90), "
                 + "CntNomePessoa VARCHAR(50), "
                 + "CntTelefone VARCHAR(24)) "
        );

        db.execSQL("INSERT INTO Contatos(CntRazSocial, CntNomePessoa, CntTelefone) VALUES('Sistemas e Cia', 'Milto Ziehlsdorf', '33702192')");
        db.execSQL("INSERT INTO Contatos(CntRazSocial, CntNomePessoa, CntTelefone) VALUES('Rosil', 'Catarina de Jesus', '32732000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Contatos");
        onCreate(db);
    }

    public static class Contatos{
        public static final String TABELA = "Contatos";
        public static final String CntCodigo = "CntCodigo";
        public static final String CntRazSocial = "CntRazSocial";
        public static final String CntNomePessoa = "CntNomePessoa";
        public static final String CntTelefone = "CntTelefone";

        public static final String[] COLUNAS = new String[]{
            CntCodigo, CntRazSocial, CntNomePessoa, CntTelefone
        };
    }

}
