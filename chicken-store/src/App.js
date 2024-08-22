import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ChickenList } from './component/ChickenList';
import { ChickenDetail } from './component/ChickenDetail';

function App () {
    return (
        <Router>
            <Routes>
                <Route path='/' element={<ChickenList />} />
                <Route path='/chicken-detail/:id' element={<ChickenDetail />} />
            </Routes>
        </Router>
    )
}

export default App;