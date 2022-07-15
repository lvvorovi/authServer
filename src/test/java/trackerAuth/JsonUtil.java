package trackerAuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Component
public class JsonUtil {

    String objectToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }
}
