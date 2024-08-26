import logo from './logo.svg';
import './App.css';
import { ChickenList } from './component/ChickenList';
import { ChickenForm } from './component/ChickenForm';
import { Modal } from './component/Modal';
import { useState } from 'react';

function MainRouter() {
  // const navigate = useNavigate(); // 페이지 이동을 위한 Hook

  const [isModalOpen, setIsModalOpen] = useState(false); // client 가 클릭했을 때만 볼 수 있도록, 처음에는 false(보이지 않기) 로 설정해주는 것

  // 검색어 상태 추가 ◀ 맨 처음에는 검색 값이 없기 때문에 공란이다.
  // const [searchTerm, setSearchTerm] = useState('');

  // open ▶ true / close ▶ false
  const openModal = () => setIsModalOpen(true);
  
  // ▲ 와 ▼ 코드는 서로 같은 형식이다.
  // const 에서 동작하는 기능이 1개일 때는, { } 를 생략하는 것이 가능하다.
  
  const closeModal = () => {
    setIsModalOpen(false);
  }

  // const handleSearch = () => {
  //   navigate(`/search?query=${searchTerm}`); // 검색 페이지로 이동하면서 검색어 전달
  // }

  return (
    <div className="app-container">
      <h1>치킨 가게 메뉴 관리</h1>

      {/* <div className='search-container'>
        <input type='text' placeholder='검색하고 싶은 치킨 메뉴를 입력하세요.' value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} className='search-input' />
        <button className='search-button' onClick={handleSearch}>검색하기</button>
      </div> */}

      <button onClick={openModal} className='chickenMenu-registerButton'>메뉴 등록하기</button>
      <ChickenList />
      <Modal isOpen={isModalOpen} onClose={closeModal}>
        <ChickenForm />
      </Modal>
    </div>
  );
}

export default MainRouter;
