package de.gothaer.webapp.presentation.mapper;


import de.gothaer.webapp.presentation.dto.PersonDto;
import de.gothaer.webapp.service.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonDtoMapper {
    PersonDto convert(Person person);
    Person convert(PersonDto personDto);

    Iterable<PersonDto> convert(Iterable<Person> personen);
}
