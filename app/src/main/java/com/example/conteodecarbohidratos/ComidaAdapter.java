package com.example.conteodecarbohidratos;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.AlimentoComida;
import com.example.conteodecarbohidratos.clases.AlimentoEncabezado;
import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Unidad;
import com.example.conteodecarbohidratos.db.AlimentoContrato;
import com.example.conteodecarbohidratos.db.MarcaDBAccess;
import com.example.conteodecarbohidratos.db.UnidadDBAccess;

import java.util.List;

public class ComidaAdapter extends ArrayAdapter<AlimentoComida> {
    Context myContexto;

    public ComidaAdapter(Context contexto, List<AlimentoComida> listaAlimentos){
        super(contexto, 0, listaAlimentos);
        myContexto=contexto;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ItemLista = convertView;

        if (ItemLista == null) {
            ItemLista = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.fila_comida, parent, false);
        }

        AlimentoComida alimentoActual = getItem(position);

        TextView nombreTV = ItemLista.findViewById(R.id.nombreTV);
        TextView categoriaTV = ItemLista.findViewById(R.id.categoriaTV);
        TextView marcaTV = ItemLista.findViewById(R.id.marcaTV);
        TextView porcionUnidadTV = ItemLista.findViewById(R.id.porcionUnidadTV);
        TextView porcionCantidadTV = ItemLista.findViewById(R.id.porcionCantidadTV);
        TextView porcioncarbsTV = ItemLista.findViewById(R.id.porcioncarbsTV);
        TextView comidaUnidadTV = ItemLista.findViewById(R.id.comidaUnidadTV);
        TextView comidaCantidadTV = ItemLista.findViewById(R.id.comidaCantidadTV);
        TextView comidacarbsTV = ItemLista.findViewById(R.id.comidacarbsTV);
        TextView tiempoEsperaTV = ItemLista.findViewById(R.id.tiempoEsperaTV);


        nombreTV.setText(alimentoActual.getMyNombre());
        categoriaTV.setText(alimentoActual.getMyCategoria());
        marcaTV.setText(alimentoActual.getMyMarca());

        if (alimentoActual.getMyCarbsNoCuenta()==0) {
            porcionCantidadTV.setVisibility(View.VISIBLE);
            porcionUnidadTV.setGravity(Gravity.CENTER);
            porcioncarbsTV.setVisibility(View.VISIBLE);
            comidaUnidadTV.setVisibility(View.VISIBLE);
            comidaCantidadTV.setVisibility(View.VISIBLE);
            porcionUnidadTV.setText(alimentoActual.getMyUnidadPorcion() + ":");
            porcionCantidadTV.setText(alimentoActual.getMyCantidadPorcion().toString());
            porcioncarbsTV.setText(alimentoActual.getMyCarbohidratosPorcion().toString() + " grCH");
            comidaUnidadTV.setText(alimentoActual.getMyUnidadComida() + ":");
            comidaCantidadTV.setText(alimentoActual.getMyCantidadComida().toString());
            comidacarbsTV.setText(alimentoActual.getMyCarbohidratosComida().toString() + " grCH");
            if (!(alimentoActual.getMyTiempoEspera()==null))
                if (!(alimentoActual.getMyTiempoEspera()==0))
                    tiempoEsperaTV.setText(alimentoActual.getMyTiempoEspera() + "'");
        }else {
            porcionUnidadTV.setText(AlimentoContrato.AlimentoEntry.NO_SE_CONTABILIZA);
            porcionUnidadTV.setGravity(Gravity.LEFT);
            porcionCantidadTV.setVisibility(View.GONE);
            porcioncarbsTV.setVisibility(View.GONE);
            comidaUnidadTV.setVisibility(View.INVISIBLE);
            comidaCantidadTV.setVisibility(View.INVISIBLE);
            porcionCantidadTV.setText("");
            porcioncarbsTV.setText("");
            comidaUnidadTV.setText("");
            comidaCantidadTV.setText("");
            tiempoEsperaTV.setText("");
            comidacarbsTV.setText(alimentoActual.getMyCarbohidratosComida().toString() + " grCH");
        }
        return ItemLista;
    }
}
