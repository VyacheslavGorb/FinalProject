let fileInput = document.getElementById("fileInput");
let submitBtn = document.getElementById("submit-btn");
let error_message = document.getElementById("illegal-file-type");
const FILE_JPEG_REGEX = new RegExp(".*\\.jpeg");
const FILE_JPG_REGEX = new RegExp(".*\\.jpg");

submitBtn.addEventListener("click", (event) => {
    if (!FILE_JPEG_REGEX.test(fileInput.value)
        && !FILE_JPG_REGEX.test(fileInput.value)) {
        event.preventDefault();
        error_message.style.display = "flex";
        window.scrollTo(0, 0);
    }
})