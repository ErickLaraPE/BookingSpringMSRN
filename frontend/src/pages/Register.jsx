
import { useState } from "react"
import clienteAxios from "../config/clienteAxios"
import { Link, useNavigate } from "react-router-dom"
import Alerta from "../config/Alerta"

const Register = () => {

    const [usernameField,setUsernameField] = useState('')
    const [emailField,setEmailField] = useState('')
    const [nameField,setNameField] = useState('')
    const [passwordField,setPasswordField] = useState('')
    const [alerta,setAlerta] = useState({})
    const navigate = useNavigate();

    const handleRegister = async () => {
        try {
            const {data} = await clienteAxios.post("/usuarios/registrar",{name:nameField,username:usernameField,password:passwordField,email:emailField})
            console.log(data)
            setAlerta({msg:data.msg,error:data.errorFlag})
            setTimeout(() => {
                navigate("/")
            },3000)
        } catch (error) {
            if (error.response) {
                const errorMessages = [
                    error.response.data.username,
                    error.response.data.password,
                    error.response.data.email,
                    error.response.data.name,
                ]
                const anyError = errorMessages.find(msg => msg !== null)
                setAlerta({ msg: anyError, error: true });
            }
            setTimeout(()=>{
                setAlerta({})
            },3000)
        }
    }

    const {msg} = alerta

    return(
        <div className="flex flex-col mx-auto bg-white p-6 rounded-lg w-3/5">
            <h1 className="text-6xl font-bold text-sky-600 text-md mb-12 text-center"> LURIN BOOKING </h1>
            <h2 className="text-4xl font-bold text-sky-600 text-md mb-12 text-center"> REGISTRARSE </h2>
            {msg && <Alerta alerta={alerta}/>}
            <div className="flex flex-col my-2">
                <label>Name:</label>
                <input className="rounded-md bg-gray-100 p-2" type="text" onChange={(e)=>setNameField(e.target.value)}/>
            </div>
            <div className="flex flex-col my-2">
                <label>Email:</label>
                <input className="rounded-md bg-gray-100 p-2" type="text" onChange={(e)=>setEmailField(e.target.value)}/>
            </div>
            <div className="flex flex-col my-2">
                <label>Username:</label>
                <input className="rounded-md bg-gray-100 p-2" type="text" onChange={(e)=>setUsernameField(e.target.value)}/>
            </div>
            <div className="flex flex-col my-2">
                <label>Password:</label>
                <input className="rounded-md bg-gray-100 p-2" type="password" onChange={(e)=>setPasswordField(e.target.value)}/>
            </div>
            <div className="rounded-lg bg-blue-400 p-2 hover:bg-blue-600 hover:cursor-pointer text-center text-md my-2">
                <input className="text-white" type="submit" value="REGISTRARSE" onClick={()=>handleRegister()}/>
            </div>
            <div className="text-center text-md my-2">
                <Link className="hover:text-red-400" to="/"> ¿Ya tienes cuenta? Inicia sesion</Link>
            </div>
        </div>
    )
}

export default Register