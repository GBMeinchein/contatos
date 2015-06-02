package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maq8 on 07/05/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String BANCO_DADOS = "SecContato";
    private static final int VERSAO = 5;

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
                 + "CntTelefone VARCHAR(24), "
                 + "CntCelular VARCHAR(24), "
                 + "CntEmail VARCHAR(60), "
                 + "CntOrigem VARCHAR(1)) "
        );

        //Tabela de senhas
        db.execSQL("CREATE TABLE Senhas("
                        + "SenCodigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "SenValor VARCHAR(90))"
        );

        db.execSQL("INSERT INTO Senhas VALUES(1, 'asd')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Contatos");
        db.execSQL("DROP TABLE Senhas");
        onCreate(db);
    }

    public static class Contatos{
        public static final String TABELA = "Contatos";
        public static final String CntCodigo = "CntCodigo";
        public static final String CntRazSocial = "CntRazSocial";
        public static final String CntNomePessoa = "CntNomePessoa";
        public static final String CntTelefone = "CntTelefone";
        public static final String CntCelular = "CntCelular";
        public static final String CntEmail = "CntEmail";
        public static final String CntOrigem = "CntOrigem";

        public static final String[] COLUNAS = new String[]{
            CntCodigo, CntRazSocial, CntNomePessoa, CntTelefone, CntCelular, CntEmail, CntOrigem
        };
    }

    public static class Senhas{
        public static final String TABELA = "Senhas";
        public static final String SenCodigo = "SenCodigo";
        public static final String SenValor = "SenValor";

        public static final String[] COLUNAS = new String[]{
                SenCodigo, SenValor
        };
    }
}
