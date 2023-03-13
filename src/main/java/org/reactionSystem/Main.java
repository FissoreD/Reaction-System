package org.reactionSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        ReactionSystem model = Parser.parseFile("./src/main/resources/input1.txt");
        model.buildGraph();
        String nodeJSON;
        try {
            nodeJSON = new ObjectMapper().writeValueAsString(model.getGraph());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nodeJSON);
    }
}
