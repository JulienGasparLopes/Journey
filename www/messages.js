const messageUpdateEntityPosition = (bytes) => {
    const info = {};
    info.uuid = readUuid(bytes)
    info.x = readInt(bytes);
    info.y = readInt(bytes);
    info.vx = readInt(bytes);
    info.vy = readInt(bytes);

    return info;
}

const messageUserDisconnection = bytes => {
    const info = {};
    info.uuid = readUuid(bytes)
    return info;
}

/** ----- ----- Helpers ----- ----- **/
const readInt = (bytes) => {
    let d = bytes.shift();
    let c = bytes.shift();
    let b = bytes.shift();
    let a = bytes.shift();
    let neg = (0b10000000 & d) << 24;
    b = b << 8;
    c = c << 16;
    d = (0b01111111 & d) << 24;
    return a + b + c + d + neg;
}

const readUuid = bytes => {
    return byteArrayToUuid(bytes.splice(0, 16));
}