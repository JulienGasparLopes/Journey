const canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

let mouseX = 0, mouseY = 0;

const users = {};

socket = new WebSocket("ws://localhost:8004");
socket.onopen = () => {
    console.log("connexion établie");
}
socket.onmessage = async msg => {
    const buffer = await msg.data.arrayBuffer();
    const bytes = Array.from(new Uint8Array(buffer));

    const msgId = readInt(bytes);
    if(msgId === 2){
        const entitiesInfo = messageUpdateEntities(bytes);
        for(let info of entitiesInfo){
            if(!(info.uuid in users)) {
                const color = "#" + [0, 0, 0].map(() => {
                    let val = Math.round(Math.random() * 200).toString(16);
                    val = val.length < 2 ? "0" + val : val;
                    return val;
                }).join("");
                users[info.uuid] = { uuid: info.uuid, color: color }
            }
            let user = users[info.uuid];
            user.x = info.x;
            user.y = info.y;
            user.life = info.life;
            user.maximumLife = info.maximumLife;
        }
    }
    else if(msgId === 3){
        const info = messageUserDisconnection(bytes);
        delete users[info.uuid];
    }
    else if(msgId === 4) {
        const entitiesInfo = messageRemoveEntities(bytes);
        console.log(users);
        console.log(entitiesInfo);
        for(let info of entitiesInfo){
            delete users[info.uuid];
        }
    }
}
const sendMessage = msg => {
    socket.send(new Uint8Array(msg));
}

// ----- ----- Keyboard Listeners ----- -----
document.addEventListener('keydown', (e) => {
    if (!e.repeat && e.key in KEYBOARD_MAPPING) {
        const message = messageMovementWrite(KEYBOARD_MAPPING[e.key], 1);
        sendMessage(message);
    }
});

document.addEventListener('keyup', (e) => {
    if(e.key in KEYBOARD_MAPPING) {
        sendMessage(messageMovementWrite(KEYBOARD_MAPPING[e.key], 0));
    }
    else if(["a", "A"].includes(e.key)) {
        sendMessage(messageUseAbility(1, mouseX, mouseY))
    }
    else if(["e", "E"].includes(e.key)) {
        sendMessage(messageUseAbility(2, mouseX, mouseY))
    }
});
// ----- ----- Mouse Listeners ----- -----
canvas.addEventListener("mousemove", e => {
    mouseX = e.clientX;
    mouseY = 300 - e.clientY;
});

// ----- ----- Render ----- -----
const render = () => {
    ctx.fillStyle = "#FFFFFF";
    ctx.fillRect(0, 0, 300, 300);
    Object.values(users).forEach(user => {
        ctx.fillStyle = user.color;
        let x = user.x - 10;
        let y = (300 - user.y) - 10;
        ctx.fillRect(x, y, 20, 20);
        ctx.fillText(user.life + "/" + user.maximumLife, x, y - 5)
    });
    setTimeout(render, 1000/50);
}
render();