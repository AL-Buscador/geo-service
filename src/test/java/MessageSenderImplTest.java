import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTest {
    @Test
    void testingSender() {

        GeoService geoService = Mockito.mock(GeoService.class);
//        Mockito.when(geoService.byIp("172.0.32.11"))
//                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        Mockito.when(geoService.byIp("96.44.183.149"))
                .thenReturn(new Location("New York", Country.USA, "10th avenue", 32));

//        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
//        Mockito.when(localizationService.locale(Country.RUSSIA))
//                .thenReturn("Добро пожаловать");

        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> map = new HashMap<String, String>();
//        map.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");
        map.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        String pref = messageSender.send(map);
//       String expect = "Добро пожаловать";
        String expect = "Welcome";
        Assertions.assertEquals(expect, pref);
    }

    @Test
    void testGeoserviceByIp() {
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("172.0.32.11"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
//        Mockito.when(geoService.byIp("96.44.183.149"))
//                .thenReturn(new Location("New York", Country.USA, "10th avenue", 32));

        Assertions.assertEquals(Country.RUSSIA,
                geoService.byIp("172.0.32.11").getCountry());

//        Assertions.assertEquals(new Location("New York", Country.USA, "10th avenue", 32),
//                geoService.byIp("96.44.183.149"));

    }

    @Test
    void testLocalService() {
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
//        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.BRAZIL)).thenReturn("Welcome");

        Assertions.assertEquals("Welcome", localizationService.locale(Country.BRAZIL));
//        Assertions.assertEquals("Добро пожаловать", localizationService.locale(Country.RUSSIA));
    }

    @Test
    void testGeoServiceByCoordinates() {
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        Mockito.when(geoService.byCoordinates(55.450, 37.365))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
//        Mockito.when(geoService.byCoordinates(40.714, 70.006))
//                .thenReturn(new Location("New York", Country.USA, "10th avenue", 32));

        Assertions.assertEquals(Country.RUSSIA,
                geoService.byCoordinates(55.450, 37.365).getCountry());

//        Assertions.assertEquals(new Location("New York", Country.USA, "10th avenue", 32),
//                geoService.byCoordinates(407.14, 70.006));
    }
}
