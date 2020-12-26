package com.kas.controller.page.pengeluaran;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PengeluaranViewController {
    @GetMapping("/pengeluaran")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/pengeluaran/index");
        return modelAndView;
    }

    @GetMapping("/laporan_pengeluaran")
    public ModelAndView laporan_pengeluaran(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/pengeluaran/laporan_pengeluaran");
        return modelAndView;
    }

    @GetMapping("/laporan_penerimaan")
    public ModelAndView laporan_penerimaan(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/pengeluaran/laporan_penerimaan");
        return modelAndView;
    }
}
