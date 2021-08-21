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

    public void clickArmarComida(View view) {
        Intent lanzarComida = new Intent(MainActivity.this, ComidaActivity.class);
        startActivity(lanzarComida);
    }

    public void clickBuscarNotas(View view) {
        Intent lanzarBuscarNota = new Intent(MainActivity.this, BuscarNotaActivity.class);
        startActivity(lanzarBuscarNota);
    }

    public void clickConfigGeneral(View view) {
        Intent lanzarMantenimiento = new Intent(MainActivity.this, MainGeneralConfigActivity.class);
        startActivity(lanzarMantenimiento);
    }

}
