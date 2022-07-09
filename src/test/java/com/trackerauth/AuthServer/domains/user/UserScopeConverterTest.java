package com.trackerauth.AuthServer.domains.user;

import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import com.trackerauth.AuthServer.domains.user.scope.UserScopeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserScopeConverterTest {

    UserScopeConverter victim = new UserScopeConverter();

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
    @DisplayName("convertToEntityAttribute Throws Exception When Incorrect 'code' Provided")
    void convertToEntityAttribute_ThrowsExceptionWhenInvalidInput() {
        String dbData = "invalid";
        assertThatThrownBy(() -> victim.convertToEntityAttribute(dbData))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("UserScope converter unable to convert dbData '" + dbData + "' to scope");
    }


}