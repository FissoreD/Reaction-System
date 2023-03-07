package org.reactionSystem;

public class Main {
    public static void main(String[] args) {
        var model = Parser.parseFile("./src/main/resources/input1.txt");
        model.buildGraph();
        System.out.println(model);
    }
}
