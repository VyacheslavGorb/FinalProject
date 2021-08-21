let login = document.getElementById("login");
let email = document.getElementById("email");
let name = document.getElementById("name");
let surname = document.getElementById("surname");
let patronymic = document.getElementById("patronymic");
let roleSelect = document.getElementById("role_select");
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
    sessionStorage.setItem("login", login.value)
    sessionStorage.setItem("email", email.value)
    sessionStorage.setItem("name", name.value)
    sessionStorage.setItem("surname", surname.value)
    sessionStorage.setItem("patronymic", patronymic.value)
    sessionStorage.setItem("role_select", roleSelect.value)
});

let storedLogin = sessionStorage.getItem("login");
let storedEmail = sessionStorage.getItem("email");
let storedName = sessionStorage.getItem("name");
let storedSurname = sessionStorage.getItem("surname");
let storedPatronymic = sessionStorage.getItem("patronymic");
let storedSelect = sessionStorage.getItem("role_select");

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

if(storedSelect !== null){
    roleSelect.value = storedSelect;
}