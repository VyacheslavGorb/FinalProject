let checkbox = document.getElementById("remove-checkbox");
let startTime = document.getElementById("time-start-input");
let endTime = document.getElementById("time-end-input");
let submitBtn = document.getElementById("submit-btn");
let errorMessage = document.getElementById("illegal-datetime");

submitBtn.addEventListener("click", (event) => {
    errorMessage.style.display = "none";
    console.log(startTime.value);
    console.log(endTime.value);
    let startHour = parseInt(startTime.value.substring(0,2));
    let startMinute = parseInt(startTime.value.substring(3));
    let endHour=parseInt(endTime.value.substring(0,2));
    let endMinute=parseInt(endTime.value.substring(3));
    if(startHour > endHour || (startHour === endHour && startMinute > endMinute)){
        event.preventDefault();
        errorMessage.style.display = "flex";
    }
})

checkbox.addEventListener("click", () => {
    startTime.disabled = checkbox.checked;
    endTime.disabled = checkbox.checked;
})