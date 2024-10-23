package com.example.auth_service.service

import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service


@Service
class EmailService  @Autowired constructor(val javaMailSender: JavaMailSender){

    fun sendVerificationEmail(to : String , subject : String,text : String ) {
        val message = javaMailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message,true)

        messageHelper.setTo(to)
        messageHelper.setSubject(subject)
        messageHelper.setText(text,true)

        javaMailSender.send(message)
    }

}