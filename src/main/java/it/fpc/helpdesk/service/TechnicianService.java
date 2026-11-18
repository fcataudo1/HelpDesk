
package it.fpc.helpdesk.service;

import it.fpc.helpdesk.dto.TechnicianRequest;
import it.fpc.helpdesk.dto.TechnicianResponse;
import it.fpc.helpdesk.entity.Technician;
import it.fpc.helpdesk.exception.DuplicateEmailException;
import it.fpc.helpdesk.exception.ResourceNotFoundException;
import it.fpc.helpdesk.mapper.TechnicianMapper;
import it.fpc.helpdesk.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final TechnicianMapper technicianMapper;

    @Transactional
    public TechnicianResponse createTechnician(TechnicianRequest request) {

        if (technicianRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(
                    "Email is already registered: " + request.getEmail()
            );
        }

        Technician technician = new Technician();

        technician.setFirstName(request.getFirstName());
        technician.setLastName(request.getLastName());
        technician.setEmail(request.getEmail());
        technician.setDepartment(request.getDepartment());

        Technician savedTechnician = technicianRepository.save(technician);

        return technicianMapper.toResponse(savedTechnician);
    }

    public List<TechnicianResponse> getAllTechnicians() {

        List<Technician> technicians = technicianRepository.findAll();

        return technicianMapper.toResponseList(technicians);
    }

    public TechnicianResponse getTechnicianById(Long id) {

        Technician technician = findTechnicianById(id);

        return technicianMapper.toResponse(technician);
    }

    @Transactional
    public TechnicianResponse updateTechnician(
            Long id, TechnicianRequest request) {

        Technician technician = findTechnicianById(id);

        if (technicianRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {

            throw new DuplicateEmailException(
                    "Email is already registered: " + request.getEmail()
            );
        }

        technician.setFirstName(request.getFirstName());
        technician.setLastName(request.getLastName());
        technician.setEmail(request.getEmail());
        technician.setDepartment(request.getDepartment());

        Technician updatedTechnician = technicianRepository.save(technician);

        return technicianMapper.toResponse(updatedTechnician);
    }

    private Technician findTechnicianById(Long id) {

        return technicianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Technician not found with id: " + id
                ));
    }



}

