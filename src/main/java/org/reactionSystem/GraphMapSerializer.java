package org.reactionSystem;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphMapSerializer extends JsonSerializer<Map<String, Node>> {
    @Override
    public void serialize(Map<String, Node> stringNodeMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Node> nodes = new ArrayList<>(stringNodeMap.values());
        jsonGenerator.writeObject(nodes);
    }
}
