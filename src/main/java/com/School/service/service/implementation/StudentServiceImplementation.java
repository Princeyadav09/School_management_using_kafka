package com.School.service.service.implementation;

import com.School.service.modal.CheckInOut;
import com.School.service.modal.Response.KafkaResponse;
import com.School.service.modal.Student;
import com.School.service.repository.CheckInOutRepository;
import com.School.service.repository.StudentRepository;
import com.School.service.service.KafkaService;
import com.School.service.service.StudentService;
import com.School.service.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class StudentServiceImplementation implements StudentService {
    public static final Logger Log = LoggerFactory.getLogger(StudentServiceImplementation.class);

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CheckInOutRepository checkInOutRepository;

    @Autowired
    KafkaService kafkaService;

    @Autowired
    KafkaResponse kafkaResponse;

    @Override
    public ResponseEntity<Object> createStudent(){
        try{

            String name = "prince";
            Student st = new Student();
            st.setName(name);
            st.setIsActive(true);
            st.setFine(0L);
            Student student = studentRepository.save(st);
            return ResponseUtils.getResponseEntity("Student saved Successfully ",HttpStatus.OK);

        } catch (Exception ex) {
            return ResponseUtils.getResponseEntity("Exception occurred ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> checkIn(Long studentId){
        try {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            Student student;
            if (optionalStudent.isPresent()) {
                student = optionalStudent.get();
            } else {
                return ResponseUtils.getResponseEntity("User not found ",HttpStatus.BAD_REQUEST);
            }
            CheckInOut checkInOut = student.getCheckInOut();

            if (checkInOut == null) {
                checkInOut = new CheckInOut();
            }
            ZonedDateTime checkInDate = checkInOut.getCheckIn();

            if (isAlreadyCheck(checkInDate)) {
                return ResponseUtils.getResponseEntity("You are allowed only once a day !",HttpStatus.BAD_REQUEST);
            }

            ZonedDateTime currentTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));

            KafkaResponse message = new KafkaResponse();
            message.setName(student.getName());
            message.setTime(currentTime);
            message.setType("student");

            if (beforeSchoolHours(currentTime)) {
                checkInOut.setCheckIn(currentTime);
                checkInOut.setStudent(student);
                checkInOut.setIsActive(true);
                checkInOutRepository.save(checkInOut);
                message.setMessage("Entry on time.");
                kafkaService.sendToConsumer(message);
                return ResponseUtils.getResponseEntity("Entry at "+currentTime,HttpStatus.OK);
            } else {
                Long fine = student.getFine();
                student.setFine(fine + 500);
                checkInOut.setCheckIn(currentTime);
                checkInOut.setStudent(student);
                checkInOut.setIsActive(true);
                checkInOutRepository.save(checkInOut);
                message.setMessage("Late Entry with fine. ");
                kafkaService.sendToConsumer(message);
                return ResponseUtils.getResponseEntity("Entry with fine 500 ! at time "+currentTime,HttpStatus.OK);
            }
        } catch (Exception ex){
            return ResponseUtils.getResponseEntity("Exception occurred ",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Object> checkOut(Long studentId){
        try {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            Student student;
            if (optionalStudent.isPresent()) {
                student = optionalStudent.get();
            } else {
                return ResponseUtils.getResponseEntity("User not found ",HttpStatus.BAD_REQUEST);
            }
            CheckInOut checkInOut = student.getCheckInOut();

            if (checkInOut == null) {
                return ResponseUtils.getResponseEntity("You are not checked in yet !",HttpStatus.BAD_REQUEST);
            }
            if (checkInOut.getCheckOut() != null) {
                return ResponseUtils.getResponseEntity("You are already checkout ",HttpStatus.BAD_REQUEST);
            }
            ZonedDateTime currentTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
            KafkaResponse message = new KafkaResponse();
            message.setName(student.getName());
            message.setTime(currentTime);
            message.setType("student");
            checkInOut.setCheckOut(currentTime);
            checkInOutRepository.save(checkInOut);
            message.setMessage("Check out successfully");
            kafkaService.sendToConsumer(message);
            return ResponseUtils.getResponseEntity("Exit at "+ currentTime,HttpStatus.OK);

        } catch (Exception ex){
            return ResponseUtils.getResponseEntity("Exception occurred ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean beforeSchoolHours(ZonedDateTime dateTime) {
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        return (hour == 10 && minute == 0) || (hour < 10);
    }

    private boolean isAlreadyCheck(ZonedDateTime checkInDate){
        if(checkInDate==null)return false;
        ZonedDateTime currentTime =  ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        LocalDate currentDate = currentTime.toLocalDate();
        LocalDate checkInLocalDate = checkInDate.toLocalDate();
        return currentDate.isEqual(checkInLocalDate);
    }
}
