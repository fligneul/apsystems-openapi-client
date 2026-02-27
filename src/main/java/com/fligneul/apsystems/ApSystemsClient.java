package com.fligneul.apsystems;

import com.fligneul.apsystems.api.ApSystemsCall;
import com.fligneul.apsystems.api.ApSystemsService;
import com.fligneul.apsystems.model.*;
import com.fligneul.apsystems.model.enums.BatchEnergyLevel;
import com.fligneul.apsystems.model.enums.DeviceEnergyLevel;
import com.fligneul.apsystems.model.enums.SystemEnergyLevel;

import java.util.List;

/**
 * Synchronous client for interacting with the APsystems OpenAPI.
 * All methods block until the request is complete and return the data directly.
 */
public class ApSystemsClient {
    private final ApSystemsService service;

    ApSystemsClient(ApSystemsService service) {
        this.service = service;
    }

    public SystemDetails getSystemDetails(String sid) {
        return new ApSystemsCall<>(service.getSystemDetails(sid)).execute();
    }

    public List<EcuInverters> getInverters(String sid) {
        return new ApSystemsCall<>(service.getInverters(sid)).execute();
    }

    public EnergySummary getEnergySummary(String sid) {
        return new ApSystemsCall<>(service.getEnergySummary(sid)).execute();
    }

    public EnergyData getEnergyInPeriod(String sid, SystemEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getEnergyInPeriod(sid, energyLevel, dateRange)).execute();
    }

    public List<String> getMeters(String sid) {
        return new ApSystemsCall<>(service.getMeters(sid)).execute();
    }

    public EnergySummary getEcuEnergySummary(String sid, String eid) {
        return new ApSystemsCall<>(service.getEcuEnergySummary(sid, eid)).execute();
    }

    public EnergyData getEcuEnergyInPeriod(String sid, String eid, DeviceEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getEcuEnergyInPeriod(sid, eid, energyLevel, dateRange)).execute();
    }

    public MeterSummary getMeterEnergySummary(String sid, String eid) {
        return new ApSystemsCall<>(service.getMeterEnergySummary(sid, eid)).execute();
    }

    public EnergyData getMeterEnergyInPeriod(String sid, String eid, DeviceEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getMeterEnergyInPeriod(sid, eid, energyLevel, dateRange)).execute();
    }

    public InverterSummary getInverterEnergySummary(String sid, String uid) {
        return new ApSystemsCall<>(service.getInverterEnergySummary(sid, uid)).execute();
    }

    public EnergyData getInverterEnergyInPeriod(String sid, String uid, DeviceEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getInverterEnergyInPeriod(sid, uid, energyLevel, dateRange)).execute();
    }

    public EnergyData getInverterBatchEnergy(String sid, String eid, BatchEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getInverterBatchEnergy(sid, eid, energyLevel, dateRange)).execute();
    }

    public StorageData getStorageLatestPower(String sid, String eid) {
        return new ApSystemsCall<>(service.getStorageLatestPower(sid, eid)).execute();
    }

    public StorageSummary getStorageEnergySummary(String sid, String eid) {
        return new ApSystemsCall<>(service.getStorageEnergySummary(sid, eid)).execute();
    }

    public EnergyData getStorageEnergyInPeriod(String sid, String eid, DeviceEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getStorageEnergyInPeriod(sid, eid, energyLevel, dateRange)).execute();
    }
}
