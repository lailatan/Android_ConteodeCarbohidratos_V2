package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Unidad;
import com.example.conteodecarbohidratos.db.AlimentoContrato;
import com.example.conteodecarbohidratos.db.AlimentoHelper;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.UnidadDBAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlimentoActivity extends AppCompatActivity {
    private static final String C_ALIMENTO = "alimento";

    private Integer alimentoActualId;
    EditText entradaNombreET;
    EditText entradaMarcaET;
    EditText entradaCategoriaET;
    EditText entradaPorcionUniET;
    EditText entradaPorcionCantET;
    EditText entradaPorcionGrET;
    EditText entradaCarboET;
    EditText entradaTiempoET;
    EditText ingredientesET;
    EditText comentariosET;
    RadioGroup aptoCelRG;
    RadioGroup rapidoRG;
    TextView buscarMarcasBT;
    TextView buscarCategoriaBT;
    TextView buscarUnidadBT;
    CheckBox NoContabilizaCB;
    LinearLayout entradaPorcionUniLL;
    LinearLayout entradaPorcionCantLL;
    LinearLayout entradaPorcionGrLL;
    LinearLayout entradaCarboLL;
    LinearLayout entradaTiempoLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento);

        entradaNombreET=  findViewById(R.id.entradaNombreET);
        entradaMarcaET=  findViewById(R.id.entradaMarcaET);
        entradaCategoriaET=  findViewById(R.id.entradaCategoriaET);
        entradaPorcionUniET=  findViewById(R.id.entradaPorcionUniET);
        entradaPorcionCantET=  findViewById(R.id.entradaPorcionCantET);
        entradaPorcionGrET=  findViewById(R.id.entradaPorcionGrET);
        entradaCarboET=  findViewById(R.id.entradaCarboET);
        entradaTiempoET=  findViewById(R.id.entradaTiempoET);
        ingredientesET=  findViewById(R.id.ingredientesET);
        comentariosET=  findViewById(R.id.comentariosET);
        aptoCelRG=  findViewById(R.id.entradaAptoCelRG);
        rapidoRG =  findViewById(R.id.entradaAbsRapidaRG);
        buscarMarcasBT =  findViewById(R.id.buscarMarcasBT);
        buscarCategoriaBT =  findViewById(R.id.buscarCategoriaBT);
        buscarUnidadBT =  findViewById(R.id.buscarUnidadBT);
        NoContabilizaCB =  findViewById(R.id.NoContabilizaCB);
        entradaPorcionUniLL=  findViewById(R.id.entradaPorcionUniLL);
        entradaPorcionCantLL=  findViewById(R.id.entradaPorcionCantLL);
        entradaPorcionGrLL=  findViewById(R.id.entradaPorcionGrLL);
        entradaCarboLL=  findViewById(R.id.entradaCarboLL);
        entradaTiempoLL=  findViewById(R.id.entradaTiempoLL);


        Bundle datos = this.getIntent().getExtras();

        Alimento alimentoActual = (Alimento) datos.getSerializable(C_ALIMENTO);

        if (alimentoActual==null) {
            alimentoActualId = 0;
        }else {
            alimentoActualId = alimentoActual.getMyID();
            CargarDatosAlimento(alimentoActual);
            if (!(alimentoActual.getMyEDITABLE()==1)) {
                InicializarFAB (false);
            } else {
                InicializarFAB (true);
            }
        }
    }

    private void InicializarFAB(boolean editable) {
        FloatingActionButton fabGrabar =  findViewById(R.id.guardarFAB);
        FloatingActionButton fabDelete =  findViewById(R.id.deleteFAB);

        if (!(editable)) {
            fabDelete.setVisibility(View.GONE);
            fabGrabar.setVisibility(View.GONE);
            DeshabilitarControles();
        } else if (alimentoActualId == 0) {
            fabDelete.setVisibility(View.GONE);
        }
    }

    private void DeshabilitarControles() {
        entradaNombreET.setEnabled(false);
        entradaMarcaET.setEnabled(false);
        entradaCategoriaET.setEnabled(false);
        entradaPorcionUniET.setEnabled(false);
        entradaPorcionCantET.setEnabled(false);
        entradaPorcionGrET.setEnabled(false);
        entradaCarboET.setEnabled(false);
        entradaTiempoET.setEnabled(false);
        ingredientesET.setEnabled(false);
        comentariosET.setEnabled(false);
        aptoCelRG.setEnabled(false);
        rapidoRG.setEnabled(false);
        buscarMarcasBT.setEnabled(false);
        buscarCategoriaBT.setEnabled(false);
        buscarUnidadBT.setEnabled(false);
        NoContabilizaCB.setEnabled(false);
    }

    private void CargarDatosAlimento(Alimento alimentoActual) {
        entradaNombreET.setText(alimentoActual.getMyNOMBRE());
         ingredientesET.setText(alimentoActual.getMyINGREDIENTES());
        comentariosET.setText(alimentoActual.getMyOBSERVACIONES());

        ((RadioButton)aptoCelRG.getChildAt(alimentoActual.getMyAPTOCEL()+1)).setChecked(true);
        ((RadioButton)rapidoRG.getChildAt(alimentoActual.getMyABSORCIONRAP()+1)).setChecked(true);

        MarcaDBAccess marcaDBA = MarcaDBAccess.getInstance(this);
        marcaDBA.open();
        Marca marcaActual=marcaDBA.getMarcaPorID(alimentoActual.getMyMARCA());
        marcaDBA.close();
        if (marcaActual==null){
            entradaMarcaET.setText("");
            entradaMarcaET.setTag("0");
        } else {
            entradaMarcaET.setText(marcaActual.getMyNOMBRE());
            entradaMarcaET.setTag(alimentoActual.getMyMARCA().toString());
        }

        CategoriaDBAccess categoriaDBA = CategoriaDBAccess.getInstance(this);
        categoriaDBA.open();
        Categoria categoriaActual = categoriaDBA.getCategoriaPorID(alimentoActual.getMyCATEGORIA());
        categoriaDBA.close();
        if (categoriaActual==null){
            entradaCategoriaET.setText("");
            entradaCategoriaET.setTag("0");
        } else {
            entradaCategoriaET.setText(categoriaActual.getMyNOMBRE());
            entradaCategoriaET.setTag(alimentoActual.getMyCATEGORIA().toString());
        }

        if (alimentoActual.getMyCARBSNOCUENTA()==0) {
            NoContabilizaCB.setChecked(false);
            UnidadDBAccess unidadDBA = UnidadDBAccess.getInstance(this);
            unidadDBA.open();
            Unidad unidadActual = unidadDBA.getUnidadPorID(alimentoActual.getMyPORCION_UNIDAD());
            unidadDBA.close();
            if (unidadActual == null) {
                entradaPorcionUniET.setText("");
                entradaPorcionUniET.setTag("0");
            } else {
                entradaPorcionUniET.setText(unidadActual.getMyNOMBRE());
                entradaPorcionUniET.setTag(alimentoActual.getMyPORCION_UNIDAD().toString());
            }
            entradaPorcionCantET.setText(alimentoActual.getMyPORCION_CANTIDAD().toString());
            entradaPorcionGrET.setText(alimentoActual.getMyPORCION_GRAMOS().toString());
            entradaCarboET.setText(alimentoActual.getMyPORCION_CARBHIDRATOS().toString());
            if (!(alimentoActual.getMyTIEMPOESPERA()==null)) {
                entradaTiempoET.setText(alimentoActual.getMyTIEMPOESPERA().toString());
            }
        } else {
            NoContabilizaCB.setChecked(true);
            entradaPorcionCantET.setText("");
            entradaPorcionGrET.setText("");
            entradaCarboET.setText("");
            entradaPorcionUniET.setText("");
            entradaPorcionUniET.setTag("0");
            entradaTiempoET.setText("");

            entradaPorcionUniLL.setVisibility(View.GONE);
            entradaPorcionCantLL.setVisibility(View.GONE);
            entradaPorcionGrLL.setVisibility(View.GONE);
            entradaCarboLL.setVisibility(View.GONE);
            entradaTiempoLL.setVisibility(View.GONE);

        }
    }

    private Boolean guardarAlimento() {
        Boolean guardado=false;

        if (DatosValidos()) {
            Integer categoriaActual=Integer.parseInt(entradaCategoriaET.getTag().toString());;
            String imagenActual="";
            Integer marcaActual = Integer.parseInt(entradaMarcaET.getTag().toString());
            String nombreActual = entradaNombreET.getText().toString();
            Integer carbsNoCuenta ;
            if (NoContabilizaCB.isChecked()) carbsNoCuenta=1;
            else carbsNoCuenta=0;
            Integer porcionUniActual = Integer.parseInt(entradaPorcionUniET.getTag().toString());
            Integer porcionCantActual = Integer.parseInt(entradaPorcionCantET.getText().toString());
            Integer porcionGrActual = Integer.parseInt(entradaPorcionGrET.getText().toString());
            Integer carboActual =  Integer.parseInt(entradaCarboET.getText().toString());
            Integer tiempoActual;
            if (entradaTiempoET.getText().toString().isEmpty()) tiempoActual=null;
            else  tiempoActual = Integer.parseInt(entradaTiempoET.getText().toString());

            String ingredientesActual = ingredientesET.getText().toString();
            String comentariosActual = comentariosET.getText().toString();
            Integer editableActual=1;

            Integer absRapidaActual = rapidoRG.indexOfChild(findViewById(rapidoRG.getCheckedRadioButtonId()))-1;
            Integer aptoCelActual = aptoCelRG.indexOfChild(findViewById(aptoCelRG.getCheckedRadioButtonId()))-1;

            Alimento alimentoActual= new Alimento( alimentoActualId, categoriaActual, imagenActual,
                    marcaActual, nombreActual,carbsNoCuenta, porcionUniActual, porcionCantActual,porcionGrActual,carboActual, tiempoActual,
                absRapidaActual, aptoCelActual, ingredientesActual,  comentariosActual, editableActual);

            AlimentoHelper alimentoDBA = new AlimentoHelper(this);

            if (alimentoActualId==0) {
                alimentoActualId = alimentoDBA.InsertarAlimento(alimentoActual);
            } else {
                alimentoDBA.updateAlimento(alimentoActual);
            }
            guardado=true;
        }
        return guardado;
    }

    private boolean DatosValidos() {
        Boolean validos=false;

        if (entradaNombreET.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.must_enter_name,Toast.LENGTH_LONG).show();
            entradaNombreET.requestFocus();
        } else if (entradaCategoriaET.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.must_select_category, Toast.LENGTH_LONG).show();
        } else if (entradaMarcaET.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.must_select_brand, Toast.LENGTH_LONG).show();
        } else if (!(NoContabilizaCB.isChecked())) {
            if (entradaPorcionUniET.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.must_enter_unit, Toast.LENGTH_LONG).show();
                entradaPorcionUniET.requestFocus();
            } else if (entradaPorcionCantET.getText().toString().isEmpty() ) {
                Toast.makeText(getApplicationContext(), R.string.must_enter_portion, Toast.LENGTH_LONG).show();
                entradaPorcionCantET.requestFocus();
            } else if (entradaCarboET.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.must_enter_carbs, Toast.LENGTH_LONG).show();
                entradaCarboET.requestFocus();
            } else validos = true;
        } else {
            validos = true;
            entradaPorcionCantET.setText("0");
            entradaPorcionGrET.setText("0");
            entradaCarboET.setText("0");
        }

        if (entradaPorcionGrET.getText().toString().isEmpty()) entradaPorcionGrET.setText("0");

        return  validos;
    }

    private void ConfirmarYBorrar() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        alertDialog.setMessage(R.string.delete_confirmation);
        alertDialog.setTitle(R.string.delete);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                AlimentoHelper alimentoDBA = new AlimentoHelper(AlimentoActivity.this);
                if (!(alimentoActualId==0)) alimentoDBA.EliminarAlimento(alimentoActualId);
                onBackPressed();
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.create();
        alertDialog.show();
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
        builder.setTitle(R.string.select_one_category);

        MarcaDBAccess marcaDBA = MarcaDBAccess.getInstance(this);
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

    public void MostrarUnidades(View view) {
        Utils.hideKeyboard(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle(R.string.select_one_unit);

        UnidadDBAccess unidadDBA = UnidadDBAccess.getInstance(this);
        unidadDBA.open();
        List<Unidad> listaUnidades = unidadDBA.getUnidadAll();
        unidadDBA.close();

        final List<String> unidades = new ArrayList<>();
        final List<String> unidadesId = new ArrayList<>();

        for (Integer i=0;i<listaUnidades.size();i=i+1) {
            unidades.add(listaUnidades.get(i).getMyNOMBRE());
            unidadesId.add(listaUnidades.get(i).getMyID().toString());
        }

        listaUnidades=null;

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, unidades);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                entradaPorcionUniET.setText(unidades.get(which));
                entradaPorcionUniET.setTag(unidadesId.get(which));
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

    public void clickFABGuardar(View view) {
        Utils.hideKeyboard(this);
        if (guardarAlimento()) {
            onBackPressed();
        }
    }

    public void clickFABBorrar(View view) {
        //if (!(alimentoActualId == 0)) ConfirmarYBorrar();
        //else onBackPressed();
        Utils.hideKeyboard(this);
        ConfirmarYBorrar();
    }

    public void clickFABCancelar(View view) {
        Utils.hideKeyboard(this);
        onBackPressed();
    }

    public void clickNoAportaCarbs(View view) {
        Utils.hideKeyboard(this);

        Boolean carbsNoCuenta=((CheckBox)view).isChecked();

        entradaPorcionCantET.setText("");
        entradaPorcionGrET.setText("");
        entradaCarboET.setText("");
        entradaTiempoET.setText("");
        entradaPorcionUniET.setText("");
        entradaPorcionUniET.setTag("0");
        //entradaPorcionCantET.setEnabled(!carbsNoCuenta);
        //entradaPorcionGrET.setEnabled(!carbsNoCuenta);
        //entradaCarboET.setEnabled(!carbsNoCuenta);
        //entradaPorcionUniET.setEnabled(!carbsNoCuenta);
        //buscarUnidadBT.setEnabled(!carbsNoCuenta);
        //entradaTiempoET.setEnabled(!carbsNoCuenta);

        if (carbsNoCuenta) {
            entradaPorcionUniLL.setVisibility(View.GONE);
            entradaPorcionCantLL.setVisibility(View.GONE);
            entradaPorcionGrLL.setVisibility(View.GONE);
            entradaCarboLL.setVisibility(View.GONE);
            entradaTiempoLL.setVisibility(View.GONE);
            entradaPorcionCantET.setHint(null);
            entradaPorcionGrET.setHint(null);
            entradaCarboET.setHint(null);
            entradaPorcionUniET.setHint(null);
            entradaTiempoET.setHint(null);
        }else {
            entradaPorcionUniLL.setVisibility(View.VISIBLE);
            entradaPorcionCantLL.setVisibility(View.VISIBLE);
            entradaPorcionGrLL.setVisibility(View.VISIBLE);
            entradaCarboLL.setVisibility(View.VISIBLE);
            entradaTiempoLL.setVisibility(View.VISIBLE);
            entradaPorcionCantET.setHint(R.string.enter_food_portionquantity);
            entradaPorcionGrET.setHint(R.string.enter_food_portion_grams);
            entradaCarboET.setHint(R.string.enter_food_carbs);
            entradaPorcionUniET.setHint(R.string.enter_food_unit);
            entradaTiempoET.setHint(R.string.enter_food_delay);
        }

    }

    public void clickRBAptoCel(View view) {
        Utils.hideKeyboard(this);
    }

    public void clickRadioAbsorcion(View view) {
        Utils.hideKeyboard(this);
    }
}