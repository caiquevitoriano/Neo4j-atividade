/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Enums.Relacionamentos;
import con.Conexao;
import interfaces.PostagemInterface;
import java.util.ArrayList;
import java.util.List;
import modelo.Postagem;
import org.neo4j.cypher.internal.v3_4.functions.Nodes;
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
public class PostagemDaoImpl implements PostagemInterface {

    private final GraphDatabaseService conexao;

    public PostagemDaoImpl() {
        conexao = Conexao.getConexao();
    }

    @Override
    public boolean publicar(Postagem postagem, String emailUser) {

        try (Transaction tx = conexao.beginTx()) {
            Node nodePostagem = conexao.createNode(Label.label("Postagem"));

            nodePostagem.setProperty("Texto", postagem.getTexto());

            Node nodeUser = conexao.findNode(Label.label("Usuario"), "Email", emailUser);
            nodeUser.createRelationshipTo(nodePostagem, Relacionamentos.POSTAR);

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public List<Postagem> listar() {
        List<Postagem> postagens = new ArrayList<>();

        try (Transaction tx = conexao.beginTx()) {

            ResourceIterator<Node> iterator = conexao.findNodes(Label.label("Postagem"));

            while (iterator.hasNext()) {
                Node node = iterator.next();
                Postagem postagem = new Postagem();

                postagem.setCodigo((int) node.getId());
                postagem.setTexto((String) node.getProperty("Texto"));

            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return postagens;
    }

    @Override
    public List<Postagem> listarPorUsuario(String emailUser) {
        List<Postagem> postagens = new ArrayList<>();

        try (Transaction tx = conexao.beginTx()) {

            Node nodeUsuario = conexao.findNode(Label.label("Usuario"), "Email", emailUser);

            for (Relationship relationship : nodeUsuario.getRelationships(Direction.OUTGOING, Relacionamentos.POSTAR)) {
                Node nodePost = relationship.getEndNode();

                Postagem post = new Postagem();
                post.setCodigo((int) nodePost.getId());
                post.setTexto((String) nodePost.getProperty("Texto"));

                postagens.add(post);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return postagens;
    }

    @Override
    public boolean remover(Postagem postagem) {

        try (Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Label.label("Postagem"), "id", postagem.getCodigo());
            node.delete();

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public boolean atualizar(Postagem postagem) {

        try (Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Label.label("Postagem"), "id", postagem.getCodigo());
            node.setProperty("Texto", postagem.getTexto());

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

 
}
