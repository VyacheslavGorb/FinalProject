let url = new URL(window.location.href);
let page = Number.parseInt(url.searchParams.get("page"));
let item = document.getElementsByClassName("page-item").item(page-1);
item.classList.add("active");



