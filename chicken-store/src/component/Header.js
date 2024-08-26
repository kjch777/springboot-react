import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export const Header = () => {
    
    const navigate = useNavigate(); // 페이지 이동을 위한 Hook

    // 검색어 상태 추가 ◀ 맨 처음에는 검색 값이 없기 때문에 공란이다.
    const [searchTerm, setSearchTerm] = useState('');

    const handleSearch = () => {
        navigate(`/search?query=${searchTerm}`); // 검색 페이지로 이동하면서 검색어 전달
      }

    return (
        <div className='search-container'>
            <input type='text' placeholder='검색하고 싶은 치킨 메뉴를 입력하세요.' value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} className='search-input' />
            <button className='search-button' onClick={handleSearch}>검색하기</button>
        </div>
    )
    
}