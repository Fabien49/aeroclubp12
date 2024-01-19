package com.fabienit.flyingclub.model.mappers;

import com.fabienit.flyingclub.model.beans.AircraftBean;
import com.fabienit.flyingclub.model.dto.AircraftDto;
import com.fabienit.flyingclub.web.proxies.ApiProxy;
import org.springframework.stereotype.Component;

@Component
public class AircraftMapper {

    private final ApiProxy apiProxy;
    public AircraftMapper(ApiProxy apiProxy) {
        this.apiProxy = apiProxy;
    }

    public AircraftDto convertToDTO(AircraftBean aircraft){
        AircraftDto aircraftDto = new AircraftDto();
        aircraftDto.setId(aircraft.getId());
        aircraftDto.setMark(aircraft.getMark());
        return aircraftDto;
    }

}
