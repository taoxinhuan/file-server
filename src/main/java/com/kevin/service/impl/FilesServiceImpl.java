package com.kevin.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.kevin.entity.Files;
import com.kevin.mapper.FilesMapper;
import com.kevin.service.FilesService;
import com.kevin.utils.Result;

/**
 * @ClassName: FileServiceImpl
 * @Description: 文件服务接口实现
 * @author kevin
 */
@Service
public class FilesServiceImpl implements FilesService {
	@Autowired
	private FilesMapper filesMapper;
	
	/**
	 * Title: saveFile Description: 保存文件
	 * @author kevin
	 */
	@Override
	@Transactional
	public Result saveFile(Files files) {
		filesMapper.insertSelective(files);
		Long id = files.getId();
		Files file = filesMapper.selectByPrimaryKey(id);
		String newUrl = file.getUrl() + "/files/" + id;
		file.setUrl(newUrl);
		filesMapper.updateByPrimaryKey(file);
		if (id != null) {
			return Result.ok();
		} else {
			return Result.build(0, "保存失败");
		}
	}
	
	/**
	 * Title: removeFile Description: 删除文件
	 * @author kevin
	 */
	@Override
	public void removeFile(Long id) {
		Files files = getFileById(id);
		if (null != files) {
			filesMapper.deleteByPrimaryKey(id);
			File f = new File(files.getSavepath());
			if (f.exists()) {
				f.delete();
			}
		}
	}
	
	/**
	 * Title: getFileById Description: 根据ID查找
	 * @author kevin
	 */
	@Override
	public Files getFileById(Long id) {
		return filesMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * Title: listFilesByPage Description: 分页查找
	 * @author kevin
	 */
	@Override
	public List<Files> listFilesByPage(int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<Files> list = filesMapper.findAll();
		return list;
	}
}
