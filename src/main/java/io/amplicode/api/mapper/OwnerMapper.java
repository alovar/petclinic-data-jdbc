package io.amplicode.api.mapper;

import io.amplicode.api.dto.OwnerDto;
import io.amplicode.model.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {
    Owner toEntity(OwnerDto ownerDto);

    OwnerDto toOwnerDto(Owner owner);

    Owner updateWithNull(OwnerDto ownerDto, @MappingTarget Owner owner);
}