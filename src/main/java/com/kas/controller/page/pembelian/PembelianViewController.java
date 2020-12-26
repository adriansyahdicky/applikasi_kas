package com.kas.controller.page.pembelian;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PembelianViewController {
    @GetMapping("/pembelian")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/pembelian/index");
        return modelAndView;
    }

    @GetMapping("/laporan_pembelian")
    public ModelAndView laporanpembelian(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/pembelian/laporan_pembelian");
        return modelAndView;
    }
}
