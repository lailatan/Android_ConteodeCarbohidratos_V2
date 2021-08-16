package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoriaActivity extends AppCompatActivity {
    static final String C_CATEGORIA = "categoria";
    private Integer categoriaActualId;
    private Integer categoriaEditable;
    EditText nombreET;
    EditText descripcionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);


        nombreET=  findViewById(R.id.nombreET);
        descripcionET=  findViewById(R.id.descripcionET);

        Bundle datos = this.getIntent().getExtras();
        Categoria categoriaActual = (Categoria) datos.getSerializable(C_CATEGORIA);

        if (categoriaActual==null) {
            categoriaActualId = 0;
            categoriaEditable=1;
        }else {
            categoriaActualId = categoriaActual.getMyID();
            nombreET.setText(categoriaActual.getMyNOMBRE());
            descripcionET.setText(categoriaActual.getMyDESCRIPCION());
            categoriaEditable = categoriaActual.getMyEDITABLE();

            if (!(categoriaActual.getMyEDITABLE()==1)) {
                InicializarFAB (false);
                if (categoriaActual.getMyDESCRIPCION()==null)
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


    private Boolean guardarCategoria() {
        Boolean guardado=false;

        if (DatosValidos()) {
            String nombreActual = nombreET.getText().toString();
            String descActual = descripcionET.getText().toString();
            Integer editableActual=1;

            Categoria categoriaActual= new Categoria( categoriaActualId,nombreActual,descActual, editableActual);

            CategoriaDBAccess categoriaDBA =CategoriaDBAccess.getInstance(this);
            categoriaDBA.open();

            if (categoriaActualId==0) {
                categoriaActualId = categoriaDBA.InsertarCategoria(categoriaActual);
            } else {
                if (categoriaEditable==1) categoriaDBA.UpdateCategoria(categoriaActual);
                else
                    Toast.makeText(getApplicationContext(), R.string.cant_modify_category,Toast.LENGTH_LONG).show();
            }
            guardado=true;
            categoriaDBA.close();
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
        if (categoriaEditable==0)
            Toast.makeText(getApplicationContext(), R.string.cant_modify_category, Toast.LENGTH_LONG).show();
        else if (!(ValidarCategoriaEnUso())) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            alertDialog.setMessage(R.string.delete_category_confirmation);
            alertDialog.setTitle(R.string.delete);
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (!(categoriaActualId == 0)) {
                        CategoriaDBAccess categoriaDBA =CategoriaDBAccess.getInstance(CategoriaActivity.this);
                        categoriaDBA.open();
                        categoriaDBA.EliminarCategoria(categoriaActualId);
                        categoriaDBA.close();
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

    private boolean ValidarCategoriaEnUso() {
        Boolean enUso= true;

        CategoriaDBAccess categoriaDBA =CategoriaDBAccess.getInstance(CategoriaActivity.this);
        categoriaDBA.open();
        Integer cantEnUso=categoriaDBA.CategoriaEnUso(this,categoriaActualId);
        categoriaDBA.close();

        if (cantEnUso==0) enUso=false;
        else if (cantEnUso==-1)
            Toast.makeText(getApplicationContext(), R.string.cant_delete_categoryinuse,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), getString(R.string.cant_delete_cateroryinuse_food) + cantEnUso + getString(R.string.foods),Toast.LENGTH_LONG).show();

        return enUso;
    }


    public void clickFABGuardar(View view) {
        Utils.hideKeyboard(this);
        if (guardarCategoria()) {
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