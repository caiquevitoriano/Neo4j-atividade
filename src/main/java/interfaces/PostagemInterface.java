/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Postagem;

/**
 *
 * @author caique
 */
public interface PostagemInterface {
    
    public boolean publicar(Postagem postagem, String emailUser);
    public List<Postagem> listar();
    public List<Postagem> listarPorUsuario(String emailUser);
    public boolean remover(Postagem postagem);
    public boolean atualizar(Postagem postagem);
    
}
