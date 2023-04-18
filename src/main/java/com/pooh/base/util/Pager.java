package com.pooh.base.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pager {

	//page 번호를 담을 변수
	private Long page;
	//한 페이지에 보여줄 글의 개수
	private Long perPage;
	//시작 index 번호
	private Long startRow;
	//오라클과는 달리 LIMIT를 사용하기 때문에, lastRow가 필요없음
	
	//전체 페이지 개수
	private Long totalPage;
	//한 블럭당 출력할 페이지수
	private Long perBlock;
	//한블럭 내 시작번호와 끝번호
	private Long startNum;
	private Long lastNum;
	//시작과 끝 블럭에서 블럭 사용을 disabled 시키기 위한 스위치
	private boolean before;
	private boolean after;
	
	//검색기능
	private String kind;
	private String search;
		
	//시작 index 번호를 계산하는 메서드
	public void makeStartRow() {
		//page = 1, startRow = 0
		//page = 2, startRow = 10
		//page = 3, startRow = 20
		this.startRow = this.getPerPage()*(this.getPage()-1);
	}
	
	//page를 계산하는 메서드
	public void makeBlock(Long totalCount) {
		//페이저 만드는 메서드 진행순서
		//1. 전체 글(row)의 갯수를 받아옴
		//2. 전체 글의 갯수로 전체 페이지(page) 갯수 구하기
		//3. 전체 페이지로 전체 블럭의 갯수 구하기
		//4. page 번호로 현재 블럭 번호 구하기
		//5. 현재 블럭 번호로 시작 번호, 끝 번호 구하기
		//6. 이전 블럭과 다음 블럭의 유무 확인
		//7. 현재 블럭이 마지막 블럭 번호와 같다면 페이저의 마지막 번호(lastNum)가 전체 페이지(totalPage)와 같게 설정
		
		//시작
		//1. 전체 글의 갯수를 받아옴 - 매개변수로 받아온다. DAO에서 전체 row를 구하는 메서드를 실행시켜줌.
		//2. 전체 글의 갯수(row)로 전체 페이지 갯수(page) 구하기 - 나머지가 0이 아니면 토탈페이지 +1 해주기
		this.totalPage = totalCount/this.getPerPage();
		if(totalCount%this.getPerPage()!=0) {
			totalPage++;
		}
		//현재 page가 totalPage를 넘지 않게 설정
		if(this.getPage()>totalPage) {
			this.setPage(totalPage);
		}
		
		//3. 전체 페이지로 전체 블럭의 갯수 구하기(총 40페이지를 블럭당 5페이지씩 보여줄거면 총 블럭은 8개가 있어야함)
		Long totalBlock = totalPage/getPerBlock();
		//3-1. 총 43페이지를 블럭당 5페이지씩 보여줄거면 총 블럭은 9개가 있어야함
		if(totalPage%this.getPerBlock()!=0) {
			totalBlock++;
		}
		
		//4. page 번호로 현재 블럭 번호 구하기(현재 페이지가 35번째 페이지면, 현재 블럭은 7번째 블럭이다)
		//page:1-5 -> curBlock=1 / page:6-10 -> curBlock=2 / page:11-15 -> curBlock=3 ...
		Long currentBlock = this.getPage()/getPerBlock();
		//4-1. 나머지가 0이 아닌 경우, 현재 블럭의 값을 1 더해줌.(현재 페이지가 38번째 페이지면, 현재 블럭은 8번째 블럭이다. 7+1)
		if(this.getPage()%getPerBlock()!=0) {
			currentBlock++;
		}
		
		//5. 현재 블럭 번호로 시작 번호, 끝 번호 구하기
		//curB -> startNum ~ lastNum
		//curB=1 -> 1 ~ 5 / curB=2 -> 6 ~ 10 / curB=3 -> 11 ~ 15 ...
		this.startNum = (currentBlock-1)*getPerBlock() +1;
		this.lastNum = currentBlock*getPerBlock();
		
		//6. 현재 블럭이 마지막 블럭 번호와 같다면 페이저의 마지막 번호(lastNum)가 전체 페이지(totalPage)와 같게 설정
		if(currentBlock==totalBlock) {
			this.lastNum = totalPage;
		}
		
		//7. 이전 블럭과 다음 블럭의 유무 확인
		this.before = false;
		this.after = false;
		if(currentBlock==1) {
			this.before=true;
		}
		if(currentBlock==totalBlock) {
			this.after=true;
		}
		
	}
	
	
	//getter를 이용한 초기값 세팅 - 명시를 하면 lombok이 만든 getter/setter 대신 이걸로 사용
	//getter/setter 메서드 명은 우측의 Outline 참고하면 됨 - 안보이는경우 window - show view
	public Long getPage() {
		//기본값 설정
		if(this.page == null || this.page < 1) {
			this.page = 1L;
		}
		return this.page;
	}
	
	public Long getPerPage() {
		if(this.perPage == null || this.perPage == 0) {
			this.perPage = 10L;
		}
		return this.perPage;
	}
	
	public Long getPerBlock() {
		if(this.perBlock == null || this.perBlock == 0) {
			this.perBlock = 5L;
		}
		return this.perBlock;
	}
	
	public String getSearch() {
		if(this.search == null) {
			search = "";
		}
		return search;
	}
}
