import '../css/Login.css';

export const Login = () => {

    return (
        <div className='login-container'>
            <h3>로그인</h3>
            <div>
                <label>
                    아이디: 
                    <input type="text" placeholder="아이디를 입력하세요." />
                </label>
                <label>
                    비밀번호: 
                    <input type="password" placeholder="비밀번호를 입력하세요." />
                </label>
                <button>로그인 하기</button>
                <div className='findSign-buttons'>
                    <button>아이디 찾기</button>
                    <button>비밀번호 찾기</button>
                    <button>회원가입 하기</button>
                </div>
            </div>
            <label>
                SNS로 로그인 하기: 
                <img src="/naver_img/btnG_circleIcon.png" className='naverLogo-img' />
            </label>
            {/* 
                <button className='naverLogin-button'>
                    <img src="/naver_img/btnG_circleIcon.png" />
                    네이버로 회원가입 하기
                </button> 
            */}
        </div>
    )
}