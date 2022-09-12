import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    vus: 1,
    duration: '1s'
}

export default function () {
    const res = http.get('http://192.168.0.74:8080/music-artist/details/f27ec8db-af05-4f36-916e-3d57f91ecf5e');
    console.log(res);
    check(res, {
        '200 status': r => r.status === 200
    })
    sleep(1);
}
