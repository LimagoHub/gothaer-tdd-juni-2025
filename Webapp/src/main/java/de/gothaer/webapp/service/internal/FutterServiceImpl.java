package de.gothaer.webapp.service.internal;


import de.gothaer.webapp.service.event.SchweinErfasstEvent;
import de.gothaer.webapp.service.event.SchweinGeandertEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FutterServiceImpl {

    public void futterBestellen() {
        System.out.println("Futter bestellen");
    }

    @EventListener
    public void handle(SchweinErfasstEvent s){
        futterBestellen();// Kafka call
    }

    @EventListener
    public void handle(SchweinGeandertEvent s){
        futterBestellen();
    }
}
