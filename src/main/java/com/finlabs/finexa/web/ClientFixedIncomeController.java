package com.finlabs.finexa.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.finlabs.finexa.dto.ClientFixedIncomeDTO;
import com.finlabs.finexa.dto.ErrorDTO;
import com.finlabs.finexa.exception.FinexaBussinessException;
import com.finlabs.finexa.model.FinexaBusinessSubmodule;
import com.finlabs.finexa.model.FinexaExceptionHandling;
import com.finlabs.finexa.repository.FinexaBusinessSubmoduleRepository;
import com.finlabs.finexa.repository.FinexaExceptionHandlingRepository;
import com.finlabs.finexa.service.ClientFixedIncomeService;
import com.finlabs.finexa.util.FinexaConstant;

@RestController
public class ClientFixedIncomeController {
	private static Logger log = LoggerFactory.getLogger(ClientFixedIncomeController.class);

	@Autowired
	ClientFixedIncomeService clientFixedIncomeService;
	@Autowired
	FinexaExceptionHandlingRepository finexaExceptionHandlingRepository;
	@Autowired
	FinexaBusinessSubmoduleRepository finexaBusinessSubmoduleRepository;
	
	@PreAuthorize("hasAnyRole('Admin','ClientInfoAddEdit')")
	@RequestMapping(value = "/createClientFixedIncome", method = RequestMethod.POST)
	public ResponseEntity<?> createClientFixedIncome(@Valid @RequestBody ClientFixedIncomeDTO clientFixedIncomeDTO,
			Errors errors, HttpServletRequest request) throws FinexaBussinessException {
		ErrorDTO result = new ErrorDTO();
		if (errors.hasErrors()) {
			result.setErrorCode("VALIDATION_ERROR");
			result.setErrorMessage(
					errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			return ResponseEntity.badRequest().body(result);
		} else {
			try {
				clientFixedIncomeDTO = clientFixedIncomeService.save(clientFixedIncomeDTO, request);
				return new ResponseEntity<ClientFixedIncomeDTO>(clientFixedIncomeDTO, HttpStatus.OK);
			} catch (RuntimeException e) {
				FinexaBusinessSubmodule subModule = finexaBusinessSubmoduleRepository
						.findByCode(FinexaConstant.MY_CLIENT_PORTFOLIO);
				FinexaExceptionHandling exception = finexaExceptionHandlingRepository
						.findByFinexaBusinessSubmoduleAndErrorCode(subModule,
								FinexaConstant.CLIENT_FIXED_INCOME_ADD_ERROR);
				throw new FinexaBussinessException(FinexaConstant.CLIENT_FIXED_INCOME_MODULE,
						FinexaConstant.CLIENT_FIXED_INCOME_ADD_ERROR,
						exception != null ? exception.getErrorMessage() : "", e);
			}
		}

	}
	
	@PreAuthorize("hasAnyRole('Admin','ClientInfoAddEdit')")
	@RequestMapping(value = "/editClientFixedIncome", method = RequestMethod.POST)
	public ResponseEntity<?> editClientFixedIncome(@Valid @RequestBody ClientFixedIncomeDTO clientFixedIncomeDTO,
			Errors errors, HttpServletRequest request) throws FinexaBussinessException {

		ErrorDTO result = new ErrorDTO();
		if (errors.hasErrors()) {
			result.setErrorCode("VALIDATION_ERROR");
			result.setErrorMessage(
					errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			return ResponseEntity.badRequest().body(result);
		} else {
			try {
				clientFixedIncomeDTO = clientFixedIncomeService.update(clientFixedIncomeDTO, request);
				return new ResponseEntity<ClientFixedIncomeDTO>(clientFixedIncomeDTO, HttpStatus.OK);
			} catch (RuntimeException e) {
				FinexaBusinessSubmodule subModule = finexaBusinessSubmoduleRepository
						.findByCode(FinexaConstant.MY_CLIENT_PORTFOLIO);
				FinexaExceptionHandling exception = finexaExceptionHandlingRepository
						.findByFinexaBusinessSubmoduleAndErrorCode(subModule,
								FinexaConstant.CLIENT_FIXED_INCOME_UPDATE_ERROR);
				throw new FinexaBussinessException(FinexaConstant.CLIENT_FIXED_INCOME_MODULE,
						FinexaConstant.CLIENT_FIXED_INCOME_UPDATE_ERROR,
						exception != null ? exception.getErrorMessage() : "", e);
			}
		}

	}
	
	@PreAuthorize("hasAnyRole('Admin','ClientInfoView')")
	@RequestMapping(value = "/clientFixedIncome/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable int id) throws FinexaBussinessException {

		try {
			ClientFixedIncomeDTO clientFixedIncomeDTO = clientFixedIncomeService.findById(id);
			return new ResponseEntity<ClientFixedIncomeDTO>(clientFixedIncomeDTO, HttpStatus.OK);
		} catch (RuntimeException e) {
			FinexaBusinessSubmodule subModule = finexaBusinessSubmoduleRepository
					.findByCode(FinexaConstant.MY_CLIENT_PORTFOLIO);
			FinexaExceptionHandling exception = finexaExceptionHandlingRepository
					.findByFinexaBusinessSubmoduleAndErrorCode(subModule,
							FinexaConstant.CLIENT_FIXED_INCOME_GET_DATA_ERROR);
			throw new FinexaBussinessException(FinexaConstant.CLIENT_FIXED_INCOME_MODULE,
					FinexaConstant.CLIENT_FIXED_INCOME_GET_DATA_ERROR,
					exception != null ? exception.getErrorMessage() : "", e);
		}

	}
	
	@PreAuthorize("hasAnyRole('Admin','ClientInfoView')")
	@RequestMapping(value = "/clientFixedIncomeList", method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<ClientFixedIncomeDTO> clientFixedIncomeDTOList = clientFixedIncomeService.findAll();

		return new ResponseEntity<List<ClientFixedIncomeDTO>>(clientFixedIncomeDTOList, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Admin','ClientInfoDelete')")
	@RequestMapping(value = "/clientFixedIncomeDelete/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> delete(@PathVariable int id) throws FinexaBussinessException {
		try {
			Integer i = clientFixedIncomeService.delete(id);
			return new ResponseEntity<Integer>(i, HttpStatus.OK);
		} catch (RuntimeException e) {
			FinexaBusinessSubmodule subModule = finexaBusinessSubmoduleRepository
					.findByCode(FinexaConstant.MY_CLIENT_PORTFOLIO);
			FinexaExceptionHandling exception = finexaExceptionHandlingRepository
					.findByFinexaBusinessSubmoduleAndErrorCode(subModule,
							FinexaConstant.CLIENT_FIXED_INCOME_DELETE_ERROR);
			throw new FinexaBussinessException(FinexaConstant.CLIENT_FIXED_INCOME_MODULE,
					FinexaConstant.CLIENT_FIXED_INCOME_DELETE_ERROR,
					exception != null ? exception.getErrorMessage() : "", e);
		}
	}
	
	@PreAuthorize("hasAnyRole('Admin','ClientInfoView')")
	@RequestMapping(value = "/clientFixedIncome/client/{clientId}", method = RequestMethod.GET)
	public ResponseEntity<?> findByClientId(@PathVariable int clientId) throws FinexaBussinessException {
		try {
			List<ClientFixedIncomeDTO> clientFixedIncomeDTOList = clientFixedIncomeService.findByClientId(clientId);
			return new ResponseEntity<List<ClientFixedIncomeDTO>>(clientFixedIncomeDTOList, HttpStatus.OK);
		} catch (RuntimeException e) {
			FinexaBusinessSubmodule subModule = finexaBusinessSubmoduleRepository
					.findByCode(FinexaConstant.MY_CLIENT_PORTFOLIO);
			FinexaExceptionHandling exception = finexaExceptionHandlingRepository
					.findByFinexaBusinessSubmoduleAndErrorCode(subModule,
							FinexaConstant.CLIENT_FIXED_INCOME_VIEW_ERROR);
			throw new FinexaBussinessException(FinexaConstant.CLIENT_FIXED_INCOME_MODULE,
					FinexaConstant.CLIENT_FIXED_INCOME_VIEW_ERROR,
					exception != null ? exception.getErrorMessage() : "", e);
		}
	}
}
