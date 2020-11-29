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

const messageMovementWrite = (keyCode, state) => {
    let bytes = [];
    writeInt(bytes, 1); //message id
    writeInt(bytes, keyCode);
    writeInt(bytes, state)
    return bytes;
}

/** ----- ----- Helpers ----- ----- **/
const readInt = (bytes) => {
    let d = bytes.shift() << 24;
    let c = bytes.shift() << 16;
    let b = bytes.shift() << 8;
    let a = bytes.shift();
    return a + b + c + d;
}

const readUuid = bytes => {
    return byteArrayToUuid(bytes.splice(0, 16));
}

const writeInt = (bytes, valueInt) => {
    let d = (valueInt >> 24) & 255;
    let c = (valueInt >> 16) & 255;
    let b = (valueInt >> 8) & 255;
    let a = valueInt & 255;
    bytes.push(d, c, b, a)
}