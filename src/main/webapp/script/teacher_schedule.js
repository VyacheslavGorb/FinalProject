let checkbox = document.getElementById("remove-checkbox");
let startTime = document.getElementById("time-start-input");
let endTime = document.getElementById("time-end-input");
let submitBtn = document.getElementById("submit-btn");
let errorMessage = document.getElementById("illegal-datetime");

submitBtn.addEventListener("click", (event) => {
    errorMessage.style.display = "none";
    console.log(startTime.value);
    console.log(endTime.value);
    let startHour = parseInt(startTime.value);
    let endHour = parseInt(endTime.value);
    if (startHour >= endHour) {
        event.preventDefault();
        errorMessage.style.display = "flex";
    }
})

checkbox.addEventListener("click", () => {
    startTime.disabled = checkbox.checked;
    endTime.disabled = checkbox.checked;
})