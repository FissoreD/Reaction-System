package org.reactionSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Search {

    public Map<String, Node> fingFixedPoint(Graph graph){
        Map<String, Node> fixedPoint=new HashMap<>();

        graph.getNodes().forEach((key, value) -> {
            if (value.getSuccessors().size()==1 && value.getSuccessors().get(key)!=null) {
                fixedPoint.put(key, value);
            }
        });
        return fixedPoint;
    }
}
