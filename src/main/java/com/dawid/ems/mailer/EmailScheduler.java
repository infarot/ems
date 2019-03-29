package com.dawid.ems.mailer;

import com.dawid.ems.service.QuiltingService;
import com.dawid.ems.service.ShiftProductionService;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EmailScheduler {

    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;
    private final QuiltingService quiltingService;
    private final ShiftProductionService shiftProductionService;


    private static final Logger log = LoggerFactory.getLogger(EmailScheduler.class);
    @Value("#{'${mailing.list}'.split(',')}")
    private List<String> mailingList;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Autowired
    public EmailScheduler(EmailSender emailSender, TemplateEngine templateEngine, QuiltingService quiltingService, ShiftProductionService shiftProductionService) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.quiltingService = quiltingService;
        this.shiftProductionService = shiftProductionService;
    }


    @Scheduled(cron="0 15 14,22 * * MON-FRI", zone="Europe/Warsaw")
    public void send() {
        log.info("The time is {}", LocalDateTime.now().format(formatter));
        Context context = new Context();
        context.setVariable("header", "MCK9999 - daily report");
        context.setVariable("title", "Production details:");
        context.setVariable("shift", "Shift: " + (shiftProductionService.getAll().get(shiftProductionService.getAll().size() - 1).getShift()));
        context.setVariable("result", "Result: " + Precision.round(shiftProductionService.getAll().get(shiftProductionService.getAll().size() - 1).getResult(), 2));
        context.setVariable("perSts", "Per seamstress: " + Precision.round(shiftProductionService.getAll().get(shiftProductionService.getAll().size() - 1).getPerSeamstress(), 2));
        context.setVariable("quiltingHeader", "Quilting details:");
        context.setVariable("operator", "Operator: " + quiltingService.getAll().get(quiltingService.getAll().size() - 1).getOperator().getName() +" "+quiltingService.getAll().get(quiltingService.getAll().size() - 1).getOperator().getLastName());
        context.setVariable("qm", "Quilted meters: " + Precision.round(quiltingService.getAll().get(quiltingService.getAll().size() - 1).getQuilterStatistics().getTotalLmt(), 2));
        context.setVariable("loss", "Total loss: " + Precision.round(quiltingService.getAll().get(quiltingService.getAll().size() - 1).getQuilterStatistics().getTotalLoss() * 100, 2) + "%");
        context.setVariable("description", "Information actual on " + LocalDateTime.now().format(formatter));
        String body = templateEngine.process("email", context);
        mailingList.forEach(a -> emailSender.sendEmail(a, "Production details - " + LocalDateTime.now().format(formatter), body));
    }


}
