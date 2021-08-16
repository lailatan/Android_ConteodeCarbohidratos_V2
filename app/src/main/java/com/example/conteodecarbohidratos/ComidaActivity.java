package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.AlimentoComida;

import java.util.ArrayList;
import java.util.List;

public class ComidaActivity extends AppCompatActivity {
    private static final String C_ALIMENTO_IDA = "alimentoida";
    private static final String C_ALIMENTO_VUELTA = "alimentovuelta";
    ArrayList<AlimentoComida> listaDeAlimentos;
    ListView alimentosListaLV;
    TextView carbTotalTV;
    TextView insulinaTotalTV;
    Integer cantidadTotalCarboHidratos=0;
    Float ratio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida);
        alimentosListaLV=findViewById(R.id.alimentosListaLV);
        insulinaTotalTV=findViewById(R.id.insulinaTotalTV);
        carbTotalTV=findViewById(R.id.carbTotalTV);
        listaDeAlimentos=new ArrayList<>();

        ratio=cargarConfiguracion();
    }

    public void clickFABCancelar(View view) {
        onBackPressed();
    }

    public void clickLimpiar(View view) { LimpiarControles(); }

    public void clickAgregarAlimento(View view) {
        Intent intent = new Intent(ComidaActivity.this, ComidaSelAlimentoActivity.class);
        intent.putExtra(C_ALIMENTO_IDA, (AlimentoComida) null);
        startActivityForResult(intent,1);
        //startActivity(intent);
    }

    public void LimpiarControles() {
        alimentosListaLV.setAdapter(null);
        cantidadTotalCarboHidratos=0;
        listaDeAlimentos.clear();
        carbTotalTV.setText("");
        insulinaTotalTV.setText("");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //si id es 0 no hago nada
            //si nombre viene en "" se debe borrar y id<>0
            AlimentoComida alimento = (AlimentoComida) data.getSerializableExtra(C_ALIMENTO_VUELTA);
            EvaluarAlimento(alimento);
        }
    }

    private void EvaluarAlimento(AlimentoComida alimento) {
        if (!(alimento.getMyId()==0)) {
            if (alimento.getMyNombre().equals("")) BorrarAlimento(alimento.getMyId(),alimento.getMyEditable());
            else AgregarModificarAlimento (alimento);
        }
    }

    private void AgregarModificarAlimento(AlimentoComida alimento) {
        Integer posicion=BuscarPosicionAlimento(alimento.getMyId(),alimento.getMyEditable());
        if (!(posicion ==-1)) {
            listaDeAlimentos.get(posicion).setMyUnidadPorcion(alimento.getMyUnidadPorcion());
            listaDeAlimentos.get(posicion).setMyCantidadPorcion(alimento.getMyCantidadPorcion());
            listaDeAlimentos.get(posicion).setMyCarbohidratosPorcion(alimento.getMyCarbohidratosPorcion());
            listaDeAlimentos.get(posicion).setMyUsaGramosComida(alimento.getMyUsaGramosComida());
            listaDeAlimentos.get(posicion).setMyUnidadComida(alimento.getMyUnidadComida());
            listaDeAlimentos.get(posicion).setMyCantidadComida(alimento.getMyCantidadComida());

            cantidadTotalCarboHidratos=cantidadTotalCarboHidratos - listaDeAlimentos.get(posicion).getMyCarbohidratosComida();
            listaDeAlimentos.get(posicion).setMyCarbohidratosComida(alimento.getMyCarbohidratosComida());
            cantidadTotalCarboHidratos = cantidadTotalCarboHidratos + alimento.getMyCarbohidratosComida();
        } else {
            if (listaDeAlimentos==null) {
                listaDeAlimentos=new ArrayList<>();
            }
            listaDeAlimentos.add(alimento);
            if (!(alimento.getMyCarbohidratosComida()==null))
                cantidadTotalCarboHidratos=cantidadTotalCarboHidratos + alimento.getMyCarbohidratosComida();
        }
        ActualizarLista();
    }

    private void BorrarAlimento(Integer idAlimento, Integer editable) {
        Integer posicion=BuscarPosicionAlimento(idAlimento, editable);
        if (!(posicion ==-1)) {
            cantidadTotalCarboHidratos=cantidadTotalCarboHidratos - listaDeAlimentos.get(posicion).getMyCarbohidratosComida();
            listaDeAlimentos.remove(posicion.intValue());
            ActualizarLista();
        }
    }

    private void ActualizarLista() {
        alimentosListaLV.setAdapter(null);
        CalcularCHInsulina();
        ComidaAdapter adaptadorDeListaAlimentos = new ComidaAdapter(getApplicationContext(), listaDeAlimentos);
        alimentosListaLV.setAdapter(adaptadorDeListaAlimentos);
        alimentosListaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(ComidaActivity.this, ComidaSelAlimentoActivity.class);
                intent.putExtra(C_ALIMENTO_IDA, listaDeAlimentos.get(position));
                startActivityForResult(intent,1);

            }
        });
    }

    private Integer BuscarPosicionAlimento(Integer idAlimento, Integer editable){
        Integer posicion=-1;
        if (!(listaDeAlimentos==null)) {
            if (listaDeAlimentos.size() > 0) {
                for (int i = 0; i < listaDeAlimentos.size(); i++) {
                    if (listaDeAlimentos.get(i).getMyId().equals(idAlimento)) {
                            if (listaDeAlimentos.get(i).getMyEditable().equals(editable)) posicion = i;
                    }
                }
            }
        }
        return posicion;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("carboTotales", cantidadTotalCarboHidratos);
        outState.putSerializable("alimentos", listaDeAlimentos);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cantidadTotalCarboHidratos = savedInstanceState.getInt("carboTotales");
        listaDeAlimentos = (ArrayList<AlimentoComida>) savedInstanceState.getSerializable("alimentos");

        if (listaDeAlimentos != null)  ActualizarLista();
    }

    //guardar configuración aplicación Android usando SharedPreferences
    private void guardarConfiguracion(Float ratioNuevo) {
        SharedPreferences prefs =
                getSharedPreferences("CalculoCarbs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("Ratio", ratioNuevo);
        editor.commit();
    }

    //cargar configuración aplicación Android usando SharedPreferences
    public Float cargarConfiguracion() {
        SharedPreferences prefs =
                getSharedPreferences("CalculoCarbs", Context.MODE_PRIVATE);
        return prefs.getFloat("Ratio", 0);
    }

    private void CalcularCHInsulina(){
        Float insulina=0F;
        carbTotalTV.setText(cantidadTotalCarboHidratos.toString() + " grCH");

        if (!(ratio==0F)){
            insulina=cantidadTotalCarboHidratos/ratio;
            insulinaTotalTV.setText(insulina.toString() + " U");
        }else {
            insulinaTotalTV.setText("");
        }
    }

    public void clickRatio(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ComidaActivity.this,R.style.MyDialogTheme);
        builder.setTitle(R.string.ratio);
        builder.setMessage(R.string.ratio_msg);
        // Set up the input
        final EditText input = new EditText(this);
        input.setLines(1);
        input.setPadding(100,100,100,20);
        if (ratio==0) input.setText("");
        else input.setText(ratio.toString());
        input.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoRatio = input.getText().toString();
                if (!nuevoRatio.isEmpty()){
                    ratio=Float.parseFloat((nuevoRatio));
                    guardarConfiguracion(ratio);
                } else {
                    ratio=0F;
                }
                CalcularCHInsulina();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }
}