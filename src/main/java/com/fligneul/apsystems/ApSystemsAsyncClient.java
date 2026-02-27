package com.fligneul.apsystems;

import com.fligneul.apsystems.api.ApSystemsCall;
import com.fligneul.apsystems.api.ApSystemsService;
import com.fligneul.apsystems.model.*;
import com.fligneul.apsystems.model.enums.BatchEnergyLevel;
import com.fligneul.apsystems.model.enums.DeviceEnergyLevel;
import com.fligneul.apsystems.model.enums.SystemEnergyLevel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Asynchronous client for interacting with the APsystems OpenAPI.
 * All methods return a {@link CompletableFuture} that completes with the data.
 */
public class ApSystemsAsyncClient {
    private final ApSystemsService service;

    ApSystemsAsyncClient(ApSystemsService service) {
        this.service = service;
    }

    public CompletableFuture<SystemDetails> getSystemDetails(String sid) {
        return new ApSystemsCall<>(service.getSystemDetails(sid)).toCompletableFuture();
    }

    public CompletableFuture<List<EcuInverters>> getInverters(String sid) {
        return new ApSystemsCall<>(service.getInverters(sid)).toCompletableFuture();
    }

    public CompletableFuture<EnergySummary> getEnergySummary(String sid) {
        return new ApSystemsCall<>(service.getEnergySummary(sid)).toCompletableFuture();
    }

    public CompletableFuture<EnergyData> getEnergyInPeriod(String sid, SystemEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getEnergyInPeriod(sid, energyLevel, dateRange)).toCompletableFuture();
    }

    public CompletableFuture<List<String>> getMeters(String sid) {
        return new ApSystemsCall<>(service.getMeters(sid)).toCompletableFuture();
    }

    public CompletableFuture<EnergySummary> getEcuEnergySummary(String sid, String eid) {
        return new ApSystemsCall<>(service.getEcuEnergySummary(sid, eid)).toCompletableFuture();
    }

    public CompletableFuture<EnergyData> getEcuEnergyInPeriod(String sid, String eid, DeviceEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getEcuEnergyInPeriod(sid, eid, energyLevel, dateRange)).toCompletableFuture();
    }

    public CompletableFuture<MeterSummary> getMeterEnergySummary(String sid, String eid) {
        return new ApSystemsCall<>(service.getMeterEnergySummary(sid, eid)).toCompletableFuture();
    }

    public CompletableFuture<EnergyData> getMeterEnergyInPeriod(String sid, String eid, DeviceEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getMeterEnergyInPeriod(sid, eid, energyLevel, dateRange)).toCompletableFuture();
    }

    public CompletableFuture<InverterSummary> getInverterEnergySummary(String sid, String uid) {
        return new ApSystemsCall<>(service.getInverterEnergySummary(sid, uid)).toCompletableFuture();
    }

    public CompletableFuture<EnergyData> getInverterEnergyInPeriod(String sid, String uid, DeviceEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getInverterEnergyInPeriod(sid, uid, energyLevel, dateRange)).toCompletableFuture();
    }

    public CompletableFuture<EnergyData> getInverterBatchEnergy(String sid, String eid, BatchEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getInverterBatchEnergy(sid, eid, energyLevel, dateRange)).toCompletableFuture();
    }

    public CompletableFuture<StorageData> getStorageLatestPower(String sid, String eid) {
        return new ApSystemsCall<>(service.getStorageLatestPower(sid, eid)).toCompletableFuture();
    }

    public CompletableFuture<StorageSummary> getStorageEnergySummary(String sid, String eid) {
        return new ApSystemsCall<>(service.getStorageEnergySummary(sid, eid)).toCompletableFuture();
    }

    public CompletableFuture<EnergyData> getStorageEnergyInPeriod(String sid, String eid, DeviceEnergyLevel energyLevel, String dateRange) {
        return new ApSystemsCall<>(service.getStorageEnergyInPeriod(sid, eid, energyLevel, dateRange)).toCompletableFuture();
    }
}
