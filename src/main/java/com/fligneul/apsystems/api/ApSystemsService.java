package com.fligneul.apsystems.api;

import com.fligneul.apsystems.model.*;
import com.fligneul.apsystems.model.enums.BatchEnergyLevel;
import com.fligneul.apsystems.model.enums.DeviceEnergyLevel;
import com.fligneul.apsystems.model.enums.SystemEnergyLevel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Retrofit service interface for APsystems OpenAPI endpoints.
 */
public interface ApSystemsService {

    /**
     * Returns the details of the system which you searched for.
     *
     * @param sid The unique identity id of the system.
     * @return A call to get SystemDetails.
     */
    @GET("user/api/v2/systems/details/{sid}")
    Call<ApiResponse<SystemDetails>> getSystemDetails(@Path("sid") String sid);

    /**
     * Returns all the devices of a system you searched for.
     *
     * @param sid The unique identity id of the system.
     * @return A call to get a list of EcuInverters.
     */
    @GET("user/api/v2/systems/inverters/{sid}")
    Call<ApiResponse<List<EcuInverters>>> getInverters(@Path("sid") String sid);

    /**
     * Returns the accumulative energy reported by inverters of a particular system.
     *
     * @param sid The unique identity id of the system.
     * @return A call to get EnergySummary.
     */
    @GET("user/api/v2/systems/summary/{sid}")
    Call<ApiResponse<EnergySummary>> getEnergySummary(@Path("sid") String sid);

    /**
     * Returns historical energy data for a particular system.
     *
     * @param sid         The unique identity id of the system.
     * @param energyLevel The energy level (hourly, daily, monthly, yearly).
     * @param dateRange   The data range to calculate (format varies by level).
     * @return A call to get EnergyData.
     */
    @GET("user/api/v2/systems/energy/{sid}")
    Call<ApiResponse<EnergyData>> getEnergyInPeriod(
            @Path("sid") String sid,
            @Query("energy_level") SystemEnergyLevel energyLevel,
            @Query("date_range") String dateRange
    );

    /**
     * Returns all the meters of a system you searched for.
     *
     * @param sid The unique identity id of the system.
     * @return A call to get a list of meter IDs.
     */
    @GET("user/api/v2/systems/meters/{sid}")
    Call<ApiResponse<List<String>>> getMeters(@Path("sid") String sid);

    // ECU-level Data API

    /**
     * Returns the accumulative energy reported by inverters below a particular ECU.
     *
     * @param sid The identity id of the system.
     * @param eid The identity id of ECU.
     * @return A call to get EnergySummary.
     */
    @GET("user/api/v2/systems/{sid}/devices/ecu/summary/{eid}")
    Call<ApiResponse<EnergySummary>> getEcuEnergySummary(
            @Path("sid") String sid,
            @Path("eid") String eid
    );

    /**
     * Returns historical energy data for a particular ECU.
     *
     * @param sid         The identity id of the system.
     * @param eid         The identity id of ECU.
     * @param energyLevel The energy level.
     * @param dateRange   The data range.
     * @return A call to get EnergyData.
     */
    @GET("user/api/v2/systems/{sid}/devices/ecu/energy/{eid}")
    Call<ApiResponse<EnergyData>> getEcuEnergyInPeriod(
            @Path("sid") String sid,
            @Path("eid") String eid,
            @Query("energy_level") DeviceEnergyLevel energyLevel,
            @Query("date_range") String dateRange
    );

    // Meter-level Data API

    /**
     * Returns the accumulative energy reported by a Meter ECU.
     *
     * @param sid The identity id of the system.
     * @param eid The identity id of Meter ECU.
     * @return A call to get MeterSummary.
     */
    @GET("user/api/v2/systems/{sid}/devices/meter/summary/{eid}")
    Call<ApiResponse<MeterSummary>> getMeterEnergySummary(
            @Path("sid") String sid,
            @Path("eid") String eid
    );

