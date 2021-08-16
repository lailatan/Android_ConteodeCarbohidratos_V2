package com.example.conteodecarbohidratos;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Switch;
import android.widget.Toast;

import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Nota;
import com.example.conteodecarbohidratos.db.AlimentoHelper;
import com.example.conteodecarbohidratos.db.AlimentoTablaDBAccess;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.NotaHelper;
import com.example.conteodecarbohidratos.db.UnidadDBAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

public class Utils {
    private static String C_CARPETA="ConteoCH";
    private static String C_PACAGENAME="com.example.conteodecarbohidratos";
    private Utils(){}

    public static Integer ResolverImagenDesdeNombre (Context contexto, String imagen) {
        String uri = "@drawable/" + imagen;
        int imageResource = contexto.getResources().getIdentifier(uri, null, contexto.getPackageName());
        //Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), imageResource);
        return imageResource;
    }

    public static String BackUp(Context contexto,String dbSTR) {
        String mensaje="";
        try {
            //File sd = Environment.getExternalStorageDirectory();
            //File data = Environment.getDataDirectory();

            String carpeta_data_path = Environment.getDataDirectory() + "/data/"+ C_PACAGENAME + "/databases/";
            String carpeta_backup_path = Environment.getExternalStorageDirectory() + "/" + C_CARPETA + "/" ;
            //String carpeta_backup_path = contexto.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + C_CARPETA + "/" ;
            File carpeta_data_file = new File(carpeta_data_path);
            File carpeta_backup_file = new File(carpeta_backup_path);

            if (!carpeta_backup_file.exists())  carpeta_backup_file.mkdir();

            if (carpeta_backup_file.canWrite()) {

                //String currentDBPath = "//data//"+ C_PACAGENAME + "//databases//" + dbSTR ;
                //String backupDBPath = C_CARPETA + "//" + dbSTR;

                //File currentDB = new File(data, currentDBPath);
                //File backupDB = new File(sd, backupDBPath);

                File currentDB = new File(carpeta_data_file, dbSTR);
                File backupDB = new File(carpeta_backup_file, dbSTR);

                Log.d("backupDB path", "" + backupDB.getAbsolutePath());

                if (!(currentDB.exists())) {
                    currentDB=CrearArchivoDB(contexto,carpeta_data_file,dbSTR);
                    mensaje= "Se creó el archivo " + dbSTR + "\n";
                }

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    //Toast.makeText(contexto, "Copia realizada con éxito del Archivo: " + backupDBPath, Toast.LENGTH_SHORT).show();
                    mensaje = mensaje + contexto.getString(R.string.backup_success) +  C_CARPETA + "//" + dbSTR;
                }
            } else {
                //Toast.makeText(contexto, "No se pudo generar la copia de respaldo. Verifique los permisos de la aplicación.", Toast.LENGTH_SHORT).show();
                mensaje=contexto.getString(R.string.backup_fail_permission);
            }

        } catch (Exception e) {
            //Toast.makeText(contexto, "No se pudo generar la copia de respaldo. Verifique que tenga espacio en almacenamiento en el dispositivo y/o permisos de la aplicación.", Toast.LENGTH_SHORT).show();
            mensaje=contexto.getString(R.string.backup_fail_space_or_permission);
            e.printStackTrace();
        }
        return mensaje;
    }

    public static String Restore(Context contexto, String dbSTR) {
        String mensaje="";
        try {
            //File sd = Environment.getExternalStorageDirectory();
            //File data = Environment.getDataDirectory();
            String carpeta_data_path = Environment.getDataDirectory() + "/data/"+ C_PACAGENAME + "/databases/";
            String carpeta_backup_path = Environment.getExternalStorageDirectory() + "/" + C_CARPETA + "/" ;

            File carpeta_data_file = new File(carpeta_data_path);
            File carpeta_backup_file = new File(carpeta_backup_path);

            if (!carpeta_data_file.exists())  carpeta_data_file.mkdir();

            if (carpeta_backup_file.canWrite()) {
                //String currentDBPath = "//data//"+ C_PACAGENAME + "//databases//" +dbSTR;
                //String backupDBPath = C_CARPETA + "//" + dbSTR;
                //File currentDB = new File(data, currentDBPath);
                //File backupDB = new File(sd, backupDBPath);
                File currentDB = new File(carpeta_data_file, dbSTR);
                File backupDB = new File(carpeta_backup_file, dbSTR);

                if (backupDB.exists()) {
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    //Toast.makeText(contexto, "Archivo restaurado con éxito: \n" + backupDBPath, Toast.LENGTH_SHORT).show();
                    mensaje=contexto.getString(R.string.restore_success) +   C_CARPETA + "//" + dbSTR;
                } else {
                    //Toast.makeText(contexto, "No se encontró el Archivo: \n" + backupDBPath + ".", Toast.LENGTH_SHORT).show();
                    mensaje=contexto.getString(R.string.restore_fail_filenotfound) +   C_CARPETA + "//" + dbSTR;
                }
            } else {
                //Toast.makeText(contexto, "No se pueden restaurar los Archivos de respaldo. Verifique los permisos de la aplicación.", Toast.LENGTH_SHORT).show();
                mensaje=contexto.getString(R.string.restore_fail_permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(contexto, "No se pudo restaurar la copia de respaldo. Verifique que existan los archivos de respaldo, que tenga espacio libre de almacenamiento en el dispositivo y/o permisos de la aplicación.", Toast.LENGTH_SHORT).show();
            mensaje=contexto.getString(R.string.restore_fail_spaceorpermission);
        }
        return mensaje;
    }

    private static File CrearArchivoDB(Context contexto, File data, String dbSTR) {

        switch (dbSTR) {
            case "alimentos_tabla.db":
                AlimentoTablaDBAccess alimentosDBA = AlimentoTablaDBAccess.getInstance(contexto);
                alimentosDBA.open();
                alimentosDBA.close();
                break;
            case "alimentos_personales.db":
                AlimentoHelper alimentoDBA = new AlimentoHelper(contexto);
                alimentoDBA.getCategoriaenUso(1);
                alimentoDBA.close();
                break;
            case "notas.db":
                NotaHelper notaDBA = new NotaHelper(contexto);
                notaDBA.getNotasPorTitulo("aaa");
                notaDBA.close();
                break;
            case "marcas.db":
                MarcaDBAccess marcaDBA = MarcaDBAccess.getInstance(contexto);
                marcaDBA.open();
                marcaDBA.close();
                break;
            case "categorias.db":
                CategoriaDBAccess categoriaDBA = CategoriaDBAccess.getInstance(contexto);
                categoriaDBA.open();
                categoriaDBA.close();
                break;
            case "unidades.db":
                UnidadDBAccess unidadesDBA = UnidadDBAccess.getInstance(contexto);
                unidadesDBA.open();
                unidadesDBA.close();
                break;
        }

        File newDB = new File(data, dbSTR);
        return newDB;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
