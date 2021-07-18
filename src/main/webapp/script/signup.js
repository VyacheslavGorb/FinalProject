let password1 = document.getElementById("password1");
let password2 = document.getElementById("password2");
let signupBtn = document.getElementById("signup_btn");
let error_message = document.getElementById("passwords_mismatch");

signupBtn.addEventListener("click", (event) => {
    if(password1.value !== password2.value){
        event.preventDefault();
        error_message.style.display = "flex";
        window.scrollTo(0, 0);
    }
})