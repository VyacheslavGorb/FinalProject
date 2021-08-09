let fileInput = document.getElementById("fileInput");
let submitBtn = document.getElementById("submit-btn");
let description = document.getElementById("description");
let experience = document.getElementById("experience");
let error_message_image = document.getElementById("illegal-file-type");

const FILE_JPEG_REGEX = new RegExp(".*\\.jpeg");
const FILE_JPG_REGEX = new RegExp(".*\\.jpg");

submitBtn.addEventListener("click", (event) => {
    error_message_image.style.display = "none";

    if (fileInput.value !== "" && !FILE_JPEG_REGEX.test(fileInput.value)
        && !FILE_JPG_REGEX.test(fileInput.value)) {
        event.preventDefault();
        error_message_image.style.display = "flex";
    }
})