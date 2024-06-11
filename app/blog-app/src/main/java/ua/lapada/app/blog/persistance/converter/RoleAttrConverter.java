package ua.lapada.app.blog.persistance.converter;


import ua.lapada.app.blog.persistance.entity.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleAttrConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role attribute) {
        return attribute.id();
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        return Role.byId(dbData);
    }
}