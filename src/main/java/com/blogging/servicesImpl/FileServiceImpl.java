package com.blogging.servicesImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogging.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	
	
	
	
	
	
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		String fileName = null;
		
		try {
			
			// get the image name
			String imageName = file.getOriginalFilename();
			
			//random name generate for image file
			String randomID = UUID.randomUUID().toString();
			fileName = randomID.concat(imageName.substring(imageName.lastIndexOf(".")));
			
			// full path, where file needs to be uploaded
			String finalPath = path + File.separator + fileName;
			
			// create folder if not created
			File f = new File(path);
			
			if(!f.exists()) {
				
				f.mkdir();
			}
			
			
			// file write or copy
			Files.copy(file.getInputStream(), Paths.get(finalPath));
			
			return fileName;
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return fileName;
	}

	
	
	
	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		//getting the full path
		
		String fullPath = path + File.separator + fileName;
		
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}
	
	

}
