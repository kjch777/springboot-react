import logo from './logo.svg';
import './App.css';
import { ChickenList } from './component/ChickenList';
import { ChickenForm } from './component/ChickenForm';

function App() {
  return (
    <div className="App">
      <ChickenList />
      <ChickenForm />
    </div>
  );
}

export default App;
