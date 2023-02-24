
let sizes = [];

function auto_scrollDown() {
    const preview = document.querySelector(".preview");
    preview.scrollTop = preview.scrollHeight;
}

function dragstart_handler(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
    ev.effectAllowed = "copyMove";
}

function dragover_handler(ev) {
    ev.preventDefault();
}

function drop_handler(ev) {
    ev.preventDefault();

    const id = ev.dataTransfer.getData("text");
    const nodeCopy = document.getElementById("r-" + id).cloneNode(true);
    const formRoot = document.querySelector("#hidden-form-obj");
    const formCopy = document.getElementById("file").cloneNode(true);


    nodeCopy.id = uuidv4();
    nodeCopy.classList.remove("hidden");

    formCopy.id = "file-" + nodeCopy.id;

    ev.target.appendChild(nodeCopy);
    formRoot.appendChild(formCopy);
    auto_scrollDown();
}
   
function dragend_handler(ev) {
     ev.dataTransfer.clearData();
}

function uuidv4() {
    return 'xxxxxxxx'.replace(/[xy]/g, function(c) {
      let r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
}

function clickHandler(e) {
    e.preventDefault();
    document.querySelector("#file-" + e.target.id).click();
}

function loadImage(input) {
    const target = document.getElementById(input.id.split("-")[1]);

    if (input.files && input.files[0]) {
        let render = new FileReader();
        render.onload = (e) => {
           target.innerHTML = "";
            const img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            target.appendChild(img);
            target.style.border = "1px solid black;";
            target.classList.add("img-attach");
        }
        render.readAsDataURL(input.files[0]);
        console.log(target.classList);
        sizes.push(getSize(target.classList[0]));
    }
}

/*
    사이즈 배열과 사진을 전송하는거다 to 서버에

*/

function getSize(itemName) {
    switch(itemName) {
        case "item1":
            return "ITEM1"
        case "item2":
            return "ITEM2"
        case "item3":
            return "ITEM3"
        case "item4":
            return "ITEM4"
        case "item5":
            return "ITEM5"
        default:
            console.log("NONE");
    }
}


function screenShot (target) {

    let class_id = $('.container').attr('id');
	if (target != null && target.length > 0) {
		let t = target[0];
        const currentUrl = window.location.href;
        const gid = currentUrl.split('tool/')[1];
    
        let formData = new FormData($('#hidden-form-obj')[0]);
        formData.append("items", new Blob([JSON.stringify(sizes)], {
            type: "application/json"
        }));

        $.ajax({
            async: false,
            type: 'post',
            url: '/tool/' + class_id,
            data: formData,
            processData: false,
            contentType: false,
            success: (data) => {
                window.location.href = data
            },
            error: (err) => {
                console.log(err)
            }
        });
	}
}

function shV2 () {
    const captureElem = document.getElementsByClassName("preview");
    const currentUrl = window.location.href;

    html2canvas(captureElem).then(canvas => {   
        canvas.toBlob(blob => {
            const formData = new FormData($('#hidden-form-obj')[0]);
            formData.append("items", new Blob([JSON.stringify(sizes)], {
                type: "application/json"
            }));
            formData.append("thumbnail", blob);
            
            $.ajax({
                async: false,
                type: 'post',
                url: '/tool/' + class_id,
                data: formData,
                processData: false,
                contentType: false,
                success: (data) => {
                    window.location.href = data
                },
                error: (err) => { console.log(err); }
            });
        });
    });
}