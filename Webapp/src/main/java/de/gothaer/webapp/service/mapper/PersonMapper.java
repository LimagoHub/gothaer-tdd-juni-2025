package de.gothaer.webapp.service.mapper;

import de.gothaer.webapp.persistence.entity.PersonEntity;
import de.gothaer.webapp.service.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person convert(PersonEntity entity);
    PersonEntity convert(Person person);
    Iterable<Person> convert(Iterable<PersonEntity> entities);
}
