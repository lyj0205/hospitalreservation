package com.teamB.hospitalreservation.config;

import com.teamB.hospitalreservation.entity.Location;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 공공 데이터 포털 API에서 사용하는 정확한 지역 코드를 제공합니다.
 */
@Configuration
public class LocationDataProvider {

    /**
     * 테스트를 위한 최소한의 지역 데이터
     * @return Location 리스트
     */
    public List<Location> getSimpleLocations() {
        return List.of(
            new Location("강남구", "110000", "110001"),
            new Location("종로구", "110000", "110016"),
            new Location("부산진구", "210000", "210004"),
            new Location("수원팔달구", "310000", "310603"),
            new Location("세종시", "410000", "410000")
        );
    }

    /**
     * API에서 사용하는 전국 모든 지역의 코드 데이터
     * @return Location 리스트
     */
    public List<Location> getHardcodedLocations() {
        return List.of(
            // 서울특별시 (sidoCode: 110000)
            new Location("강남구", "110000", "110001"),
            new Location("강동구", "110000", "110002"),
            new Location("강서구", "110000", "110003"),
            new Location("관악구", "110000", "110004"),
            new Location("구로구", "110000", "110005"),
            new Location("도봉구", "110000", "110006"),
            new Location("동대문구", "110000", "110007"),
            new Location("동작구", "110000", "110008"),
            new Location("마포구", "110000", "110009"),
            new Location("서대문구", "110000", "110010"),
            new Location("성동구", "110000", "110011"),
            new Location("성북구", "110000", "110012"),
            new Location("영등포구", "110000", "110013"),
            new Location("용산구", "110000", "110014"),
            new Location("은평구", "110000", "110015"),
            new Location("종로구", "110000", "110016"),
            new Location("중구", "110000", "110017"),
            new Location("송파구", "110000", "110018"),
            new Location("중랑구", "110000", "110019"),
            new Location("양천구", "110000", "110020"),
            new Location("서초구", "110000", "110021"),
            new Location("노원구", "110000", "110022"),
            new Location("광진구", "110000", "110023"),
            new Location("강북구", "110000", "110024"),
            new Location("금천구", "110000", "110025"),

            // 부산광역시 (sidoCode: 210000)
            new Location("부산남구", "210000", "210001"),
            new Location("부산동구", "210000", "210002"),
            new Location("부산동래구", "210000", "210003"),
            new Location("부산진구", "210000", "210004"),
            new Location("부산북구", "210000", "210005"),
            new Location("부산서구", "210000", "210006"),
            new Location("부산영도구", "210000", "210007"),
            new Location("부산중구", "210000", "210008"),
            new Location("부산해운대구", "210000", "210009"),
            new Location("부산사하구", "210000", "210010"),
            new Location("부산금정구", "210000", "210011"),
            new Location("부산강서구", "210000", "210012"),
            new Location("부산연제구", "210000", "210013"),
            new Location("부산수영구", "210000", "210014"),
            new Location("부산사상구", "210000", "210015"),
            new Location("부산기장군", "210000", "210100"),

            // 인천광역시 (sidoCode: 220000)
            new Location("인천미추홀구", "220000", "220001"),
            new Location("인천동구", "220000", "220002"),
            new Location("인천부평구", "220000", "220003"),
            new Location("인천중구", "220000", "220004"),
            new Location("인천서구", "220000", "220005"),
            new Location("인천남동구", "220000", "220006"),
            new Location("인천연수구", "220000", "220007"),
            new Location("인천계양구", "220000", "220008"),
            new Location("인천강화군", "220000", "220100"),
            new Location("인천옹진군", "220000", "220200"),

            // 대구광역시 (sidoCode: 230000)
            new Location("대구남구", "230000", "230001"),
            new Location("대구동구", "230000", "230002"),
            new Location("대구북구", "230000", "230003"),
            new Location("대구서구", "230000", "230004"),
            new Location("대구수성구", "230000", "230005"),
            new Location("대구중구", "230000", "230006"),
            new Location("대구달서구", "230000", "230007"),
            new Location("대구달성군", "230000", "230100"),
            new Location("대구군위군", "230000", "230200"),

            // 광주광역시 (sidoCode: 240000)
            new Location("광주동구", "240000", "240001"),
            new Location("광주북구", "240000", "240002"),
            new Location("광주서구", "240000", "240003"),
            new Location("광주광산구", "240000", "240004"),
            new Location("광주남구", "240000", "240005"),

            // 대전광역시 (sidoCode: 250000)
            new Location("대전유성구", "250000", "250001"),
            new Location("대전대덕구", "250000", "250002"),
            new Location("대전서구", "250000", "250003"),
            new Location("대전동구", "250000", "250004"),
            new Location("대전중구", "250000", "250005"),

            // 울산광역시 (sidoCode: 260000)
            new Location("울산남구", "260000", "260001"),
            new Location("울산동구", "260000", "260002"),
            new Location("울산중구", "260000", "260003"),
            new Location("울산북구", "260000", "260004"),
            new Location("울산울주군", "260000", "260100"),

            // 경기도 (sidoCode: 310000)
            new Location("광명시", "310000", "310100"),
            new Location("동두천시", "310000", "310200"),
            new Location("부천시", "310000", "310300"),
            new Location("성남수정구", "310000", "310401"),
            new Location("성남중원구", "310000", "310402"),
            new Location("성남분당구", "310000", "310403"),
            new Location("수원권선구", "310000", "310601"),
            new Location("수원장안구", "310000", "310602"),
            new Location("수원팔달구", "310000", "310603"),
            new Location("수원영통구", "310000", "310604"),
            new Location("안양만안구", "310000", "310701"),
            new Location("안양동안구", "310000", "310702"),
            new Location("의정부시", "310000", "310800"),
            new Location("과천시", "310000", "310900"),
            new Location("구리시", "310000", "311000"),
            new Location("안산단원구", "310000", "311101"),
            new Location("안산상록구", "310000", "311102"),
            new Location("평택시", "310000", "311200"),
            new Location("하남시", "310000", "311300"),
            new Location("군포시", "310000", "311400"),
            new Location("남양주시", "310000", "311500"),
            new Location("의왕시", "310000", "311600"),
            new Location("시흥시", "310000", "311700"),
            new Location("오산시", "310000", "311800"),
            new Location("고양덕양구", "310000", "311901"),
            new Location("고양일산서구", "310000", "311902"),
            new Location("고양일산동구", "310000", "311903"),
            new Location("용인기흥구", "310000", "312001"),
            new Location("용인수지구", "310000", "312002"),
            new Location("용인처인구", "310000", "312003"),
            new Location("이천시", "310000", "312100"),
            new Location("파주시", "310000", "312200"),
            new Location("김포시", "310000", "312300"),
            new Location("안성시", "310000", "312400"),
            new Location("화성시", "310000", "312500"),
            new Location("광주시", "310000", "312600"),
            new Location("양주시", "310000", "312700"),
            new Location("포천시", "310000", "312800"),
            new Location("여주시", "310000", "312900"),
            new Location("가평군", "310000", "310001"),
            new Location("양평군", "310000", "310009"),
            new Location("연천군", "310000", "310011"),

            // 강원도 (sidoCode: 320000)
            new Location("강릉시", "320000", "320100"),
            new Location("동해시", "320000", "320200"),
            new Location("속초시", "320000", "320300"),
            new Location("원주시", "320000", "320400"),
            new Location("춘천시", "320000", "320500"),
            new Location("태백시", "320000", "320600"),
            new Location("삼척시", "320000", "320700"),
            new Location("고성군", "320000", "320001"),
            new Location("양구군", "320000", "320004"),
            new Location("양양군", "320000", "320005"),
            new Location("영월군", "320000", "320006"),
            new Location("인제군", "320000", "320008"),
            new Location("정선군", "320000", "320009"),
            new Location("철원군", "320000", "320010"),
            new Location("평창군", "320000", "320012"),
            new Location("홍천군", "320000", "320013"),
            new Location("화천군", "320000", "320014"),
            new Location("횡성군", "320000", "320015"),

            // 충청북도 (sidoCode: 330000)
            new Location("청주상당구", "330000", "330101"),
            new Location("청주흥덕구", "330000", "330102"),
            new Location("청주청원구", "330000", "330103"),
            new Location("청주서원구", "330000", "330104"),
            new Location("충주시", "330000", "330200"),
            new Location("제천시", "330000", "330300"),
            new Location("괴산군", "330000", "330001"),
            new Location("단양군", "330000", "330002"),
            new Location("보은군", "330000", "330003"),
            new Location("영동군", "330000", "330004"),
            new Location("옥천군", "330000", "330005"),
            new Location("음성군", "330000", "330006"),
            new Location("진천군", "330000", "330009"),
            new Location("증평군", "330000", "330011"),

            // 충청남도 (sidoCode: 340000)
            new Location("천안서북구", "340000", "340201"),
            new Location("천안동남구", "340000", "340202"),
            new Location("공주시", "340000", "340300"),
            new Location("보령시", "340000", "340400"),
            new Location("아산시", "340000", "340500"),
            new Location("서산시", "340000", "340600"),
            new Location("논산시", "340000", "340700"),
            new Location("계룡시", "340000", "340800"),
            new Location("당진시", "340000", "340900"),
            new Location("금산군", "340000", "340002"),
            new Location("부여군", "340000", "340007"),
            new Location("서천군", "340000", "340009"),
            new Location("예산군", "340000", "340012"),
            new Location("청양군", "340000", "340014"),
            new Location("홍성군", "340000", "340015"),
            new Location("태안군", "340000", "340016"),

            // 전라북도 (sidoCode: 350000)
            new Location("군산시", "350000", "350100"),
            new Location("남원시", "350000", "350200"),
            new Location("익산시", "350000", "350300"),
            new Location("전주완산구", "350000", "350401"),
            new Location("전주덕진구", "350000", "350402"),
            new Location("정읍시", "350000", "350500"),
            new Location("김제시", "350000", "350600"),
            new Location("고창군", "350000", "350001"),
            new Location("무주군", "350000", "350004"),
            new Location("부안군", "350000", "350005"),
            new Location("순창군", "350000", "350006"),
            new Location("완주군", "350000", "350008"),
            new Location("임실군", "350000", "350010"),
            new Location("장수군", "350000", "350011"),
            new Location("진안군", "350000", "350013"),

            // 전라남도 (sidoCode: 360000)
            new Location("나주시", "360000", "360200"),
            new Location("목포시", "360000", "360300"),
            new Location("순천시", "360000", "360400"),
            new Location("여수시", "360000", "360500"),
            new Location("광양시", "360000", "360700"),
            new Location("강진군", "360000", "360001"),
            new Location("고흥군", "360000", "360002"),
            new Location("곡성군", "360000", "360003"),
            new Location("구례군", "360000", "360006"),
            new Location("담양군", "360000", "360008"),
            new Location("무안군", "360000", "360009"),
            new Location("보성군", "360000", "360010"),
            new Location("신안군", "360000", "360012"),
            new Location("영광군", "360000", "360014"),
            new Location("영암군", "360000", "360015"),
            new Location("완도군", "360000", "360016"),
            new Location("장성군", "360000", "360017"),
            new Location("장흥군", "360000", "360018"),
            new Location("진도군", "360000", "360019"),
            new Location("함평군", "360000", "360020"),
            new Location("해남군", "360000", "360021"),
            new Location("화순군", "360000", "360022"),

            // 경상북도 (sidoCode: 370000)
            new Location("경주시", "370000", "370100"),
            new Location("구미시", "370000", "370200"),
            new Location("김천시", "370000", "370300"),
            new Location("안동시", "370000", "370400"),
            new Location("영주시", "370000", "370500"),
            new Location("영천시", "370000", "370600"),
            new Location("포항남구", "370000", "370701"),
            new Location("포항북구", "370000", "370702"),
            new Location("문경시", "370000", "370800"),
            new Location("상주시", "370000", "370900"),
            new Location("경산시", "370000", "371000"),
            new Location("고령군", "370000", "370002"),
            new Location("봉화군", "370000", "370007"),
            new Location("성주군", "370000", "370010"),
            new Location("영덕군", "370000", "370012"),
            new Location("영양군", "370000", "370013"),
            new Location("예천군", "370000", "370017"),
            new Location("울릉군", "370000", "370018"),
            new Location("울진군", "370000", "370019"),
            new Location("의성군", "370000", "370021"),
            new Location("청도군", "370000", "370022"),
            new Location("청송군", "370000", "370023"),
            new Location("칠곡군", "370000", "370024"),

            // 경상남도 (sidoCode: 380000)
            new Location("김해시", "380000", "380100"),
            new Location("사천시", "380000", "380300"),
            new Location("진주시", "380000", "380500"),
            new Location("창원마산회원구", "380000", "380701"),
            new Location("창원마산합포구", "380000", "380702"),
            new Location("창원진해구", "380000", "380703"),
            new Location("창원의창구", "380000", "380704"),
            new Location("창원성산구", "380000", "380705"),
            new Location("통영시", "380000", "380800"),
            new Location("밀양시", "380000", "380900"),
            new Location("거제시", "380000", "381000"),
            new Location("양산시", "380000", "381100"),
            new Location("거창군", "380000", "380002"),
            new Location("고성군", "380000", "380003"),
            new Location("남해군", "380000", "380005"),
            new Location("산청군", "380000", "380008"),
            new Location("의령군", "380000", "380011"),
            new Location("창녕군", "380000", "380014"),
            new Location("하동군", "380000", "380016"),
            new Location("함안군", "380000", "380017"),
            new Location("함양군", "380000", "380018"),
            new Location("합천군", "380000", "380019"),

            // 제주특별자치도 (sidoCode: 390000)
            new Location("서귀포시", "390000", "390100"),
            new Location("제주시", "390000", "390200"),

            // 세종특별자치시 (sidoCode: 410000)
            new Location("세종시", "410000", "410000")
        );
    }
}