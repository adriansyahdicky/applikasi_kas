package com.kas.controller.api;

import com.kas.dto.*;
import com.kas.entity.Supplier;
import com.kas.service.SupplierService;
import com.kas.utils.ErrorUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping(value = "/getSuppliers")
    public List<Supplier> getSuppliers(){
        try{
            return supplierService.getSuppliers();
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

    @GetMapping(value = "/getById/{id}")
    public Supplier getById(@PathVariable("id")Long id){
        return supplierService.findById(id);
    }

    @PostMapping(value = "/createSupplier")
    public String createSupplier(@RequestBody @Valid SupplierCreateDTO supplierCreateDTO,
                                 BindingResult bindingResult) throws Exception {

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }
        else{
            try{
                supplierService.Save(supplierCreateDTO);
                jsonObject.put("status", "Success");
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return jsonObject.toString();
        }

    }

    @PostMapping(value = "/updateSupplier")
    public String updateSupplier(@RequestBody @Valid SupplierUpdateDTO supplierUpdateDTO,
                                 BindingResult bindingResult) throws Exception {

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }
        else{
            try{
                supplierService.Update(supplierUpdateDTO);
                jsonObject.put("status", "Success");
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return jsonObject.toString();
        }

    }

    @PostMapping(value = "/deleteSupplier/{id}")
    public String deleteSupplier(@PathVariable("id") Long id) throws Exception {

        JSONObject jsonObject = new JSONObject();

        try{
            supplierService.Delete(id);
            jsonObject.put("status", "Success");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return jsonObject.toString();
    }

    @GetMapping(value = "/searchSupplierByName")
    public List<ResponseSearch> searchSupplierByName(String q) throws Exception {

        try{
            return supplierService.findCustomerByName(q);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
