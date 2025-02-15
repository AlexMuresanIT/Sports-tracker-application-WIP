package health.tracker.api.mappers;

import health.tracker.api.domain.DTO.OutdoorRunningDTO;
import health.tracker.api.domain.Entity.OutdoorRunning;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OutdoorRunningMapper {

    @Mapping(target = "status", ignore = true)
    OutdoorRunningDTO toDTO(OutdoorRunning outdoorRunning);

    List<OutdoorRunningDTO> toDTOs(List<OutdoorRunning> outdoorRunnings);

    OutdoorRunning toEntity(OutdoorRunningDTO outdoorRunningDTO);
}
