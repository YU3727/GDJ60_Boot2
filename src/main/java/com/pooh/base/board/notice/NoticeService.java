package com.pooh.base.board.notice;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pooh.base.board.BoardFileVO;
import com.pooh.base.board.BoardService;
import com.pooh.base.board.BoardVO;
import com.pooh.base.util.FileManager;
import com.pooh.base.util.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j //log 확인을 위함
@Transactional(rollbackFor=Exception.class)
public class NoticeService implements BoardService{
	//source - Override/implement Method 눌러서 작성
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	//properties 파일에 key값을 value에 넣어주면 path에 value(D:/production/upload/notice)값이 저장된다.
	//외부 값을 끌어쓰기에 좋다.
	@Value("${app.upload.notice}")
	private String path;
	
	@Autowired
	private FileManager fileManager;

	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
		// TODO Auto-generated method stub
		pager.makeStartRow();
		
		pager.makeBlock(noticeDAO.getTotalCount(pager));
		
		return noticeDAO.getList(pager);
	}

	@Override
	public BoardVO getDetail(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.getDetail(boardVO);
	}
	
	@Override
	public BoardFileVO getFileDetail(BoardFileVO boardFileVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.getFileDetail(boardFileVO);
	}

	@Override
	public int setInsert(BoardVO boardVO, MultipartFile[] multipartFiles) throws Exception {
		
		//글 추가
		int result = noticeDAO.setInsert(boardVO);
		//auto-increment로 생성된 num이 잘 들어가있는지 확인.(oracle에서 selectKey와 유사함)
		log.error("Num: {}", boardVO.getNum());
		
		Random random = new Random();
		int num = random.nextInt(1);	
		if(num==0) {
			throw new Exception();
		}
		
		//파일저장 - 파일이 있는경우에만 시행
		if(multipartFiles!=null) {
			for(MultipartFile multipartFile : multipartFiles) {
				//빈 파일이 오는 경우는 제외한다. - 입력폼을 만들고 파일을 넣지 않는경우에 객체는 만들어지지만, 파일이 없기때문에 사이즈가 없다.
				//또는 multipartFile.getSize()로 비교도 가능
				if(!multipartFile.isEmpty()) { //비어있다면 continue;로 넘겨도됨
					String fileName =  fileManager.saveFile(path, multipartFile);
					log.error("fileName : {}", fileName);
					
					BoardFileVO boardFileVO = new BoardFileVO();
					boardFileVO.setFileName(fileName);
					boardFileVO.setOriName(multipartFile.getOriginalFilename());
					//auto-increment로 num을 받아오기때문에 어떻게 넣어줘야하는지 생각해봐야한다.
					//useGeneratedKeys로 num값을 생성하고, keyProperty로 num값을 입력해주므로, 글추가 -> 파일추가 순으로 작업한다.
					boardFileVO.setNum(boardVO.getNum());
					
					//파일 정보 DB에 입력하기
					result = noticeDAO.setFileInsert(boardFileVO);
				}
			}
		}
		return result;
	}

	@Override
	public int setUpdate(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setDelete(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
