import React from "react";
import { useLocation, useParams } from "react-router-dom";
import { Button, DatePicker, Form, Input } from "antd";
import '../assets/css/entityform.css';

function AddEntity() {

    let entityDetails = useLocation();
    // let { entity } = useParams();
    var eDetails = entityDetails.state.data;

    const [form] = Form.useForm();
    const submitHandler = (values) => {
        console.log(values.dob.$d.toDateString());
        values.class = entityDetails.state.entity;
        fetch("http://localhost:8080/save", {
            method: "POST",
            mode: "cors",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(values)
        }).then((response) => console.log(response));
    };

    return (
        <div className="Container">
            <Form form={form} onFinish={submitHandler}>
                {Object.keys(eDetails).map((i, key) => {
                    if (eDetails[i] === "java.lang.String") {
                        return (
                            <Form.Item key={i} name={i}>
                                <Input key={i} placeholder={i} />
                            </Form.Item>
                        );
                    } else if (eDetails[i] === "java.time.LocalDate") {
                        return (
                            <Form.Item key={i} name={i}>
                                <DatePicker disabledTime key={i} placeholder={i} />
                            </Form.Item>
                        );
                    } else {
                        return (
                            <Form.Item key={i} name={i}>
                                <Input key={i} placeholder={i} />
                            </Form.Item>
                        );
                    }
                })}

                <Button htmlType="submit">Submit</Button>
            </Form>
        </div>
    );
}

export default AddEntity;