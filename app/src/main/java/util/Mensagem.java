package util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by maq8 on 14/05/2015.
 */
public class Mensagem {

    public static AlertDialog criarDialogConfirmacao(Activity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage("Deseja realmente excluir?");
        alert.setPositiveButton("Sim", (DialogInterface.OnClickListener) activity);
        alert.setNegativeButton("Não", (DialogInterface.OnClickListener) activity);

        return alert.create();
    }


    public static AlertDialog criarAlertDialog(Activity activity){
        final CharSequence[] items = {
                "Editar",
                "Ligar",
                "Excluir",
                "Cancelar"
        };

        AlertDialog.Builder alert= new AlertDialog.Builder(activity);
        alert.setTitle("Opções");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);
        return alert.create();
    }
}
