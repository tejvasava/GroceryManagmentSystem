package com.grocery.main.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.grocery.main.core.Categories;
import com.grocery.main.core.Products;
import com.grocery.main.core.User;
import com.grocery.main.core.UserRole;
import com.grocery.main.dto.CategoriesDto;
import com.grocery.main.dto.ProductDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.enums.ResponseStatus;
import com.grocery.main.repository.CategoriesRepository;
import com.grocery.main.repository.ProductsRepository;
import com.grocery.main.repository.UserRepository;
import com.grocery.main.repository.UserRoleRepository;
import com.grocery.main.security.JwtUser;
import com.grocery.main.service.ProductService;
import com.grocery.main.utils.Messages;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private CategoriesRepository categoriesRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseVO<ProductDto> addEditProduct(ProductDto productDto) {
		
		try {
			JwtUser jwtUser = getUserByToken();
			
			if(Objects.nonNull(jwtUser))
			 {
				User user= userRepository.findByFirstName(jwtUser.getName());
				
				UserRole userRole=userRoleRepository.findByRoleName(user.getUserRole().getRoleName());
				
				if(StringUtils.equals("Admin", userRole.getRoleName()))
				{
					Optional<Categories> categories=categoriesRepository.findById(productDto.getCategories().getCategoryId());
					
					ResponseVO resVo = validateRequest(productDto);

					if (resVo == null) {
						Products products = new Products();
						
						if (Objects.nonNull(productDto.getProductId())) {
							products = productsRepository.findById(productDto.getProductId()).get();
						}
						
						products.setCategories(categories.get());
						products.setPrice(productDto.getPrice());
						products.setProductName(productDto.getProductName());
						products.setQuantity(productDto.getQuantity());
						productsRepository.save(products);
						
						return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
								productDto.getProductId() == null ? Messages.PRODUCT_SUBMIT_SUCCESS
										: Messages.PRODUCT_UPDATE_SUCCESS,productDto);
					}
				}
			 }

			return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
					productDto.getProductId() == null ? Messages.PERMISSION_DENIED
							: Messages.PRODUCT_UPDATE_SUCCESS,null);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(),productDto);
		}
	}

	private ResponseVO validateRequest(ProductDto productDto) {
		
		if (!StringUtils.isNotBlank(productDto.getProductName())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.PRODUCT_NAME);
		}
		
		return null;
	}

	@Override
	public ProductDto getProductVOById(Long id) {
		Optional<Products> product = productsRepository.findById(id);
		if (product.isPresent()) {
			return convertToVO(product.get(), Boolean.TRUE);
		}
		return null;
	}

	@Override
	public Page<ProductDto> findAllProduct(int pageNo, int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("productName").ascending());
		 Page<Products> productPage = productsRepository.findAll(pageRequest);
        return productPage.map(obj -> convertToVO(obj, Boolean.TRUE));
	}

	private ProductDto convertToVO(Products product, Boolean true1) {
		
		ProductDto vo = new ProductDto();
		BeanUtils.copyProperties(product,vo); 
		vo.setCategories(convertToProductVO(product.getCategories()));
		vo.setPrice(product.getPrice());
		vo.setProductId(product.getProductId());
		vo.setProductName(product.getProductName());
		vo.setQuantity(product.getQuantity());
		return vo;
	}

	private CategoriesDto convertToProductVO(Categories categories) {
		CategoriesDto vo = new CategoriesDto();
		BeanUtils.copyProperties(categories,vo); 
		vo.setCategoryId(categories.getCategoryId());
		vo.setCategoryName(categories.getCategoryName());
		return vo; 
	}

	public Products getProductById(Long productId) {
		Optional<Products> userRole = productsRepository.findById(productId);
		if (userRole.isPresent()) {
			return userRole.get();
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
