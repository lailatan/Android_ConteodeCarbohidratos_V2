package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.conteodecarbohidratos.db.UnidadDBAccess;

public class BackupRestoreActivity extends AppCompatActivity {
    ProgressBar indicadorPB;
    TextView progresoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_restore);

        indicadorPB = findViewById(R.id.indicadorPB);
        progresoTV = findViewById(R.id.progresoTV);
        indicadorPB.setVisibility(View.GONE);
    }

    public void clickBackup(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alertDialog.setMessage(R.string.backup_confirmation);
        alertDialog.setTitle(R.string.backup);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                    GenerarBackup();
                }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    private void GenerarBackup(){
        String mensaje="";
        progresoTV.setText(mensaje);
        indicadorPB.setMax(120);
        indicadorPB.setProgress(0);
        indicadorPB.setVisibility(View.VISIBLE);

        /*
        mensaje= Utils.BackUp(BackupRestoreActivity.this, "conteocarbs.db");
        progresoTV.setText(mensaje + "\n" );
        indicadorPB.setProgress(120);
        */

        mensaje= Utils.BackUp(BackupRestoreActivity.this, "alimentos_tabla.db");
        progresoTV.setText(mensaje + "\n" );
        indicadorPB.setProgress(20);

        mensaje= Utils.BackUp(BackupRestoreActivity.this, "alimentos_personales.db");
        progresoTV.setText(progresoTV.getText().toString() + "\n" + mensaje + "\n");
        indicadorPB.setProgress(40);

        mensaje= Utils.BackUp(BackupRestoreActivity.this, "categorias.db");
        progresoTV.setText(progresoTV.getText().toString() +  "\n" + mensaje + "\n");
        indicadorPB.setProgress(60);

        mensaje= Utils.BackUp(BackupRestoreActivity.this, "marcas.db");
        progresoTV.setText(progresoTV.getText().toString() +  "\n" + mensaje + "\n");
        indicadorPB.setProgress(80);

        mensaje= Utils.BackUp(BackupRestoreActivity.this, "unidades.db");
        progresoTV.setText(progresoTV.getText().toString() +  "\n" + mensaje + "\n");
        indicadorPB.setProgress(100);

        mensaje= Utils.BackUp(BackupRestoreActivity.this, "notas.db");
        progresoTV.setText(progresoTV.getText().toString() +  "\n" + mensaje + "\n");
        indicadorPB.setProgress(120);

    }

    private void GenerarRestore() {
        String mensaje="";
        progresoTV.setText(mensaje);
        indicadorPB.setMax(120);
        indicadorPB.setProgress(0);
        indicadorPB.setVisibility(View.VISIBLE);
        /*
        mensaje= Utils.Restore(BackupRestoreActivity.this, "conteocarbs.db");
        progresoTV.setText(mensaje + "\n" );
        indicadorPB.setProgress(120);
        */
        mensaje= Utils.Restore(BackupRestoreActivity.this, "alimentos_tabla.db");
        progresoTV.setText(mensaje + "\n" );
        indicadorPB.setProgress(20);

        mensaje= Utils.Restore(BackupRestoreActivity.this, "alimentos_personales.db");
        progresoTV.setText(progresoTV.getText().toString() + "\n" + mensaje + "\n");
        indicadorPB.setProgress(40);

        mensaje= Utils.Restore(BackupRestoreActivity.this, "categorias.db");
        progresoTV.setText(progresoTV.getText().toString() +  "\n" + mensaje + "\n");
        indicadorPB.setProgress(60);

        mensaje= Utils.Restore(BackupRestoreActivity.this, "marcas.db");
        progresoTV.setText(progresoTV.getText().toString() +  "\n" + mensaje + "\n");
        indicadorPB.setProgress(80);

        mensaje= Utils.Restore(BackupRestoreActivity.this, "unidades.db");
        progresoTV.setText(progresoTV.getText().toString() +  "\n" + mensaje + "\n");
        indicadorPB.setProgress(100);

        mensaje= Utils.Restore(BackupRestoreActivity.this, "notas.db");
        progresoTV.setText(progresoTV.getText().toString() +  "\n" + mensaje + "\n");
        indicadorPB.setProgress(120);

    }

    public void clickRestore(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alertDialog.setMessage(R.string.restore_confirmation);
        alertDialog.setTitle(R.string.restore);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                GenerarRestore();
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    public void clickVolver(View view) {
        onBackPressed();
    }
}