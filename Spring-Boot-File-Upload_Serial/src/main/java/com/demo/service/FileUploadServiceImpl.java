package com.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.dao.FileUploadRepository;
import com.demo.entity.FileUploadMetaData;
import com.demo.model.FileUploadMetaDataModel;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

	/** Save the uploaded file to this folder */
	private static String UPLOADED_FOLDER = "C:" + File.separator + "Users"
			+ File.separator + "Ahmar" + File.separator + "Desktop"
			+ File.separator + "images" + File.separator;

	/**
	 * Thdis object is required to store file meta data into in memory database
	 */
	@Autowired
	private FileUploadRepository fileUploadMetaData;

	/**
	 * Method is use to save Uploaded files
	 */
	public void saveUploadedFiles(List<MultipartFile> files) throws IOException {
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			saveMetaData(file, path);

		}

	}

	/**
	 * Method is use to save metadata of multipart
	 */
	public void saveMetaData(MultipartFile file, Path path) throws IOException {
		FileUploadMetaData metaData = new FileUploadMetaData();
		metaData.setName(file.getOriginalFilename());
		metaData.setContentType(file.getContentType());
		metaData.setContentSize(file.getSize());
		metaData.setPath(path.toString());

		fileUploadMetaData.save(metaData);
	}

	/**
	 * Method is use to return all metadata
	 * 
	 */

	public List<FileUploadMetaDataModel> getfileUploadMetaData() {
		List<FileUploadMetaDataModel> list = new ArrayList<>();

		List<FileUploadMetaData> fileMetaData = fileUploadMetaData.findAll();

		fileMetaData
				.forEach(x -> {
					FileUploadMetaDataModel fileUploadMetaDataModel = new FileUploadMetaDataModel();

					BeanUtils.copyProperties(x, fileUploadMetaDataModel);

					list.add(fileUploadMetaDataModel);
				});

		return list;

	}
}
