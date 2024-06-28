// import React from 'react';

// function User(){
//     return (
//       <div>
//         <h3>Lütfen Gerekli Bilgileri Giriniz</h3>
//         <form>Email:
//             <label>
//             <input type="text"/>
//             </label>            
//             <button>Submit</button>
//         </form>
//       </div>
//     );
// }


import React, { Component } from 'react'
import PropTypes from 'prop-types'
import UserConsumer from '../context'
import axios from 'axios'
import { Link } from 'react-router-dom'

class User extends Component {
  state = {
    isVisible: true
  }
  static defaultProps = {
    name: "Bilgi Yok",
    salary: "Bilgi Yok",
    department: " Bilgi Yok"
  }
  onClickEvent = (e) => {
    // console.log(this);
    // console.log(number);

    this.setState({
      isVisible: !this.state.isVisible
    })

  }

  onDeleteUser = async (dispatch, e) => {
    const { id } = this.props;
    //Delete Request
    await axios.delete(`http://localhost:3000/users/${id}`);

    // deleteUser(id);

    // Consumer Dispatch
    dispatch({ type: "DELETE_USER", payload: id })
  }
  componentWillUnmount() {
    console.log("Component Will Unmount")
  }

  // constructor(props) {
  //   super(props);

  //   this.onClickEvent = this.onClickEvent.bind();
  // }
  // constructor(props) {
  //   super(props);

  //   this.state = {
  //     isVisible : false
  //   }
  // }
  render() {
    //Destructing
    const {id, name, department, salary } = this.props;
    const { isVisible } = this.state;

    return (
      <UserConsumer>
        {
          value => {
            const { dispatch } = value;

            return (
              <div className="col-md-8 mb-4">
                {/* <h3>Lütfen Gerekli Bilgileri Giriniz</h3>
                <form>Email:
                    <label>
                    <input type="text"/>
                    </label>            
                    <button>Submit</button>
                </form> */}

                {/* <ul>
                  <li>İsim: {name} <i class="fa-regular fa-trash-can"></i></li>
                  <li>Departman: {department}</li>
                  <li>Maaş: {salary}</li>
                </ul> */}

                <div className='card' style={isVisible ? { backgroundColor: "#628489", color: "white" } : null}>
                  <div className="card-header d-flex justify-content-between">
                    <h4 className="d-inline" onClick={this.onClickEvent}>{name}</h4>
                    <i onClick={this.onDeleteUser.bind(this, dispatch)} className="far fa-trash-alt" style={{ cursor: "pointer", fontSize: "24px", marginTop: "6px" }}></i>
                  </div>
                  {isVisible ?
                    <div className="card-body">
                      <p className="card-text">Maaş : {salary}</p>
                      <p className="card-text">Departman : {department}</p>
                      <Link to= {`/edit/${id}`} className="btn btn-dark btn-block" style={{display: "block", width: "100%"}}>Update User</Link>
                    </div> : null
                  }
              </div>

              </div>
    )
  }
}
      </UserConsumer >
    )


  }
}
User.propType = {
  name: PropTypes.string.isRequired,
  salary: PropTypes.string.isRequired,
  department: PropTypes.string.isRequired,
}

export default User;

