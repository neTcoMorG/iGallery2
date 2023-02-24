

function expend (event) {
    const parent = event.currentTarget;
    const child = parent.parentNode.childNodes[3];

    if (child.classList.contains("hidden")) {
        child.classList.remove("hidden");
    }
    else {
        child.classList.add("hidden");
    }
}