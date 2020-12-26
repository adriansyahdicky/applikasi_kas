package com.kas.controller.api;

import com.kas.dto.PembelianDTO;
import com.kas.dto.ShowPembelianDetailResponse;
import com.kas.entity.Pembelian;
import com.kas.service.PembelianService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/pembelian")
public class PembelianController {

    @Autowired
    private PembelianService pembelianService;

    @PostMapping(value = "/savePembelian")
    public String savePesanan(@RequestBody @Valid PembelianDTO pembelianDTO,
                              BindingResult bindingResult) throws Exception {

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }
        else{
            try{
                pembelianService.Save(pembelianDTO);
                jsonObject.put("status", "Success");
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return jsonObject.toString();
        }

    }

    @GetMapping(value = "/getPembelians")
    public List<Pembelian> getPembelians(){
        try{
            return pembelianService.getPembelians();
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

    @PostMapping(value = "/laporanpembelian/{tglFrom}/{tglTo}")
    public ResponseEntity<Map<String, Object>> laporanpembelian(@PathVariable("tglFrom") String tglFrom, @PathVariable("tglTo") String tglTo, Pageable pageRequest){

        try{
            Page<Pembelian> pembelianPage = pembelianService.getPembelians(pageRequest, tglFrom, tglTo);

            Map<String, Object> response = new HashMap<>();
            response.put("data", pembelianPage.getContent());
            response.put("total_rows", pembelianPage.getTotalElements());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

    @GetMapping(value = "/getPembelianDetail/{id}")
    public ShowPembelianDetailResponse getPembelianDetail(@PathVariable("id") Long id){
        try{
            return pembelianService.getShowPembelianDetail(id);
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

}
