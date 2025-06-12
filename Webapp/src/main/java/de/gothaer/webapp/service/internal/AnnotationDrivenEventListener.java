package de.gothaer.webapp.service.internal;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnnotationDrivenEventListener {

    public AnnotationDrivenEventListener() {
        System.out.println("AnnotationDrivenEventListener");
    }

    @EventListener
    public void handleContextStart(CustomSpringEvent cse) {

        System.out.println("Handling context started event.");
    }
}