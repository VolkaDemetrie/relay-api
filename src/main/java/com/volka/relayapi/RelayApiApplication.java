package com.volka.relayapi;

import com.volka.relayapi.properties.CustomNettyProperties;
import com.volka.relayapi.properties.ExternalRelayApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import reactor.tools.agent.ReactorDebugAgent;

@EnableConfigurationProperties({CustomNettyProperties.class, ExternalRelayApiProperties.class})
@SpringBootApplication
public class RelayApiApplication {

    /**
     * spring 컨텍스트 초기화 전에 적용해야함. -> 초기화 후면 이미 만들어진 쓰레드에 대한 블로킹 검출은 못함.
     * @param args
     */
    public static void main(String[] args) {
        if (isDevProfiles()) {
            System.out.println("[ReactorDebugAgent] INSTALL");
            ReactorDebugAgent.init();
            ReactorDebugAgent.processExistingClasses();
        }

        SpringApplication.run(RelayApiApplication.class, args);
    }


    private static boolean isDevProfiles() {
        String profile = System.getProperty("spring.profiles.active");
        return "local".equals(profile) || "dev".equals(profile);
    }

//    private static boolean isDevProfiles(String[] args) {
//        String profile = null;
//        for (String arg : args) {
//            if (arg.contains("profiles.active=")) {
//                profile = arg.split("=")[1];
//                return profile.equals("local") || profile.equals("dev");
//            }
//        }
//
//        return true;
//    }
}
