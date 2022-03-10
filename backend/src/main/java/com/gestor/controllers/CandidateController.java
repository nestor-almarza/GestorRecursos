package com.gestor.controllers;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestor.domains.Candidate;
import com.gestor.domains.filters.CandidateFilter;
import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.exceptions.CandidateServiceException;
import com.gestor.services.ICandidateService;
import com.gestor.services.IUserSessionService;
import com.gestor.utils.rolesEnum.Role;
import com.gestor.utils.sanitizer.ICandidateSanitizer;
import com.gestor.utils.validators.ICandidateValidator;
import com.gestor.utils.validators.errors.ValidationError;
import com.lowagie.text.DocumentException;

@CrossOrigin(
		origins = { "http://localhost:8080/", "http://localhost:4200/"},
		allowCredentials = "true"
)
@RestController
@RequestMapping(value = "/api/candidate")
public class CandidateController {

	@Autowired
	private ICandidateValidator formValidation;

	@Autowired
	private ICandidateService candidateServiceImpl;

	@Autowired
	private ICandidateSanitizer candidateSanitizer;

	@Autowired
	private IUserSessionService userSessionService;

	// Create candidate
	@PostMapping(value = "/new")
	public ResponseEntity<?> createCandidate(
			@CookieValue("session") String sessionId,
			@RequestBody Candidate candidate) {

		// Permission check //
		Role[] requiredRoles = {Role.ROLE_CANDIDATE, Role.ROLE_EMPLOYER};
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// Candidate validation
		ValidationError formErrors = formValidation.validateCandidate(candidate);

		if (!formErrors.getErrors().isEmpty()) {
			return new ResponseEntity<>(formErrors.getErrors(), HttpStatus.BAD_REQUEST);
		}

		// Candidate Sanitization
		candidate = candidateSanitizer.sanitize(candidate);


		try {
				CandidatePresenter candidatePresenter = candidateServiceImpl.createCandidate(candidate);
				return new ResponseEntity<>(candidatePresenter, HttpStatus.OK);

		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Get candidate by ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCandidateById(
			@PathVariable(name = "id") String candidateId,
			@CookieValue("session") String sessionId
			) {

		// Permission check //
		Role[] requiredRoles = {Role.ROLE_CANDIDATE, Role.ROLE_EMPLOYER};
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		try {
			CandidatePresenter candidatePresenter = candidateServiceImpl.getCandidateById(candidateId);

			return new ResponseEntity<>(candidatePresenter, HttpStatus.OK);

		}catch (NoSuchElementException nsee) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
			catch (Exception e) {
				e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get page of candidates
	@PostMapping
	public ResponseEntity<?> getCandidatePage(
			@CookieValue("session") String sessionId,
			@RequestBody CandidateFilter candidateFilter,
			@RequestParam(name = "page") int page, 
			@RequestParam(name = "size") int size,
			@RequestParam(name = "sortBy") String sortBy,
			@RequestParam(name = "direction") String direction
			){

		// Permission check //
		Role[] requiredRoles = { Role.ROLE_EMPLOYER };
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		try {
			Pageable pageable = PageRequest.of(
					page,
					size,
					Sort.Direction.fromString(
							(StringUtils.isBlank(direction) ? "ASC" : direction)
					),
					(StringUtils.isBlank(sortBy) ? "lastName" : sortBy)
			);

			Page<CandidatePresenter> candidatesPage = candidateServiceImpl.getCandidatePage(candidateFilter, pageable);

			return new ResponseEntity<>(candidatesPage, HttpStatus.OK);
		} catch (PropertyReferenceException pre){
			// this error happens when sortBy is not a correct property
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Get list of candidates
	@GetMapping(value = "/list")
	public ResponseEntity<?> getCandidateList(
			@CookieValue("session") String sessionId
	) {

		// Permission check //
		Role[] requiredRoles = { Role.ROLE_EMPLOYER};
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		try {
			List<CandidatePresenter> setCandidates = candidateServiceImpl.getAllCandidate();

			return new ResponseEntity<>(setCandidates, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Delete candidate
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteCandidate(
			@CookieValue("session") String sessionId,
			@PathVariable(name = "id") String candidateId
	) {

		// Permission check //
		Role[] requiredRoles = { Role.ROLE_EMPLOYER };
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		try {
			candidateServiceImpl.deleteCandidate(candidateId);
	
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (EmptyResultDataAccessException erdae) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Update candidate
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateCandidate(
			@CookieValue("session") String sessionId,
			@PathVariable(name = "id") String candidateId,
			@RequestBody Candidate candidate) {

		// Permission check //
		Role[] requiredRoles = {Role.ROLE_CANDIDATE, Role.ROLE_EMPLOYER};
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// Candidate Validation
		ValidationError formErrors = formValidation.validateCandidate(candidate);
		
		if(!formErrors.getErrors().isEmpty()) {
			return new ResponseEntity<>(formErrors, HttpStatus.BAD_REQUEST);
		}

		// Candidate Sanitization
		candidate = candidateSanitizer.sanitize(candidate);

		try {
			CandidatePresenter candidatePresenter = candidateServiceImpl.updateCandidate(candidateId, candidate);
			
			return new ResponseEntity<>(candidatePresenter, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(value = "/list")
	public ResponseEntity<?> deleteListCandidate(
			@CookieValue("session") String sessionId,
			@RequestBody List<String> listCandidateId
	) {

		// Permission check //
		Role[] requiredRoles = {Role.ROLE_EMPLOYER};
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		try {
			this.candidateServiceImpl.deleteListCandidate(listCandidateId);
		} catch (CandidateServiceException cse) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Get candidate by ID to be exported in PDF Mint format
	@GetMapping(value = "/mint/{id}")
	public ResponseEntity<?> getCandidateByIdToExportPdfMint(
			@CookieValue("session") String sessionId,
			@PathVariable(name = "id") String candidateId,
			HttpServletResponse response
			){

		// Permission check //
		Role[] requiredRoles = {Role.ROLE_EMPLOYER};
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		try {
			candidateServiceImpl.exportPdfCandidateMint(response, candidateId);
			
		} catch (DocumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Get candidate by ID to be exported in PDF Manjaro format
	@GetMapping(value = "/manjaro/{id}")
	public ResponseEntity<?> getCandidateByIdToExportPdfManjaroEntity(
			@CookieValue("session") String sessionId,
			HttpServletResponse response,
			@PathVariable(name = "id") String candidateId
	){

		// Permission check //
		Role[] requiredRoles = {Role.ROLE_EMPLOYER};
		if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		try {
			candidateServiceImpl.exportPdfCandidateManjaro(response, candidateId);

		} catch (DocumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
