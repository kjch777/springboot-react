import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ChickenDetail } from './component/ChickenDetail';
import MainRouter from './MainRouter';
import { SearchResult } from './component/SearchResult';
import { Header } from './component/Header';

function App () {
    return (
        <Router>
            <Header />
            <Routes>
                <Route path='/' element={<MainRouter />} />
                {/* Routes 안에는 Route 로 설정된 태그만 들어올 수 있다. <MainRouter /> ◀ 이런 식으로 넣으면 에러가 발생한다. */}
                <Route path='/chicken-detail/:id' element={<ChickenDetail />} />
                <Route path='/search' element={<SearchResult />} />
            </Routes>
        </Router>
    )
}

export default App;