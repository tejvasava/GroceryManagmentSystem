package com.grocery.main.service.impl;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.grocery.main.core.Inventory;
import com.grocery.main.core.Products;
import com.grocery.main.core.User;
import com.grocery.main.core.UserRole;
import com.grocery.main.dto.InventoryDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.enums.ResponseStatus;
import com.grocery.main.repository.InventoryRepository;
import com.grocery.main.repository.UserRepository;
import com.grocery.main.repository.UserRoleRepository;
import com.grocery.main.security.JwtUser;
import com.grocery.main.service.InventoryService;
import com.grocery.main.utils.Messages;

@Service
public class InventoryServiceImpl implements InventoryService{

	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	
	@Override
	public ResponseVO<InventoryDto>  addEditInventory(InventoryDto inventoryDto) {
		try {
			
			JwtUser jwtUser = getUserByToken();
			
			if(Objects.nonNull(jwtUser))
			 {
				User user= userRepository.findByFirstName(jwtUser.getName());
				
				UserRole userRole=userRoleRepository.findByRoleName(user.getUserRole().getRoleName());
				
				if(StringUtils.equals("Admin", userRole.getRoleName()) || StringUtils.equals("sales manager", userRole.getRoleName()))
				{
					ResponseVO resVo = validateRequest(inventoryDto);
					
					if (resVo == null) {
						Products products = productServiceImpl.getProductById(inventoryDto.getProductId());
						Inventory inventory = new Inventory();
						
						if (Objects.nonNull(inventoryDto.getInventoryId())) {
							inventory = inventoryRepository.findById(inventoryDto.getInventoryId()).get();
						}
						inventory.setProducts(products);
						inventory.setStockQuantity(inventoryDto.getStockQuantity());
						
						inventoryRepository.save(inventory);
						
						return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
								inventoryDto.getInventoryId() == null ? Messages.INVENTORY_SUBMIT_SUCCESS
										: Messages.INVENTORY_UPDATE_SUCCESS,inventoryDto);
			
					}
				}
			
			 }
			return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
					inventoryDto.getInventoryId() == null ? Messages.PERMISSION_DENIED
							: Messages.INVENTORY_UPDATE_SUCCESS,null);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(),inventoryDto);
		}
		
	}

	private ResponseVO validateRequest(InventoryDto inventoryDto) {
		
		if (Objects.isNull(inventoryDto.getProductId())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.INVENTORY_PRODUCTID);
		}
		
		if (Objects.isNull(inventoryDto.getStockQuantity())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.INVENTORY_STOCK_QUANTITY);
		}
		return null;
	}
	
	private JwtUser getUserByToken() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (Objects.nonNull(object) && object instanceof JwtUser) {
			return ((JwtUser) object);
		}

		return null;
	}

}
