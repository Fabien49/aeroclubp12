package com.fabienit.bibliobatch.proxies;

import com.fabienit.bibliobatch.beans.BorrowBean;
import com.fabienit.bibliobatch.beans.ReservationBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * ApiProxi
 */
@FeignClient(name = "${api.name}", url = "${api.url}")
@Component
public interface ApiProxy {

    @GetMapping(value="/borrows")
    public List<BorrowBean> getBorrows();

    @GetMapping(value = "/reservations")
    public List<ReservationBean> getReservations();

    @GetMapping(value = "reservations/delete/outdated")
    public void deleteOutDatedReservations();

    @PostMapping(value= "reservations/notifications/update")
    public void updateReservationAfterNotification(@Valid @RequestBody ReservationBean reservation);
}