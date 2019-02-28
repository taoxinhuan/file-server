package com.kevin.service;

import java.util.List;

import com.kevin.entity.Files;
import com.kevin.utils.Result;

/**
 * @ClassName: FileService
 * @Description:文件服务接口
 * @author kevin
 */
public interface FilesService {
	/**
	 * 保存文件
	 * @param Files
	 * @return
	 */
	Result saveFile(Files files);
	
	/**
	 * 删除文件
	 * @param Files
	 * @return
	 */
	void removeFile(Long id);
	
	/**
	 * 根据id获取文件
	 * @param Files
	 * @return
	 */
	Files getFileById(Long id);
	
	/**
	 * 分页查询，按上传时间降序
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	List<Files> listFilesByPage(int pageNumber, int pageSize);
}
