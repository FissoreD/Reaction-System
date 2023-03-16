package org.reactionSystem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Argument
    private List<String> arguments = new ArrayList<>();
    @Option(name = "-f", usage = "set file path to save graph in")
    private String fileName;

    @Option(name = "-cnt", usage = "the content of the request")
    private String cntJson;

    public static void main(String[] args) throws IOException {
        new Main().doMain(args);
    }

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        arguments = arguments;
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java Main [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: java Main" + parser.printExample(ALL));

            return;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(cntJson);
        String cnt = "";
        var mode = json.get("mode").asInt();
        if (mode == 1) {
            ReactionSystem model = Parser.parseCnt(json.get("cnt").asText());
            model.buildGraph();
            cnt = model.getGraph().toJson();
        } else {
            var jsonCnt = json.get("cnt");
            Graph graph = Graph.fromJSON(jsonCnt.get("graph"));
            cnt = switch (mode) {
                case 2 -> graph.getFixedPoints().toString();
                case 3 -> graph.getNPeriodicPoints(jsonCnt.get("len").asInt()).toString();
                default -> cnt;
            };
        }
        writer.write(cnt);
//        System.out.println(cnt);
        writer.close();
    }
}
