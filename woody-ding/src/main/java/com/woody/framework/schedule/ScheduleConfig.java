package com.woody.framework.schedule;

import com.woody.framework.utils.ConfigUitl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    private static Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    //频率
    private static final String frequence = "schedule_test_read_frequence";
    private static AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                if (isRunning.get()) {
                    return;
                }
                isRunning.set(true);
                try {
                    process();
                } catch (Exception e) {
                    logger.error("", e);
                } finally {
                    isRunning.set(false);
                }
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {

                Calendar nextExecutionTime = new GregorianCalendar();
                Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                Integer readFrequence = null;
                try {
                    readFrequence = Integer.valueOf(ConfigUitl.getValue(frequence));
                } catch (Exception e) {
                    logger.error("Schedule Frequence not Configure right", frequence, e);
                    return null;
                }
                nextExecutionTime.add(Calendar.SECOND, readFrequence);
                return nextExecutionTime.getTime();
            }
        });
    }

    /**
     * 处理信息
     */
    public void process() {
        logger.info("Schdule Task beginning....................");

        System.out.println("Hi+++" + new Date(System.currentTimeMillis()));
    }
}
