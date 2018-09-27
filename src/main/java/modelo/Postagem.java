/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author caique
 */
public class Postagem {
    
    private int codigo;
    private String texto;

    public Postagem(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public Postagem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.codigo;
        hash = 47 * hash + Objects.hashCode(this.texto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Postagem other = (Postagem) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        if (!Objects.equals(this.texto, other.texto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Postagem{" + "codigo=" + codigo + ", texto=" + texto + '}';
    }
    
    
    
}
