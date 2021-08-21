package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainDatosConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_datos_config);
    }

    public void clickBuscarMarcas(View view) {
        Intent lanzarBuscar = new Intent(MainDatosConfigActivity.this, BuscarMarcaActivity.class);
        startActivity(lanzarBuscar);
        finish();
    }

    public void clickBuscarUnidades(View view) {
        Intent lanzarBuscar = new Intent(MainDatosConfigActivity.this, BuscarUnidadActivity.class);
        startActivity(lanzarBuscar);
        finish();
    }

    public void clickBuscarCategorias(View view) {
        Intent lanzarBuscar = new Intent(MainDatosConfigActivity.this, BuscarCategoriaActivity.class);
        startActivity(lanzarBuscar);
        finish();
    }
    public void clickFABCancelar(View view) {
        Utils.hideKeyboard(this);
        onBackPressed();
    }

}