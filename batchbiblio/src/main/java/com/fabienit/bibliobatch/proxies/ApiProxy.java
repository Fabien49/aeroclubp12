package com.fabienit.bibliobatch.proxies;

import com.fabienit.bibliobatch.beans.BorrowBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * ApiProxi
 */
@FeignClient(name = "${api.name}", url = "${api.url}")
public interface ApiProxy {

    @GetMapping(value="/borrows")
    public List<BorrowBean> getBorrows();
}