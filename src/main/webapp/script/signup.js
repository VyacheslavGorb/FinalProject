let login = document.getElementById("login");
let email = document.getElementById("email");
let name = document.getElementById("name");
let surname = document.getElementById("surname");
let patronymic = document.getElementById("patronymic");
let password1 = document.getElementById("password1");
let password2 = document.getElementById("password2");
let signupBtn = document.getElementById("signup_btn");
let error_message = document.getElementById("passwords_mismatch");

signupBtn.addEventListener("click", (event) => {
    error_message.style.display = "none";
    if (password1.value !== password2.value) {
        event.preventDefault();
        error_message.style.display = "flex";
        window.scrollTo(0, 0);
        return;
    }
    localStorage.setItem("login", login.value)
    localStorage.setItem("email", email.value)
    localStorage.setItem("name", name.value)
    localStorage.setItem("surname", surname.value)
    localStorage.setItem("patronymic", patronymic.value)
});

let storedLogin = localStorage.getItem("login");
let storedEmail = localStorage.getItem("email");
let storedName = localStorage.getItem("name");
let storedSurname = localStorage.getItem("surname");
let storedPatronymic = localStorage.getItem("patronymic");

if(storedLogin !== null){
    login.value = storedLogin;
}

if(storedEmail !== null){
    email.value = storedEmail;
}

if(storedName !== null){
    name.value = storedName;
}

if(storedSurname != null){
    surname.value = storedSurname;
}

if(storedPatronymic !== null){
    patronymic.value = storedPatronymic;
}