package io.github.brewagebear;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

@Slf4j
public class SimpleGoogleSheetSourceConfig extends AbstractConfig {

    public static final String TASK_ID = "task.id";
    public static final String TASK_MAX = "task.max";
    public static final String TOPIC_NAME_CONFIG = "topic";
    public static final String POLL_INTERVAL_CONFIG = "poll.interval.ms";
    public static final long POLL_INTERVAL_DEFAULT = 1000;

    public static final ConfigDef CONFIG_DEF =
            new ConfigDef()
                    .define(TOPIC_NAME_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "Topic name.")
                    .define(POLL_INTERVAL_CONFIG, ConfigDef.Type.LONG, POLL_INTERVAL_DEFAULT, ConfigDef.Importance.HIGH, "Max interval between messages (ms)");


    public SimpleGoogleSheetSourceConfig(final Map<?, ?> props) {
        super(CONFIG_DEF, props);
        log.info("{}", props);
    }

    public String getTopicName() {
        return getString(TOPIC_NAME_CONFIG);
    }

    public long getPollInterval() {
        return getLong(POLL_INTERVAL_CONFIG);
    }
}
