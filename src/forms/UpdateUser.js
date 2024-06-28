import React, { useState, useEffect, useContext } from 'react';
import UserContext from '../context';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useParams, useNavigate } from 'react-router-dom';

const UpdateUser = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { dispatch } = useContext(UserContext);

    const [user, setUser] = useState({
        name: "",
        department: "",
        salary: "",
        error: false
    });

    const { name, department, salary, error } = user;

    useEffect(() => {
        const fetchUser = async () => {
            const response = await axios.get(`http://localhost:3000/users/${id}`);
            setUser(response.data);
        };
        fetchUser();
    }, [id]);

    const validateForm = () => {
        if (name === "" || department === "" || salary === "") {
            return false;
        }
        return true;
    };

    const onInputChange = e => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    const onSubmit = async e => {
        e.preventDefault();

        if (!validateForm()) {
            setUser({ ...user, error: true });
            return;
        }

        const updatedUser = { name, department, salary, };
        const response = await axios.put(`http://localhost:3000/users/${id}`, updatedUser);
        dispatch({ type: "UPDATE_USER", payload: response.data });
        navigate("/users");
    };

    return (
        <div className='col-md-8 mb-4'>
            <div className="card">
                <div className="card-header">
                    <h4>Update User Form</h4>
                </div>
                {
                    error &&
                    <div className='alert alert-danger'>
                        Lütfen bilgilerinizi kontrol edin.
                    </div>

                }
                <div className="card-body">
                    <form onSubmit={onSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <input
                                type='text'
                                name='name'
                                id='name'
                                placeholder='Enter Name'
                                className='form-control'
                                value={name}
                                onChange={onInputChange}
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
                                onChange={onInputChange}
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
                                onChange={onInputChange}
                            />
                        </div>
                        <button className='btn btn-danger' type='submit' style={{ display: "block", width: "100%", marginTop: "16px" }}>
                            <Link to="/users/*" style={{ textDecoration: "none", color: "white" }}>
                                Update Users
                            </Link>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default UpdateUser;
