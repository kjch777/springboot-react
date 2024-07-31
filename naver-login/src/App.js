import logo from './logo.svg';
import './App.css';
import { Login as Login } from './Login';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import { UserInfo } from './UserInfo';
// html 파일이 1개밖에 없는 React 에서는,
// Router 를 사용하여 각 js 파일의 경로를 설정해주어야 한다.
// BrowserRouter as Router ◀ Web 의 전체적인 경로
// Switch → Routes ◀ 경로들
// Route ◀ 경로
function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Login />} />
        <Route path='/userinfo' element={<UserInfo />} />
      </Routes>
    </Router>
  );
}

export default App;
