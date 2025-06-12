package de.gothaer.webapp.presentation.mapper;


import de.gothaer.webapp.presentation.dto.SchweinDTO;
import de.gothaer.webapp.service.model.Schwein;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchweinDTOMapper {
    SchweinDTO convert(Schwein schwein);
    Schwein convert(SchweinDTO schweinDto);

    Iterable<SchweinDTO> convert(Iterable<Schwein> schweine);
}
