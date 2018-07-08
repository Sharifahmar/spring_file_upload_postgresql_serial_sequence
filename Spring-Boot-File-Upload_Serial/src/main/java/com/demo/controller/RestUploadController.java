package com.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.exception.CustomError;
import com.demo.model.FileUploadMetaDataModel;
import com.demo.service.FileUploadServiceImpl;

@RestController
public class RestUploadController {
	private final Logger logger = Logger.getLogger(RestUploadController.class);

	@Autowired
	private FileUploadServiceImpl iFileUploadService;

	/**
	 * Single file upload
	 * 
	 * @param uploadfile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/fileupload", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFile(
			@RequestParam("file") MultipartFile uploadfile,
			final HttpServletRequest request) {

		/** Below data is what we saving into database */
		logger.info("Single file upload!");
		logger.info("fileName : " + uploadfile.getOriginalFilename());
		logger.info("contentType : " + uploadfile.getContentType());
		logger.info("contentSize : " + uploadfile.getSize());

		if (uploadfile.isEmpty()) {
			return new ResponseEntity<String>("please select a file!",
					HttpStatus.OK);
		}

		try {
			/** File will get saved to file system and database */
			iFileUploadService.saveUploadedFiles(Arrays.asList(uploadfile));
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Successfully uploaded - "
				+ uploadfile.getOriginalFilename(), new HttpHeaders(),
				HttpStatus.OK);

	}

	/**
	 * Multiple files to upload
	 * 
	 * @param extraField
	 * @param uploadfiles
	 * @return
	 */
	@RequestMapping(value = "/api/multiplefiles", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFileMulti(
			@RequestParam("files") MultipartFile[] uploadfiles) {
		logger.info("Multiple file upload!");
		String uploadedFileName = Arrays.stream(uploadfiles)
				.map(x -> x.getOriginalFilename())
				.filter(x -> !StringUtils.isEmpty(x))
				.collect(Collectors.joining(" , "));
		if (StringUtils.isEmpty(uploadedFileName)) {
			return new ResponseEntity<String>("please select files!",
					HttpStatus.OK);
		}

		if (uploadfiles.length == 0) {
			return new ResponseEntity<String>("please select files!",
					HttpStatus.OK);
		}

		try {
			/** File will get saved to file system and database */
			iFileUploadService.saveUploadedFiles(Arrays.asList(uploadfiles));
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Successfully uploaded - "
				+ uploadedFileName, HttpStatus.OK);

	}

	/**
	 * Rest endpoint api to get uploaded files
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFileUploadMetaData", method = RequestMethod.GET)
	public ResponseEntity<?> getAllDetails() {

		List<FileUploadMetaDataModel> fileMetaDataList = iFileUploadService
				.getfileUploadMetaData();

		if (fileMetaDataList.isEmpty()) {
			CustomError customError = new CustomError();
			customError.setErrDesc("Error while fetching data list");

			return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(fileMetaDataList, HttpStatus.OK);

	}

}
