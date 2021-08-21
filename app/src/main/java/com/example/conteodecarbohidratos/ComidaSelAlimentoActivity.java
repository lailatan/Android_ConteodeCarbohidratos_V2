package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.AlimentoComida;
import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Unidad;
import com.example.conteodecarbohidratos.db.AlimentoContrato;
import com.example.conteodecarbohidratos.db.AlimentoHelper;
import com.example.conteodecarbohidratos.db.AlimentoTablaDBAccess;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.UnidadDBAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ComidaSelAlimentoActivity extends AppCompatActivity {
    private static final String C_ALIMENTOBUSQUEDA = "alimento";
    private static final String C_ALIMENTOBUSQUEDAEDITABLE = "alimento_editable";
    private static final String C_ALIMENTO_IDA = "alimentoida";
    private static final String C_ALIMENTO_VUELTA = "alimentovuelta";
    private static final String C_GRAMOS = "Gramos/CC";

    private Integer mAlimentoId;
    private Integer mCarbsNoCuenta;
    private Integer mEditable;
    private Boolean mVolversinCambios;

    TextView fragNombreTV;
    TextView fragCategoriaTV;
    TextView fragMarcaTV;
    TextView fragPorcionUniTV;
    TextView fragPorcionCantTV;
    TextView fragPorcionGrTV;
    TextView fragCarboTV;
    TextView fragTiempoEsperaTV;
    TextView fragAptoCelTV;
    TextView fragAbsRapidaTV;
    TextView fragBuscarAlimentoBT;
    RadioGroup fragUnidoGrRG;
    RadioButton fragGrRB;
    RadioButton fragUnidadesRB;
    EditText fragCantidadET;
    TextView fragCarboEquivalentesTV;
    TextView unidadReferenciaTV;
    TextView cantidadReferenciaTV;
    TextView carbsReferenciaTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_sel_alimento);

        InicializarTextViews();

        Bundle datos = this.getIntent().getExtras();
        AlimentoComida alimento = (AlimentoComida) datos.getSerializable(C_ALIMENTO_IDA);
        if (alimento==null) mAlimentoId =0;
        else {
            mAlimentoId = alimento.getMyId();
            mCarbsNoCuenta=alimento.getMyCarbsNoCuenta();
        }

        if (!(mAlimentoId == 0)) {
            CargarAlimento(alimento);
            fragNombreTV.setEnabled(false);
            fragBuscarAlimentoBT.setVisibility(View.GONE);
        } else {
            FloatingActionButton fabDelete =  findViewById(R.id.deleteFAB);
            fabDelete.setVisibility(View.GONE);
        }

        fragCantidadET.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(fragNombreTV.getText().toString().isEmpty())) CalcularCarbs();
            }
        });
        mVolversinCambios=true;
    }

    private void InicializarTextViews() {
        fragNombreTV=findViewById(R.id.fragNombreET);
        fragCategoriaTV=findViewById(R.id.fragCategoriaTV);
        fragMarcaTV=findViewById(R.id.fragMarcaTV);
        fragPorcionUniTV=findViewById(R.id.fragPorcionUniTV);
        fragPorcionCantTV=findViewById(R.id.fragPorcionCantTV);
        fragPorcionGrTV=findViewById(R.id.fragPorcionGrTV);
        fragCarboTV=findViewById(R.id.fragCarboTV);
        fragTiempoEsperaTV=findViewById(R.id.fragTiempoEsperaTV);
        fragAptoCelTV=findViewById(R.id.fragAptoCelTV);
        fragAbsRapidaTV=findViewById(R.id.fragAbsRapidaTV);

        fragBuscarAlimentoBT=findViewById(R.id.fragBuscarAlimentoBT);

        fragUnidoGrRG=findViewById(R.id.fragUnidoGrRG);
        fragGrRB=findViewById(R.id.fragGrRB);
        fragUnidadesRB=findViewById(R.id.fragUnidadesRB);

        fragCantidadET=findViewById(R.id.fragCantidadET);
        fragCarboEquivalentesTV=findViewById(R.id.fragCarboEquivalentesTV);

        unidadReferenciaTV=findViewById(R.id.unidadReferenciaTV);
        cantidadReferenciaTV=findViewById(R.id.cantidadReferenciaTV);
        carbsReferenciaTV=findViewById(R.id.carbsReferenciaTV);
    }

    private void CargarAlimento(AlimentoComida alimento) {
        ((RadioButton)fragUnidoGrRG.getChildAt(alimento.getMyUsaGramosComida()+1)).setChecked(true);
        //RemarcarMedidaGramos (alimento.getMyUsaGramosComida());
        fragCantidadET.setText(alimento.getMyCantidadComida().toString());
        fragCarboEquivalentesTV.setText(alimento.getMyCarbohidratosComida().toString());
        CargarDatosAlimento(alimento.getMyId(),alimento.getMyEditable());
    }

    private void CargarDatosAlimento(Integer alimentoId,Integer editable) {
        Alimento alimentoActual;
        //TextView fragPorcionUniLBL=findViewById(R.id.fragPorcionUniLBL);
        LinearLayout fragPorcionCantLL=findViewById(R.id.fragPorcionCantLL);
        //LinearLayout fragPorcionGrLL=findViewById(R.id.fragPorcionGrLL);
        //LinearLayout fragCarboLL=findViewById(R.id.fragCarboLL);
        LinearLayout fragTiempoEsperaLL=findViewById(R.id.fragTiempoEsperaLL);

        LinearLayout fragUnidoGrLL =findViewById(R.id.fragUnidoGrLL);
        LinearLayout referenciaLL=findViewById(R.id.referenciaLL);
        LinearLayout fragCantidadLL=findViewById(R.id.fragCantidadLL);

        if (!(alimentoId==0)) {
            if (editable==0) {
                AlimentoTablaDBAccess alimentoDBA = AlimentoTablaDBAccess.getInstance(this);
                alimentoDBA.open();
                alimentoActual = alimentoDBA.getAlimentosPorId(alimentoId);
                alimentoDBA.close();
            }else {
                AlimentoHelper alimentoDBA = new AlimentoHelper(this);
                alimentoActual = alimentoDBA.getAlimentosPorId(alimentoId);
                alimentoDBA.close();
            }
            if (!(alimentoActual==null)) {
                referenciaLL .setVisibility(View.VISIBLE);
                fragNombreTV.setText(alimentoActual.getMyNOMBRE());
                fragNombreTV.setTag(alimentoActual.getMyID().toString());
                mCarbsNoCuenta=alimentoActual.getMyCARBSNOCUENTA();
                mEditable=alimentoActual.getMyEDITABLE();

                Integer categoriaINT = alimentoActual.getMyCATEGORIA();
                if (!(categoriaINT == 0)) {
                    CategoriaDBAccess categoriaDBA = CategoriaDBAccess.getInstance(this);
                    categoriaDBA.open();
                    Categoria categoriaActual = categoriaDBA.getCategoriaPorID(categoriaINT);
                    categoriaDBA.close();
                    if (categoriaActual == null) fragCategoriaTV.setText("");
                    else fragCategoriaTV.setText(categoriaActual.getMyNOMBRE());
                }

                Integer marcaINT = alimentoActual.getMyMARCA();
                if (!(marcaINT == 0)) {
                    MarcaDBAccess marcaDBA = MarcaDBAccess.getInstance(this);
                    marcaDBA.open();
                    Marca marcaActual = marcaDBA.getMarcaPorID(marcaINT);
                    marcaDBA.close();
                    if (marcaActual == null) fragMarcaTV.setText("");
                    else fragMarcaTV.setText(marcaActual.getMyNOMBRE());
                }

                if (alimentoActual.getMyCARBSNOCUENTA()==0) {
                    //fragPorcionUniLBL.setVisibility(View.VISIBLE);
                    fragPorcionCantLL.setVisibility(View.VISIBLE);
                    //fragPorcionGrLL.setVisibility(View.VISIBLE);
                    fragTiempoEsperaLL.setVisibility(View.VISIBLE);

                    fragUnidoGrLL.setVisibility(View.VISIBLE);
                    referenciaLL.setVisibility(View.VISIBLE);
                    fragCantidadLL.setVisibility(View.VISIBLE);

                    Integer unidadINT = alimentoActual.getMyPORCION_UNIDAD();
                    if (!(unidadINT == 0)) {
                        UnidadDBAccess unidadDBA = UnidadDBAccess.getInstance(this);
                        unidadDBA.open();
                        Unidad unidadActual = unidadDBA.getUnidadPorID(unidadINT);
                        unidadDBA.close();
                        if (unidadActual == null) fragPorcionUniTV.setText("");
                        else fragPorcionUniTV.setText(unidadActual.getMyNOMBRE());
                    }

                    fragPorcionCantTV.setText(alimentoActual.getMyPORCION_CANTIDAD().toString());
                    fragPorcionGrTV.setText(alimentoActual.getMyPORCION_GRAMOS().toString());
                    fragCarboTV.setText(alimentoActual.getMyPORCION_CARBHIDRATOS().toString());
                    if (!(alimentoActual.getMyTIEMPOESPERA() == null)) {
                        if (!(alimentoActual.getMyTIEMPOESPERA()==0)) {
                            fragTiempoEsperaTV.setText(alimentoActual.getMyTIEMPOESPERA().toString());
                        } else {
                            fragTiempoEsperaTV.setText("");
                        }
                        fragTiempoEsperaTV.setText("");
                    }

                    if (alimentoActual.getMyPORCION_GRAMOS() == 0) {
                        fragUnidadesRB.setChecked(true);
                        fragUnidoGrRG.setEnabled(false);
                        fragGrRB.setEnabled(false);
                        //fragPorcionGrLL.setVisibility(View.GONE);

                        unidadReferenciaTV.setText(fragPorcionUniTV.getText().toString());
                        cantidadReferenciaTV.setText(fragPorcionCantTV.getText().toString());

                    } else {
                        //fragPorcionGrLL.setVisibility(View.VISIBLE);
                        fragUnidoGrRG.setEnabled(true);
                        fragGrRB.setEnabled(true);
                        if (fragUnidadesRB.isChecked()) {
                            unidadReferenciaTV.setText(fragPorcionUniTV.getText().toString());
                            cantidadReferenciaTV.setText(fragPorcionCantTV.getText().toString());
                        } else {
                            unidadReferenciaTV.setText(C_GRAMOS);
                            cantidadReferenciaTV.setText(fragPorcionGrTV.getText().toString());
                        }
                    }
                    carbsReferenciaTV.setText(fragCarboTV.getText().toString());

                }else {
                    fragPorcionUniTV.setText(AlimentoContrato.AlimentoEntry.NO_SE_CONTABILIZA);
                    fragPorcionCantTV.setText("");
                    fragPorcionGrTV.setText("");
                    fragCarboTV.setText("");

                    //fragPorcionUniLBL.setVisibility(View.GONE);
                    fragPorcionCantLL.setVisibility(View.GONE);
                    //fragPorcionGrLL.setVisibility(View.GONE);
                    fragTiempoEsperaLL.setVisibility(View.GONE);

                    fragUnidoGrLL.setVisibility(View.GONE);
                    referenciaLL.setVisibility(View.GONE);
                    fragCantidadLL.setVisibility(View.GONE);

                    fragCarboEquivalentesTV.setText("0");
                }

                String aptoCel="";
                switch (alimentoActual.getMyAPTOCEL()) {
                    case AlimentoContrato.AlimentoEntry.APTO_CELIACO_NO:
                        aptoCel=getResources().getString(R.string.no);
                        break;
                    case AlimentoContrato.AlimentoEntry.APTO_CELIACO_SI:
                        aptoCel=getResources().getString(R.string.yes);
                        break;
                    default:
                        aptoCel=getResources().getString(R.string.dont_know);
                        break;
                }
                fragAptoCelTV.setText(aptoCel);

                String absRapida="";
                switch (alimentoActual.getMyABSORCIONRAP()) {
                    case AlimentoContrato.AlimentoEntry.ABSORCION_LENTA:
                        absRapida=getResources().getString(R.string.no);
                        break;
                    case AlimentoContrato.AlimentoEntry.ABSORCION_RAPIDA:
                        absRapida=getResources().getString(R.string.yes);
                        break;
                    default:
                        absRapida=getResources().getString(R.string.dont_know);
                        break;
                }
                fragAbsRapidaTV.setText(absRapida);

            } else {
                referenciaLL.setVisibility(View.GONE);
                LimpiarCamposAlimento();
            }
        } else {
            referenciaLL.setVisibility(View.GONE);
            LimpiarCamposAlimento();
        }
    }

    private void LimpiarCamposAlimento() {
        fragNombreTV.setText("");
        fragNombreTV.setTag("");

        fragCategoriaTV.setText("");
        fragPorcionUniTV.setText("");
        fragMarcaTV.setText("");
        fragPorcionCantTV.setText("");
        fragPorcionGrTV.setText("");
        fragCarboTV.setText("");
        fragTiempoEsperaTV.setText("");
        fragAptoCelTV.setText("");
        fragAbsRapidaTV.setText("");
        mEditable=0;
        mCarbsNoCuenta=0;
        mAlimentoId=0;
    }

    public void clickBuscarAlimento(View view) {
        Intent intent = new Intent(ComidaSelAlimentoActivity.this, BuscarEnTodosAlimentosActivity.class);
        //intent.putExtra(C_ALIMENTO_IDA, (Alimento) null);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //si id es 0 no hago nada
            //si nombre viene en "" se debe borrar y id<>0
            Integer alimento = data.getIntExtra(C_ALIMENTOBUSQUEDA,0);
            Integer editable = data.getIntExtra(C_ALIMENTOBUSQUEDAEDITABLE,0);
            CargarDatosAlimento(alimento,editable);
        }
    }

    public void clickBuscarAlimento_Dialog(View view) {
        Utils.hideKeyboard(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle(R.string.select_one_food);

        AlimentoHelper alimentoDBA = new AlimentoHelper(this);
        List<Alimento> listaAlimentos = alimentoDBA.getAlimentosPorNombreYCategoria("",0);
        alimentoDBA.close();

        final List<String> alimentos = new ArrayList<>();
        final List<Integer> alimentosId = new ArrayList<>();

        for (Integer i=0;i<listaAlimentos.size();i=i+1) {
            alimentos.add(listaAlimentos.get(i).getMyNOMBRE());
            alimentosId.add(listaAlimentos.get(i).getMyID());
        }

        listaAlimentos=null;

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, alimentos);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CargarDatosAlimento(alimentosId.get(which),1);
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
        if (ValidoDatos()) {
            mVolversinCambios=false;
            CalcularCarbs();
            onBackPressed();
        }
    }

    private void CalcularCarbs() {
        Integer alimentocantidad=0;
        Integer alimentocarbs=0;
        if (!(fragCantidadET.getText().toString().isEmpty())) {

            if (mCarbsNoCuenta==0) {
                Float cantidad = Float.parseFloat(fragCantidadET.getText().toString());

                Integer usoGramos = fragUnidoGrRG.indexOfChild(findViewById(fragUnidoGrRG.getCheckedRadioButtonId())) - 1;
                if (!(fragCarboTV.getText().toString().isEmpty()))
                    alimentocarbs = Integer.parseInt(fragCarboTV.getText().toString());
                else alimentocarbs = 0;

                if (usoGramos == 0) {
                    if (!(fragPorcionCantTV.getText().toString().isEmpty()))
                        alimentocantidad = Integer.parseInt(fragPorcionCantTV.getText().toString());
                    else alimentocantidad = 0;
                } else {
                    if (!(fragPorcionGrTV.getText().toString().isEmpty()))
                        alimentocantidad = Integer.parseInt(fragPorcionGrTV.getText().toString());
                    else alimentocantidad = 0;
                }

                if (alimentocantidad == 0 || alimentocarbs == 0) {
                    fragCarboEquivalentesTV.setText("0");
                    fragCantidadET.setText("0");
                } else {
                    Float carboTotales = (cantidad * alimentocarbs) / alimentocantidad;
                    Integer carboTotalesInt = Math.round(carboTotales);
                    fragCarboEquivalentesTV.setText(carboTotalesInt.toString());
                }
            }else {
                fragCarboEquivalentesTV.setText("0");
            }
        } else {
            fragCarboEquivalentesTV.setText("");
        }

    }

    private boolean ValidoDatos() {
        Boolean datosValidos=false;
        if (fragNombreTV.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.must_enter_food, Toast.LENGTH_LONG).show();
            fragNombreTV.requestFocus();
        } else if (mCarbsNoCuenta==0) {
            if (fragCantidadET.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), R.string.must_enter_quantity,Toast.LENGTH_LONG).show();
                fragCantidadET.requestFocus();
            } else if (Integer.parseInt(fragCantidadET.getText().toString())==0) {
                Toast.makeText(getApplicationContext(), R.string.must_enter_quantity, Toast.LENGTH_LONG).show();
                fragCantidadET.requestFocus();
            } else datosValidos=true;
        } else {
            fragCantidadET.setText("0");
            fragCarboEquivalentesTV.setText("0");
            datosValidos=true;
        }

        return datosValidos;
    }

    public void clickFABBorrar(View view) {
        Utils.hideKeyboard(this);
        ConfirmarYBorrar();
    }

    public void clickFABCancelar(View view) {
        //fragNombreTV.setTag("0");
        //fragCantidadET.setText("0");
        //fragCarboEquivalentesTV.setText("0");
        Utils.hideKeyboard(this);
        onBackPressed();
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
                //fragNombreTV.setText("");
                //fragCantidadET.setText("0");
                //fragCarboEquivalentesTV.setText("0");
                mVolversinCambios=false;
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

    @Override
    public void onBackPressed() {
        String unidad ="";
        Integer cantidad =0;
        AlimentoComida alimento;
        Integer usaGramos=0;
        Integer carbo=0;
        Integer cantTotal=0;
        Integer carboTotal=0;
        Integer tiempoEspera=0;

        if (mVolversinCambios) {
            alimento = new AlimentoComida(0,"","","",0,0,"",0,0,0,"",0,0,null);
        }else {
            if (mCarbsNoCuenta==0) {
                usaGramos = fragUnidoGrRG.indexOfChild(findViewById(fragUnidoGrRG.getCheckedRadioButtonId())) - 1;
                if (usaGramos == 1) {
                    cantidad = Integer.parseInt(fragPorcionGrTV.getText().toString());
                    unidad = C_GRAMOS;
                } else {
                    cantidad = Integer.parseInt(fragPorcionCantTV.getText().toString());
                    unidad = fragPorcionUniTV.getText().toString();
                }
                carbo=Integer.parseInt(fragCarboTV.getText().toString());
                cantTotal=Integer.parseInt(fragCantidadET.getText().toString());
                carboTotal =Integer.parseInt(fragCarboEquivalentesTV.getText().toString());
                if (fragTiempoEsperaTV.getText().toString().isEmpty()) tiempoEspera=null;
                else tiempoEspera = Integer.parseInt(fragTiempoEsperaTV.getText().toString());
            }

            alimento = new AlimentoComida(Integer.parseInt(fragNombreTV.getTag().toString()),
                fragNombreTV.getText().toString(),
                fragMarcaTV.getText().toString(),
                fragCategoriaTV.getText().toString(),
                mEditable,
                mCarbsNoCuenta,
                unidad,
                cantidad,
                carbo,
                usaGramos,
                unidad,
                cantTotal,
                carboTotal,
                tiempoEspera);
        }
        Intent intent = new Intent();
        intent.putExtra(C_ALIMENTO_VUELTA,alimento);
        setResult(1, intent);
        finish();
    }

    public void clickMedidaGramoRB(View view) {
        Utils.hideKeyboard(this);
        fragCantidadET.setText("");
        carbsReferenciaTV.setText(fragCarboTV.getText().toString());
        if (fragUnidadesRB.isChecked()) {
            unidadReferenciaTV.setText(fragPorcionUniTV.getText().toString());
            cantidadReferenciaTV.setText(fragPorcionCantTV.getText().toString());
        } else {
            unidadReferenciaTV.setText(C_GRAMOS);
            cantidadReferenciaTV.setText(fragPorcionGrTV.getText().toString());
        }
        if (!(fragNombreTV.getText().toString().isEmpty())) CalcularCarbs();
    }
}