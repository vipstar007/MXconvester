package com.vn.napas.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vn.napas.config.PropertiesConfig;
import com.vn.napas.model.jsonbase.JsonValidateObject;
import com.vn.napas.model.response.ValidateXMConverterResponse;
import com.vn.napas.model.validate.DataFile;
import com.vn.napas.model.validate.KindOfMessage;
import com.vn.napas.service.FileService;
import com.vn.napas.utils.Constants;
import com.vn.napas.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
@Log4j2
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final PropertiesConfig propertiesConfig;
    @Override
    public DataFile watchFileChanges() throws Exception {
        Path path = Paths.get(propertiesConfig.getPathFileConfigValidate()).toAbsolutePath().getParent();
        WatchService watchService = FileSystems.getDefault().newWatchService();
        // Đăng ký thư mục để theo dõi sự kiện thay đổi
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        DataFile df = new DataFile();
        // Bắt đầu lắng nghe sự kiện
        while (true) {
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("File updated. Reloading content.");
                    BufferedReader br = new BufferedReader(new FileReader(propertiesConfig.getPathFileConfigValidate()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null)
                        sb.append(line + "\n");

                    JsonNode json = Convert.convertStringToJsonNode(sb.toString());
                    //Check Url
                    List<JsonValidateObject> val = new Gson().fromJson(json.get("Nrt").get("checkURL").toString(), new TypeToken<ArrayList<JsonValidateObject>>() {
                    }.getType());
                    for (JsonValidateObject obj : val) {

                    }

                }
            }

            // Đặt lại WatchKey để tiếp tục theo dõi
            boolean reset = key.reset();
            if (!reset) {
                System.err.println("Could not reset the watch key.");
            }
        }

    }
}
