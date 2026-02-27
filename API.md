# API Reference

The SDK provides two clients depending on your concurrency model:
- **`ApSystemsClient`**: Synchronous methods that return data directly.
- **`ApSystemsAsyncClient`**: Asynchronous methods that return a `CompletableFuture<T>`.

Both clients share the same method signatures for the available endpoints.

## Available Endpoints

| Method | Description | Return Data |
| :--- | :--- | :--- |
| `getSystemDetails(sid)` | Returns general system info | `SystemDetails` |
| `getInverters(sid)` | List of all inverters/ECUs | `List<EcuInverters>` |
| `getEnergySummary(sid)` | Accumulative energy summary | `EnergySummary` |
| `getEnergyInPeriod(sid, level, range)` | Historical energy data | `EnergyData` |
| `getMeters(sid)` | List of meter IDs | `List<String>` |

## ECU-level API

| Method | Description | Return Data |
| :--- | :--- | :--- |
| `getEcuEnergySummary(sid, eid)` | Energy summary for a specific ECU | `EnergySummary` |
| `getEcuEnergyInPeriod(sid, eid, level, range)` | Historical energy for a specific ECU | `EnergyData` |

## Meter-level API

| Method | Description | Return Data |
| :--- | :--- | :--- |
| `getMeterEnergySummary(sid, eid)` | Detailed meter energy (consumed, produced, etc.) | `MeterSummary` |
| `getMeterEnergyInPeriod(sid, eid, level, range)` | Historical meter data | `EnergyData` |

## Inverter-level API

| Method | Description | Return Data |
| :--- | :--- | :--- |
| `getInverterEnergySummary(sid, uid)` | Energy summary per channel for an inverter | `InverterSummary` |
| `getInverterEnergyInPeriod(sid, uid, level, range)` | Historical data for a specific inverter | `EnergyData` |
| `getInverterBatchEnergy(sid, eid, level, range)` | Batch energy data for all inverters under an ECU | `EnergyData` |

## Storage-level API

| Method | Description | Return Data |
| :--- | :--- | :--- |
| `getStorageLatestPower(sid, eid)` | Current status of storage (SOC, charge/discharge power) | `StorageData` |
| `getStorageEnergySummary(sid, eid)` | Energy summary for storage | `StorageSummary` |
| `getStorageEnergyInPeriod(sid, eid, level, range)` | Historical data for storage | `EnergyData` |

---

## Parameters Reference

### `energy_level` Enums

| Enum | Valid Values | Used In |
| :--- | :--- | :--- |
| `SystemEnergyLevel` | `HOURLY`, `DAILY`, `MONTHLY`, `YEARLY` | System API |
| `DeviceEnergyLevel` | `MINUTELY`, `HOURLY`, `DAILY`, `MONTHLY`, `YEARLY` | ECU, Meter, Inverter, Storage APIs |
| `BatchEnergyLevel` | `POWER`, `ENERGY` | Inverter Batch API |

### `date_range` Format
- **Minutely**: `yyyy-MM-dd`
- **Hourly**: `yyyy-MM-dd`
- **Daily**: `yyyy-MM`
- **Monthly**: `yyyy`
- **Yearly**: (Not required)

## The `EnergyData` Wrapper
The `EnergyData` object automatically parses the different response formats:
- **Daily/Hourly/Monthly/Yearly**: Access values via `.getValues()`.
- **Minutely**: Access timestamps via `.getTimes()`, power via `.getPower()`, and cumulative energy via `.getToday()`.
- **Per Channel**: Access specific channel data (e1, e2, dc_p1, etc.) via `.getChannelData()`.
