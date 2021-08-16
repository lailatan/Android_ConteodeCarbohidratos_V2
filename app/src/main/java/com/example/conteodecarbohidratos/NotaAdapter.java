package com.example.conteodecarbohidratos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.conteodecarbohidratos.clases.Nota;

import java.util.List;

public class NotaAdapter extends ArrayAdapter<Nota> {
    Context myContexto;

    public NotaAdapter(Context contexto, List<Nota> listaNotas){
        super(contexto, 0, listaNotas);
        myContexto=contexto;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View ItemLista = convertView;
            if(ItemLista == null){
                ItemLista = LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.fila_nota,parent, false );
            }

            Nota notaActual = getItem(position);

            TextView tituloTV = ItemLista.findViewById(R.id.tituloTV);
            TextView descripcionTV = ItemLista.findViewById(R.id.descripcionTV);
            TextView fechaTV = ItemLista.findViewById(R.id.fechaTV);

            tituloTV.setText(notaActual.getMyTITULO());
            fechaTV.setText(notaActual.getMyFECHA());
            Integer cantLetras=notaActual.getMyDESCRIPCION().length();
            Integer maxLargo=50;
            if (cantLetras<maxLargo) maxLargo=cantLetras;
            descripcionTV.setText(notaActual.getMyDESCRIPCION().substring(0,maxLargo) + " ...");

            return ItemLista;
    }
}
