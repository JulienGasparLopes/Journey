/** ----- ----- Decode Messages ----- ----- **/

const messageUpdateEntities = bytes => {
    const entitiesInfo = [];
    const entitiesNumber = readInt(bytes);

    for(let i = 0; i<entitiesNumber; i++){
        let info = {}
        info.uuid = readUuid(bytes)
        info.x = readInt(bytes);
        info.y = readInt(bytes);
        info.vx = readInt(bytes);
        info.vy = readInt(bytes);
        info.life = readInt(bytes);
        info.maximumLife = readInt(bytes);
        entitiesInfo.push(info);
    }

    return entitiesInfo;
}

const messageRemoveEntities = bytes => {
    const entitiesInfo = [];
    const entitiesNumber = readInt(bytes);

    for(let i = 0; i<entitiesNumber; i++){
        let info = {}
        info.uuid = readUuid(bytes)
        entitiesInfo.push(info);
    }

    return entitiesInfo;
}

const messageUserDisconnection = bytes => {
    const info = {};
    info.uuid = readUuid(bytes)
    return info;
}

/** ----- ----- Encode Messages ----- ----- **/

const messageMovementWrite = (keyCode, state) => {
    const bytes = [];
    writeInt(bytes, 1); // Message Id
    writeInt(bytes, keyCode);
    writeInt(bytes, state)
    return bytes;
}

const messageUseAbility = (abilityId, mouseX, mouseY) => {
    const bytes = [];
    writeInt(bytes, 8); // Message Id
    writeInt(bytes, abilityId);
    writeInt(bytes, mouseX);
    writeInt(bytes, mouseY);
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