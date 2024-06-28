// import React from "react";

// const Navbar = () => {
//     return (
//         <div>
//             <h3>User App</h3>
//         </div>
//     );
// }

// export default Navbar;

import React from 'react';
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom';


function Navbar({title}) {
  return (
    <nav className="navbar navbar-expand-lg  navbar-dark bg-dark mb-3 p-3 justify-content-between">
      <a href="/" className="navbar-brand">{title}</a>

      <ul className="navbar-nav ml-auto">
      

        <li className="nav-item active">
          <Link to= "/" className='nav-link'>Home</Link>
        </li>
        <li className="nav-item active">
          <Link to= "/add" className='nav-link'>Add User</Link>
        </li>
        <li className="nav-item active">
          <Link to= "/users" className='nav-link'>Users</Link>
        </li>
      </ul>

    </nav>
  )
}

Navbar.propTypes = {
  title: PropTypes.string.isRequired
}
// Navbar.defaultProps = {
//   title : "Default App"
// }
export default Navbar;
