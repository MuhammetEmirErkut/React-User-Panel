import React, { Component } from 'react';
import './App.css';
import Navbar from "./layout/Navbar";
import Users from "./components/Users";
import AddUser from './forms/AddUser';
import UpdateUser from './forms/UpdateUser';
import NotFound from './components/NotFound';

import {
  BrowserRouter,
  Link,
  Route,
  Routes,
} from "react-router-dom";
// import Test from "./components/Test";

// const homePageRouter = createBrowserRouter([
//   {
//     path: "/",
//     element: <div>Hello world!</div>,
//   },
// ]);

// const aboutPageRouter = createBrowserRouter([
//   {
//     path: "/blog/*",
//     element: <div>Hello world!</div>,
//   },
// ]);

class App extends Component {

  // state = {
  //   users: [
  //     {
  //       id : 1,
  //       name : "Emir Erkut",
  //       salary : "2.500.000",
  //       department : "Bilişim"
  //     },
  //     {
  //       id : 2,
  //       name : "Furkan Ayrancı",
  //       salary : "2.500.000",
  //       department : "Bilişim"
  //     },
  //     {
  //       id : 3,
  //       name : "Emir Kaya",
  //       salary : "2.500.000",
  //       department : "Bilişim"
  //     }

  //   ]
  // }

  // constructor(props) {
  //   super(props);

  //   this.state = {
  //     text: 34,
  //     isAuth: true
  //   };
  // }
  // deleteUser = (id) => {
  //   this.setState({
  //     users : this.state.users.filter(user => id !== user.id)
  //   })
  // }
  render() {
    return (
      // <Router>
      //   <div className="container">
      //     <Route path='/' render={
      //       () => {
      //         return <h3>Home Page</h3>
      //       }
      //     } />
      //     <Route path='/about' render={
      //       () => {
      //         return <h3>About Page</h3>
      //       }
      //     } />

      //   </div>
      // </Router>
      <BrowserRouter>
        <div>
          {/* <h4 className='header'>App Component</h4> */}
          {/* <Test test="deneme" /> */}

          <Navbar title="User App" />
          {/* <ToolBar /> */}



          {/* <AddUser />
        <Users /> */}


          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/add/*" element={<AddUser />} />
            <Route path="/users/*" element={<Users />} />
            <Route path="/edit/:id" element={<UpdateUser />} />
            <Route path="*" element={<NotFound />} />
          </Routes>

        </div>
      </BrowserRouter>
    );
  }
}

// function ToolBar() {
//   return (
//     <>
//       <div style={{marginTop: "10px"}}>
//         <p style={{ fontSize: "24px", display: "inline", margin: "0px 20px 0 0 " }}>
//           <Link style={{textDecoration: "none", color: "#16394F"}} to="/add">Add User</Link>
//         </p>
//         <p style={{ fontSize: "24px", display: "inline", margin: "10px 20px 0 0 " }}>
//           <Link style={{textDecoration: "none", color: "#16394F"}} to="users">Users</Link>
//         </p>
//       </div>
//     </>
//   );
// }

function Home() {
  return (
    <>
      <h1>Welcome!</h1>
      <p>
        Check out the  <Link to="/add">Add User</Link> or the{" "}
        <Link to="users">Users</Link> section
      </p>
    </>
  );
}

export default App;

