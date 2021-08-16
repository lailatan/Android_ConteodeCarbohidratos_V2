package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Unidad;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.UnidadDBAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UnidadActivity extends AppCompatActivity {
    static final String C_UNIDAD = "unidad";
    private Integer unidadActualId;
    private Integer unidadEditable;
    EditText nombreET;
    EditText descripcionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidad);

        nombreET=  findViewById(R.id.nombreET);
        descripcionET=  findViewById(R.id.descripcionET);

        Bundle datos = this.getIntent().getExtras();
        Unidad unidadActual = (Unidad) datos.getSerializable(C_UNIDAD);

        if (unidadActual==null) {
            unidadActualId = 0;
            unidadEditable=1;
        }else {
            unidadActualId = unidadActual.getMyID();
            nombreET.setText(unidadActual.getMyNOMBRE());
            descripcionET.setText(unidadActual.getMyDESCRIPCION());
            unidadEditable = unidadActual.getMyEDITABLE();

            if (!(unidadActual.getMyEDITABLE()==1)) {
                InicializarFAB (false);
                if (unidadActual.getMyDESCRIPCION()==null)
                    descripcionET.setText("-----");
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
        }
    }

    private void DeshabilitarControles() {
        nombreET.setEnabled(false);
        descripcionET.setEnabled(false);
    }


    private Boolean guardarUnidad() {
        Boolean guardado=false;

        if (DatosValidos()) {
            String nombreActual = nombreET.getText().toString();
            String descActual = descripcionET.getText().toString();
            Integer editableActual=1;

            Unidad unidadActual= new Unidad( unidadActualId,nombreActual,descActual, editableActual);

            UnidadDBAccess unidadDBA =UnidadDBAccess.getInstance(this);
            unidadDBA.open();

            if (unidadActualId==0) {
                unidadActualId = unidadDBA.InsertarUnidad(unidadActual);
            } else {
                if (unidadEditable==1) unidadDBA.UpdateUnidad(unidadActual);
                else
                    Toast.makeText(getApplicationContext(), R.string.cant_modifiy_unit,Toast.LENGTH_LONG).show();
            }
            guardado=true;
            unidadDBA.close();
        }
        return guardado;
    }

    private boolean DatosValidos() {
        Boolean validos=false;

        if (nombreET.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.must_enter_name,Toast.LENGTH_LONG).show();
            nombreET.requestFocus();
        } else validos = true;

        return  validos;
    }

    private void ConfirmarYBorrar() {
        if (unidadEditable==0)
            Toast.makeText(getApplicationContext(), R.string.cant_modifiy_unit, Toast.LENGTH_LONG).show();
        else if (!(ValidarUnidadEnUso())) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            alertDialog.setMessage(R.string.delete_unit_confirmation);
            alertDialog.setTitle(R.string.delete);
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (!(unidadActualId == 0)) {
                        UnidadDBAccess unidadDBA =UnidadDBAccess.getInstance(UnidadActivity.this);
                        unidadDBA.open();
                        unidadDBA.EliminarUnidad(unidadActualId);
                        unidadDBA.close();
                    }
                    onBackPressed();
                }
            });
            alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.create();
            alertDialog.show();
        }
    }

    private boolean ValidarUnidadEnUso() {
        Boolean enUso= true;

        UnidadDBAccess unidadDBA =UnidadDBAccess.getInstance(UnidadActivity.this);
        unidadDBA.open();
        Integer cantEnUso=unidadDBA.UnidadEnUso(this,unidadActualId);
        unidadDBA.close();

        if (cantEnUso==0) enUso=false;
        else if (cantEnUso==-1)
            Toast.makeText(getApplicationContext(), R.string.cant_delete_unitinuse,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), getString(R.string.cant_delete_unitinusefood) + cantEnUso + R.string.foods,Toast.LENGTH_LONG).show();

        return enUso;
    }


    public void clickFABGuardar(View view) {
        Utils.hideKeyboard(this);
        if (guardarUnidad()) {
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


}