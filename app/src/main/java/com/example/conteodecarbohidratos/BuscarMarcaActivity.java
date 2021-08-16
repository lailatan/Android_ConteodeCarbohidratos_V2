package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Nota;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.NotaHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BuscarMarcaActivity extends AppCompatActivity {
    static final String C_MARCA = "marca";
    ListView marcasListaLV;
    EditText nombreET;
    TextView view_vacio;
    TextView limpiarBT;
    TextView buscarBT;
    RadioGroup mostrarRG;

    private MantenimientoAdapter  adaptadorDeListaMarcas;
    Boolean vuelvodeActivityDetalle=false;
    Boolean tengoDatos=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_marca);
        marcasListaLV = findViewById(R.id.marcasListaLV);
        nombreET=findViewById(R.id.nombreET);
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
                Intent intent = new Intent(BuscarMarcaActivity.this, MarcaActivity.class);
                intent.putExtra(C_MARCA, (Marca) null);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (tengoDatos) {
            if (vuelvodeActivityDetalle ) {
                vuelvodeActivityDetalle = false;
                BuscarMarcas();
            } else LimpiarControles();
        }
    }

    public void clickBuscarMarcas(View view) {
        Utils.hideKeyboard(this);
        BuscarMarcas();
    }

    private void BuscarMarcas(){
        String nombre= nombreET.getText().toString();

        MarcaDBAccess marcaDBA =MarcaDBAccess.getInstance(this);
        marcaDBA.open();
        List<Object> listaMarcas = marcaDBA.getMarcasPorNombre(nombre);
        marcaDBA.close();

        adaptadorDeListaMarcas = new MantenimientoAdapter(getApplicationContext(), listaMarcas);

        marcasListaLV.setAdapter(adaptadorDeListaMarcas);

        if(listaMarcas.size()==0){
            view_vacio.setText(R.string.not_found);

        } else {
            view_vacio.setText("");
            tengoDatos=true;
            marcasListaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    vuelvodeActivityDetalle=true;
                    Intent abrirMarca = new Intent(getApplicationContext(), MarcaActivity.class);
                    abrirMarca.putExtra(C_MARCA, ((Marca) adaptadorDeListaMarcas.getItem(position)));
                    startActivity(abrirMarca);
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
        marcasListaLV.setAdapter(null);
        //adaptadorDeListaAlimentos.clear();
        nombreET.setText("");
        view_vacio.setText("");
    }
    public void clickMostrarCriterio(View view) {
        Utils.hideKeyboard(this);
        MostrarCritetio(Integer.parseInt(((RadioButton)view).getTag().toString()   ));
    }

    private void MostrarCritetio(Integer tag) {
        if (tag==0) {
            nombreET.setVisibility(View.GONE);
            buscarBT.setVisibility(View.GONE);
            limpiarBT.setVisibility(View.GONE);

        } else{
            nombreET.setVisibility(View.VISIBLE);
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