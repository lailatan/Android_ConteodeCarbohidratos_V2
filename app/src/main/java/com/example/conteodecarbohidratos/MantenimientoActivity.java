package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MantenimientoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);
    }

    public void clickBuscarMarcas(View view) {
        Intent lanzarBuscar = new Intent(MantenimientoActivity.this, BuscarMarcaActivity.class);
        startActivity(lanzarBuscar);
        finish();
    }

    public void clickBuscarUnidades(View view) {
        Intent lanzarBuscar = new Intent(MantenimientoActivity.this, BuscarUnidadActivity.class);
        startActivity(lanzarBuscar);
        finish();
    }

    public void clickBuscarCategorias(View view) {
        Intent lanzarBuscar = new Intent(MantenimientoActivity.this, BuscarCategoriaActivity.class);
        startActivity(lanzarBuscar);
        finish();
    }

    public void clickBachupRestore(View view) {
        Intent lanzarBR = new Intent(MantenimientoActivity.this, BackupRestoreActivity.class);
        startActivity(lanzarBR);
        finish();
    }
}