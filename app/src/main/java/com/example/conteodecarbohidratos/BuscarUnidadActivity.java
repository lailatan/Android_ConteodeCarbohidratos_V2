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
import com.example.conteodecarbohidratos.clases.Unidad;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.UnidadDBAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BuscarUnidadActivity extends AppCompatActivity {
    static final String C_UNIDAD = "unidad";
    ListView unidadListaLV;
    EditText nombreET;
    TextView view_vacio;
    TextView limpiarBT;
    TextView buscarBT;
    RadioGroup mostrarRG;
    private MantenimientoAdapter  adaptadorDeListaUnidades;
    Boolean vuelvodeActivityDetalle=false;
    Boolean tengoDatos=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_unidad);

        unidadListaLV = findViewById(R.id.unidadesListaLV);
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
                Intent intent = new Intent(BuscarUnidadActivity.this, UnidadActivity.class);
                intent.putExtra(C_UNIDAD, (Unidad) null);
                startActivity(intent);
            }
        });

    }
    protected void onResume() {
        super.onResume();
        if (tengoDatos) {
            if (vuelvodeActivityDetalle ) {
                vuelvodeActivityDetalle = false;
                BuscarUnidades();
            } else LimpiarControles();
        }
    }

    public void clickBuscarUnidades(View view) {
        Utils.hideKeyboard(this);
        BuscarUnidades();
    }

    private void BuscarUnidades(){
        String nombre= nombreET.getText().toString();

        UnidadDBAccess unidadesDBA =UnidadDBAccess.getInstance(this);
        unidadesDBA.open();
        List<Object> listaUnidades = unidadesDBA.getUnidadesPorNombre(nombre);
        unidadesDBA.close();

        adaptadorDeListaUnidades = new MantenimientoAdapter(getApplicationContext(), listaUnidades);

        unidadListaLV.setAdapter(adaptadorDeListaUnidades);

        if(listaUnidades.size()==0){
            view_vacio.setText(R.string.not_found);

        } else {
            tengoDatos=true;
            view_vacio.setText("");
            unidadListaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    vuelvodeActivityDetalle=true;
                    Intent abrirUnidad = new Intent(getApplicationContext(), UnidadActivity.class);
                    abrirUnidad.putExtra(C_UNIDAD, ((Unidad) adaptadorDeListaUnidades.getItem(position)));
                    startActivity(abrirUnidad);
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
        unidadListaLV.setAdapter(null);
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