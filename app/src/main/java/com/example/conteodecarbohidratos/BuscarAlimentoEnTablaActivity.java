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
import android.widget.Toast;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.db.AlimentoTablaDBAccess;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;

import java.util.ArrayList;
import java.util.List;

public class BuscarAlimentoEnTablaActivity extends AppCompatActivity {
    static final String C_ALIMENTOID = "alimento_id";
    static final String C_ALIMENTONOMBRE = "alimento_nombre";
    static final String C_ALIMENTOCATEGORIAID = "alimento_categoria_id";
    static final String C_ALIMENTOPORCIONUNI = "alimento_porcion_unidad_str";
    static final String C_ALIMENTOPORCIONUNIID = "alimento_porcion_unidad_id";
    static final String C_ALIMENTOPORCIONCANT = "alimento_porcion_cantidad";
    static final String C_ALIMENTOPORCIONGR = "alimento_porcion_gr";
    static final String C_ALIMENTOCARBO = "alimento_carbo";
    static final String C_ALIMENTONOCONTABILIZA = "alimento_no_carb";
    ListView alimentosListaLV;
    //ExpandableHeightListView alimentosListaLV;
    EditText entradaNombreET;
    EditText entradaCategoriaET;
    TextView buscarCategoriaBT;
    TextView view_vacio;
    TextView limpiarBT;
    TextView buscarBT;
    RadioGroup mostrarRG;

    private AlimentoTablaAdapter  adaptadorDeListaAlimentos;

    Boolean listaInicializada=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_alimento_en_tabla);
        entradaNombreET=findViewById(R.id.entradaNombreET);
        entradaCategoriaET=findViewById(R.id.entradaCategoriaET);
        buscarCategoriaBT=findViewById(R.id.buscarCategoriaBT);
        view_vacio = findViewById(R.id.view_vacio);
        alimentosListaLV = findViewById(R.id.alimentosListaLV);
        limpiarBT = findViewById(R.id.limpiarBT);
        buscarBT = findViewById(R.id.buscarBT);
        mostrarRG = findViewById(R.id.mostrarRG);


        //InicializarSpinner();
    }

    public void clickBuscarAlimento(View view) {
        Utils.hideKeyboard(this);
        BuscarAlimentos();
    }

    private void BuscarAlimentos(){
        EditText entradaNombreET = findViewById(R.id.entradaNombreET);
        String nombre= entradaNombreET.getText().toString();
        Integer categoriaId = Integer.parseInt(entradaCategoriaET.getTag().toString());

        AlimentoTablaDBAccess alimentoDBA = AlimentoTablaDBAccess.getInstance(this);
        alimentoDBA.open();
        List<Object> listaAlimentos = alimentoDBA.getAlimentosPorNombreYCategoriaConHeader(nombre,categoriaId);
        alimentoDBA.close();
/*
        if (!listaInicializada) {
            View headerDeLista = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fila_alimento_tabla, alimentosListaLV, false);
            TextView porcionTV = headerDeLista.findViewById(R.id.porcionTV);
            TextView carboTV = headerDeLista.findViewById(R.id.carbohidratosTV);
            TextView nombreTV = headerDeLista.findViewById(R.id.nombreTV);
            porcionTV.setText(R.string.portion);
            carboTV.setText(R.string.carbs_gr);
            nombreTV.setText(R.string.food);
            alimentosListaLV.addHeaderView(headerDeLista, null, false);
            listaInicializada=true;
        }
*/
        adaptadorDeListaAlimentos = new AlimentoTablaAdapter(getApplicationContext(), listaAlimentos);
        alimentosListaLV.setAdapter(adaptadorDeListaAlimentos);
        //alimentosListaLV.setExpanded(true);

        if(listaAlimentos.size()==0){
            view_vacio.setText(R.string.not_found);
        } else {
            view_vacio.setText("");

            alimentosListaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Integer posAlimento = position;
                    if (adaptadorDeListaAlimentos.getItem(posAlimento) instanceof Alimento) {
                        Intent abrirAlimento = new Intent(getApplicationContext(), AlimentoTablaActivity.class);
                        abrirAlimento.putExtra(C_ALIMENTOID, ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyID());
                        abrirAlimento.putExtra(C_ALIMENTONOMBRE, ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyNOMBRE());
                        TextView unidadSTRTV = (TextView) view.findViewById(R.id.porcionUnidadTV);
                        String unidadSTR  = unidadSTRTV.getText().toString();
                        abrirAlimento.putExtra(C_ALIMENTOPORCIONUNI, unidadSTR);
                        abrirAlimento.putExtra(C_ALIMENTOCATEGORIAID, ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyCATEGORIA());
                        abrirAlimento.putExtra(C_ALIMENTOPORCIONUNIID, ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyPORCION_UNIDAD());
                        abrirAlimento.putExtra(C_ALIMENTOPORCIONCANT, ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyPORCION_CANTIDAD());
                        abrirAlimento.putExtra(C_ALIMENTOPORCIONGR, ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyPORCION_GRAMOS());
                        abrirAlimento.putExtra(C_ALIMENTOCARBO, ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyPORCION_CARBHIDRATOS());
                        abrirAlimento.putExtra(C_ALIMENTONOCONTABILIZA, ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyCARBSNOCUENTA());
                        startActivity(abrirAlimento);
                    }
                }
            });
        }
    }

    public void MostrarCategorias(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle(R.string.select_one_category);

        CategoriaDBAccess categoriaDBA =CategoriaDBAccess.getInstance(this);
        categoriaDBA.open();
        List<Categoria> listaCategorias = categoriaDBA.getCategoriaAll();
        categoriaDBA.close();

        final List<String> categorias = new ArrayList<>();
        final List<String> categoriasId = new ArrayList<>();

        for (Integer i=0;i<listaCategorias.size();i=i+1) {
            categorias.add(listaCategorias.get(i).getMyNOMBRE());
            categoriasId.add(listaCategorias.get(i).getMyID().toString());
        }

        listaCategorias=null;

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, categorias);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                entradaCategoriaET.setText(categorias.get(which));
                entradaCategoriaET.setTag(categoriasId.get(which));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();

        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.8f), (int)(displayRectangle.height() * 0.8f));    }

    public void clickLimpiar(View view) {
        Utils.hideKeyboard(this);
        //alimentosListaLV.setAdapter(null);
        adaptadorDeListaAlimentos.clear();
        entradaNombreET.setText("");
        entradaCategoriaET.setText("");
        entradaCategoriaET.setTag("0");
        view_vacio.setText("");
    }

    public void clickMostrarCriterio(View view) {
        Utils.hideKeyboard(this);
        MostrarCritetio(Integer.parseInt(((RadioButton)view).getTag().toString()   ));
    }

    private void MostrarCritetio(Integer tag) {
        if (tag==0) {
            entradaNombreET.setVisibility(View.GONE);
            entradaCategoriaET.setVisibility(View.GONE);
            buscarCategoriaBT.setVisibility(View.GONE);
            buscarBT.setVisibility(View.GONE);
            limpiarBT.setVisibility(View.GONE);

        } else{
            entradaNombreET.setVisibility(View.VISIBLE);
            entradaCategoriaET.setVisibility(View.VISIBLE);
            buscarCategoriaBT.setVisibility(View.VISIBLE);
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
