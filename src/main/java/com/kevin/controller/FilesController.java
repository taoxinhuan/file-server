package com.kevin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kevin.entity.Files;
import com.kevin.service.FilesService;
import com.kevin.utils.Result;
import com.kevin.utils.UploadUtil;

/**
 * @ClassName: FileController
 * @author kevin
 */
@CrossOrigin(origins = "*", maxAge = 3600) // 允许所有域名访问
@Controller
public class FilesController {
	@Autowired
	private FilesService filesService;
	
	@Autowired
	private UploadUtil uploadUtil;
	
	/** 访问地址 */
	@Value("${global.upload.path}")
	private String path;
	
	/** 存放地址 */
	@Value("${global.upload.docBase}")
	private String docBase;
	
	/**
	 * @Title: index
	 * @Description: 首页,显示最新上传的10条文件信息
	 * @author kevin
	 */
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("files", filesService.listFilesByPage(0, 10));
		return "index";
	}
	
	/**
	 * @Title: listFilesByPage
	 * @Description: 分页查询文件信息
	 * @author kevin
	 */
	@GetMapping("files/{pageIndex}/{pageSize}")
	@ResponseBody
	public List<Files> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return filesService.listFilesByPage(pageIndex, pageSize);
	}
	
	/**
	 * @Title: uploadFile
	 * @Description: 上传文件
	 * @author kevin
	 */
	@PostMapping("/upload")
	@ResponseBody
	public Result uploadFile(@RequestParam("file") MultipartFile[] file, HttpServletRequest request) {
		final Map<String, Object> result = new HashMap<String, Object>();
		// 判断file数组不能为空并且长度大于0
		if (file != null && file.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < file.length; i++) {
				MultipartFile f = file[i];
				// 判断文件是否为空
				if (!f.isEmpty()) {
					String fileName = f.getOriginalFilename();
					// 文件后缀
					String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
					// 重命名文件
					String nowName = uploadUtil.getNewName(fileType);
					// 文件大小
					String fileSize = f.getSize() + "";
					// 访问路径
					String url = "http://" + request.getLocalAddr() + ":" + request.getLocalPort();
					final ByteArrayOutputStream fileStream = new ByteArrayOutputStream();
					// 存放位置
					String savePath = uploadUtil.getFilePath(docBase, nowName);
					try {
						// 复制文件内容
						IOUtils.copy(f.getInputStream(), fileStream);
						// 复制文件
						FileUtils.copyInputStreamToFile(new ByteArrayInputStream(fileStream.toByteArray()),
								new File(savePath));
					} catch (Exception e) {
						return new Result(0, "上传失败", null);
					}
					result.put("filename", fileName);
					result.put("nowname", nowName);
					result.put("filetype", fileType);
					result.put("filesize", fileSize);
					result.put("savepath", savePath);
					result.put("url", url);
					filesService.saveFile(new Files(fileName, nowName, savePath, url, fileType, fileSize));
				} else {
					return new Result(0, "请选择文件上传", result);
				}
			}
		}
		return new Result(1, "上传成功", result);
	}
	
	/**
	 * @Title: downFile
	 * @Description: 下载文件
	 * @author kevin
	 */
	@GetMapping("files/{id}")
	public String downFile(HttpServletResponse response, @PathVariable Long id) throws Exception {
		Files files = filesService.getFileById(id);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String savePath = files.getSavepath();
		Long fileSize = Long.parseLong(files.getFilesize());
		String filename = new String(files.getFilename().getBytes("UTF-8"), "ISO8859-1");
		// 设置文件输出类型
		response.setContentType("application/octet-stream");
		// 设置文件输出名称
		response.setHeader("Content-disposition", "attachment; filename=" + filename);
		// 获取文件的长度
		response.setHeader("Content-Length", String.valueOf(fileSize));
		// 获取输入流
		bis = new BufferedInputStream(new FileInputStream(savePath));
		// 获取输出流
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		// 关闭流
		bis.close();
		bos.close();
		return null;
	}
	
	/**
	 * @Title: deleteFile
	 * @Description: 删除文件
	 * @author kevin
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result deleteFile(@PathVariable Long id) {
		try {
			filesService.removeFile(id);
			return Result.ok();
		} catch (Exception e) {
			return Result.build(0, "删除失败");
		}
	}
}
