package com.kas.controller.api;

import com.kas.dto.PembelianDTO;
import com.kas.dto.PenjualanDTO;
import com.kas.entity.Penjualan;
import com.kas.entity.Pesanan;
import com.kas.service.PenjualanService;
import com.kas.utils.ErrorUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/penjualan")
public class PenjualanController {

    @Autowired
    private PenjualanService penjualanService;

    @PostMapping(value = "/savePenjualan")
    public String savePenjualan(@RequestBody @Valid PenjualanDTO penjualanDTO,
                              BindingResult bindingResult) throws Exception {

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }
        else{
            try{
                penjualanService.Save(penjualanDTO);
                jsonObject.put("status", "Success");
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return jsonObject.toString();
        }

    }

    @PostMapping(value = "/laporanpenjualan/{tglFrom}/{tglTo}")
    public ResponseEntity<Map<String, Object>> laporanpenjualan(@PathVariable("tglFrom") String tglFrom, @PathVariable("tglTo") String tglTo, Pageable pageRequest){

        try{
            Page<Penjualan> pembelianPage = penjualanService.getPenjualans(pageRequest, tglFrom, tglTo);

            Map<String, Object> response = new HashMap<>();
            response.put("data", pembelianPage.getContent());
            response.put("total_rows", pembelianPage.getTotalElements());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

}
