package io.amplicode.api.mapper;

import io.amplicode.api.dto.OwnerDto;
import io.amplicode.api.dto.PetDeepInfo;
import io.amplicode.model.Owner;
import io.amplicode.model.PetType;
import org.mapstruct.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {
    Owner toEntity(OwnerDto ownerDto);

    OwnerDto toOwnerDto(Owner owner);

    Owner updateWithNull(OwnerDto ownerDto, @MappingTarget Owner owner);

    @Mapping(source = "typeId", target = "typeId")
    default PetDeepInfo.PetTypeInfo petType(AggregateReference<PetType, Long> ref) {
        return ref::getId;
    }

    @Mapping(source = "typeId", target = "typeId")
    default AggregateReference<PetType, Long> petType(PetDeepInfo.PetTypeInfo rr) {
        return AggregateReference.to(rr.getId());
    }
}