package com.hospital.service;

import com.hospital.dto.PrescriptionRequest;
import com.hospital.dto.PrescriptionResponse;

public interface PrescriptionService {

    PrescriptionResponse addPrescription(PrescriptionRequest request);
}