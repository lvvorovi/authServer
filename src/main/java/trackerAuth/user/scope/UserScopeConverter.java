package trackerAuth.user.scope;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserScopeConverter implements AttributeConverter<UserScope, String> {

    @Override
    public String convertToDatabaseColumn(UserScope attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public UserScope convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(UserScope.values())
                .filter(scope -> scope.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("UserScope converter unable to convert dbData '" +
                        dbData + "' to scope"));
    }
}
