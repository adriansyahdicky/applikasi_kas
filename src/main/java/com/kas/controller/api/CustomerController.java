package com.kas.controller.api;

import com.kas.dto.CustomerCreateDTO;
import com.kas.dto.CustomerUpdateDTO;
import com.kas.dto.ResponseSearch;
import com.kas.entity.Customer;
import com.kas.service.CustomerService;
import com.kas.utils.ErrorUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/getCustomers")
    public List<Customer> getCustomers(){
        try{
            return customerService.getCustomers();
        }catch (Exception ex){
            throw new RuntimeException("error : "+ex.getMessage());
        }

    }

    @GetMapping(value = "/getById/{id}")
    public Customer getById(@PathVariable("id")Long id){
        Customer findCustomer = customerService.findById(id);
        return findCustomer;
    }

    @PostMapping(value = "/createCustomer")
    public String createCustomer(@RequestBody @Valid CustomerCreateDTO customerCreateDTO,
                                 BindingResult bindingResult) throws Exception {

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }
        else{
            try{
                customerService.Save(customerCreateDTO);
                jsonObject.put("status", "Success");
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return jsonObject.toString();
        }

    }

    @PostMapping(value = "/updateCustomer")
    public String updateCustomer(@RequestBody @Valid CustomerUpdateDTO customerUpdateDTO,
                                 BindingResult bindingResult) throws Exception {

        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }
        else{
            try{
                customerService.Update(customerUpdateDTO);
                jsonObject.put("status", "Success");
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return jsonObject.toString();
        }

    }

    @PostMapping(value = "/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) throws Exception {

        JSONObject jsonObject = new JSONObject();

        try{
            customerService.Delete(id);
            jsonObject.put("status", "Success");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return jsonObject.toString();
    }

    @GetMapping(value = "/searchCustomerByName")
    public List<ResponseSearch> searchCustomerByName(String q) throws Exception {

        try{
            return customerService.findCustomerByName(q);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
