import React, { useContext } from "react";
import { AuthContext } from "./AuthContext";
import { Link } from "react-router-dom";

// 로그인 성공 시 보여질 Header
// 로그인 성공 시 ○○님 환영합니다! 가 보여지게 만들기
// 비밀번호 암호화 처리하여 DB 에 저장

// 비밀번호 찾기 ▶ 더이상 기존 비밀번호를 찾을 수 없기 때문에, client 로부터 새로운 비밀번호를 입력받아 저장할 것

export const Header = () => {
    
    const {loginMember, setLoginMember} = useContext(AuthContext);
    /* 
        const [loginMember, setLoginMember] = useContext(AuthContext); ◀ [] 변수를 새로 설정할 때
        const {loginMember, setLoginMember} = useContext(AuthContext); ◀ {} 외부에서 작성된 변수를 가져와서 사용할 때
    */

    // localStorage: client 컴퓨터의 웹 사이트에 데이터를 영구적으로 저장한다.
    // localStorage: 저장된 데이터는 웹 브라우저를 닫거나, 컴퓨터를 껐다 켜도 유지된다.
    // client 가 직접 로그아웃을 하거나, 타이머를 설정해서 삭제되게 만들거나, 캐시를 지우지 않는한 유지된다.
    // ex) 구글 크롬 로그인
    const handleLogout = () => {
        setLoginMember(null);
        localStorage.removeItem('loginMember');
    }

    return (
        <header>
            <h1>Header Section</h1>
            <nav>
            {/* {loginMember ? (존재한다면) : (존재하지않는다면)} */}
            {/* {loginMember ? (<span>{loginMember.Java dto NaverUser 에 작성된 변수명}님 환영합니다.</span>) : ()} */}
                {loginMember ? 
                    
                    (<div>
                        <span>{loginMember.name}님 환영합니다.</span>
                        <button onClick={handleLogout}>로그아웃</button>
                    </div>) : 
                    
                    (<div>
                        <Link to="/login">로그인 하러가기</Link>
                        <Link to="/api/naver">회원가입 하러가기</Link>
                    </div>)}
            </nav>
        </header>
    )
}