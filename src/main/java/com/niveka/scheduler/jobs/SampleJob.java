package com.niveka.scheduler.jobs;

/**
 *  Created by Nivek@lara on 01/06/2019.
 */

import com.niveka.config.JobConfiguration;
import com.niveka.config.QuartzConfiguration;
import com.niveka.domain.CompteRenduSuivi;
import com.niveka.domain.Prospect;
import com.niveka.domain.User;
import com.niveka.payload.Notify;
import com.niveka.payload.NotifyResponse;
import com.niveka.service.AndroidPushNotificationsService;
import com.niveka.service.CompteRenduSuiviService;
import com.niveka.service.ProspectService;
import com.niveka.service.UserService;
import com.niveka.service.dto.CompteRenduSuiviDTO;
import com.niveka.web.rest.util.Utils;
import org.codehaus.jettison.json.JSONException;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * This is the job class that will be triggered based on the job configuration
 * defined in {@link JobConfiguration}
 *
 * @author niveka@lara
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SampleJob extends QuartzJobBean implements InitializingBean{
    private static Logger log = LoggerFactory.getLogger(SampleJob.class);
    private CompteRenduSuiviService compteRenduSuiviService;
    private UserService userService;
    private ProspectService prospectService;
    private AndroidPushNotificationsService androidPushNotificationsService;

    private ApplicationContext applicationContext;

/*    public void setCompteRenduSuiviService(CompteRenduSuiviService crs){
        this.compteRenduSuiviService = crs;
    }*/

    /**
     * This method is called by Spring since we set the
     * {@link SchedulerFactoryBean#setApplicationContextSchedulerContextKey(String)}
     * in {@link QuartzConfiguration}
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        userService = applicationContext.getBean(UserService.class);
        compteRenduSuiviService = applicationContext.getBean(CompteRenduSuiviService.class);
        androidPushNotificationsService = applicationContext.getBean(AndroidPushNotificationsService.class);
        prospectService = applicationContext.getBean(ProspectService.class);
    }

    /**
     * This is the method that will be executed each time the trigger is fired.
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<CompteRenduSuivi> notHonores = compteRenduSuiviService.findAll(false,false);
        notHonores.forEach(this::__);
    }

    private void __(CompteRenduSuivi compteRenduSuivi){
        String jodadate = compteRenduSuivi.getDateProchaineRdv();
        if (jodadate==null || jodadate.equals("")){
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = sdf.parse(jodadate);
        } catch (ParseException e) {
            return;
        }
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE,61);
        Date newDate = calendar.getTime();
        log.debug("DATE = {}",sdf.format(date));
        log.debug("NOW = {}",sdf.format(now));
        log.debug("NEW-NOW = {}",sdf.format(newDate));
        if (date.before(newDate) && date.after(now)){
            User commercial = userService.findOne(compteRenduSuivi.getUserId());
            Prospect prospect = prospectService._findOne(compteRenduSuivi.getProspectId());
            if (commercial!=null){
                SimpleDateFormat s0 = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat s1 = new SimpleDateFormat("HH:mm");
                Notify notify = new Notify();
                notify.setType(Utils.TYPE_NOTIFY.ALARM);
                notify.setTitle("Rappel du RDV");
                notify.setSenderName("FIRED-AUTO-ALARMER");
                notify.setUniquid(UUID.randomUUID().toString());
                String content = "Nous vous rappelons que vous avez programmé un rendez-vous avec "+prospect.getNom()+"" +
                    " le "+s0.format(date)+" à "+s1.format(date)+".";
                notify.setContent(content);
                if (commercial.getAndroidFcmToken()!=null && commercial.getAndroidFcmToken().equals("")){
                    try {
                        String[] ids = {commercial.getAndroidFcmToken()};
                        NotifyResponse response = androidPushNotificationsService.notifyUsers(notify,ids,false);
                        if (response.getStatusCode()==200){
                            if (compteRenduSuivi.isFirstAlarm()){
                                compteRenduSuivi.setSecondAlarm(true);
                                CompteRenduSuiviDTO dto = new CompteRenduSuiviDTO();
                                BeanUtils.copyProperties(compteRenduSuivi,dto);
                                compteRenduSuiviService.save(dto);
                            }else{
                                compteRenduSuivi.setFirstAlarm(true);
                                CompteRenduSuiviDTO dto = new CompteRenduSuiviDTO();
                                BeanUtils.copyProperties(compteRenduSuivi,dto);
                                compteRenduSuiviService.save(dto);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
