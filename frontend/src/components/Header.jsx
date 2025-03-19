import { useNavigate } from "react-router-dom"
import useAuth from "../hooks/useAuth"


const Header = () => {

    const {auth,cerrarSesionAuth} = useAuth()

    const navigate = useNavigate()

    const cerrarSesion = () => {
        cerrarSesionAuth();
        localStorage.removeItem('token')
        navigate("/")
    }

    return(
        <div className="bg-white flex flex-row top-0 items-center justify-end pr-24 py-6">
            <h2 className=""> </h2>
            <p className="text-lg text-black text-center mx-6"> Bienvenido <span>{auth.name}</span></p>
            <button className="bg-red-400 text-white hover:bg-red-600 hover:cursor-pointer text-center rounded-lg p-2 mx-6" type="button" onClick={()=>cerrarSesion()}>Cerrar sesion</button>
        </div>
    )

}

export default Header