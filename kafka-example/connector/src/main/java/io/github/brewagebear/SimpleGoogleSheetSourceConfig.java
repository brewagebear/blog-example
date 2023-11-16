package io.github.brewagebear;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.AbstractConfig;

import java.util.HashMap;
import java.util.Map;

import static io.github.brewagebear.config.GoogleCredentialsConfig.*;

@Slf4j
public class SimpleGoogleSheetSourceConfig extends AbstractConfig implements CustomConnectConfig {

    public static final String TASK_ID = "task.id";
    public static final String TASK_MAX = "task.max";
    public static final String TOPIC_NAME_CONFIG = "topic";
    public static final String POLL_INTERVAL_CONFIG = "poll.interval.ms";
    public static final long POLL_INTERVAL_DEFAULT = 1000;

    public SimpleGoogleSheetSourceConfig(final Map<?, ?> props) {
        super(CONFIG_DEF, props);
        log.info("{}", props);
    }

    public Map<String, String> getGoogleCredential() {
        HashMap<String, String> map = new HashMap<>();
        map.put(GOOGLE_SERVICE_TYPE_CONFIG, getString(GOOGLE_SERVICE_TYPE_CONFIG));
        map.put(GOOGLE_SERVICE_PROJECT_ID_CONFIG, getString(GOOGLE_SERVICE_PROJECT_ID_CONFIG));
        map.put(GOOGLE_SERVICE_PRIVATE_KEY_ID_CONFIG, getString(GOOGLE_SERVICE_PRIVATE_KEY_ID_CONFIG));
        map.put(GOOGLE_SERVICE_PRIVATE_KEY_CONFIG, getString(GOOGLE_SERVICE_PRIVATE_KEY_CONFIG));
        map.put(GOOGLE_SERVICE_CLIENT_EMAIL_CONFIG, getString(GOOGLE_SERVICE_CLIENT_EMAIL_CONFIG));
        map.put(GOOGLE_SERVICE_CLIENT_ID_CONFIG, getString(GOOGLE_SERVICE_CLIENT_ID_CONFIG));
        map.put(GOOGLE_SERVICE_AUTH_URI_CONFIG, getString(GOOGLE_SERVICE_AUTH_URI_CONFIG));
        map.put(GOOGLE_SERVICE_TOKEN_URI_CONFIG, getString(GOOGLE_SERVICE_TOKEN_URI_CONFIG));
        map.put(GOOGLE_SERVICE_AUTH_PROVIDER_CONFIG, getString(GOOGLE_SERVICE_AUTH_PROVIDER_CONFIG));
        map.put(GOOGLE_SERVICE_CLIENT_CERT_CONFIG, getString(GOOGLE_SERVICE_CLIENT_CERT_CONFIG));
        map.put(GOOGLE_SERVICE_UNIVERSE_DOMAIN_CONFIG, getString(GOOGLE_SERVICE_UNIVERSE_DOMAIN_CONFIG));
        map.put(GOOGLE_SPREAD_SHEET_ID_CONFIG, getString(GOOGLE_SPREAD_SHEET_ID_CONFIG));

        return map;
    }

    public int getFetchSize() { return getInt(GOOGLE_SPREAD_SHEET_FETCH_SIZE_CONFIG); }

    public String getTopicName() {
        return getString(TOPIC_NAME_CONFIG);
    }

    public long getPollInterval() {
        return getLong(POLL_INTERVAL_CONFIG);
    }
}
