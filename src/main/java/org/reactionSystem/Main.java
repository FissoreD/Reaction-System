package org.reactionSystem;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.kohsuke.args4j.OptionHandlerFilter.ALL;

public class Main {

    @Option(name="-buildGraph",usage="give a set of rules to build a graph with")
    private String fomulas=null;
    @Option(name="-f",usage="set file path to save graph in")
    private String fileName = "test.txt";


    @Argument
    private List<String> arguments = new ArrayList<String>();

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java Main [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: java Main"+parser.printExample(ALL));

            return;
        }
        if(fomulas!=null){
            ReactionSystem model = Parser.parseCnt(fomulas);
            model.buildGraph();

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(model.getGraph().toJson());
            writer.close();
            System.out.println(model.getGraph().toJson());
        }

    }
    public static void main(String[] args) throws IOException {
        new Main().doMain(args);
    }
}
