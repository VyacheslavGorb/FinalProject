let login = document.getElementById("login");
let loginBtn = document.getElementById("login_btn");

loginBtn.addEventListener("click", () => {
    localStorage.setItem("login1", login.value)
});

let storedLogin = localStorage.getItem("login1");
if(storedLogin !== null){
    login.value = storedLogin;
}