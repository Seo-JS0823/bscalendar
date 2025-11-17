/** init */
let init = () => {
  let el, els = document.querySelectorAll('.box');
  let btnsendEl = document.getElementById('btnsend'); //가입버튼 활성 or 비활성화
  
  // 아이디 유효성 체크
  el = els[3].children[0];
  el.onkeyup = e => {
    let el = e.target;
    
    if( el.value.length !== 0 ) {
      let msg = e.target.parentElement.nextElementSibling;

      if( onlyNumberAndEng(el.value) === false ) {
          msg.innerText = '아이디: 영문 또는 숫자만 가능합니다.';
          btnsendEl.disabled = true;
      } else if( idLength(el.value) === false ) {
          msg.innerText = '아이디: 5~20자의 글자여야 합니다.';
          btnsendEl.disabled = true; } 
    } 
  }
  // 아이디 중복 체크
  /*el = els[3].children[0];
  el.onblur = e => {
      let el = e.target;
      if (( onlyNumberAndEng(el.value) || idLength(el.value) )){
      verifyUserId(el.value); }
  }*/
  
  // 비밀번호 유효성 체크
  el = els[4].children[0]; 
  el.onkeyup = e => {
    let el = e.target;

    if( el.value.length !== 0 ) {
      let msg = e.target.parentElement.nextElementSibling;

      if( checkPassword(el.value) === false ) {
          msg.innerText = '비밀번호: 8글자 이상의 영문, 숫자, 특수문자 조합으로 사용하세요.';
          btnsendEl.disabled = true;
      } else {
          msg.innerText = '';
          btnsendEl.disabled = false; }
    } 
  }

  // 비밀번호 재입력란 일치 체크
  el = els[5].children[0]; 
  el.onkeyup = e => {
    let el = e.target;

    if( el.value.length !== 0 ) {
      let msg = e.target.parentElement.nextElementSibling;

      if( isMatch(els[4].children[0].value, el.value) === false ) {
          msg.innerText = '비밀번호 확인: 비밀번호와 일치하지 않습니다.';
          btnsendEl.disabled = true;
      } else {
          msg.innerText = '';
          btnsendEl.disabled = false; }
    } 
  }
} // End of ## init

/** 아이디: 글자수 제한 체크*/
let idLength = value => value.length >= 5 && value.length <= 20;
/** 아이디: 숫자, 영문 제한 체크*/
let onlyNumberAndEng = str =>  /^[A-Za-z0-9][A-Za-z0-9]*$/.test(str);
/** 비밀번호: 숫자, 영문, 특수문자 체크*/
let checkPassword = str =>  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(str);
/** 비밀번호: 재입력 체크 */
let isMatch = (password1, password2) => password1 === password2; 

/** 필수항목 입력체크 */
function doSignUp() {
  let el, els = document.querySelectorAll('.box');
  
  el = els[0].children[0]; 
  if( el.value.length == 0 ) {
    let msg = el.parentElement.nextElementSibling;

    msg.innerText = '이름을 입력해주세요.'; return false;
  }
  
  el = els[1].children[0]; 
  if( el.value.length == 0 ) {
    let msg = el.parentElement.nextElementSibling;

    msg.innerText = '직책을 입력해주세요.'; return false;
  }
  
  el = els[2].children[0]; 
  if( el.value.length == 0 ) {
    let msg = el.parentElement.nextElementSibling;

    msg.innerText = '부서명을 입력해주세요.'; return false;
  }
  
  el = els[3].children[0]; 
  if( el.value.length == 0 ) {
    let msg = el.parentElement.nextElementSibling;

    msg.innerText = '아이디를 입력해주세요.'; return false;
  }
  
  el = els[4].children[0]; 
  if( el.value.length == 0 ) {
    let msg = el.parentElement.nextElementSibling;

    msg.innerText = '비밀번호를 입력해주세요.'; return false;
  }
  
  el = els[5].children[0]; 
  if( el.value.length == 0 ) {
    let msg = el.parentElement.nextElementSibling;

    msg.innerText = '비밀번호 확인을 입력해주세요.'; return false;
  }
  
  return true;
} // End of ## 필수항목 체크

/** 아이디 중복 체크 */
let verifyUserId = userId => {
    let btnsendEl = document.getElementById('btnsend'); //가입버튼 활성 or 비활성화
    let url = "/api/member/ajaxIdChk";
    let json = { CheckId: userId };
  let data = {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(json) };

  fetch(url, data)
    .then(response => response.json())
    .then(data => { // 정상적인 통신이 되었을 때 실행되는 콜백
        let el = document.getElementsByTagName("input")[3].parentElement.nextElementSibling;
        
      if(data.result == "success"){ // 사용 가능
        el.innerText = "사용가능한 아이디입니다.";
        btnsendEl.disabled = false;
      } else{ // 사용 불가능
        el.innerText = "이미 사용중인 아이디입니다.";
        btnsendEl.disabled = true; }            
    })
    .catch(error => console.log("error:", error)) // 정상적인 통신이 안 되었을 경우 실행되는 콜백
} // End of verifyUserId*/