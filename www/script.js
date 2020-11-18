const canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

const users = {};

socket = new WebSocket("ws://localhost:8004");
socket.onopen = () => {
    console.log("connexion établie");
}
socket.onmessage = async msg => {
    const buffer = await msg.data.arrayBuffer();
    const bytes = Array.from(new Uint8Array(buffer));

    const msgId = bytes.splice(0, 1)[0];
    if(msgId === 2){
        const info = messageUpdateEntityPosition(bytes);
        if(!(info.uuid in users)) {
            const color = "#" + [0, 0, 0].map(() => Math.round(Math.random() * 255).toString(16)).join("");
            users[info.uuid] = { uuid: info.uuid, color: color }
        }
        let user = users[info.uuid];
        user.x = info.x;
        user.y = info.y;
    }
    else if(msgId === 3){
        console.log("YAAAAA")
        const info = messageUserDisconnection(bytes);
        console.log(info)
        delete users[info.uuid];
    }
}

// ----- ----- Keyboard Listeners ----- -----
document.addEventListener('keydown', (e) => {
    if (!e.repeat)
    socket.send(new Uint8Array([1, KEYBOARD_MAPPING[e.key], 1]));
});

document.addEventListener('keyup', (e) => {
    if(e.key in KEYBOARD_MAPPING)
        socket.send(new Uint8Array([1, KEYBOARD_MAPPING[e.key], 0]));
});

// ----- ----- Render ----- -----
const render = () => {
    ctx.fillStyle = "#FFFFFF";
    ctx.fillRect(0, 0, 300, 300);
    Object.values(users).forEach(user => {
        ctx.fillStyle = user.color;
        ctx.fillRect(user.x - 10, 300 - (user.y + 10), 20, 20);
    });
    setTimeout(render, 1000/50);
    console.log(Object.values(users).length)
}
render();