package com.grocery.main.service.impl;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.grocery.main.core.Orders;
import com.grocery.main.core.User;
import com.grocery.main.dto.OrderDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.enums.ResponseStatus;
import com.grocery.main.repository.OrdersRepository;
import com.grocery.main.service.OrderService;

@Service
public class OrderServiceImpl  implements OrderService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private OrdersRepository orderRepository;

	@Override
	public ResponseVO<OrderDto> addEditOrder(OrderDto orderDto) {
		try {

			ResponseVO resVo = validateRequest(orderDto);
			
			if (resVo == null) {
				//Products products = productServiceImpl.getProductById(inventoryDto.getProductId());
				User user = userServiceImpl.getUserById(orderDto.getUserId());
				
				Orders orders = new Orders();
				
				if (Objects.nonNull(orderDto.getOrderId())) {
					orders = orderRepository.findById(orderDto.getOrderId()).get();
				}
				orders.setUser(user);
				orders.setOrderDate(LocalDate.now());
				//orders.setOrderId(0);
				orders.setTotalAmount(orderDto.getTotalAmount());
				
				orderRepository.save(orders);
				
				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						orderDto.getOrderId() == null ? "Order Placed Succssfully !!"
								: "Order updated Succssfully !!",orderDto);
	
			}
			return resVo;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(),orderDto);
		}
	}

	private ResponseVO validateRequest(OrderDto orderDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<OrderDto> findAllOrders(int pageNo, int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("orderId").ascending());
		Page<Orders> orderPage = orderRepository.findAll(pageRequest);
		return orderPage.map(obj -> convertToVO(obj, Boolean.TRUE));
	}

	private OrderDto convertToVO(Orders order, Boolean true1) {
		OrderDto vo = new OrderDto();
		BeanUtils.copyProperties(order,vo); 
		vo.setUserId(order.getUser().getUserId());
		vo.setOrderDate(order.getOrderDate().toString());
		vo.setOrderId(order.getOrderId());
		vo.setTotalAmount(order.getTotalAmount());
		return vo; 
	}

	@Override
	public OrderDto getOrderVOById(Long id) {
		Optional<Orders> order = orderRepository.findById(id);
		if (order.isPresent()) {
			return convertToVO(order.get(), Boolean.TRUE);
		}
		return null;
	}

	public Orders getOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
