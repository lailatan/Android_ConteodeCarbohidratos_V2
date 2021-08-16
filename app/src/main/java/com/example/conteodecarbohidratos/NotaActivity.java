package com.example.conteodecarbohidratos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Nota;
import com.example.conteodecarbohidratos.clases.Unidad;
import com.example.conteodecarbohidratos.db.AlimentoContrato;
import com.example.conteodecarbohidratos.db.AlimentoHelper;
import com.example.conteodecarbohidratos.db.CategoriaDBAccess;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.NotaHelper;
import com.example.conteodecarbohidratos.db.UnidadDBAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotaActivity extends AppCompatActivity {
    static final String C_NOTA = "nota";
    private Integer notaActualId;
    EditText tituloET;
    EditText descripcionET;
    EditText fechaET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        tituloET=  findViewById(R.id.tituloET);
        descripcionET=  findViewById(R.id.descripcionET);
        fechaET=  findViewById(R.id.fechaET);

        Bundle datos = this.getIntent().getExtras();

        Nota notaActual = (Nota) datos.getSerializable(C_NOTA);

        if (notaActual==null) {
            notaActualId = 0;
        }else {
            notaActualId = notaActual.getMyID();
            CargarDatosNota(notaActual);
        }
    }


    private void CargarDatosNota(Nota notaActual) {
        tituloET.setText(notaActual.getMyTITULO());
        descripcionET.setText(notaActual.getMyDESCRIPCION());
        fechaET.setText(notaActual.getMyFECHA());
    }


    private Boolean guardarNota() {
        Boolean guardado=false;

        if (DatosValidos()) {
            String tituloActual = tituloET.getText().toString();
            String descActual = descripcionET.getText().toString();

            String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            fechaET.setText(fechaActual);

            Integer editableActual=1;

            Nota notaActual= new Nota( notaActualId, tituloActual, descActual,
                    fechaActual, editableActual);

            NotaHelper notaDBA = new NotaHelper(this);

            if (notaActualId==0) {
                notaActualId = notaDBA.InsertarNota(notaActual);
            } else {
                notaDBA.updateNota(notaActual);
            }
            guardado=true;
        }
        return guardado;
    }

    private boolean DatosValidos() {
        Boolean validos=false;

        if (tituloET.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.must_enter_title,Toast.LENGTH_LONG).show();
            tituloET.requestFocus();
        } else if (descripcionET.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.must_write_note, Toast.LENGTH_LONG).show();
        } else validos = true;

        return  validos;
    }


    private void ConfirmarYBorrar() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        alertDialog.setMessage(R.string.delete_note_confirmation);
        alertDialog.setTitle(R.string.delete);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                NotaHelper notaDBA = new NotaHelper(NotaActivity.this);
                if (!(notaActualId==0)) notaDBA.EliminarNota(notaActualId);
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


    public void clickFABGuardar(View view) {
        Utils.hideKeyboard(this);
        if (guardarNota()) {
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