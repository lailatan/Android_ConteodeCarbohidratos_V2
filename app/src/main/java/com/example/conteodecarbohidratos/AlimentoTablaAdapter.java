package com.example.conteodecarbohidratos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.AlimentoEncabezado;
import com.example.conteodecarbohidratos.clases.Unidad;
import com.example.conteodecarbohidratos.db.AlimentoContrato;
import com.example.conteodecarbohidratos.db.UnidadDBAccess;

import java.util.List;

    public class AlimentoTablaAdapter extends ArrayAdapter<Object> {
        Integer categoriaActual=0;
        Context myContexto;

        public AlimentoTablaAdapter(Context contexto, List<Object> listaAlimentos){
            super(contexto, 0, listaAlimentos);
            myContexto=contexto;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ItemLista = convertView;

        if  (getItem(position) instanceof AlimentoEncabezado) {
            if(ItemLista == null  || convertView.findViewById(R.id.headerTV)==null) {
                ItemLista = LayoutInflater.from(getContext()).inflate(R.layout.filaheader_alimento_tabla, parent, false);
            }
            AlimentoEncabezado tituloActual = (AlimentoEncabezado) getItem(position);

            TextView headerTV = ItemLista.findViewById(R.id.headerTV);
            headerTV.setText(tituloActual.getEncabezado());
            //getResources().getDrawable(R.mipmap.horno_ico);
            //headerTV.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.horno_ico, 0, 0, 0);

            //Integer imagenINT = Utils.ResolverImagenDesdeNombre(myContexto, tituloActual.getIconoEncabezado());
            //headerTV.setCompoundDrawablesWithIntrinsicBounds(imagenINT, 0, 0, 0);

        } else {
            if(ItemLista == null || !(convertView.findViewById(R.id.headerTV)==null)) {
                ItemLista = LayoutInflater.from(getContext()).inflate(R.layout.fila_alimento_tabla, parent, false);
            }
            Alimento alimentoActual =(Alimento) getItem(position);


            TextView nombreTV = ItemLista.findViewById(R.id.nombreTV);
            TextView porcionUnidadTV = ItemLista.findViewById(R.id.porcionUnidadTV);
            TextView porcionCantTV = ItemLista.findViewById(R.id.porcionCantidadTV);
            TextView porcionGrTV = ItemLista.findViewById(R.id.porcionGrTV);
            TextView carboTV = ItemLista.findViewById(R.id.carbohidratosTV);

            nombreTV.setText(alimentoActual.getMyNOMBRE());

            String gramos = alimentoActual.getMyPORCION_GRAMOS().toString();

            if (alimentoActual.getMyCARBSNOCUENTA()==0) {
                Integer unidadINT = alimentoActual.getMyPORCION_UNIDAD();
                if (!(unidadINT==0)) {
                    UnidadDBAccess unidadDBA = UnidadDBAccess.getInstance(myContexto);
                    unidadDBA.open();
                    Unidad unidadActual=unidadDBA.getUnidadPorID(unidadINT);
                    unidadDBA.close();
                    if (unidadActual==null) porcionUnidadTV.setText("");
                    else porcionUnidadTV.setText(unidadActual.getMyNOMBRE());
                }
                porcionGrTV.setVisibility(View.VISIBLE);
                porcionCantTV.setVisibility(View.VISIBLE);
                carboTV.setVisibility(View.VISIBLE);

                porcionCantTV.setText(alimentoActual.getMyPORCION_CANTIDAD().toString());
                carboTV.setText(alimentoActual.getMyPORCION_CARBHIDRATOS().toString() + " grCH.");
                if (!(alimentoActual.getMyPORCION_GRAMOS()==0)) {
                    porcionGrTV.setText(" ( " + gramos + " gr/cc ) ");
                } else {
                    porcionGrTV.setText("");
                }
            } else {
                porcionGrTV.setVisibility(View.GONE);
                porcionCantTV.setVisibility(View.GONE);
                carboTV.setVisibility(View.GONE);
                porcionUnidadTV.setText(AlimentoContrato.AlimentoEntry.NO_SE_CONTABILIZA);
            }

        }
        return ItemLista;


/*-----------------------------------------------------------------------------------------------------
            View ItemLista = convertView;
            if(ItemLista == null){
                ItemLista = LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.fila_alimento,parent, false );
            }

            Alimento alimentoActual = getItem(position);

            TextView nombreTV = ItemLista.findViewById(R.id.nombreTV);
            TextView porcionTV = ItemLista.findViewById(R.id.porcionTV);
            TextView carboTV = ItemLista.findViewById(R.id.carbohidratosTV);

            nombreTV.setText(alimentoActual.getMyNOMBRE());
            porcionTV.setText(alimentoActual.getMyPORCION());
            carboTV.setText(alimentoActual.getMyCARBHIDRATOS().toString() + " gr.CH.");

            //Integer imagenINT = Utils.ResolverImagenDesdeNombre(contexto, arcanoActual.getMyImagen(),mazoInt);
            //arcanobImagenIV.setImageResource(imagenINT);

            return ItemLista;

 */
    }
}

