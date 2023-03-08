package org.reactionSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ReactionSystem model = Parser.parseFile("./src/main/resources/input1.txt");
        model.buildGraph();
        //System.out.println(model);
        String nodeJSON = null;
        try {
            nodeJSON = new ObjectMapper().writeValueAsString(model.getGraph());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nodeJSON);
    }
}
