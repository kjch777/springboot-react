import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { PizzaRouter } from './components/PizzaRouter';
import { PizzaList } from './components/PizzaList';
import { PizzaResult } from './components/PizzaResult';

function App() {
  return (
    <Router>
      <PizzaRouter />
      <Routes>
        <Route path='/' element={<PizzaList />} />
        <Route path='/search' element={<PizzaResult />} />
        {/* <Route path='/pizza-detail' element={< />} /> */}
      </Routes>
    </Router>
  );
}

export default App;
