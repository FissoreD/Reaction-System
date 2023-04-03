package org.reactionSystem;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import java.util.Map;
import java.util.Set;

public class NodeMapSerializer extends JsonSerializer<Map<String, Node>> {

    /**
     * 
     * @param nodes              the map of nodes to serialize
     * @param jsonGenerator      the json generator
     * @param serializerProvider the serializer provider
     * @throws IOException
     */
    @Override
    public void serialize(Map<String, Node> nodes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        Set<String> names = nodes.keySet();
        jsonGenerator.writeObject(names);
    }
}