    /**
     * Returns historical energy data for a particular Meter ECU.
     *
     * @param sid         The identity id of the system.
     * @param eid         The identity id of Meter ECU.
     * @param energyLevel The energy level.
     * @param dateRange   The data range.
     * @return A call to get EnergyData.
     */
    @GET("user/api/v2/systems/{sid}/devices/meter/period/{eid}")
    Call<ApiResponse<EnergyData>> getMeterEnergyInPeriod(
            @Path("sid") String sid,
            @Path("eid") String eid,
            @Query("energy_level") DeviceEnergyLevel energyLevel,
            @Query("date_range") String dateRange
    );

    // Inverter-level Data API

    /**
     * Returns the energy summary per channel for a particular inverter.
     *
     * @param sid The unique identity id of the system.
     * @param uid The identity id of inverter.
     * @return A call to get InverterSummary.
     */
    @GET("user/api/v2/systems/{sid}/devices/inverter/summary/{uid}")
    Call<ApiResponse<InverterSummary>> getInverterEnergySummary(
            @Path("sid") String sid,
            @Path("uid") String uid
    );

    /**
     * Returns historical energy data for a particular inverter.
     *
     * @param sid         The unique identity id of the system.
     * @param uid         The identity id of inverter.
     * @param energyLevel The energy level.
     * @param dateRange   The data range.
     * @return A call to get EnergyData.
     */
    @GET("user/api/v2/systems/{sid}/devices/inverter/energy/{uid}")
    Call<ApiResponse<EnergyData>> getInverterEnergyInPeriod(
            @Path("sid") String sid,
            @Path("uid") String uid,
            @Query("energy_level") DeviceEnergyLevel energyLevel,
            @Query("date_range") String dateRange
    );

    /**
     * Returns batch energy data for all inverters below a particular ECU.
     *
     * @param sid         The unique identity id of the system.
     * @param eid         The identity id of ECU.
     * @param energyLevel The energy level (power or energy).
     * @param dateRange   The data range.
     * @return A call to get EnergyData.
     */
    @GET("user/api/v2/systems/{sid}/devices/inverter/batch/energy/{eid}")
    Call<ApiResponse<EnergyData>> getInverterBatchEnergy(
            @Path("sid") String sid,
            @Path("eid") String eid,
            @Query("energy_level") BatchEnergyLevel energyLevel,
            @Query("date_range") String dateRange
    );

    // Storage-level Data API

    /**
     * Returns the latest status of a Storage ECU.
     *
     * @param sid The identity id of the system.
     * @param eid The identity id of Storage ECU.
     * @return A call to get StorageData.
     */
    @GET("installer/api/v2/systems/{sid}/devices/storage/latest/{eid}")
    Call<ApiResponse<StorageData>> getStorageLatestPower(
            @Path("sid") String sid,
            @Path("eid") String eid
    );

    /**
     * Returns the accumulative energy reported by a Storage ECU.
     *
     * @param sid The identity id of the system.
     * @param eid The identity id of Storage ECU.
     * @return A call to get StorageSummary.
     */
    @GET("installer/api/v2/systems/{sid}/devices/storage/summary/{eid}")
    Call<ApiResponse<StorageSummary>> getStorageEnergySummary(
            @Path("sid") String sid,
            @Path("eid") String eid
    );

    /**
     * Returns historical energy data for a particular Storage ECU.
     *
     * @param sid         The identity id of the system.
     * @param eid         The identity id of Storage ECU.
     * @param energyLevel The energy level.
     * @param dateRange   The data range.
     * @return A call to get EnergyData.
     */
    @GET("installer/api/v2/systems/{sid}/devices/storage/period/{eid}")
    Call<ApiResponse<EnergyData>> getStorageEnergyInPeriod(
            @Path("sid") String sid,
            @Path("eid") String eid,
            @Query("energy_level") DeviceEnergyLevel energyLevel,
            @Query("date_range") String dateRange
    );
}
