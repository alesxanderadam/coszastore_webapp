package alticshaw.com.coszastore.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomBoolean extends JsonSerializer<Boolean> {
    @Override
    public void serialize(Boolean aBoolean, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (aBoolean != null) {
            if (aBoolean) {
                jsonGenerator.writeNumber(0);
            } else {
                jsonGenerator.writeNumber(1);
            }
        } else {
            jsonGenerator.writeNull();
        }
    }
}
