package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.db.AlimentoContrato;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;

public class AlimentoTablaActivity extends AppCompatActivity {
    static final String C_ALIMENTOID = "alimento_id";
    static final String C_ALIMENTONOMBRE = "alimento_nombre";
    static final String C_ALIMENTOCATEGORIAID = "alimento_categoria_id";
    static final String C_ALIMENTOPORCIONUNI = "alimento_porcion_unidad_str";
    static final String C_ALIMENTOPORCIONUNIID = "alimento_porcion_unidad_id";
    static final String C_ALIMENTOPORCIONCANT = "alimento_porcion_cantidad";
    static final String C_ALIMENTOPORCIONGR = "alimento_porcion_gr";
    static final String C_ALIMENTOCARBO = "alimento_carbo";
    static final String C_ALIMENTONOCONTABILIZA = "alimento_no_carb";

    TextView nombreATTV;
    TextView categoriaATTV;
    TextView porcionUniATTV;
    TextView porcionCantATTV;
    TextView porcionGrATTV;
    TextView carboATTV;
    Integer alimentoID;
    CheckBox NoContabilizaCB;
    LinearLayout porcionUniATLL;
    LinearLayout porcionATLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento_tabla);
        nombreATTV = findViewById(R.id.nombreATTV);
        categoriaATTV = findViewById(R.id.categoriaATTV);
        porcionUniATTV = findViewById(R.id.porcionUniATTV);
        porcionCantATTV = findViewById(R.id.porcionCantATTV);
        porcionGrATTV = findViewById(R.id.porcionGrATTV);
        carboATTV = findViewById(R.id.carboATTV);
        NoContabilizaCB = findViewById(R.id.NoContabilizaCB);
        porcionUniATLL= findViewById(R.id.porcionUniATLL);
        porcionATLL= findViewById(R.id.porcionATLL);

        Bundle datos = this.getIntent().getExtras();
        alimentoID = datos.getInt(C_ALIMENTOID);
        String nombre = datos.getString(C_ALIMENTONOMBRE);
        Integer categoriaid = datos.getInt(C_ALIMENTOCATEGORIAID);
        String porcionUni = datos.getString(C_ALIMENTOPORCIONUNI);
        Integer porcionUniID = datos.getInt(C_ALIMENTOPORCIONUNIID);
        Integer porcionCant = datos.getInt(C_ALIMENTOPORCIONCANT);
        Integer porcionGr = datos.getInt(C_ALIMENTOPORCIONGR);
        Integer carbo = datos.getInt(C_ALIMENTOCARBO);
        Integer noContabiliza = datos.getInt(C_ALIMENTONOCONTABILIZA);

        nombreATTV.setText(nombre);

        CategoriaDBAccess categoriaDBA = CategoriaDBAccess.getInstance(this);
        categoriaDBA.open();
        Categoria categoriaActual = categoriaDBA.getCategoriaPorID(categoriaid);
        categoriaDBA.close();
        if (categoriaActual==null) categoriaATTV.setText("");
         else categoriaATTV.setText(categoriaActual.getMyNOMBRE());

        if (noContabiliza==0) {
            NoContabilizaCB.setChecked(false);
            NoContabilizaCB.setVisibility(View.GONE);
            porcionUniATTV.setText(porcionUni);
            porcionCantATTV.setText(porcionCant.toString());
            if (!(porcionGr == 0)) {
                //porcionGrATTV.setText("( " + getString(R.string.estimate) + " " + porcionGr.toString() + " gr/cc )");
                porcionGrATTV.setText(porcionGr.toString());
            } else {
                porcionGrATTV.setText("");
            }
            //carboATTV.setText(getString(R.string.estimate) + " " + carbo.toString() + " gr.CH.");
            carboATTV.setText(carbo.toString());
        } else {
            NoContabilizaCB.setChecked(true);
            porcionUniATLL.setVisibility(View.GONE);
            porcionATLL.setVisibility(View.GONE);

        }

    }

    public void clickVolverAlimento(View view) {
        onBackPressed();
    }
}