package com.kas.controller.api;

import com.kas.dto.PembelianDTO;
import com.kas.dto.PengeluaranDTO;
import com.kas.entity.Pembelian;
import com.kas.entity.Penerimaan;
import com.kas.entity.Pengeluaran;
import com.kas.service.PengeluaranService;
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
@RequestMapping(value = "/api/pengeluaran")
public class PengeluaranController {

    @Autowired
    private PengeluaranService pengeluaranService;

    @PostMapping(value = "/savePengeluaran")
    public String savePengeluaran(@RequestBody @Valid PengeluaranDTO pengeluaranDTO,
                              BindingResult bindingResult) throws Exception {

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }
        else{
            try{
                pengeluaranService.Save(pengeluaranDTO);
                jsonObject.put("status", "Success");
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return jsonObject.toString();
        }

    }

    @PostMapping(value = "/laporanpengeluaran/{tglFrom}/{tglTo}")
    public ResponseEntity<Map<String, Object>> laporanpengeluaran(@PathVariable("tglFrom") String tglFrom, @PathVariable("tglTo") String tglTo, Pageable pageRequest){

        try{
            Page<Pengeluaran> pembelianPage = pengeluaranService.getPengeluarans(pageRequest, tglFrom, tglTo);

            Map<String, Object> response = new HashMap<>();
            response.put("data", pembelianPage.getContent());
            response.put("total_rows", pembelianPage.getTotalElements());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

    @PostMapping(value = "/laporanpenerimaan/{tglFrom}/{tglTo}")
    public ResponseEntity<Map<String, Object>> laporanpenerimaan(@PathVariable("tglFrom") String tglFrom, @PathVariable("tglTo") String tglTo, Pageable pageRequest){

        try{
            Page<Penerimaan> pembelianPage = pengeluaranService.getPenerimaans(pageRequest, tglFrom, tglTo);

            Map<String, Object> response = new HashMap<>();
            response.put("data", pembelianPage.getContent());
            response.put("total_rows", pembelianPage.getTotalElements());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }


}
