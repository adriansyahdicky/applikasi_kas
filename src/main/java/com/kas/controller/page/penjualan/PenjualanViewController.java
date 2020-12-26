package com.kas.controller.page.penjualan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PenjualanViewController {
    @GetMapping("/penjualan")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/penjualan/index");
        return modelAndView;
    }

    @GetMapping("/laporan_penjualan")
    public ModelAndView laporanpenjualan(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/penjualan/laporan_penjualan");
        return modelAndView;
    }
}
