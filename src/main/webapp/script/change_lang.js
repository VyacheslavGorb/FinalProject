document.getElementById("location_input").value = location;

let lang_input = document.getElementById("lang_input");
let form = document.getElementById("change_lang_form");

document.getElementById("change_lang_en").addEventListener("click", () => {
    lang_input.value = "en";
    form.submit();
})

document.getElementById("change_lang_ru").addEventListener("click", () => {
    lang_input.value = "ru";
    form.submit();
})
