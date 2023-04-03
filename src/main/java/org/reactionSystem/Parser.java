package org.reactionSystem;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Parser {

    /**
     * 
     * @param cnt the line to parse
     * @param pos the position of the part to parse
     * @return the atoms at the given position
     */
    public static String[] getAtoms(String[] cnt, int pos) {
        var s = pos < cnt.length ? cnt[pos] : "";
        var cleanStr = s.strip();
        return cleanStr.equals("") ? new String[] {} : cleanStr.split(" ");
    }

    /**
     * 
     * @param input the input to parse
     * @return the reaction system
     *         build a reaction system from a list of string
     *         for each line of the input, the line is split into 3 parts the first
     *         part is the attractors, the second part is the inhibitors and the
     *         third part is the result
     */
    public static ReactionSystem parseCnt(List<String> input) {
        ReactionSystem reactionSystem = new ReactionSystem();
        input.forEach(line -> {
            var cnt = line.strip().split(";");
            var attractors = getAtoms(cnt, 0);
            var inhibitors = getAtoms(cnt, 1);
            var result = getAtoms(cnt, 2);
            reactionSystem.addRule(new Rule(attractors, inhibitors, result));
        });
        return reactionSystem;
    }

    /**
     * 
     * @param input the input to parse
     * @return the reaction system
     */
    public static ReactionSystem parseCnt(String... input) {
        return parseCnt(List.of(input));
    }

    /**
     * 
     * @param input the input to parse
     * @return the reaction system
     */
    public static ReactionSystem parseCnt(String input) {
        return parseCnt(input.split("\n"));
    }

    /**
     * 
     * @param fileName the file to parse
     * @return the reaction system
     */
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
