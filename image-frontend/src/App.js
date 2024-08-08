import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Banner from './components/Layout/Banner';
import Header from './components/Layout/Header';
import Main from './components/Main';
import Board from "./components/Board";
import Profile from './components/Profile';
import Footer from './components/Layout/Footer';

// frontEnd api URL 설정 ◀ react router dom

// frondEnd api URL
// Board path = "/board"
// Profile path = "/profile"

function App () {
    return (
        <Router>
            <Banner />
            <Header />
                <Routes>
                    <Route path='/' element={<Main />} />
                    <Route path="/board" element={<Board />} />
                    <Route path="/profile" element={<Profile />} />
                </Routes>
            <Footer />
        </Router>
    )
}

export default App;