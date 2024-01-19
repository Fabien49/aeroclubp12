package com.fabienit.flyingclub.model.mappers;

import com.fabienit.flyingclub.model.beans.AircraftBean;
import com.fabienit.flyingclub.model.beans.ReservationBean;
import com.fabienit.flyingclub.model.dto.ReservationDto;
import com.fabienit.flyingclub.web.proxies.ApiProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {

    private final ApiProxy apiProxy;
    private final AircraftMapper aircraftMapper;

    public ReservationMapper(ApiProxy apiProxy, AircraftMapper aircraftMapper) {
        this.apiProxy = apiProxy;
        this.aircraftMapper = aircraftMapper;
    }

    public ReservationBean convertToEntity(ReservationDto reservationDto) {
        ReservationBean reservationBean = new ReservationBean();
        reservationBean.setRegisteredUser(apiProxy.getRegisteredUserById(reservationDto.getRegisteredUserId()));
        reservationBean.setId(reservationDto.getId());
        reservationBean.setBorrowingDate(reservationDto.getBorrowingDate());
        reservationBean.setReturnDate(reservationDto.getReturnDate());
        reservationBean.setAircraft(apiProxy.getAircraftById(reservationDto.getAircraftDto().getId()));
        return reservationBean;
    }

    public List<ReservationDto> getAllReservationsDTO(@RequestBody List<AircraftBean> availableAircrafts ) {
        List<ReservationBean> reservations = apiProxy.getReservations(availableAircrafts);
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservationDto convertToDTO(ReservationBean reservationBean) {
        ReservationDto reservationDTO = new ReservationDto();
        reservationDTO.setId(reservationBean.getId());
        reservationDTO.setBorrowingDate(reservationBean.getBorrowingDate());
        reservationDTO.setReturnDate(reservationBean.getReturnDate());

        if (reservationBean.getRegisteredUser() != null) {
            reservationDTO.setRegisteredUserId( reservationBean.getRegisteredUser().getId());
        }

        if (reservationBean.getAircraft() != null) {

            reservationDTO.setAircraftDto(aircraftMapper.convertToDTO(apiProxy.getAircraftById(reservationBean.getAircraft().getId())));
        }

        return reservationDTO;
    }

}
