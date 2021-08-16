package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Nota;
import com.example.conteodecarbohidratos.db.AlimentoHelper;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.NotaHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BuscarNotaActivity extends AppCompatActivity {
    static final String C_NOTA = "nota";
    ListView notaListaLV;
    EditText entradaTituloET;
    TextView view_vacio;
    TextView limpiarBT;
    TextView buscarBT;
    RadioGroup mostrarRG;
    private NotaAdapter  adaptadorDeListaNotas;
    Boolean vuelvodeActivityDetalle=false;
    Boolean tengoDatos=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_nota);

        notaListaLV = findViewById(R.id.notasListaLV);
        entradaTituloET=findViewById(R.id.entradaTituloET);
        view_vacio = findViewById(R.id.view_vacio);
        limpiarBT = findViewById(R.id.limpiarBT);
        buscarBT = findViewById(R.id.buscarBT);
        mostrarRG = findViewById(R.id.mostrarRG);

        // setup FAB
        FloatingActionButton fab =  findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vuelvodeActivityDetalle=true;
                Intent intent = new Intent(BuscarNotaActivity.this, NotaActivity.class);
                intent.putExtra(C_NOTA, (Nota) null);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tengoDatos) {
            if (vuelvodeActivityDetalle ) {
                vuelvodeActivityDetalle = false;
                BuscarNotas();
            } else LimpiarControles();
        }
    }

    public void clickBuscarNotas(View view) {
        Utils.hideKeyboard(this);
        BuscarNotas();
    }

    private void BuscarNotas(){
        String nombre= entradaTituloET.getText().toString();

        NotaHelper notaDBA = new NotaHelper(this);
        List<Nota> listaNotas = notaDBA.getNotasPorTitulo(nombre);
        notaDBA.close();

        adaptadorDeListaNotas = new NotaAdapter(getApplicationContext(), listaNotas);
        notaListaLV.setAdapter(adaptadorDeListaNotas);

        if(listaNotas.size()==0){
            view_vacio.setText(R.string.not_found);

        } else {
            view_vacio.setText("");
            tengoDatos=true;
            notaListaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    vuelvodeActivityDetalle=true;
                    Intent abrirNota = new Intent(getApplicationContext(), NotaActivity.class);
                    abrirNota.putExtra(C_NOTA, ((Nota) adaptadorDeListaNotas.getItem(position)));
                    startActivity(abrirNota);
                }
            });
        }
    }


    public void clickLimpiar(View view) {
        Utils.hideKeyboard(this);
        LimpiarControles();
    }

    public void LimpiarControles() {
        tengoDatos=false;
        vuelvodeActivityDetalle=false;
        notaListaLV.setAdapter(null);
        //adaptadorDeListaAlimentos.clear();
        entradaTituloET.setText("");
        view_vacio.setText("");
    }
    public void clickMostrarCriterio(View view) {
        Utils.hideKeyboard(this);
        MostrarCritetio(Integer.parseInt(((RadioButton)view).getTag().toString()   ));
    }

    private void MostrarCritetio(Integer tag) {
        if (tag==0) {
            entradaTituloET.setVisibility(View.GONE);
            buscarBT.setVisibility(View.GONE);
            limpiarBT.setVisibility(View.GONE);

        } else{
            entradaTituloET.setVisibility(View.VISIBLE);
            buscarBT.setVisibility(View.VISIBLE);
            limpiarBT.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        MostrarCritetio(mostrarRG.indexOfChild(findViewById(mostrarRG.getCheckedRadioButtonId())));
        //if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        //{
        //    Toast.makeText(this,"PORTRAIT",Toast.LENGTH_LONG).show();
        //    //add your code what you want to do when screen on PORTRAIT MODE
        //}
        //else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        //{
        //    Toast.makeText(this,"LANDSCAPE",Toast.LENGTH_LONG).show();
        //    //add your code what you want to do when screen on LANDSCAPE MODE
        //}
    }

    public void clickVolver(View view) {
        onBackPressed();
    }
}