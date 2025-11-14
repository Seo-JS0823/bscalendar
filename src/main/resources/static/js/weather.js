let weather;

weather = {
    pageNo: "1", 	        // 페이지 번호
    numOfRows: "12",        // 한 페이지 결과 수
    base_date: "20251105",  // 발표일자
    base_time: "2300",      // 발표시각
    nx: "98",               // 예보지점 x 좌표
    ny: "76",               // 예보지점 y 좌표
    array_code:{
        POP: { code: "POP", name: "강수확률", unit: "%" },
        PTY: { code: "PTY", name: "강수형태", unit: "코드값" },
        PCP: { code: "PCP", name: "1시간 강수량", unit: "범주 (1 mm)" },
        REH: { code: "REH", name: "습도", unit: "%" },
        SNO: { code: "SNO", name: "1시간 신적설", unit: "범주(1 cm)" },
        SKY: { code: "SKY", name: "하늘상태", unit: "코드값" },
        TMP: { code: "TMP", name: "1시간 기온", unit: "℃" },
        UUU: { code: "UUU", name: "풍속(동서성분)", unit: "m/s" },
        VVV: { code: "WSD", name: "풍속(남북성분)", unit: "m/s" },
        WAV: { code: "WAV", name: "파고", unit: "M" },
        VEC: { code: "VEC", name: "풍향", unit: "deg" },
        WSD: { code: "WSD", name: "풍속", unit: "m/s" },
    },
    weekWeather:[],
    getDate:() => {
        let public_time = new Date();
        let current_time = new Date();
        let diffTime = 0;
        let month = public_time.getMonth();
        let day = public_time.getDate();

        // 가장 최근의 base_date, base_time 구하기
        public_time.setHours(public_time.getHours() - 2);
        public_time.setMinutes(public_time.getMinutes() - 10);
        month = `${public_time.getMonth() + 1}`.padStart(2, "0");
        day = `${public_time.getDate()}`.padStart(2, '0');

        public_time.setHours(parseInt(public_time.getHours() / 3) * 3 + 2);
        public_time.setMinutes(10);
        weather.base_date = `${public_time.getFullYear()}${month}${day}`;
        weather.base_time = public_time.getHours() * 100;
        weather.base_time = weather.base_time.toString().padStart(4, "0");
        //console.log("public_time:" + public_time);
        //console.log("current_time:" + current_time);

        // 발표 시각과 현재 시간과의 차이를 구해 pageNo 구하기
        diffTime = (current_time.getTime() - public_time.getTime()) / 60000;
        if (diffTime < 50) weather.pageNo = 1;        // 2310~2359
        else if (diffTime < 110) weather.pageNo = 2;  // 00~0:59
        else if (diffTime < 170) weather.pageNo = 3;  // 1:00~1:59
        else weather.pageNo = 4;
        //console.log("pageNo = " + weather.pageNo);
        //console.log("기준 일자및시간: ", weather.base_date, weather.base_time);
    },
    getWeather:() => {
        let xhr, params, url, serviceKey;
        xhr = new XMLHttpRequest();
        url = 'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst';                              // url(단기예보조회URL)
        serviceKey = SERVICE_KEY;   // servicekey(단기)
        params = '?'  + encodeURIComponent('serviceKey') + '='+ encodeURIComponent(serviceKey);
        params += '&' + encodeURIComponent('pageNo') + '=' + encodeURIComponent(weather.pageNo);
        params += '&' + encodeURIComponent('numOfRows') + '=' + encodeURIComponent(weather.numOfRows);
        params += '&' + encodeURIComponent('dataType') + '=' + encodeURIComponent('JSON');
        params += '&' + encodeURIComponent('base_date') + '=' + encodeURIComponent(weather.base_date);
        params += '&' + encodeURIComponent('base_time') + '=' + encodeURIComponent(weather.base_time);
        params += '&' + encodeURIComponent('nx') + '=' + encodeURIComponent(weather.nx);
        params += '&' + encodeURIComponent('ny') + '=' + encodeURIComponent(weather.ny);

        fetch(url + params)
        .catch(error => console.error('Error:', error))
        .then(response => response.json())
        .then(data => { 
            if (parseInt(data.response.header.resultCode) > 0) {
                console.error(`ERROR CODE: ${data.response.header.resultMsg} ( ${data.response.header.resultCode} )`);
            }
            //console.log(data);
            let result = data.response.body.items.item;
            result.forEach(element => {
                weather.array_code[element.category].value= element.fcstValue;
            });
            weather.draw();
        })
    }, // End of getOneWeather()
    ptyIcon: x => {
        /* 강수형태 이미지 설정 */
        let icon = null;
        switch(x){
            case "1": icon = "rainy"; break;                // 비
            case "2": icon = "rainy-snowy"; break;          // 비/눈(진눈깨비 등)
            case "3": icon = "snowy"; break;                // 눈
            case "4": icon = "shower"; break;               // 소나기(단기예보에서 사용)
            case "5": icon = "raindrop"; break;             // 빗방울(초단기예보에서 사용) - 강수량적을때
            case "6":                                       // 빗방울/눈날림(초단기예보에서 사용) - 강수량적을때
            case "7": icon = "drifting-snow"; break;        // 눈날림(초단기예보에서 사용)
            default:  icon = "day";                         // 없음(강수 없음)
        }

        return icon;
    }, // End of ptyIcon()
    skyIcon: x => {
        /* 하늘상태 이미지 설정 */
        let icon = null;
        switch (x) {
            case "1": weather.base_time > 1800 ? icon = "night" : icon = "day"; break                      // 맑음
            case "3": weather.base_time > 1800 ? icon = "cloudy-night" : icon = "cloudy-day"; break;       // 구름많음
            case "4": icon = "cloudy"; break;                                                              // 흐림
            default:  icon = "day";                                                                        // 기본값
        }

        return icon;
    }, // End of skyIcon()
    draw:() => {
        /* 상세 데이터, 아이콘 보여주기 */
        let el, els, str, imgsrc="../img/weather-icon/";
        let category = ["POP", "PTY", "REH", "SKY", "UUU", "VVV", "VEC", "WSD"]
        els = document.getElementsByClassName("weather")[0];
    
        for(let i = 0; i < category.length; i++){
            str = weather.array_code[category[i]].name;
            str += "(";
            str += weather.array_code[category[i]].unit;
            str += ") : "
            str += weather.array_code[category[i]].value;

            el = els.getElementsByClassName("weather-detail")[0].getElementsByClassName("item")[i];
            el.textContent = str;
        }

        el = els.getElementsByClassName("weather-icon")[0].getElementsByTagName("img")[0];
        if(weather.array_code["PTY"].value === "0") {
            imgsrc += weather.ptyIcon(weather.array_code["PTY"].value) + ".svg";
            el.src = imgsrc;
        } else {
            imgsrc += weather.skyIcon(weather.array_code["SKY"].value) + ".svg";
            el.src = imgsrc;
        }
    }, // End of draw()
    getWeekWeather:() => { //공공포털 오류: 데이터 못가져옴, 지점번호 자료 없음
        let xhr, url, params, serviceKey, dt;
        xhr = new XMLHttpRequest();
        url = 'http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst';                                      // url(중기예보조회URL)
        serviceKey = SERVICE_KEY;   // servicekey(중기)
        weather.base_time >= 1800 ? dt = "1800" : dt = "0600";

        params = '?' + encodeURIComponent('serviceKey') + '='+encodeURIComponent(serviceKey);                      // 서비스키
        params += '&' + encodeURIComponent('pageNo') + '=' + encodeURIComponent('1');                              // 페이지번호
        params += '&' + encodeURIComponent('numOfRows') + '=' + encodeURIComponent('10');                          // 한 페이지 결과 수
        params += '&' + encodeURIComponent('dataType') + '=' + encodeURIComponent('JSON');                         // 응답자료형식
        params += '&' + encodeURIComponent('stnId') + '=' + encodeURIComponent('108');                             // 지점번호: 108 전국, 109 서울, 인천, 경기도 등
        params += '&' + encodeURIComponent('tmFc') + '=' + encodeURIComponent(`${weather.base_date}${dt}`);        // 발표시각, 입력포맷 YYYYMMDD0600 (1800) 
        
        fetch(url + params)
        .catch(error => console.error('Error:', error))
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
    }, // End of getWeekWeather()
}

var options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0
};

let success = pos => {
    var crd = pos.coords;
    let rs = dfs_xy_conv("toXY", crd.latitude, crd.longitude);

    weather.nx = rs.x;
    weather.ny = rs.y;
    // Error: weather.getWeather 에서 호출하면 값이 설정값으로 변환안됨
    //console.log("Your current position is:");
    //console.log(`Latitude : ${crd.latitude}`);
    //console.log(`Longitude: ${crd.longitude}`);
}

let error = err => {
    console.warn(`ERROR(${err.code}): ${err.message}`);
}

function init() {
    navigator.geolocation.getCurrentPosition(success, error, options);
    weather.getDate();
    weather.getWeather();
    
    /*let onClickWeek = () => {
        weather.getWeekWeather();
    }

    let el = document.getElementsByClassName("weather")[0].children[0].children[1];
    el.addEventListener('click', onClickWeek);*/
}