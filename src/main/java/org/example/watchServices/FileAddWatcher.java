package org.example.watchServices;

import org.example.xmlHandlers.XmlParser;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class FileAddWatcher {

    XmlParser xmlParser;

    public FileAddWatcher(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    public void watchForChanges() {
        try (WatchService service = FileSystems.getDefault().newWatchService()) {
            Map<WatchKey, Path> keyMap = new HashMap<>();
            Path path = Paths.get("src/main/java/org/example/inputFiles");
            keyMap.put(path.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE),
                    path);
            WatchKey watchKey;
            do {
                watchKey = service.take();
                Path eventDir = keyMap.get(watchKey);

                for(WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path eventPath = (Path) event.context();
                    System.out.println(eventPath);
                    xmlParser.main(String.valueOf(eventPath));

                }

            } while (watchKey.reset());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
