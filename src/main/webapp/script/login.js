let login = document.getElementById("login");
let loginBtn = document.getElementById("login_btn");

loginBtn.addEventListener("click", () => {
    localStorage.setItem("login", login.value)
});

let storedLogin = localStorage.getItem("login");
if(storedLogin !== null){
    login.value = storedLogin;
}