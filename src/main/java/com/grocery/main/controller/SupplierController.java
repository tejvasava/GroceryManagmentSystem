package com.grocery.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.main.dto.ResponseVO;
import com.grocery.main.dto.SupplierDto;
import com.grocery.main.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;
	
	
	@PostMapping("/addEditSupplier")
	public  ResponseVO<SupplierDto>  addEditSupplier(@RequestBody SupplierDto supplierDto) {
		return supplierService.addEditSupplier(supplierDto);
	}
	
	@GetMapping("/listSupplier")
	public Page<SupplierDto> getAllCategory(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		return supplierService.findAllSupplier(pageNo, pageSize);
	}
	
	@GetMapping("/getSupplier")
	public SupplierDto getSupplierById(@RequestParam("id") Long id) {
		return supplierService.getSupplierVOById(id);
	}
	
}
