import logo from './logo.svg';
import './App.css';
import { AirPollutionData } from './AirPollutionData';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>대기 오염 정보 조회</h1>
        <AirPollutionData />
      </header>
    </div>
  );
}

export default App;
