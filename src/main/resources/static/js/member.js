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

 //회원가입창에서 되돌아가기
 $('#joinBackBtn').click(function(){
    location.href="/";
 })

 //회원가입 정보 보내기
 $('#joinBtn').click(function(){
    let chk = true;
    let userName = $('input[name=userName]').val();
    let passWord = $('input[name=passWord]').val();
    let name = $('input[name=name]').val();
    let email = $('input[name=email]').val();
    let birth = $('input[name=birth]').val();

    if(userName==''){
        chk = false;
    }else if(passWord==''){
        chk = false;
    }else if(name==''){
        chk = false;
    }else if(email==''){
        chk = false;
    }else if(birth==''){
        chk = false;
    }

    if(chk){
        $('#joinForm').submit();
    }else{
        alert('빈 칸을 입력하세요');
    }

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