package io.github.brewagebear;

import com.google.api.services.sheets.v4.Sheets;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.SystemTime;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.brewagebear.SimpleGoogleSheetSourceConfig.TASK_ID;

@Slf4j
public class SimpleGoogleSheetTask extends SourceTask {
    private final static String POSITION_NAME = "position";
    private final static int FETCH_SIZE = 5;
    private final static long DEFAULT_WAIT_MS = 500;
    private final static String SPREADSHEET_ID = "1U_Q4qWeIzrXezL8CERnfxwNNxfNZlyyVwG0mujTKJZY";
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Time time;
    private long lastProcessedOffset;
    private SimpleGoogleSheetSourceConfig config;
    private long lastPollMs;
    private String taskId;
    private Sheets sheets;
    private int lastRow = 2;
    private String range;

    public SimpleGoogleSheetTask() {
        this.time = new SystemTime();
        this.lastPollMs = time.milliseconds();
    }

    @Override
    public void start(Map<String, String> props) {
        log.info("Starting Google Sheet task with config: {}", props);
        config = new SimpleGoogleSheetSourceConfig(props);
        GoogleSheetService googleSheetService = new GoogleSheetService();
        taskId = props.get("task.id");
        range = getNextRange(2);

        sheets = googleSheetService.connect();
        running.set(true);

        // get offsets for a specific task id
        final Map<String, Object> offset =
                context.offsetStorageReader().offset(Collections.singletonMap(TASK_ID, taskId));

        if (offset != null) {
            final Long currentOffset = (Long) offset.get(POSITION_NAME);
            if (currentOffset != null) {
                lastProcessedOffset = currentOffset;
            } else {
                // no position found
                lastProcessedOffset = 0L;
            }
        } else {
            // first time there is no offset.
            lastProcessedOffset = 0L;
        }
    }

    @Override
    public List<SourceRecord> poll() {
        log.debug("Polling for new data");

        final long timeSinceLastPollMs = time.milliseconds() - lastPollMs;

        if (timeSinceLastPollMs < config.getPollInterval()) {
            log.debug("Sleep, time since last poll = {}", timeSinceLastPollMs);
            time.sleep(DEFAULT_WAIT_MS);
            return null;
        }

        if (!running.get()) {
            return null;
        }

        List<SourceRecord> records = new ArrayList<>();

        try {
            List<List<String>> data = new GoogleSheetService().getResponse(SPREADSHEET_ID, sheets, range);

            if (data != null && !data.isEmpty()) {
                for (List<String> row : data) {
                    records.add(new SourceRecord(Collections.singletonMap(TASK_ID, taskId),
                            Collections.singletonMap(POSITION_NAME, lastProcessedOffset),
                            config.getTopicName(),
                            Schema.STRING_SCHEMA, row.get(0),
                            Schema.STRING_SCHEMA, row.get(1)
                    ));

                    lastProcessedOffset += 1;
                }

                log.info("polling data {}", records);
                range = getNextRange(lastRow);
                log.info("next range {}", range);
            } else {
                stop();
            }

            lastPollMs = time.milliseconds();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records;
    }

    private String getNextRange(int lastRange) {
        updateLastRow();
        int endRow = lastRange + SimpleGoogleSheetTask.FETCH_SIZE - 1;
        return "A" + (lastRange) + ":B" + (endRow);
    }

    private void updateLastRow() {
        this.lastRow += FETCH_SIZE;
    }

    @Override
    public void stop() {
        running.set(false);
    }

    @Override
    public String version() {
        return "1.0";
    }
}
