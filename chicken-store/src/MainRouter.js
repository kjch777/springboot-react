import logo from './logo.svg';
import './App.css';
import { ChickenList } from './component/ChickenList';
import { ChickenForm } from './component/ChickenForm';
import { Modal } from './component/Modal';
import { useState } from 'react';

function MainRouter() {
  const [isModalOpen, setIsModalOpen] = useState(false); // client 가 클릭했을 때만 볼 수 있도록, 처음에는 false(보이지 않기) 로 설정해주는 것

  // open ▶ true / close ▶ false
  const openModal = () => setIsModalOpen(true);
  
  // ▲ 와 ▼ 코드는 서로 같은 형식이다.
  // const 에서 동작하는 기능이 1개일 때는, { } 를 생략하는 것이 가능하다.
  
  const closeModal = () => {
    setIsModalOpen(false);
  }

  return (
    <div className="app-container">
      <h1>치킨 가게 메뉴 관리</h1>
      <button onClick={openModal} className='chickenMenu-registerButton'>메뉴 등록하기</button>
      <ChickenList />
      <Modal isOpen={isModalOpen} onClose={closeModal}>
        <ChickenForm />
      </Modal>
    </div>
  );
}

export default MainRouter;
