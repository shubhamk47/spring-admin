import logo from './logo.svg';
import './App.css';
import EntityDetails from './components/EntityDetails';
import { useEffect, useState } from 'react';
import axios from "axios";

function App() {

  const [entityList, setEntityList] = useState(null);
  const [objects, setObjects] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:8080/").then((response) => {
      setEntityList(response.data);
      // console.log(Object.values(response.data));
    }).catch((error) => {
      console.log(error);
    })
  }, [])

  const getAllObjects = (entity) => (e) => {
    e.preventDefault();
    axios.get("http://localhost:8080/" + entity).then((response) => {
      setObjects(response.data);
      console.log(response.data)
    }).catch((error) => {
      console.log(error);
    });
    console.log(entity);
  }

  if(!entityList) return null;

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        {Object.keys(entityList).map((k, i) => {
          return <a key={i} href={k.split(".").pop()} onClick={getAllObjects(k.split(".").pop())}>{k.split(".").pop()}</a>;
        })}
        <EntityDetails />
      </header>
    </div>
  );
}

export default App;
