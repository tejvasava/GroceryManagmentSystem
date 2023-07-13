package com.grocery.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.main.dto.InventoryDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@PostMapping("/addEditInventory")
	public  ResponseVO<InventoryDto> addEditInventory(@RequestBody InventoryDto inventoryDto) {
		return inventoryService.addEditInventory(inventoryDto);
	}
}
