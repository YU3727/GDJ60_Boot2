package com.pooh.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.AbstractView;

import com.pooh.base.board.BoardFileVO;

//customView로 사용하기 위해 abstarctView를 상속받아야한다.
//customView를 사용할 때 bean의 이름을 따로 지정하고 싶다면 @Component("bean 이름 여기에 작성")
@Component
public class FileManager extends AbstractView{
	
	@Value("${app.upload}")
	private String path;
	
	
	//CustomView - 파일 다운로드를 위함
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 BoardFileVO boardFileVO = (BoardFileVO)model.get("boardFileVO");
		 //NoticeController에서 작성했던 ModelAttribute 값 가져오기(게시판 종류의 이름)
		 String board = (String)model.get("board");
		 
		 File file = new File(path+board, boardFileVO.getFileName());
		 
		 //한글 처리
		 response.setCharacterEncoding("UTF-8");
		 
		 //총 파일의 크기
		 response.setContentLengthLong(file.length());
		 
		 //다운로드시 파일의 이름을 인코딩
		 String oriName = URLEncoder.encode(boardFileVO.getOriName(), "UTF-8");
		 
		 //header 설정
		 response.setHeader("Content-Disposition", "attachment;filename=\""+oriName+"\"");
		 response.setHeader("Content-Transfer-Encoding", "binary");
		 
		 //HDD에서 파일을 읽고
		 FileInputStream fi = new FileInputStream(file);
		 //Client 로 전송 준비
		 OutputStream os = response.getOutputStream();
		 
		 //전송
		 FileCopyUtils.copy(fi, os);
		 
		 //자원 해제
		 os.close();
		 fi.close();
		
	}
	
	
	
	//파일 저장

	//1. HDD에 파일을 저장하고 저장된 파일명을 리턴
	public String saveFile(String path, MultipartFile multipartFile) throws Exception{
		//session은 경로를 불러오기위해 필요했지만, 지금은 경로가 D: 부터 나와있어서 없어도 된다.
		
		File file = new File(path);
		
		//1. 경로에 폴더가 없는경우 폴더 만들기.
		if(!file.exists()) {
			file.mkdirs(); //dir은 최하위폴더만, dirs는 모든하위폴더까지 다 만듬
		}
		
		//2. 파일의 고유이름 만들기
		String fileName = UUID.randomUUID().toString(); //UUID.randomUUID()는 String이 아님. 그래서 toString()
		//확장자 추가(지금은 원래 파일이름 통째로 추가)
		fileName = fileName+"_"+multipartFile.getOriginalFilename();
		
		//3. 경로명과 파일명의 정보를 가진 파일 객체 생성 - 위에서 만든 파일객체 재활용
		file = new File(file, fileName); //File(path, fileName)또한 가능하다
		
		//4. 파일을 목적지에 저장하기
		//2~3가지 방법이 있음. multipartFile에 내장되어있는 transTo 또는 fileCopyUtis 이용
		//FileCopyUtils.copy(multipartFile.getBytes(), file); //(
		multipartFile.transferTo(file);
		
		//두가지 방법 다 return이 void라 성공했는지 아닌지 확인할 수 없다. 단 하나, exception이 발생하는 경우를 제외하고.
		
		return fileName;
		
		
	}
	
}
