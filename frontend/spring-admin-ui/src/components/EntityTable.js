import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../assets/css/homepage.css";

function EntityTable() {

    const [entities, setEntities] = useState();

    useEffect(() => {
        fetch("http://localhost:8080/")
            .then((response) => response.json())
            .then((result) => {
                setEntities(result);
            }
            )
    }, []);

    return (
        <div className="home-container">
            <h1>Data Admin</h1>
            <div style={{paddingLeft : "5%"}}>
                <table className="entity-table">
                    <thead>
                        <tr>
                            <th>Tables</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {entities && Object.keys(entities).map((key, index) => {
                            var entityName = key.split(".").at(-1);
                            var addUrl = '/add/' + entityName;
                            var changeUrl = '/change/' + entityName;
                            return (
                                <tr key={entityName}>
                                    <td>{entityName}</td>
                                    <td id="options"><Link to= {addUrl} state= {{ data : entities[key], entity : key }}>+ Add</Link>  <Link href= {changeUrl}>% Change</Link></td>
                                </tr>
                            )
                        })}
                    </tbody>
                </table>
            </div>
        </div>
    )

}

export default EntityTable;