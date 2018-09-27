/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Usuario;

/**
 *
 * @author caique
 */
public interface UsuarioInterface {
    
    public boolean cadastrar(Usuario usuario);
    public List<Usuario> listar();
    public List<Usuario> listarSeguidores(String emailUser);
    public List<Usuario> listarSeguindo(String emailUser);
    public boolean seguir(String emailUser, String emailDestiny);
    public boolean remover(Usuario usuario);
    public boolean atualizar(Usuario usuario);
    
}
