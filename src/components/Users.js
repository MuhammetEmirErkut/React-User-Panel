import React, { Component } from 'react'
import User from "./User";
import UserConsumer from '../context';

class Users extends Component {
  render() {

    return (
      <UserConsumer>
        {
          value => {
            const { users } = value;
            return (
              <div>
                {
                  users.map(user => {
                    return (
                      <User
                        key={user.id}
                        id={user.id}
                        name={user.name}
                        salary={user.salary}
                        department={user.department}
                      />
                    )
                  })
                }
              </div>
            )
          }
        }
      </UserConsumer>
    )


    // return (
    //   <div>
    //     {
    //         users.map(user => {
    //             return(
    //                 <User
    //                     key = {user.id}
    //                     id = {user.id}
    //                     name = {user.name}
    //                     salary = {user.salary}
    //                     department = {user.department}
    //                 />
    //             )
    //         })
    //     }
    //   </div>
    // )
  }
}
// Users.propTypes = {
//     users: PropTypes.array.isRequired,
//     deleteUser: PropTypes.func.isRequired
// }
export default Users;
