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
import android.widget.Toast;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.AlimentoComida;
import com.example.conteodecarbohidratos.clases.GlobalInfo;

import java.util.ArrayList;
import java.util.List;

public class ComidaActivity extends AppCompatActivity {
    private static final String C_ALIMENTO_IDA = "alimentoida";
    private static final String C_ALIMENTO_VUELTA = "alimentovuelta";
    //ArrayList<AlimentoComida> listaDeAlimentos;
    ListView alimentosListaLV;
    TextView carbTotalTV;
    TextView insulinaTotalTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida);
        alimentosListaLV=findViewById(R.id.alimentosListaLV);
        insulinaTotalTV=findViewById(R.id.insulinaTotalTV);
        carbTotalTV=findViewById(R.id.carbTotalTV);
        //listaDeAlimentos=new ArrayList<>();
        GlobalInfo.ratio=cargarConfiguracion();
        ActualizarLista();
    }

    public void clickFABCancelar(View view) {
        onBackPressed();
    }

    public void clickLimpiar(View view) { LimpiarControles(); }

    public void clickAgregarAlimento(View view) {
        Intent intent = new Intent(ComidaActivity.this, ComidaSelAlimentoActivity.class);
        intent.putExtra(C_ALIMENTO_IDA, (AlimentoComida) null);
        startActivityForResult(intent,1);
    }

    public void LimpiarControles() {
        alimentosListaLV.setAdapter(null);
        GlobalInfo.cantidadTotalCarboHidratos=0;
        GlobalInfo.comida.clear();
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
            GlobalInfo.comida.get(posicion).setMyUnidadPorcion(alimento.getMyUnidadPorcion());
            GlobalInfo.comida.get(posicion).setMyCantidadPorcion(alimento.getMyCantidadPorcion());
            GlobalInfo.comida.get(posicion).setMyCarbohidratosPorcion(alimento.getMyCarbohidratosPorcion());
            GlobalInfo.comida.get(posicion).setMyUsaGramosComida(alimento.getMyUsaGramosComida());
            GlobalInfo.comida.get(posicion).setMyUnidadComida(alimento.getMyUnidadComida());
            GlobalInfo.comida.get(posicion).setMyCantidadComida(alimento.getMyCantidadComida());

            GlobalInfo.cantidadTotalCarboHidratos -= GlobalInfo.comida.get(posicion).getMyCarbohidratosComida();
            GlobalInfo.comida.get(posicion).setMyCarbohidratosComida(alimento.getMyCarbohidratosComida());
            GlobalInfo.cantidadTotalCarboHidratos += alimento.getMyCarbohidratosComida();
        } else {
            if (GlobalInfo.comida==null) {
                GlobalInfo.comida=new ArrayList<>();
            }
            GlobalInfo.comida.add(alimento);
            if (!(alimento.getMyCarbohidratosComida()==null))
                GlobalInfo.cantidadTotalCarboHidratos += alimento.getMyCarbohidratosComida();
        }
        ActualizarLista();
    }

    private void BorrarAlimento(Integer idAlimento, Integer editable) {
        Integer posicion=BuscarPosicionAlimento(idAlimento, editable);
        if (!(posicion ==-1)) {
            GlobalInfo.cantidadTotalCarboHidratos-= GlobalInfo.comida.get(posicion).getMyCarbohidratosComida();
            GlobalInfo.comida.remove(posicion.intValue());
            ActualizarLista();
        }
    }

    private void ActualizarLista() {
        alimentosListaLV.setAdapter(null);
        CalcularCHInsulina();
        ComidaAdapter adaptadorDeListaAlimentos = new ComidaAdapter(getApplicationContext(), GlobalInfo.comida);
        alimentosListaLV.setAdapter(adaptadorDeListaAlimentos);
        alimentosListaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(ComidaActivity.this, ComidaSelAlimentoActivity.class);
                intent.putExtra(C_ALIMENTO_IDA, GlobalInfo.comida.get(position));
                startActivityForResult(intent,1);

            }
        });
    }

    private Integer BuscarPosicionAlimento(Integer idAlimento, Integer editable){
        Integer posicion=-1;
        if (!(GlobalInfo.comida==null)) {
            if (GlobalInfo.comida.size() > 0) {
                for (int i = 0; i < GlobalInfo.comida.size(); i++) {
                    if (GlobalInfo.comida.get(i).getMyId().equals(idAlimento)) {
                            if (GlobalInfo.comida.get(i).getMyEditable().equals(editable)) posicion = i;
                    }
                }
            }
        }
        return posicion;
    }

/*
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("carboTotales", GlobalInfo.cantidadTotalCarboHidratos);
        outState.putSerializable("alimentos", GlobalInfo.comida);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        GlobalInfo.cantidadTotalCarboHidratos = savedInstanceState.getInt("carboTotales");
        GlobalInfo.comida = (ArrayList<AlimentoComida>) savedInstanceState.getSerializable("alimentos");
        if (GlobalInfo.comida != null)  ActualizarLista();
    }
*/

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
        Double insulina=0.0;
        carbTotalTV.setText(GlobalInfo.cantidadTotalCarboHidratos.toString() + " grCH");

        if (!(GlobalInfo.ratio==0F)){
            insulina=Utils.redondear(GlobalInfo.cantidadTotalCarboHidratos/GlobalInfo.ratio,1);
            insulinaTotalTV.setText(insulina.toString() + " U");
        }else {
            Toast.makeText(getApplicationContext(), R.string.must_enter_ratio, Toast.LENGTH_LONG).show();
            //clickRatio();
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
        if (GlobalInfo.ratio==0) input.setText("");
        else input.setText(GlobalInfo.ratio.toString());
        input.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoRatio = input.getText().toString();
                if (!nuevoRatio.isEmpty()){
                    GlobalInfo.ratio=Float.parseFloat((nuevoRatio));
                    guardarConfiguracion(GlobalInfo.ratio);
                } else {
                    GlobalInfo.ratio=0F;
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