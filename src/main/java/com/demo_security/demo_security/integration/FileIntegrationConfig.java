package com.demo_security.demo_security.integration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.MessageChannel;
import java.io.File;
import org.springframework.messaging.MessageHandler;

@Configuration
@IntegrationComponentScan
public class FileIntegrationConfig {
    @Bean
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("input")); // Thư mục cần theo dõi
        source.setFilter(new SimplePatternFileListFilter("*.txt"));
        return source;
    }

    @Bean
    public MessageChannel fileInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel")
    public MessageHandler fileMessageHandler() {
        return message -> {
            File payload = (File) message.getPayload();
            System.out.println("File detected: " + payload.getAbsolutePath());
        };
    }

    @Bean
    public org.springframework.integration.endpoint.SourcePollingChannelAdapter fileSourcePollingChannelAdapter() {
        org.springframework.integration.endpoint.SourcePollingChannelAdapter adapter = new org.springframework.integration.endpoint.SourcePollingChannelAdapter();
        adapter.setSource(fileReadingMessageSource());
        adapter.setOutputChannel(fileInputChannel());
        return adapter;
    }
}
