import React, { Component } from 'react';
import { motion } from 'framer-motion';
import UserConsumer from '../context';
import axios from 'axios';
// import User from './User';

// var uniqid = require('uniqid');

class AddUser extends Component {
    state = {
        isVisible: true,
        name: "",
        department: "",
        salary: "",
        error: false
    };

    toggleVisibility = () => {
        this.setState(prevState => ({
            isVisible: !prevState.isVisible
        }));
    };

    validateForm = () => {
        const { name, department, salary } = this.state;

        if (name === "" || department === "" || salary === "") {
            return false;
        }
        return true;
    }

    changeInput = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        })
    }
    addUser = async (dispatch, e) => {
        e.preventDefault();
        const { name, department, salary } = this.state;

        const newUser = {
            // id: uniqid(),
            // name: name,
            // salary: salary,
            // department: department
            name, // ES6
            department,
            salary
        }

        if (!this.validateForm()) {
            this.setState({
                error: true
            })
            return;
        }

        const response = await axios.post("http://localhost:3000/users", newUser)

        dispatch({ type: "ADD_USER", payload: response.data })
    }

    render() {
        const { isVisible, name, salary, department, error } = this.state;

        return <UserConsumer>
            {
                value => {
                    const { dispatch } = value;
                    return (
                        <div className='col-md-8 mb-4'>
                            <button className='btn btn-dark btn-block mb-2' onClick={this.toggleVisibility} style={{ display: 'block', width: '100%' }} >
                                {isVisible ? 'Hide Form' : 'Show Form'}
                            </button>

                            {isVisible && (
                                <motion.div
                                    initial={{ opacity: 0, height: 0 }}
                                    animate={{ opacity: 1, height: 'auto' }}
                                    exit={{ opacity: 0, height: 0 }}
                                    transition={{ duration: 0.5 }}
                                >
                                    <div className="card">
                                        <div className="card-header">
                                            <h4>Add User Form</h4>
                                        </div>
                                        {
                                            error ?
                                                <div className='alert alert-danger'>
                                                    Lütfen bilgilerinizi kontrol edin.
                                                </div>
                                                : null
                                        }
                                        <div className="card-body">
                                            <form onSubmit={this.addUser.bind(this, dispatch)}>
                                                <div className="form-group">
                                                    <label htmlFor="name">Name</label>
                                                    <input
                                                        type='text'
                                                        name='name'
                                                        id='name'
                                                        placeholder='Enter Name'
                                                        className='form-control'
                                                        value={name}
                                                        onChange={this.changeInput}
                                                    />
                                                </div>
                                                <div className="form-group">
                                                    <label htmlFor="department">Department</label>
                                                    <input
                                                        type='text'
                                                        name='department'
                                                        id='department'
                                                        placeholder='Enter Department'
                                                        className='form-control'
                                                        value={department}
                                                        onChange={this.changeInput}
                                                    />
                                                </div>
                                                <div className="form-group">
                                                    <label htmlFor="salary">Salary</label>
                                                    <input
                                                        type='text'
                                                        name='salary'
                                                        id='salary'
                                                        placeholder='Enter Salary'
                                                        className='form-control'
                                                        value={salary}
                                                        onChange={this.changeInput}
                                                    />
                                                </div>
                                                <button className='btn btn-danger' type='submit' style={{ display: "block", width: "100%", marginTop: "16px" }}>Add User</button>
                                            </form>
                                        </div>
                                    </div>
                                </motion.div>
                            )}

                        </div>
                    );
                }
            }
        </UserConsumer>


    }
}

export default AddUser;
