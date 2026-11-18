
package it.fpc.helpdesk.mapper;

import it.fpc.helpdesk.dto.TechnicianResponse;
import it.fpc.helpdesk.entity.Technician;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TechnicianMapper {

    public TechnicianResponse toResponse(Technician technician) {

        return new TechnicianResponse(
                technician.getId(),
                technician.getFirstName(),
                technician.getLastName(),
                technician.getEmail(),
                technician.getDepartment()
        );
    }

    public List<TechnicianResponse> toResponseList(
            List<Technician> technicians) {

        List<TechnicianResponse> response = new ArrayList<>();

        for (Technician technician : technicians) {
            response.add(toResponse(technician));
        }

        return response;
    }
}
