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
import com.example.conteodecarbohidratos.db.AlimentoHelper;
import com.example.conteodecarbohidratos.db.AlimentoTablaDBAccess;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BuscarEnTodosAlimentosActivity extends AppCompatActivity {
    private static final String C_ALIMENTOBUSQUEDA = "alimento";
    private static final String C_ALIMENTOBUSQUEDAEDITABLE = "alimento_editable";
    ListView alimentosListaLV;
    EditText entradaNombreET;
    EditText entradaCategoriaET;
    EditText entradaMarcaET;
    TextView buscarCategoriaBT;
    TextView buscarMarcaBT;
    TextView view_vacio;
    TextView limpiarBT;
    TextView buscarBT;
    RadioGroup mostrarRG;
    RadioButton tablaRB;
    RadioButton personalRB;
    private AlimentoAdapter  adaptadorDeListaAlimentos;
    Boolean tengoDatos=false;
    Integer idAlimentoSeleccionado=0;
    Integer alimentoEditable=0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_en_todos_alimentos);
        alimentosListaLV = findViewById(R.id.alimentosListaLV);
        entradaNombreET=findViewById(R.id.entradaNombreET);
        entradaCategoriaET=findViewById(R.id.entradaCategoriaET);
        entradaMarcaET=findViewById(R.id.entradaMarcaET);
        buscarCategoriaBT=findViewById(R.id.buscarCategoriaBT);
        buscarMarcaBT=findViewById(R.id.buscarMarcaBT);
        view_vacio = findViewById(R.id.view_vacio);
        limpiarBT = findViewById(R.id.limpiarBT);
        buscarBT = findViewById(R.id.buscarBT);
        mostrarRG = findViewById(R.id.mostrarRG);
        tablaRB = findViewById(R.id.tablaRB);
        personalRB = findViewById(R.id.personalRB);

        idAlimentoSeleccionado=0;
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
        Integer marcaId = Integer.parseInt(entradaMarcaET.getTag().toString());
        boolean buscarAlimentoPersonal = personalRB.isChecked();
        List<Object> listaAlimentos;

        if (buscarAlimentoPersonal) {
            AlimentoHelper alimentoHelper = new AlimentoHelper(this);
            listaAlimentos = alimentoHelper.getTodosAlimentosBusquedaConHeader(this, nombre, categoriaId, marcaId);
            alimentoHelper.close();
        }else{
            AlimentoTablaDBAccess alimentoDBA = AlimentoTablaDBAccess.getInstance(this);
            alimentoDBA.open();
            listaAlimentos = alimentoDBA.getTodosAlimentosBusquedaConHeader(this, nombre, categoriaId, marcaId);
            alimentoDBA.close();
        }

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
        adaptadorDeListaAlimentos = new AlimentoAdapter(getApplicationContext(), listaAlimentos,false);
        alimentosListaLV.setAdapter(adaptadorDeListaAlimentos);

        if(listaAlimentos.size()==0){
            view_vacio.setText(R.string.not_found);

        } else {
            tengoDatos=true;
            view_vacio.setText("");
            alimentosListaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Integer posAlimento = position;
                    if (adaptadorDeListaAlimentos.getItem(posAlimento) instanceof Alimento) {
                        idAlimentoSeleccionado = ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyID();
                        alimentoEditable = ((Alimento) adaptadorDeListaAlimentos.getItem(posAlimento)).getMyEDITABLE();
                        onBackPressed();
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(C_ALIMENTOBUSQUEDA,idAlimentoSeleccionado);
        intent.putExtra(C_ALIMENTOBUSQUEDAEDITABLE,alimentoEditable);
        setResult(1, intent);
        finish();
    }

    public void MostrarCategorias(View view) {
        Utils.hideKeyboard(this);
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
                0.8f), (int)(displayRectangle.height() * 0.8f));
    }

    public void MostrarMarcas(View view) {
        Utils.hideKeyboard(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle(R.string.select_one_brand);

        MarcaDBAccess marcaDBA =MarcaDBAccess.getInstance(this);
        marcaDBA.open();
        List<Marca> listaMarcas = marcaDBA.getMarcaAll();
        marcaDBA.close();

        final List<String> marcas = new ArrayList<>();
        final List<String> marcasId = new ArrayList<>();

        for (Integer i=0;i<listaMarcas.size();i=i+1) {
            marcas.add(listaMarcas.get(i).getMyNOMBRE());
            marcasId.add(listaMarcas.get(i).getMyID().toString());
        }

        listaMarcas=null;

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, marcas);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                entradaMarcaET.setText(marcas.get(which));
                entradaMarcaET.setTag(marcasId.get(which));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();

        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.8f), (int)(displayRectangle.height() * 0.8f));
    }

    public void clickLimpiar(View view) {
        Utils.hideKeyboard(this);
        LimpiarControles();
    }

    public void LimpiarControles() {
        tengoDatos=false;
        alimentosListaLV.setAdapter(null);
        //adaptadorDeListaAlimentos.clear();
        entradaNombreET.setText("");
        entradaCategoriaET.setText("");
        entradaCategoriaET.setTag("0");
        entradaMarcaET.setText("");
        entradaMarcaET.setTag("0");
        view_vacio.setText("");
        idAlimentoSeleccionado=0;
    }

    public void clickMostrarCriterio(View view) {
        Utils.hideKeyboard(this);
        MostrarCritetio(Integer.parseInt(((RadioButton)view).getTag().toString()   ));
    }

    private void MostrarCritetio(Integer tag) {
        if (tag==0) {
            entradaNombreET.setVisibility(View.GONE);
            entradaCategoriaET.setVisibility(View.GONE);
            entradaMarcaET.setVisibility(View.GONE);
            buscarCategoriaBT.setVisibility(View.GONE);
            buscarMarcaBT.setVisibility(View.GONE);
            buscarBT.setVisibility(View.GONE);
            limpiarBT.setVisibility(View.GONE);

        } else{
            entradaNombreET.setVisibility(View.VISIBLE);
            entradaCategoriaET.setVisibility(View.VISIBLE);
            buscarCategoriaBT.setVisibility(View.VISIBLE);
            entradaMarcaET.setVisibility(View.VISIBLE);
            buscarMarcaBT.setVisibility(View.VISIBLE);
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
        Utils.hideKeyboard(this);
        idAlimentoSeleccionado=0;
        onBackPressed();
    }
}