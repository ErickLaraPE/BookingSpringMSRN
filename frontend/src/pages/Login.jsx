
import { useState,useEffect } from "react"
import axios from "axios"
import { Link, useNavigate } from "react-router-dom"
import useAuth from "../hooks/useAuth"
import Alerta from "../config/Alerta"

const Login = () => {

    const [usernameField,setUsernameField] = useState('')
    const [passwordField,setPasswordField] = useState('')
    const navigate = useNavigate()
    const {auth,setAuth} = useAuth()
    const [alerta,setAlerta] = useState({})

    useEffect(() => {
            if (auth.id) {
                // Redirigir al inicio de sesión si no está autenticado
                navigate('/menu', { replace: true });
            }
    }, [auth, navigate]);

    const handleLogin = async () => {
        try {
            const {data} = await axios.post("http://127.0.0.1:8080/login",{username:usernameField,password:passwordField})
            setAlerta({msg:data.message,error:data.errorFlag})
            setAuth(data)
            localStorage.setItem("token",data.token)
            setTimeout(()=>{
                setAlerta({})
                navigate('/menu')
            },3000)
        } catch (error) {
            if (error.response) {
                setAlerta({ msg: error.response.data.message, error: true });
            }
            setTimeout(()=>{
                setAlerta({})
            },3000)
        }
    }

    const {msg} = alerta

    return(
        <div className="flex flex-col  mx-auto bg-white p-6 rounded-lg w-3/5">
            <h1 className="text-6xl font-bold text-sky-600 text-md mb-12 text-center"> LURIN BOOKING </h1>
            <h2 className="text-4xl font-bold text-sky-600 text-md mb-12 text-center"> INICIAR SESION </h2>
            {msg && <Alerta alerta={alerta}/>}
            <div className="flex flex-col my-2">
                <label>Username:</label>
                <input className="rounded-md bg-gray-100 p-2" type="text" onChange={(e)=>setUsernameField(e.target.value)}/>
            </div>
            <div className="flex flex-col my-2">
                <label>Password:</label>
                <input className="rounded-md bg-gray-100 p-2" type="password" onChange={(e)=>setPasswordField(e.target.value)}/>
            </div>
            <div className="rounded-lg bg-blue-400 p-2 hover:bg-blue-600 hover:cursor-pointer text-center text-md my-2">
                <input className="text-white" type="submit" value="INICIAR SESION" onClick={()=>handleLogin()}/>
            </div>
            <div className="text-center text-md my-2">
                <Link className="hover:text-red-400" to="/register"> ¿Aun no tienes cuenta? Registrate</Link>
            </div>
        </div>
    )
}

export default Login