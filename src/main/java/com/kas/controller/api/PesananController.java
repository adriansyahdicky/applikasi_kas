package com.kas.controller.api;

import com.kas.dto.PesananDTO;
import com.kas.dto.ShowPesananDetailResponse;
import com.kas.entity.Pesanan;
import com.kas.service.PesananService;
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
@RequestMapping(value = "/api/pesanan")
public class PesananController {

    @Autowired
    private PesananService pesananService;

    @PostMapping(value = "/savePesanan")
    public String savePesanan(@RequestBody @Valid PesananDTO pesananDTO,
                                 BindingResult bindingResult) throws Exception {

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }
        else{
            try{
                pesananService.Save(pesananDTO);
                jsonObject.put("status", "Success");
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return jsonObject.toString();
        }

    }

    @GetMapping(value = "/getPesanans")
    public List<Pesanan> getPesanans(){
        try{
            return pesananService.getPesanans();
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

    @GetMapping(value = "/getPesananDetail/{id}")
    public ShowPesananDetailResponse getPesananDetail(@PathVariable("id") Long id){
        try{
            return pesananService.getShowPesananDetail(id);
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

    @PostMapping(value = "/laporanpesanan/{tglFrom}/{tglTo}")
    public ResponseEntity<Map<String, Object>> laporanpesanan(@PathVariable("tglFrom") String tglFrom, @PathVariable("tglTo") String tglTo, Pageable pageRequest){

        try{
            Page<Pesanan> pembelianPage = pesananService.getPesanans(pageRequest, tglFrom, tglTo);

            Map<String, Object> response = new HashMap<>();
            response.put("data", pembelianPage.getContent());
            response.put("total_rows", pembelianPage.getTotalElements());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

}
