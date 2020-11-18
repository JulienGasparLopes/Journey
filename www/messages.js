const messageUpdateEntityPosition = (bytes) => {
    const info = {};
    info.uuid = byteArrayToUuid(bytes.splice(0, 16));
    info.x = bytes.shift();
    info.y = bytes.shift();
    info.vx = bytes.shift();
    info.vy = bytes.shift();

    return info;
}

const messageUserDisconnection = bytes => {
    const info = {};
    info.uuid = byteArrayToUuid(bytes.splice(0, 16));
    return info;
}