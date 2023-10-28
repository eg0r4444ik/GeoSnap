package com.korotkov.hackathon;

import com.korotkov.hackathon.entity.SatelliteEntity;
import com.korotkov.hackathon.repository.SatellitesRepository;
import com.korotkov.hackathon.service.SatellitesService;
import com.korotkov.hackathon.util.Zone;
import com.korotkov.hackathon.util.coordinatesUtil.CartesianCoordinates;
import com.korotkov.hackathon.util.coordinatesUtil.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class HackathonApplicationTests {

    @Autowired
    private SatellitesService satellitesService;
    private final int EARTH_RADIUS = 6378100;

    @Test
    void contextLoads() {

        List<Point> points = new ArrayList<>(){{
                add(new Point(new CartesianCoordinates(977322, 1638871, 0)));
                add(new Point(new CartesianCoordinates(6212827, 340434, 0)));
                add(new Point(new CartesianCoordinates(2529098, 1032133, 35318)));
                add(new Point(new CartesianCoordinates(1778919, 3099616, 0)));
                add(new Point(new CartesianCoordinates(986978, 216693, 0)));
                add(new Point(new CartesianCoordinates(2500474, 1880986, 16622)));
                add(new Point(new CartesianCoordinates(5130334, 2721067, 45553)));
                add(new Point(new CartesianCoordinates(2403539, 3091199, 0)));
                add(new Point(new CartesianCoordinates(3772394, 3219172, 38500)));
                add(new Point(new CartesianCoordinates(2342388, 383292, 0)));
            }};

        List<Zone> zones = new ArrayList<>(){
            {
                add(new Zone(
                        new CartesianCoordinates(616376.7570772032, -5549643.637215415, 3082481.9226151933),
                        new CartesianCoordinates(34262.143514021685, -308484.51789712085, -6370543.384809462),
                        new CartesianCoordinates(5527888.398727247, -787981.3817389739, 3082481.9226151933),
                        new CartesianCoordinates(307275.22326570435, -43801.020848903056, -6370543.384809462)));
                add(new Zone(
                        new CartesianCoordinates(3128577.5524861496, -5493593.389733468, 844152.6972986062),
                        new CartesianCoordinates(-2898416.82992863, 5089445.049855016, -2525250.2003747565),
                        new CartesianCoordinates(-2528703.423232166, -5794240.6604297515, 844152.6972986062),
                        new CartesianCoordinates(2342673.766859323, 5367974.539579748, -2525250.2003747565)));
                add(new Zone(
                        new CartesianCoordinates(-3570406.501313965, -5248634.152340843, -619835.9139774764),
                        new CartesianCoordinates(111119.4233254837, 163349.80345793048, -6375039.523443762),
                        new CartesianCoordinates(5610685.265671361, 2969204.2535538026, -619835.9139774764),
                        new CartesianCoordinates(-174617.68315533496, -92408.59948833562, -6375039.523443762)));
                add(new Zone(
                        new CartesianCoordinates(-3951209.629773503, -34974.748591346455, -5006683.417048266),
                        new CartesianCoordinates(5159743.953849013, 45672.27874263249, 3749015.3352144356),
                        new CartesianCoordinates(-505631.17011306627, -3918879.672975937, -5006683.417048266),
                        new CartesianCoordinates(660285.7396402044, 5117525.4145294465, 3749015.3352144356)));
                add(new Zone(
                        new CartesianCoordinates(3110688.2518964997, 3666734.8329507182, -4190326.2252883087),
                        new CartesianCoordinates(-1583184.3426649359, -1866184.1708800257, 5889910.320836593),
                        new CartesianCoordinates(3452304.5122563457, 3347076.2301321295, -4190326.2252883087),
                        new CartesianCoordinates(-1757049.8897096196, -1703493.9705129534, 5889910.320836593)));
                add(new Zone(
                        new CartesianCoordinates(-3805959.3625832032, 1803319.5712911228, 4789871.758634427),
                        new CartesianCoordinates(2673435.041099437, -1266712.8765447263, -5650517.071863197),
                        new CartesianCoordinates(2724543.420625561, 3211565.240651715, 4789871.758634427),
                        new CartesianCoordinates(-1913811.7772107618, -2255912.4344690223, -5650517.071863197)));
                add(new Zone(
                        new CartesianCoordinates(1436748.0772166753, -6014072.040850331, -1564241.6885112461),
                        new CartesianCoordinates(-241553.9000016596, 1011118.4969689391, -6292809.44479342),
                        new CartesianCoordinates(246224.79962336487, -6178404.397413131, -1564241.6885112461),
                        new CartesianCoordinates(-41396.65232151991, 1038746.9464192467, -6292809.44479342)));
                add(new Zone(
                        new CartesianCoordinates(57715.48347661939, -188943.3976009552, -6375039.523443762),
                        new CartesianCoordinates(1856001.036228636, -6075997.645900214, -563624.3168399315),
                        new CartesianCoordinates(79021.83875588176, 181069.69246555868, -6375039.523443762),
                        new CartesianCoordinates(2541165.8324758345, 5822796.875327536, -563624.3168399315)));
                add(new Zone(
                        new CartesianCoordinates(-6309157.531898419, -785664.0444136524, -507368.56130926503),
                        new CartesianCoordinates(-2684720.0747522293, -334321.66202603554, -5775869.350714448),
                        new CartesianCoordinates(2998712.3436248875, -5606287.633825676, -507368.56130926503),
                        new CartesianCoordinates(1276034.5872857913, -2385629.599398835, -5775869.350714448)));
                add(new Zone(
                        new CartesianCoordinates(-269895.6556677202, 1175705.3671387122, -6262989.12938007),
                        new CartesianCoordinates(616736.1454054969, -2686593.804067691, -5751696.26013887),
                        new CartesianCoordinates(687282.0551635938, -991347.6443292685, -6262989.12938007),
                        new CartesianCoordinates(-1570502.0685097987, 2265319.622903351, -5751696.26013887)));
            }};

        List<Zone> goodZones = new ArrayList<>(){{
            add(new Zone(
                    new CartesianCoordinates(-2663822.805946997,3535012.260346316 ,4592155.919360524 ),
                    new CartesianCoordinates(-2663822.805946997,3535011.260346316 , 4592155.919360524),
                    new CartesianCoordinates(-2663821.805946997,3535012.260346316 , 4592155.919360524),
                    new CartesianCoordinates(-2663821.805946997, 3535011.260346316, 4592155.919360524)));
            add(new Zone(
                    new CartesianCoordinates(1350008.2145318012,286953.1043071652 ,6226981.23866012 ),
                    new CartesianCoordinates(1350008.2145318012,286952.1043071652 , 6226981.23866012),
                    new CartesianCoordinates(1350009.2145318012,286953.1043071652 , 6226981.23866012),
                    new CartesianCoordinates(1350009.2145318012, 286952.1043071652, 6226981.23866012)));
            add(new Zone(
                    new CartesianCoordinates(-277465.8477539008,544557.3877206986 ,6348750.236448825 ),
                    new CartesianCoordinates(-277465.8477539008,544556.3877206986 , 6348750.236448825),
                    new CartesianCoordinates(-277464.8477539008,544557.3877206986 , 6348750.236448825),
                    new CartesianCoordinates(-277464.8477539008, 544556.3877206986, 6348750.236448825)));
            add(new Zone(
                    new CartesianCoordinates(415899.6262758905,-51066.05299196737 ,6364320.809724743 ),
                    new CartesianCoordinates(415899.6262758905,-51067.05299196737 , 6364320.809724743),
                    new CartesianCoordinates(415900.6262758905,-51066.05299196737 , 6364320.809724743),
                    new CartesianCoordinates(415900.6262758905, -51067.05299196737, 6364320.809724743)));
            add(new Zone(
                    new CartesianCoordinates(-1242508.8918792466,1710166.7748072676 ,6017612.555320117 ),
                    new CartesianCoordinates(-1242508.8918792466,1710165.7748072676 , 6017612.555320117),
                    new CartesianCoordinates(-1242507.8918792466,1710166.7748072676 , 6017612.555320117),
                    new CartesianCoordinates(-1242507.8918792466, 1710165.7748072676, 6017612.555320117)));
            add(new Zone(
                    new CartesianCoordinates(3361252.395991821,1712643.6379319557 ,5142858.515445515 ),
                    new CartesianCoordinates(3361252.395991821,1712642.6379319557 , 5142858.515445515),
                    new CartesianCoordinates(3361253.395991821,1712643.6379319557 , 5142858.515445515),
                    new CartesianCoordinates(3361253.395991821, 1712642.6379319557, 5142858.515445515)));
            add(new Zone(
                    new CartesianCoordinates(4170346.224921968,-4027256.536466201 ,-2658867.571687118 ),
                    new CartesianCoordinates(4170346.224921968,-4027257.536466201 , -2658867.571687118),
                    new CartesianCoordinates(4170347.224921968,-4027256.536466201 , -2658867.571687118),
                    new CartesianCoordinates(4170347.224921968, -4027257.536466201, -2658867.571687118)));
            add(new Zone(
                    new CartesianCoordinates(-26405.086419146148,59306.795115520996 ,6377769.601158725 ),
                    new CartesianCoordinates(-26405.086419146148,59305.795115520996 , 6377769.601158725),
                    new CartesianCoordinates(-26404.086419146148,59306.795115520996 , 6377769.601158725),
                    new CartesianCoordinates(-26404.086419146148, 59305.795115520996, 6377769.601158725)));
            add(new Zone(
                    new CartesianCoordinates(-3157158.168988828,556692.1675568687 ,5513855.795771952 ),
                    new CartesianCoordinates(-3157158.168988828,556691.1675568687 , 5513855.795771952),
                    new CartesianCoordinates(-3157157.168988828,556692.1675568687 , 5513855.795771952),
                    new CartesianCoordinates(-3157157.168988828, 556691.1675568687, 5513855.795771952)));
            add(new Zone(
                    new CartesianCoordinates(-1570349.9557288385,-510237.63059477264 ,6160667.024508585 ),
                    new CartesianCoordinates(-1570349.9557288385,-510238.63059477264 , 6160667.024508585),
                    new CartesianCoordinates(-1570348.9557288385,-510237.63059477264 , 6160667.024508585),
                    new CartesianCoordinates(-1570348.9557288385, -510238.63059477264, 6160667.024508585)));
        }};

        SatelliteEntity satellite0 = SatelliteEntity.builder()
                .id(0L)
                .distanceToEarth(29586428)
                .earthToOrbitAngle(5.358160803622591)
                .orbitPeriod(88142104)
                .timeStart(1698440483169L)
                .viewAngle(0.7853981633974483)
                .build();
        SatelliteEntity satellite1 = SatelliteEntity.builder()
                .id(1L)
                .distanceToEarth(20151261)
                .earthToOrbitAngle(0.20943951023931953)
                .orbitPeriod(152334308)
                .timeStart(1698440483189L)
                .viewAngle(0.12217304763960307)
                .build();
        SatelliteEntity satellite2 = SatelliteEntity.builder()
                .id(2L)
                .distanceToEarth(2417159)
                .earthToOrbitAngle(2.0420352248333655)
                .orbitPeriod(139717832)
                .timeStart(1698440483189L)
                .viewAngle(0.5184364492350666)
                .build();
        SatelliteEntity satellite3 = SatelliteEntity.builder()
                .id(3L)
                .distanceToEarth(5480784)
                .earthToOrbitAngle(3.01941960595019)
                .orbitPeriod(26018507)
                .timeStart(1698440483189L)
                .viewAngle(0.4835298641951802)
                .build();
        SatelliteEntity satellite4 = SatelliteEntity.builder()
                .id(4L)
                .distanceToEarth(3540976)
                .earthToOrbitAngle(2.199114857512855)
                .orbitPeriod(167123194)
                .timeStart(1698440483189L)
                .viewAngle(0.3490658503988659)
                .build();
        SatelliteEntity satellite5 = SatelliteEntity.builder()
                .id(5L)
                .distanceToEarth(23662853)
                .earthToOrbitAngle(3.6128315516282616)
                .orbitPeriod(93507396)
                .timeStart(1698440483189L)
                .viewAngle(0.6457718232379019)
                .build();
        SatelliteEntity satellite6 = SatelliteEntity.builder()
                .id(6L)
                .distanceToEarth(17031217)
                .earthToOrbitAngle(2.3736477827122884)
                .orbitPeriod(57693941)
                .timeStart(1698440483190L)
                .viewAngle(0.6632251157578452)
                .build();
        SatelliteEntity satellite7 = SatelliteEntity.builder()
                .id(7L)
                .distanceToEarth(18787806)
                .earthToOrbitAngle(5.131268000863329)
                .orbitPeriod(130336973)
                .timeStart(1698440483190L)
                .viewAngle(0.22689280275926285)
                .build();
        SatelliteEntity satellite8 = SatelliteEntity.builder()
                .id(8L)
                .distanceToEarth(6808866)
                .earthToOrbitAngle(6.108652381980153)
                .orbitPeriod(98243260)
                .timeStart(1698440483190L)
                .viewAngle(0.13962634015954636)
                .build();
        SatelliteEntity satellite9 = SatelliteEntity.builder()
                .id(9L)
                .distanceToEarth(25796413)
                .earthToOrbitAngle(3.455751918948773)
                .orbitPeriod(157425469)
                .timeStart(1698440483190L)
                .viewAngle(0.6981317007977318)
                .build();

        List<SatelliteEntity> satellites = new ArrayList<>(){{
            add(satellite0);
            add(satellite1);
            add(satellite2);
            add(satellite3);
            add(satellite4);
            add(satellite5);
            add(satellite6);
            add(satellite7);
            add(satellite8);
            add(satellite9);
        }};

        List<Boolean> ans = new ArrayList<>(){{
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
            add(false);
            add(false);
            add(true);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
            add(true);
            add(false);
            add(false);
            add(false);
            add(true);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
            add(false);
            add(false);
            add(true);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
        }};

        int curr = 0;
        for(SatelliteEntity satellite : satellites){
            for(Zone zone : zones){
                assertThat(satellitesService.doesSatelliteCoverArea(zone, satellite)).isEqualTo(ans.get(curr));
                curr++;
            }
        }

        for(int i = 0; i < satellites.size(); i++){
            assertThat(satellitesService.doesSatelliteCoverArea(goodZones.get(i), satellites.get(i))).isTrue();
        }
    }

}
