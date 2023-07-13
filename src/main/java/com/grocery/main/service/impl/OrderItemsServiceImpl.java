package com.grocery.main.service.impl;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.grocery.main.core.Inventory;
import com.grocery.main.core.OrderItems;
import com.grocery.main.core.Orders;
import com.grocery.main.core.Products;
import com.grocery.main.core.User;
import com.grocery.main.dto.OrderItemsDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.enums.ResponseStatus;
import com.grocery.main.repository.InventoryRepository;
import com.grocery.main.repository.OrderItemsRepository;
import com.grocery.main.repository.OrdersRepository;
import com.grocery.main.repository.ProductsRepository;
import com.grocery.main.security.JwtUser;
import com.grocery.main.service.OrderItemsService;

@Service
public class OrderItemsServiceImpl implements OrderItemsService{

private static final Logger LOGGER = LoggerFactory.getLogger(OrderItemsServiceImpl.class);
	
	@Autowired
	private OrderItemsRepository orderItemsRepository;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Override
	public ResponseVO addEditOrderItems(OrderItemsDto orderItemsDto) {
		try {

			JwtUser jwtUser = getUserByToken();
			
			ResponseVO resVo = validateRequest(orderItemsDto);
			
			if (resVo == null) {
				Products products = productServiceImpl.getProductById(orderItemsDto.getProductId());
				
				User user = userServiceImpl.getUserById(jwtUser.getId());
				OrderItems orderItems = new OrderItems();
				
				if (Objects.nonNull(orderItemsDto.getOrderItemId())) {
					orderItems = orderItemsRepository.findById(orderItemsDto.getOrderItemId()).get();
				}
				
				
				Inventory inventory= new Inventory();
				
				if (Objects.nonNull(products)) {
					inventory = inventoryRepository.findById(products.getProductId()).get();
				}
				
				if(inventory.getStockQuantity()>=orderItemsDto.getQuantity())
				{
					inventory.setStockQuantity(inventory.getStockQuantity()-orderItemsDto.getQuantity());
					inventory.setProducts(products);
					inventoryRepository.save(inventory);
				}else
				{
					return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
							orderItems.getOrderItemId() == null ? "That much of Stock is not Availble for place your oreder !!"
									: "",orderItems);
				}
				
				
				
				Orders orders = new Orders();
				orders.setOrderDate(LocalDate.now());
				Long totalAmount=(orderItemsDto.getQuantity()*products.getPrice());
				orders.setTotalAmount(totalAmount);
				orders.setUser(user);
				
				orderItems.setOrders(orders);
				orderItems.setPrice(products.getPrice());
				orderItems.setProducts(products);
				orderItems.setQuantity(orderItemsDto.getQuantity());
				
				ordersRepository.save(orders);
				orderItemsRepository.save(orderItems);
				
				
				
				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						orderItems.getOrderItemId() == null ? "Order Placed Succssfully !!"
								: "Order updated Succssfully !!",orderItems);
	
			}
			return resVo;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(),orderItemsDto);
		}
		
	}

	private ResponseVO validateRequest(OrderItemsDto orderItemsDto) {
		// TODO Auto-generated method stub
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
