package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.FileUploadMetaData;

/** A JPA repository used to perform crud operations on file meta data records in database*/
public interface FileUploadRepository extends JpaRepository<FileUploadMetaData, Long> {
}