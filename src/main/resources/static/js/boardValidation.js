/**
 * boardForm - Validation Check
 */
//선택자로 이벤트 걸 대상 선택
const submitButton=document.getElementById('submitButton');

//이벤트 걸기
submitButton.addEventListener('click', function(){
	//확인용
	console.log('submitBtn click');
	
	let chkTitle = document.getElementById('title').value;
	let chkWriter = document.getElementById('writer').value;
	
	if(chkTitle!='' && chkWriter!=''){
		let frm = document.getElementById('contactForm');
		frm.submit();
	}else{
		alert('title, writer는 필수 입력값입니다.');
	}
	
});