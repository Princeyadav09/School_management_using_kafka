package com.School.service.service.implementation;

import com.School.service.modal.CheckInOut;
import com.School.service.modal.Response.KafkaResponse;
import com.School.service.modal.Staff;
import com.School.service.repository.CheckInOutRepository;
import com.School.service.repository.StaffRepository;
import com.School.service.service.KafkaService;
import com.School.service.service.StaffService;
import com.School.service.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImplementation implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    CheckInOutRepository checkInOutRepository;

    @Autowired
    KafkaService kafkaService;

    @Autowired
    KafkaResponse kafkaResponse;


    @Override
    public ResponseEntity<Object> staffCreation(){
        try{

            String name = "Saquib";
            Staff staff = new Staff();
            staff.setName(name);
            staff.setIsActive(true);
            Staff st = staffRepository.save(staff);
            return ResponseUtils.getResponseEntity("Staff saved Successfully ",HttpStatus.OK);

        } catch (Exception ex) {
            return ResponseUtils.getResponseEntity("Exception occurred ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> checkIn(Long staffId){
        try {

            Optional<Staff> optionalStaff = staffRepository.findById(staffId);
            Staff staff;
            if (optionalStaff.isPresent()) {
                staff = optionalStaff.get();
            } else {
                return ResponseUtils.getResponseEntity("Staff not found ",HttpStatus.BAD_REQUEST);
            }
            List<CheckInOut> list = staff.getCheckInOuts();

            for(int i=0;i< list.size();i++){
                if(list.get(i).getCheckOut()==null){
                    return ResponseUtils.getResponseEntity("You are already checked in ",HttpStatus.BAD_REQUEST);
                }
            }
            CheckInOut checkInOut= new CheckInOut();

            ZonedDateTime currentTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));

            KafkaResponse message = new KafkaResponse();
            message.setName(staff.getName());
            message.setTime(currentTime);
            message.setType("staff");

            checkInOut.setCheckIn(currentTime);
            checkInOut.setStaff(staff);
            checkInOut.setIsActive(true);
            checkInOutRepository.save(checkInOut);
            message.setMessage("staff check in.");
            kafkaService.sendToConsumer(message);
            return ResponseUtils.getResponseEntity("Staff Entry at "+currentTime,HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseUtils.getResponseEntity("Exception occurred ",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Object> checkOut(Long staffId){
        try {
            Optional<Staff> optionalStaff = staffRepository.findById(staffId);
            Staff staff;
            if (optionalStaff.isPresent()) {
                staff = optionalStaff.get();
            } else {
                return ResponseUtils.getResponseEntity("Staff not found ",HttpStatus.BAD_REQUEST);
            }
            List<CheckInOut> list = staff.getCheckInOuts();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCheckOut() == null) {
                    CheckInOut checkInOut = list.get(i);
                    ZonedDateTime currentTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));

                    KafkaResponse message = new KafkaResponse();
                    message.setName(staff.getName());
                    message.setTime(currentTime);
                    message.setType("staff");

                    checkInOut.setCheckOut(currentTime);
                    checkInOut.setStaff(staff);
                    checkInOut.setIsActive(false);
                    checkInOutRepository.save(checkInOut);
                    message.setMessage("staff check out.");
                    kafkaService.sendToConsumer(message);
                    return ResponseUtils.getResponseEntity("Exit at "+currentTime,HttpStatus.OK);
                }
            }
            return ResponseUtils.getResponseEntity("You have not checked in yet ! ",HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return ResponseUtils.getResponseEntity("Exception occurred ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
