package ch.gruppe.d.energieagentur.util.adapter.deserializer;


import ch.gruppe.d.energieagentur.util.Converter;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.time.LocalTime;

/**
 * This class is used to deserialize a LocalTime
 */
public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    /**
     * Deserializes a LocalTime
     *
     * @param jsonParser             jsonParser
     * @param deserializationContext deserializationContext
     * @return LocalTime
     * @throws IOException          IOException
     * @throws JacksonException     JacksonException
     */
    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TextNode node = jsonParser.getCodec().readTree(jsonParser);
        return node.textValue().isEmpty() ? null : Converter.convertTextToLocalTime(node.textValue());
    }
}
