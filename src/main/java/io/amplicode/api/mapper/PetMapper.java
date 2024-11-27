package io.amplicode.api.mapper;

import io.amplicode.api.dto.PetDeepInfo;
import io.amplicode.api.dto.PetDto;
import io.amplicode.model.Pet;
import io.amplicode.model.PetType;
import org.mapstruct.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {

    Pet toEntity(PetDto petDto);

    PetDto toDto(Pet pet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pet partialUpdate(PetDto petDto, @MappingTarget Pet pet);

    Pet updateWithNull(PetDto petDto, @MappingTarget Pet pet);

    @Mapping(source = "typeId", target = "typeId")
    default PetDeepInfo.PetTypeInfo toPetType(AggregateReference<PetType, Long> ref) {
        return ref::getId;
    }

    @Mapping(source = "typeId", target = "typeId")
    default AggregateReference<PetType, Long> toPetType(PetDeepInfo.PetTypeInfo rr) {
        return AggregateReference.to(rr.getId());
    }
}