package org.example.watchServices;

import lombok.extern.slf4j.Slf4j;
import org.example.configs.Config;
import org.example.xmlHandlers.XmlHandler;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FileAddWatcher {

  XmlHandler xmlHandler;

  public FileAddWatcher(XmlHandler xmlHandler) {
    this.xmlHandler = xmlHandler;
  }

  public void watchForChanges() {
    try (WatchService service = FileSystems.getDefault().newWatchService()) {

      Map<String, String> configProperties = Config.getConfigProperties();
      String inputPath = configProperties.get("inputPath");

      Map<WatchKey, Path> keyMap = new HashMap<>();
      Path path = Paths.get(inputPath);
      keyMap.put(path.register(service, StandardWatchEventKinds.ENTRY_CREATE), path);
      WatchKey watchKey;
      do {
        watchKey = service.take();
        Path eventDir = keyMap.get(watchKey);

        for (WatchEvent<?> event : watchKey.pollEvents()) {
          WatchEvent.Kind<?> kind = event.kind();

          Path eventPath = (Path) event.context();
          log.info("Starting processing" + eventPath);

          xmlHandler.processFile(String.valueOf(eventPath));
        }

      } while (watchKey.reset());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
