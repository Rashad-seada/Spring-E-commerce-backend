package com.example.auth_service.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

class EmailConfigration {

    @Value("\${spring.mail.username}")
    lateinit var emailUsername : String;

    @Value("\${spring.mail.password}")
    lateinit var password : String;

    @Bean
    fun javaMailSender() : JavaMailSender {
        val mailSender = JavaMailSenderImpl()

        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = emailUsername
        mailSender.password = password

        val props = mailSender.javaMailProperties
        props.put("mail.transport.protocol","smtp")
        props.put("mail.smtp.auth","true")
        props.put("mail.smtp.starttls.enable","true")
        props.put("mail.smtp.auth","true")
        props.put("mail.debug","true")

        return mailSender
    }


}