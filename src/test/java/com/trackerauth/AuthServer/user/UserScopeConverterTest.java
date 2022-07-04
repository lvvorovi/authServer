package com.trackerauth.AuthServer.user;

import com.trackerauth.AuthServer.domains.user.UserScope;
import com.trackerauth.AuthServer.domains.user.UserScopeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserScopeConverterTest {

    @InjectMocks
    UserScopeConverter victim;

    @Test
    @DisplayName("convertToDatabaseColumn returns null when passed null")
    void convertToDatabaseColumn_ReturnNullWhenNull() {
        assertNull(victim.convertToDatabaseColumn(null));
    }

    @Test
    @DisplayName("convertToDatabaseColumn for each roles")
    void convertToDatabaseColumn_ReturnCodeForEachRole() {
        Arrays.stream(UserScope.values()).forEach(role ->
                assertEquals(role.getCode(), victim.convertToDatabaseColumn(role)));
    }

    @Test
    @DisplayName("convertToEntityAttribute when null return null")
    void convertToEntityAttribute_ReturnNullWhenNull() {
        assertNull(victim.convertToEntityAttribute(null));
    }

    @Test
    @DisplayName("convertToEntityAttribute Return Role For Each Code")
    void convertToEntityAttribute_ReturnRoleForEachCode() {
        Arrays.stream(UserScope.values())
                .forEach(role ->
                        assertEquals(role, victim.convertToEntityAttribute(role.getCode()))
                );
    }

    @Test
    @DisplayName("convertToEntityAtribute_ThrowsExceptionWhenIncorrectCodeProvided")
    void convertToEntityAttribute_ThrowsExceptionWhenInvalidInput() {
        assertThatThrownBy(() -> victim.convertToEntityAttribute("invalid"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("UserScope converter unable to convert dbData invalid to scope");
    }


}