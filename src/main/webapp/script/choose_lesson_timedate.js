let form = document.getElementById("date_form");
let select = document.getElementById("date_select");

select.addEventListener("change", () => {
    let value = select.value;
    form.submit();
})