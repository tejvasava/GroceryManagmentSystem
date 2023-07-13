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
import com.grocery.main.core.User;
import com.grocery.main.core.UserRole;
import com.grocery.main.dto.CategoriesDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.enums.ResponseStatus;
import com.grocery.main.repository.CategoriesRepository;
import com.grocery.main.repository.UserRepository;
import com.grocery.main.repository.UserRoleRepository;
import com.grocery.main.security.JwtUser;
import com.grocery.main.service.CategoryService;
import com.grocery.main.utils.Messages;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseVO<CategoriesDto> addEditCategory(CategoriesDto categoriesDto) {

		try {

			JwtUser jwtUser = getUserByToken();

			 if(Objects.nonNull(jwtUser))
			 {
				User user= userRepository.findByFirstName(jwtUser.getName());
				
				UserRole userRole=userRoleRepository.findByRoleName(user.getUserRole().getRoleName());
				
				if(StringUtils.equals("Admin", userRole.getRoleName()) || StringUtils.equals("sales manager", userRole.getRoleName()))
				{
					ResponseVO<Void> resVo = validateRequest(categoriesDto);

					if (resVo == null) {
						Categories categories = new Categories();

						if (Objects.nonNull(categoriesDto.getCategoryId())) {
							 categories = categoriesRepository.findById(categoriesDto.getCategoryId()).get();
						}
						categories.setCategoryName(categoriesDto.getCategoryName());
						categoriesRepository.save(categories);

						return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
								categoriesDto.getCategoryId() == null ? Messages.CATEGORY_SUBMIT_SUCCESS
										: Messages.CATEGORY_UPDATE_SUCCESS,
								categoriesDto);

				}
			 }
			
			}
			return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
					categoriesDto.getCategoryId() == null ? Messages.PERMISSION_DENIED
							: Messages.CATEGORY_UPDATE_SUCCESS,
							null);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(), categoriesDto);
		}
	}


	private ResponseVO<Void> validateRequest(CategoriesDto categoriesDto) {

		if (!StringUtils.isNotBlank(categoriesDto.getCategoryName())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.APP_SUBMIT_FAILURE);
		}

		Boolean isAlreadyExist = isAlreadyExist(categoriesDto);
		if (isAlreadyExist) {
			return ResponseVO.create(HttpStatus.CONFLICT.value(), ResponseStatus.FAIL.name(),
					Messages.CATEGORY_EXIST_FAILURE);
		}

		return null;

	}

	private Boolean isAlreadyExist(CategoriesDto categoriesDto) {
		Categories categories = null;
		if (categoriesDto.getCategoryId() != null) {
			/*
			 * categories =
			 * categoriesRepository.findFirstByCategoryNameAndCategoryIdNot(categoriesDto.
			 * getCategoryName(), categoriesDto.getCategoryId()); if (categories != null) {
			 * return Boolean.TRUE; }
			 */ }
		return Boolean.FALSE;
	}

	@Override
	public Page<CategoriesDto> findAllCategory(int pageNo, int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("categoryName").ascending());
		Page<Categories> categoryPage = categoriesRepository.findAll(pageRequest);

		return categoryPage.map(obj -> convertToVO(obj, Boolean.TRUE));
	}

	private CategoriesDto convertToVO(Categories category, Boolean true1) {
		CategoriesDto vo = new CategoriesDto();
		BeanUtils.copyProperties(category, vo);
		vo.setCategoryId(category.getCategoryId());
		vo.setCategoryName(category.getCategoryName());
		return vo;
	}

	@Override
	public CategoriesDto getCaegoryVOById(Long id) {
		Optional<Categories> category = categoriesRepository.findById(id);
		if (category.isPresent()) {
			return convertToVO(category.get(), Boolean.TRUE);
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
