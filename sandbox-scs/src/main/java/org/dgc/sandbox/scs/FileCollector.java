package org.dgc.sandbox.scs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableBinding(Source.class)
@Component
public class FileCollector
{
    @Autowired
    private Source channels;

    @Value("${hotfolder.path}")
    private String path;

    public static void main(String[] args) throws IOException, InterruptedException
    {
        ConfigurableApplicationContext context  = SpringApplication.run(FileCollector.class, args);
        FileCollector fc = context.getBean(FileCollector.class);
        fc.watch();
    }

    public void watch() throws IOException, InterruptedException
    {
        Path hotFolderPath = Paths.get(this.path);

        try (WatchService hotFolderWs = hotFolderPath.getFileSystem().newWatchService())
        {
            hotFolderPath.register(hotFolderWs,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);

            while (true)
            {
                WatchKey watchKey = hotFolderWs.take();

                if (watchKey != null) {
                    // Poll for file system events on the WatchKey
                    for (final WatchEvent<?> event : watchKey.pollEvents())
                    {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE)
                        {
                            System.out.print(event.context().toString());
                            sendMessage(new String(Files.readAllBytes(Paths.get(this.path + "\\" + event.context().toString()))));
                        }
                    }
                }

                boolean valid = watchKey.reset();
                if (!valid)
                {
                    break;
                }
            }
        }
    }

    private void sendMessage(String body)
    {
        this.channels.output().send(MessageBuilder.createMessage(body.getBytes(StandardCharsets.UTF_8), new MessageHeaders(null)));
    }
}
