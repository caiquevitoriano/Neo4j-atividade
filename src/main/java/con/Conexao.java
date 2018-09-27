/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package con;

import java.io.File;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author caique
 */
public class Conexao {
    
    private static final File BANCO = new File("banco.db");

    public static GraphDatabaseService getConexao() {
        GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(BANCO);

        return graphDB;
    }
    
}
