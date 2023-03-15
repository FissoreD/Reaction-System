package org.reactionSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ReactionSystem model = Parser.parseCnt(args[0]);
        model.buildGraph();

        BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
        writer.write(model.getGraph().toJson());
        writer.close();
        
        System.out.println(model.getGraph().toJson());
    }
}
