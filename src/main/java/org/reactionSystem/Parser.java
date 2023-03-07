package org.reactionSystem;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Parser {

    public static String[] getAtoms(String s) {
        var cleanStr = s.strip();
        return cleanStr.equals("") ? new String[]{} : cleanStr.split(" ");
    }

    public static ReactionSystem parseCnt(List<String> input) {
        ReactionSystem reactionSystem = new ReactionSystem();
        input.forEach(line -> {
            var cnt = line.strip().split(";");
            var attractors = getAtoms(cnt[0]);
            var inhibitors = getAtoms(cnt[1]);
            var result = getAtoms(cnt[2]);
            reactionSystem.addRule(new Rule(attractors, inhibitors, result));
        });
        return reactionSystem;
    }

    public static ReactionSystem parseCnt(String... input) {
        return parseCnt(List.of(input));
    }

    public static ReactionSystem parseCnt(String input) {
        return parseCnt(input.split("\n"));
    }


    public static ReactionSystem parseFile(String fileName) {
        Path filePath = Paths.get(fileName);
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            return parseCnt(lines);
        } catch (IOException ex) {
            throw new RuntimeException(String.format("I/O error: %s%n", ex));
        }
    }
}
