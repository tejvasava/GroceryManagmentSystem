package com.grocery.main.service;

import com.grocery.main.dto.InventoryDto;
import com.grocery.main.dto.ResponseVO;

public interface InventoryService {

	ResponseVO<InventoryDto> addEditInventory(InventoryDto inventoryDto);
	

}
