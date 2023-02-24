
const background = document.querySelector(".modal-overlay");
const main = document.querySelector(".main");

async function detail(event) {
    modal_processor();
    const imgUrl = event.target.src;
    const imgTag = document.getElementById("modal-img");
    imgTag.src = imgUrl;
    background.style.background = "url('" + imgUrl + "')";
    
    //const recv = await getMetadata(event.target.id);
    // const picName = document.getElementById('pic-name');
    // const picDate = document.getElementById('pic-date');
    // const camera = document.getElementById("camera");

    // camera.innerText = recv.device;
    // picName.innerText = recv.name;
    // picDate.innerText = recv.date;

    // return {
    //     lat: recv.lat,
    //     lon: recv.lon
    // };
}

function modal_processor() {
    const modal = document.querySelector(".modal");
    const overlay = document.querySelector(".modal-overlay");
    
    modal.classList.remove("hidden");    
    overlay.addEventListener('click', () => {
        modal.classList.add("hidden");
        background.style.background = "black";
    });
}


async function getMetadata(id) {
    // let result;
    // await $.get(('/image/meta?id=' + id), (data) => {
    //     result = data;
    // });
    // return result;
}