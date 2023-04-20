package com.pooh.base.board.qna;

import java.util.List;

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
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class QnaService implements BoardService{
	
	@Autowired
	private QnaDAO qnaDAO;
	
	@Autowired
	private FileManager fileManager;
	
	//파일 저장을 위한 위치설정
	@Value("${app.upload.qna}")
	public String path;

	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
		// TODO Auto-generated method stub
		pager.makeStartRow();
		
		pager.makeBlock(qnaDAO.getTotalCount(pager));
		return qnaDAO.getList(pager);
	}

	@Override
	public BoardVO getDetail(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return qnaDAO.getDetail(boardVO);
	}

	@Override
	public int setInsert(BoardVO boardVO, MultipartFile[] multipartFiles) throws Exception {
		//글저장
		int result = qnaDAO.setInsert(boardVO);
		
		//updateRef
		result = qnaDAO.setRef(boardVO);
		
		//파일저장
		if(multipartFiles!=null) {
			for(MultipartFile multipartFile : multipartFiles) {
				if(!multipartFile.isEmpty()) {
					//파일이름 만들기
					String fileName = fileManager.saveFile(path, multipartFile);
					log.info("fileName =======> {}", multipartFile.getOriginalFilename());
					
					//저장할 파일객체 생성
					BoardFileVO boardFileVO = new BoardFileVO();
					boardFileVO.setFileName(fileName);
					boardFileVO.setOriName(multipartFile.getOriginalFilename());
					boardFileVO.setNum(boardVO.getNum());
					
					//만들어진 파일객체정보 db에 입력
					result = qnaDAO.setFileInsert(boardFileVO);
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

	@Override
	public BoardFileVO getFileDetail(BoardFileVO boardFileVO) throws Exception {
		// TODO Auto-generated method stub
		return qnaDAO.getFileDetail(boardFileVO);
	}
	
}
