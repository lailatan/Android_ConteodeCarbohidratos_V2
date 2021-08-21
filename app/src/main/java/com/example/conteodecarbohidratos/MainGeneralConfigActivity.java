package com.example.conteodecarbohidratos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainGeneralConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_general_config);
    }

    public void clickAlimentosGenerales(View view) {
        Intent lanzarBuscarAlimento = new Intent(MainGeneralConfigActivity.this, BuscarAlimentoEnTablaActivity.class);
        startActivity(lanzarBuscarAlimento);
    }

    public void clickAlimentosPersonales(View view) {
        Intent lanzarBuscarAlimento = new Intent(MainGeneralConfigActivity.this, BuscarAlimentoActivity.class);
        startActivity(lanzarBuscarAlimento);
    }


    public void clickBachupRestore(View view) {
        Intent lanzarBR = new Intent(MainGeneralConfigActivity.this, BackupRestoreActivity.class);
        startActivity(lanzarBR);
        finish();
    }

    public void clickConfigDatos(View view) {
        Intent lanzarMantenimiento = new Intent(MainGeneralConfigActivity.this, MainDatosConfigActivity.class);
        startActivity(lanzarMantenimiento);
    }

    public void clickFABCancelar(View view) {
        Utils.hideKeyboard(this);
        onBackPressed();
    }

}
