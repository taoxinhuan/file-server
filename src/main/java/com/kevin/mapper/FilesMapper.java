package com.kevin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kevin.entity.Files;

@Mapper
public interface FilesMapper {
	void deleteByPrimaryKey(Long id);
	
	void insertSelective(Files record);
	
	Files selectByPrimaryKey(Long id);
	
	void updateByPrimaryKey(Files record);
	
	List<Files> findAll();
}
