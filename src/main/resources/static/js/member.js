/**
 * member 관련
 */

 
 //로그인창에서 되돌아가기
 $('#loginBackBtn').click(function(){
    location.href="/";
 })

 //로그인 정보 보내기
 $('#loginBtn').click(function(){
    chkValidation();
 })



 function chkValidation(){
    //확인할 값 선택하기
    let userName = $('input[name=userName]').val();
    let passWord = $('input[name=passWord]').val();

    if(userName==''){
        alert('username을 입력하세요');
    }else if(passWord==''){
        alert('password를 입력하세요');
    }else{
        $('#loginForm').submit();
    }
 }


 //비밀번호 찾기 창에서 되돌아가기
 $('#findBackBtn').click(function(){
    location.href="/member/login";
 })

 //비밀번호 찾기 정보 보내기
 $('#findBtn').click(function(){
    $('#findForm').submit();
 })