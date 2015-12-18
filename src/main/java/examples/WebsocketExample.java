package examples;

import com.devicehive.client.api.HiveClient;
import com.devicehive.client.model.Device;
import com.devicehive.client.model.exceptions.HiveException;
import com.devicehive.client.websocket.HiveFactory;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebsocketExample {


    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    HiveClient client = HiveFactory.createClient(URI.create("http://playground.devicehive.com/api/rest"), true);

                    client.authenticate(Const.API_KEY);
                    List<Device> devices = client.getDeviceAPI().list(null, null, null, null, null, null, null, null, null, null, null, null);
                    System.out.println(devices);
                } catch (HiveException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}