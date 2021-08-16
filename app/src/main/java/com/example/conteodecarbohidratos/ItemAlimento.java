package com.example.conteodecarbohidratos;

public class ItemAlimento {
    private String nombre;
    private String marca;
    private String tipo;

    public ItemAlimento(String nombreEntrada, String marcaEntrada, String tipoEntrada){

        this.nombre  = nombreEntrada;
        this.marca = marcaEntrada;
        this.tipo = tipoEntrada;

    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombreEntrada){
        this.nombre = nombreEntrada;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}

