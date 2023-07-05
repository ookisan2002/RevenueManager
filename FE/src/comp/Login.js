import { useState } from "react"
import { useSelector,useDispatch } from "react-redux"
import { useNavigate } from 'react-router-dom';
import { setStatus } from "../redux/features/userSlice"
const Login = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const { loginStatus } = useSelector(state => state.user)
    function validate(email, password) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const passwordLength = password.length;

        if (!emailRegex.test(email)) {
            return false;
        } else if (passwordLength !== 10) {
            return false;
        } else {
            return true;
        }
    }

    function handleSubmit(email, password) {
        if (validate(email, password)) {
            fetch(`http://localhost:8080/account/${email}`)
                .then((res) => res.json())
                .then((data) => {
                    if(password===data.dob) {
                        console.log("ok");
                        navigate('/main');
                    }else{
                        dispatch(setStatus());
                        setEmail('');
                        setPassword('');
                        console.log("re_render");
                    }
                }
                )
                .catch((err) => console.log(err))
        } else {
            dispatch(setStatus());
            setEmail('');
            setPassword('');
            console.log("re_render");
        }
    }

    return (
        <div class="wrapper">

            <div class="form-box login">
                <h2>Login</h2>
                <form action="#">

                    <div class="input-box">
                        <span class="icon">
                            <i class='bx bx-envelope' ></i>
                        </span>
                        <input id="email" type="email" required onChange={(e) => setEmail(e.target.value)} value={email} />
                        <label for="">Email</label>
                    </div>

                    <div class="input-box">
                        <span class="icon">
                            <i class='bx bx-lock'></i>
                        </span>
                        <input id="password" type="password" required onChange={(e) => setPassword(e.target.value)} value={password} />
                        <label for="">Password</label>
                        {!loginStatus && (<p style={{ "text-align": "right", 'margin-top': '5px' }}>Kiểm tra lại email và pass.</p>)}

                    </div>

                    <button type="button" class="btn" onClick={()=> handleSubmit(email,password)}>Login</button>
                </form>
            </div>
        </div>
    )
}
export default Login