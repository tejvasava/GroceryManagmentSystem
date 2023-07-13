package com.grocery.main.service;

import org.springframework.data.domain.Page;

import com.grocery.main.dto.ResponseVO;
import com.grocery.main.dto.SupplierDto;

public interface SupplierService {

	ResponseVO<SupplierDto> addEditSupplier(SupplierDto supplierDto);

	Page<SupplierDto> findAllSupplier(int pageNo, int pageSize);

	SupplierDto getSupplierVOById(Long id);

}
