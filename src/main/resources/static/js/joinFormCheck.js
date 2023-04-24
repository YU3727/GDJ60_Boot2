 //joinForm에서 검증기능 동작
 
 //회원가입창에서 되돌아가기
 $('#joinBackBtn').click(function(){
    location.href="/";
 })


 //id 중복 체크
 $('#userName').blur(idDuplicateCheck);

 function idDuplicateCheck() {
    //ajax
    //fetch - JavaScript
    //$.get, $.post, $.ajax - jQuery

    //ajax 내부에는 object(객체)가 있다.
    //기본 세팅은 여기까지
    $.ajax({
        type:"GET",
        url:"./idDuplicateCheck",
        data:{
            userName:$('#userName').val()
        },
        success:function(response){ //응답으로 오는 결과를 함수의 매개변수로 받는다.
            console.log(response);
            //response가 boolean 타입인지 체크
            if(!response){
                alert('중복된 id입니다');
                $('#userName').val('');
            }
        },
        error:function(){ //에러가 발생했을때의 조치도 입력해준다.
            console.log('error');
        }
    })
 }


 //pw 일치 체크
 






  //회원가입 정보 보내기
  $('#joinBtn').click(function(){
    //server에서 유효성검증
    $('#joinForm').submit();

    //front에서 검증
    // let chk = true;
    // let userName = $('input[name=userName]').val();
    // let passWord = $('input[name=passWord]').val();
    // let name = $('input[name=name]').val();
    // let email = $('input[name=email]').val();
    // let birth = $('input[name=birth]').val();

    // if(userName==''){
    //     chk = false;
    // }else if(passWord==''){
    //     chk = false;
    // }else if(name==''){
    //     chk = false;
    // }else if(email==''){
    //     chk = false;
    // }else if(birth==''){
    //     chk = false;
    // }

    // if(chk){
    //     $('#joinForm').submit();
    // }else{
    //     alert('빈 칸을 입력하세요');
    // }

 })