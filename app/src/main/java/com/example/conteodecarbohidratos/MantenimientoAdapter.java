package com.example.conteodecarbohidratos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Unidad;

import java.util.List;

public class MantenimientoAdapter extends ArrayAdapter<Object> {
    Context myContexto;

    public MantenimientoAdapter(Context contexto, List<Object> lista) {
        super(contexto, 0, lista);
        myContexto = contexto;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ItemLista = convertView;
        if (ItemLista == null) {
            ItemLista = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.fila_mantenimiento, parent, false);
        }

        Object objetoActual = getItem(position);

        TextView nombreTV = ItemLista.findViewById(R.id.nombreTV);
        TextView descripcionTV = ItemLista.findViewById(R.id.descripcionTV);

        String nombreSTR = "";
        String descripcionSTR = "";

        if (objetoActual instanceof Marca) {
            nombreSTR = ((Marca) objetoActual).getMyNOMBRE();
            descripcionSTR = ((Marca) objetoActual).getMyDESCRIPCION();
        } else if (objetoActual instanceof Categoria) {
            nombreSTR = ((Categoria) objetoActual).getMyNOMBRE();
            descripcionSTR = ((Categoria) objetoActual).getMyDESCRIPCION();
        } else if (objetoActual instanceof Unidad) {
            nombreSTR = ((Unidad) objetoActual).getMyNOMBRE();
            descripcionSTR = ((Unidad) objetoActual).getMyDESCRIPCION();
        }

        nombreTV.setText(nombreSTR);
        descripcionTV.setText(descripcionSTR);

        return ItemLista;
    }
}