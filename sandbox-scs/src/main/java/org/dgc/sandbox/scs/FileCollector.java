package org.dgc.sandbox.scs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

@EnableBinding(Source.class)
@Controller
public class FileCollector
{
    //@Value("${hotfolder.path}")
    private String path;

    @PostConstruct
    public void watch() throws IOException, InterruptedException
    {
        Path hotFolderPath = Paths.get("d:\\Temp");

        try (WatchService hotFolderWs = hotFolderPath.getFileSystem().newWatchService())
        {
            hotFolderPath.register(hotFolderWs,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);

            while (true)
            {
                WatchKey watchKey = hotFolderWs.poll(1000L, TimeUnit.MILLISECONDS);

                if (watchKey != null) {
                    // Poll for file system events on the WatchKey
                    for (final WatchEvent<?> event : watchKey.pollEvents())
                    {
                        System.out.print(event.context().toString());
                    }
                }
            }
        }


    }
}
