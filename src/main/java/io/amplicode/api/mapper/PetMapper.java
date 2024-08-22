package io.amplicode.api.mapper;

import io.amplicode.api.dto.PetDto;
import io.amplicode.model.Pet;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {

    Pet toEntity(PetDto petDto);

    PetDto toDto(Pet pet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pet partialUpdate(PetDto petDto, @MappingTarget Pet pet);

    Pet updateWithNull(PetDto petDto, @MappingTarget Pet pet);
}