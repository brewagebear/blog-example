package io.github.brewagebear;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.brewagebear.SimpleGoogleSheetSourceConfig.TASK_ID;
import static io.github.brewagebear.SimpleGoogleSheetSourceConfig.TASK_MAX;

@Slf4j
public class SimpleGoogleSheetConnector extends SourceConnector {

    private Map<String, String> configProps;

    @Override
    public void start(Map<String, String> props) {
        log.info("Starting Google Sheet Source -> {}", props);
        configProps = props;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return SimpleGoogleSheetTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        log.info("Setting task configurations for {} workers.", maxTasks);

        final List<Map<String, String>> configs = new ArrayList<>(maxTasks);

        for (int i = 0; i < maxTasks; ++i) {
            final Map<String, String> taskConfig = new HashMap<>(configProps);
            // add task specific values
            taskConfig.put(TASK_ID, String.valueOf(i));
            taskConfig.put(TASK_MAX, String.valueOf(maxTasks));
            configs.add(taskConfig);
        }
        return configs;
    }

    @Override
    public void stop() {
        log.info("Stopping Google Sheet Source");
    }

    @Override
    public ConfigDef config() {
        return SimpleGoogleSheetSourceConfig.CONFIG_DEF;
    }

    @Override
    public String version() {
        return "1.0";
    }
}
