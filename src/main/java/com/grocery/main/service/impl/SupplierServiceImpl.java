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

import com.grocery.main.core.Suppliers;
import com.grocery.main.core.User;
import com.grocery.main.core.UserRole;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.dto.SupplierDto;
import com.grocery.main.enums.ResponseStatus;
import com.grocery.main.repository.SuppliersRepository;
import com.grocery.main.repository.UserRepository;
import com.grocery.main.repository.UserRoleRepository;
import com.grocery.main.security.JwtUser;
import com.grocery.main.service.SupplierService;
import com.grocery.main.utils.Messages;

@Service
public class SupplierServiceImpl implements SupplierService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Autowired
	private SuppliersRepository suppliersRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseVO<SupplierDto> addEditSupplier(SupplierDto supplierDto) {

		try {

			JwtUser jwtUser = getUserByToken();

			if (Objects.nonNull(jwtUser)) {
				User user = userRepository.findByFirstName(jwtUser.getName());

				UserRole userRole = userRoleRepository.findByRoleName(user.getUserRole().getRoleName());

				if (StringUtils.equals("Admin", userRole.getRoleName())) {
					ResponseVO resVo = validateRequest(supplierDto);

					if (resVo == null) {
						Suppliers suppliers = new Suppliers();

						if (Objects.nonNull(supplierDto.getSupplierId())) {
							suppliers = suppliersRepository.findById(supplierDto.getSupplierId()).get();
						}
						suppliers.setAddress(supplierDto.getAddress());
						suppliers.setContactPerson(supplierDto.getContactPerson());
						suppliers.setEmail(supplierDto.getEmail());
						suppliers.setPhoneNumber(supplierDto.getPhoneNumber());
						suppliers.setSupplierId(supplierDto.getSupplierId());
						suppliers.setSupplierName(supplierDto.getSupplierName());

						suppliersRepository.save(suppliers);

						return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
								supplierDto.getSupplierId() == null ? Messages.SUPPLIER_SUBMIT_SUCCESS
										: Messages.SUPPLIER_UPDATE_SUCCESS,
								supplierDto);

					}
				}
			}

			return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
					supplierDto.getSupplierId() == null ? Messages.PERMISSION_DENIED : Messages.SUPPLIER_UPDATE_SUCCESS,
					supplierDto);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(), supplierDto);
		}
	}

	private ResponseVO validateRequest(SupplierDto supplierDto) {

		if (!StringUtils.isNotBlank(supplierDto.getContactPerson())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.CUSTOMER_FIRSTNAME);
		}

		if (!StringUtils.isNotBlank(supplierDto.getAddress())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.CUSTOMER_ADDRESS);
		}

		if (!StringUtils.isNotBlank(supplierDto.getEmail())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.CUSTOMER_EMAIL);
		}

		if (!StringUtils.isNotBlank(supplierDto.getSupplierName())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.CUSTOMER_LASTNAME);
		}

		return null;
	}

	@Override
	public Page<SupplierDto> findAllSupplier(int pageNo, int pageSize) {
		
		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("supplierName").ascending());
		Page<Suppliers> supplierPage = suppliersRepository.findAll(pageRequest);

		return supplierPage.map(obj -> convertToVO(obj, Boolean.TRUE));

	}

	private SupplierDto convertToVO(Suppliers suppliers, Boolean true1) {

		SupplierDto vo = new SupplierDto();
		BeanUtils.copyProperties(suppliers, vo);
		vo.setAddress(suppliers.getAddress());
		vo.setContactPerson(suppliers.getContactPerson());
		vo.setEmail(suppliers.getEmail());
		vo.setPhoneNumber(suppliers.getPhoneNumber());
		vo.setSupplierId(suppliers.getSupplierId());
		vo.setSupplierName(suppliers.getSupplierName());
		return vo;
	}

	@Override
	public SupplierDto getSupplierVOById(Long id) {
		Optional<Suppliers> supplier = suppliersRepository.findById(id);
		if (supplier.isPresent()) {
			return convertToVO(supplier.get(), Boolean.TRUE);
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