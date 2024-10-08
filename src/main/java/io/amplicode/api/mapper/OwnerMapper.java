package io.amplicode.api.mapper;

import io.amplicode.api.dto.OwnerDto;
import io.amplicode.model.Owner;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {

    Owner toEntity(OwnerDto ownerDto);

    OwnerDto toDto(Owner owner);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Owner partialUpdate(OwnerDto ownerDto, @MappingTarget Owner owner);

    Owner updateWithNull(OwnerDto ownerDto, @MappingTarget Owner owner);

    OwnerDto toOwnerDto(Owner owner);
}