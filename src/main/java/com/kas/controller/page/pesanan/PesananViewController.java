package com.kas.controller.page.pesanan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PesananViewController {

    @GetMapping("/pesanan")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/pesanan/index");
        return modelAndView;
    }

    @GetMapping("/laporan_pesanan")
    public ModelAndView laporanpesanan(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/pesanan/laporan_pesanan");
        return modelAndView;
    }

}
