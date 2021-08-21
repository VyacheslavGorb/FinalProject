let login = document.getElementById("login");
let loginBtn = document.getElementById("login_btn");

loginBtn.addEventListener("click", () => {
    sessionStorage.setItem("login1", login.value)
});

let storedLogin = sessionStorage.getItem("login1");
if(storedLogin !== null){
    login.value = storedLogin;
}