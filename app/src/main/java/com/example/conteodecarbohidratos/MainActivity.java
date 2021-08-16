package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickAlimentosGenerales(View view) {
        Intent lanzarBuscarAlimento = new Intent(MainActivity.this, BuscarAlimentoEnTablaActivity.class);
        startActivity(lanzarBuscarAlimento);
    }

    public void clickAlimentosPersonales(View view) {
        Intent lanzarBuscarAlimento = new Intent(MainActivity.this, BuscarAlimentoActivity.class);
        startActivity(lanzarBuscarAlimento);
    }

    public void clickBuscarNotas(View view) {
        Intent lanzarBuscarNota = new Intent(MainActivity.this, BuscarNotaActivity.class);
        startActivity(lanzarBuscarNota);
    }

    public void clickMantenimiento(View view) {
        Intent lanzarMantenimiento = new Intent(MainActivity.this, MantenimientoActivity.class);
        startActivity(lanzarMantenimiento);
    }

    public void clickArmarComida(View view) {
        Intent lanzarComida = new Intent(MainActivity.this, ComidaActivity.class);
        startActivity(lanzarComida);
    }
}
