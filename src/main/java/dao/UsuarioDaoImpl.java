/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Enums.Nomes;
import con.Conexao;
import Enums.Relacionamentos;
import interfaces.UsuarioInterface;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author caique
 */
public class UsuarioDaoImpl implements UsuarioInterface {

    private final GraphDatabaseService conexao;

    public UsuarioDaoImpl() {
        conexao = Conexao.getConexao();
    }

    @Override
    public boolean cadastrar(Usuario usuario) {

        try (Transaction tx = conexao.beginTx()) {

            Node node = conexao.createNode(Label.label("Usuario"));

            node.setProperty("Email", usuario.getEmail());
            node.setProperty("Nome", usuario.getNome());
            node.setProperty("Senha", usuario.getSenha());

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;

    }

    @Override
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        try (Transaction tx = conexao.beginTx()) {

            ResourceIterator<Node> iterator = conexao.findNodes(Label.label("Usuario"));

            while (iterator.hasNext()) {
                Node node = iterator.next();

                Usuario user = new Usuario();
                user.setEmail((String) node.getProperty("Email"));
                user.setNome((String) node.getProperty("Nome"));
                user.setSenha((String) node.getProperty("Senha"));

                usuarios.add(user);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return usuarios;
    }

    @Override
    public List<Usuario> listarSeguidores(String emailUser) {
        List<Usuario> usuarios = new ArrayList<>();

        try (Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Label.label("Usuario"), "Email", emailUser);

            for (Relationship relacionamento : node.getRelationships(Direction.INCOMING, Relacionamentos.SEGUIR)) {
                Node node2 = relacionamento.getEndNode();

                Usuario user = new Usuario();
                user.setEmail((String) node2.getProperty("Email"));
                user.setNome((String) node2.getProperty("Nome"));
                user.setSenha((String) node2.getProperty("Senha"));

                usuarios.add(user);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return usuarios;
    }

    @Override
    public List<Usuario> listarSeguindo(String emailUser) {
        List<Usuario> usuarios = new ArrayList<>();

        try (Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Label.label("Usuario"), "Email", emailUser);

            for (Relationship relacionamento : node.getRelationships(Direction.OUTGOING, Relacionamentos.SEGUIR)) {
                Node node2 = relacionamento.getEndNode();

                Usuario user = new Usuario();
                user.setEmail((String) node2.getProperty("Email"));
                user.setNome((String) node2.getProperty("Nome"));
                user.setSenha((String) node2.getProperty("Senha"));

                usuarios.add(user);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return usuarios;
    }

    public boolean seguir(String emailUser, String emailDestiny) {

        try (Transaction tx = conexao.beginTx()) {
            Node node1 = conexao.findNode(Label.label("Usuario"), "Email", emailUser);
            Node node2 = conexao.findNode(Label.label("Usuario"), "Email", emailDestiny);

            node1.createRelationshipTo(node2, Relacionamentos.SEGUIR);

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public boolean remover(Usuario usuario) {

        try (Transaction tx = conexao.beginTx()) {

            //Encontra o usuario
            Node node = conexao.findNode(Label.label("Usuario"), "Email", usuario.getNome());

            //Remove as Postagens
            for (Relationship relationship : node.getRelationships(Direction.OUTGOING, Relacionamentos.POSTAR)) {
                Node nodePost = relationship.getEndNode();
                nodePost.delete();
            }

            //Remove o Usuario
            node.delete();

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public boolean atualizar(Usuario usuario) {

        try (Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Label.label("Usuario"), "Email", usuario.getNome());

            node.setProperty("nome", usuario.getNome());
            node.setProperty("senha", usuario.getSenha());

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }


}
