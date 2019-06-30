package com.niveka.config;

import com.niveka.scheduler.constants.SchedulerConstants;
import com.niveka.scheduler.jobs.SampleJob;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 *  Created by Nivek@lara on 01/06/2019.
 */

@Configuration
public class JobConfiguration {

    private SchedulerFactoryBean schedulerFactoryBean;

    public JobConfiguration(SchedulerFactoryBean s){
        this.schedulerFactoryBean = s;
    }

    @PostConstruct
    private void initialize() throws Exception {
        schedulerFactoryBean.getScheduler().addJob(sampleJobDetail(), true, true);
        if (!schedulerFactoryBean.getScheduler().checkExists(new TriggerKey(
            SchedulerConstants.CHECK_SUIVI_DATE_RDV_JOB_POLLING_TRIGGER_KEY,
            SchedulerConstants.CHECK_SUIVI_DATE_RDV_JOB_POLLING_GROUP))) {
            schedulerFactoryBean.getScheduler().scheduleJob(sampleJobTrigger());
        }
    }
    /**
     * <p>
     * The job is configured here where we provide the job class to be run on
     * each invocation. We give the job a name and a value so that we can
     * provide the trigger to it on our method {@link #sampleJobTrigger()}
     * </p>
     *
     * @return an instance of {@link JobDetail}
     */
    private static JobDetail sampleJobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(
            new JobKey(SchedulerConstants.CHECK_SUIVI_DATE_RDV_JOB_POLLING_JOB_KEY,
                SchedulerConstants.CHECK_SUIVI_DATE_RDV_JOB_POLLING_GROUP));
        jobDetail.setJobClass(SampleJob.class);
        jobDetail.setDurability(true);
        return jobDetail;
    }
    /**
     * <p>
     * This method will define the frequency with which we will be running the
     * scheduled job which in this instance is every minute three seconds after
     * the start up.
     * </p>
     *
     * @return an instance of {@link Trigger}
     */
    private static Trigger sampleJobTrigger() {
        return newTrigger().forJob(sampleJobDetail())
            .withIdentity(SchedulerConstants.CHECK_SUIVI_DATE_RDV_JOB_POLLING_TRIGGER_KEY,
                SchedulerConstants.CHECK_SUIVI_DATE_RDV_JOB_POLLING_GROUP)
            .withPriority(50)
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(30*60))//execution tou les 5minutes
            .startAt(Date.from(LocalDateTime.now().plusSeconds(3).atZone(ZoneId.systemDefault()).toInstant()))
            .build();
    }

}
